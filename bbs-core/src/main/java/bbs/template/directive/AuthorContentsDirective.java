package bbs.template.directive;

import bbs.template.DirectiveHandler;
import bbs.template.TemplateDirective;
import org.springframework.stereotype.Component;

@Component
public class AuthorContentsDirective extends TemplateDirective {

    @Override
    public String getName() {
        return "author_contents";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
    }
}
