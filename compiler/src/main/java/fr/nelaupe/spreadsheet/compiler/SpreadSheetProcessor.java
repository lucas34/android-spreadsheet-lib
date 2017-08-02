package fr.nelaupe.spreadsheet.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import fr.nelaupe.spreadsheetlib.SpreadSheetCell;

/**
 * Created by lucas34990 on 15/2/16.
 */
public class SpreadSheetProcessor extends AbstractProcessor {

    private static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {

        Set<TypeElement> classesWithFieldThatContainsAnnotations = new HashSet<>();

        Set<? extends Element> elements = env.getElementsAnnotatedWith(SpreadSheetCell.class);
        for (Element element : elements) {

            Element enclosing = element.getEnclosingElement();
            if (enclosing.getKind().isClass()) {
                classesWithFieldThatContainsAnnotations.add((TypeElement) enclosing);
            }
        }

        for (TypeElement key : classesWithFieldThatContainsAnnotations) {
            String packageName = key.getEnclosingElement().toString();
            String className = key.getSimpleName().toString();
            generateBinder(env, className, packageName, key);
        }

        return true;
    }

    private void generateBinder(RoundEnvironment env, String className, String packageName, TypeElement typeElement) {
        String generatedClassName = className + "Binding";
        String output = packageName + "." + generatedClassName;

        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile(output, typeElement);

            ClassName entity = ClassName.get(packageName, className);

            ClassName annotationClass = ClassName.get("fr.nelaupe.spreadsheetlib", "AnnotationFields");
            TypeName listOAnnotations = ParameterizedTypeName.get(ClassName.get("java.util", "List"), annotationClass);

            MethodSpec.Builder fill = MethodSpec.methodBuilder("fill")
                    .addAnnotation(Override.class)
                    .returns(listOAnnotations)
                    .addStatement("$T result = new $T<>()", listOAnnotations, ClassName.get("java.util", "ArrayList"))
                    .addModifiers(Modifier.PROTECTED);

            for (Element field : env.getElementsAnnotatedWith(SpreadSheetCell.class)) {
                if (field.getEnclosingElement().getSimpleName().toString().equals(className)) {
                    SpreadSheetCell annotation = field.getAnnotation(SpreadSheetCell.class);
                    fill.addStatement("result.add(new $T($S, $L, $L, $S, $L))", annotationClass, annotation.name(),  annotation.size(), annotation.position(), field.getSimpleName(), false);
                }
            }
            fill.addStatement("return result");


            MethodSpec.Builder getValueAt = MethodSpec.methodBuilder("getValueAt")
                    .addParameter(String.class, "fieldName")
                    .addParameter(entity, "data")
                    .addAnnotation(Override.class)
                    .returns(Object.class)
                    .addModifiers(Modifier.PUBLIC);

            getValueAt.beginControlFlow("switch(fieldName)");
            for (Element field : env.getElementsAnnotatedWith(SpreadSheetCell.class)) {
                if (field.getEnclosingElement().getSimpleName().toString().equals(className)) {
                    SpreadSheetCell annotation = field.getAnnotation(SpreadSheetCell.class);
                    getValueAt.beginControlFlow("case $S : ", field.getSimpleName());
                    getValueAt.addStatement("return data.$L", field.getSimpleName());
                    getValueAt.endControlFlow();
                }
            }

            getValueAt.beginControlFlow("default : ");
            getValueAt.addStatement("return null");
            getValueAt.endControlFlow();

            getValueAt.endControlFlow();

            TypeName parameterizedTypeName = ParameterizedTypeName.get(ClassName.get("fr.nelaupe.spreadsheetlib", "FieldBinder"), entity);
            TypeSpec hello = TypeSpec.classBuilder(generatedClassName)
                    .superclass(parameterizedTypeName)
                    .addMethod(fill.build())
                    .addMethod(getValueAt.build())
                    .build();

            JavaFile javaFile = JavaFile.builder("fr.nelaupe.spreadsheetlib", hello)
                    .build();

            Writer writer = jfo.openWriter();
            javaFile.writeTo(writer);
            writer.flush();
            writer.close();

        } catch (Exception exception) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, exception.getMessage());
            // e.printStackTrace();
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportedTypes = new HashSet<String>();
        supportedTypes.add(SpreadSheetCell.class.getCanonicalName());
        return supportedTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
