package bbs.template;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.util.Date;
import java.util.List;

public abstract class BaseMethod implements TemplateMethodModelEx {

    public String getString(List<TemplateModel> arguments, int index) throws TemplateModelException {
        return TemplateModelUtils.convertString(getModel(arguments, index));
    }

    public Integer getInteger(List<TemplateModel> arguments, int index) throws TemplateModelException {
        return TemplateModelUtils.convertInteger(getModel(arguments, index));
    }

    public Long getLong(List<TemplateModel> arguments, int index) throws TemplateModelException {
        return TemplateModelUtils.convertLong(getModel(arguments, index));
    }

    public Date getDate(List<TemplateModel> arguments, int index) throws TemplateModelException {
        return TemplateModelUtils.converDate(getModel(arguments, index));
    }

    public TemplateModel getModel(List<TemplateModel> arguments, int index) {
        if (index < arguments.size()) {
            return arguments.get(index);
        }
        return null;
    }
}
