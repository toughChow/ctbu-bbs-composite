package bbs.template;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Map;

public abstract class TemplateDirective implements TemplateDirectiveModel {

    protected static String RESULTS = "results";

    @Override
    public void execute(Environment environment, Map map,
                        TemplateModel[] models, TemplateDirectiveBody body) throws TemplateException, IOException {
        try {
            execute(new DirectiveHandler(environment, map, models, body));
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new TemplateException(e, environment);
        }

    }

    abstract public String getName();

    abstract public void execute(DirectiveHandler handler) throws Exception;
}
