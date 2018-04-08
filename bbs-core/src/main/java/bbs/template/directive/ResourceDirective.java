package bbs.template.directive;

import bbs.base.context.Global;
import bbs.template.DirectiveHandler;
import bbs.template.TemplateDirective;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ResourceDirective extends TemplateDirective {
    @Override
    public String getName() {
        return "resource";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        String src = handler.getString("src","#");
        String base = handler.getString("base");

        if(StringUtils.isBlank(base)) {
            base = handler.getContextPath();
        }

        if(Global.getImageDomain()) {
            base = Global.getImageHost();
        }

        StringBuffer buf = new StringBuffer();

        buf.append(base);
        buf.append(src);

        handler.renderString(buf.toString());
    }
}
