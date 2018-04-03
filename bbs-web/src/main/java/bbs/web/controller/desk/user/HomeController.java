package bbs.web.controller.desk.user;

import bbs.web.controller.BaseController;
import bbs.web.controller.desk.Views;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends BaseController {

    @GetMapping(value = "/home")
    public String home(){
        return getView(Views.REDIRECT_HOME);
    }
}
