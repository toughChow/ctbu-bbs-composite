package bbs.web.controller;

import bbs.web.controller.desk.Views;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController extends BaseController{

    @GetMapping(value = "/login")
    public String view(){
        return getView(Views.LOGIN);
    }

}
