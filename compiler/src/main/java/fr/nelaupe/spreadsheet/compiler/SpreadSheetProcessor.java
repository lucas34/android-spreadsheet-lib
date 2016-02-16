package fr.nelaupe.spreadsheet.compiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

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
public class SpreadSheetProcessor extends AbstractProcessor
{


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env)
    {
        for (TypeElement annotation : annotations)
        {
            System.out.println("annotation = " + annotation);
        }

        Set<? extends Element> elements = env.getElementsAnnotatedWith(SpreadSheetCell.class);
        for (Element element : elements)
        {
            System.out.println("element = " + element);
            System.out.println("element.asType() = " + element.asType());
            System.out.println("element.getClass() = " + element.getClass());
            System.out.println("element is TypeElement = " + (element instanceof TypeElement));

            Element enclosing = element.getEnclosingElement();
            System.out.println("element.getEnclosingElement() = " + enclosing);
            System.out.println("element.getEnclosingElement().getKind() = " + enclosing.getKind());


            if (element instanceof VariableElement)
            {
                TypeElement typeElement = (TypeElement) element.getEnclosingElement();

                String packageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
                String className = getClassName(typeElement, packageName);

                System.out.println("className = " + className);

                try
                {
                    String generatedClassName = className + "Binding";
                    String output = packageName + "." + generatedClassName;
                    System.out.println("output = " + output);

                    JavaFileObject jfo = processingEnv.getFiler().createSourceFile(output, typeElement);

                    Writer writer = jfo.openWriter();
                    writer.write("//Generated class\n");
                    writer.write("package " + packageName + ";\n\n");
                    writer.write("public final class " + generatedClassName + " extends fr.nelaupe.spreadsheetlib.BinderField<"+className+"> {");
                    writer.write("  \n");
                    writer.write("  \n\n");
                    writer.write("  public "+className+"Binding() { ");
                    writer.write("  \n");
                    writer.write("      this.fields = new java.util.ArrayList<>();");
                    writer.write("  \n");
                    for(Element field : env.getElementsAnnotatedWith(SpreadSheetCell.class)) {
                        SpreadSheetCell annotation = field.getAnnotation(SpreadSheetCell.class);
                        writer.write("      this.fields.add(new fr.nelaupe.spreadsheetlib.AnnotationFields(\""+annotation.name()+"\", "+annotation.size()+", "+annotation.position()+", \""+field.getSimpleName()+"\", false));");
                        writer.write("  \n");
                    }
                    writer.write("  }\n");
                    writer.write("  @Override");
                    writer.write("  public Object getValueAt(String fieldName, "+className+" data) {");
                    writer.write("      switch (fieldName) {");
                    for(Element field : env.getElementsAnnotatedWith(SpreadSheetCell.class)) {;
                        writer.write("      case \""+field.getSimpleName()+"\" : {");
                        writer.write("          return data."+field.getSimpleName()+";");
                        writer.write("   }");
                    }
                    writer.write("  }");
                    writer.write("return null;");
                    writer.write("}");
                    writer.write("}");
                    writer.flush();
                    writer.close();
                }
                catch (Exception exception)
                {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, exception.getMessage());
                    // e.printStackTrace();
                }
            }

            return true;
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes()
    {
        Set<String> supportedTypes = new HashSet<String>();
        supportedTypes.add(SpreadSheetCell.class.getCanonicalName());
        return supportedTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion()
    {
        return SourceVersion.latestSupported();
    }

    private static String getClassName(TypeElement type, String packageName)
    {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

}
