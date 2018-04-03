package bbs.web.controller.desk;

import bbs.core.data.AccountProfile;
import bbs.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegController extends BaseController{

    @GetMapping(value = "/reg")
    public String view(){
//        AccountProfile profile = getSubject().getProfile();
//        if(profile != null){
//            return "redirect:/home";
//        }
        return getView(Views.REG);
    }
}
