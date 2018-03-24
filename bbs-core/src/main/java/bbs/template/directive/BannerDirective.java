package bbs.template.directive;

import bbs.template.DirectiveHandler;
import bbs.template.TemplateDirective;
import org.springframework.stereotype.Component;

@Component
public class BannerDirective extends TemplateDirective {

    @Override
    public String getName() {
        return "banner";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
    }
}
