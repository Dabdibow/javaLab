import com.google.auto.service.AutoService;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"HtmlForm", "HtmlInput"})

public class HtmlProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // получить типы с аннотаций HtmlForm
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        for (Element element : annotatedElements) {

            // получаем полный путь для генерации html
            String path = "target/classes/html" + element.getSimpleName().toString() + ".html";
            String tempName = element.getSimpleName().toString() + ".ftlh";
            Path out = Paths.get(path);

            // freemarker setup
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
            cfg.setDefaultEncoding("UTF-8");
            Map<String, Object> attributes = new HashMap<>();

            try {
                //template load
                cfg.setTemplateLoader(new FileTemplateLoader(new File("src/main/resources/ftlh")));
                Template template = cfg.getTemplate(tempName);

                HtmlForm annotation = element.getAnnotation(HtmlForm.class);
                List<Map<String, String>> inputs = new ArrayList<>();
                element.getEnclosedElements().forEach(element1 -> {
                    HtmlInput htmlInput = element1.getAnnotation(HtmlInput.class);
                    if (htmlInput != null) {
                        Map<String, String> lineAttrs = new HashMap<>();
                        lineAttrs.put("type", htmlInput.type());
                        lineAttrs.put("name", htmlInput.name());
                        lineAttrs.put("placeholder", htmlInput.placeholder());
                        inputs.add(lineAttrs);
                    }
                });

                // attributes input
                attributes.put("inputs", inputs);
                attributes.put("action", annotation.action());
                attributes.put("method", annotation.method());

                //write to file
                BufferedWriter writer = new BufferedWriter(new FileWriter(out.toFile().getAbsolutePath()));
                try {
                    template.process(attributes, writer);
                } catch (TemplateException e) {
                    throw new IllegalStateException(e);
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }

        }

        return true;
    }
}
