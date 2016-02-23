package fr.nelaupe.spreadsheet.compiler;

import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
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
            System.out.println("Generation of the binder for class : "+ key);
            generateBinder(env, key.getSimpleName().toString(), "fr.nelaupe.spreedsheet", key);
        }

        return true;
    }

    private void generateBinder(RoundEnvironment env, String className, String packageName, TypeElement typeElement) {
        String generatedClassName = className + "Binding";
        String output = packageName + "." + generatedClassName;

        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile(output, typeElement);

            Writer writer = jfo.openWriter();
            writer.write("//Generated class\n");
            writer.write("package " + packageName + ";\n\n");
            writer.write("public final class " + generatedClassName + " extends fr.nelaupe.spreadsheetlib.BinderField<" + className + "> {\n");
            writer.write("  \n\n");
            writer.write("  public " + className + "Binding() { \n");
            writer.write("      this.fields = new java.util.ArrayList<>();\n");
            for (Element field : env.getElementsAnnotatedWith(SpreadSheetCell.class)) {
                if(field.getEnclosingElement().getSimpleName().toString().equals(className)) {
                    SpreadSheetCell annotation = field.getAnnotation(SpreadSheetCell.class);
                    writer.write("      this.fields.add(new fr.nelaupe.spreadsheetlib.AnnotationFields(\"" + annotation.name() + "\", " + annotation.size() + ", " + annotation.position() + ", \"" + field.getSimpleName() + "\", false));\n");
                }
            }
            writer.write("  }\n");
            writer.write("  \n\n");
            writer.write("  @Override\n");
            writer.write("  public Object getValueAt(String fieldName, " + className + " data) {\n");
            writer.write("      switch (fieldName) {\n");
            for (Element field : env.getElementsAnnotatedWith(SpreadSheetCell.class)) {
                writer.write("  \n");
                if(field.getEnclosingElement().getSimpleName().toString().equals(className)) {
                    writer.write("          case \"" + field.getSimpleName() + "\" : {\n");
                    writer.write("              return data." + field.getSimpleName() + ";\n");
                    writer.write("          }\n");
                }
            }
            writer.write("      }\n");
            writer.write("      return null;\n");
            writer.write("  }\n");
            writer.write("}\n");
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
