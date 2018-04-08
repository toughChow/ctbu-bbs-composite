package bbs.template.directive;

import bbs.template.DirectiveHandler;
import bbs.template.TemplateDirective;
import org.springframework.stereotype.Component;

@Component
public class ContentsDirective extends TemplateDirective {

    @Override
    public String getName() {
        return "contents";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {

    }
}
