package bbs.web.controller.admin;

import bbs.core.data.AuthMenu;
import bbs.core.persist.service.UserService;
import bbs.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String list(ModelMap model, String key, Integer pn) {
        model.put("page", userService.findUserPaging(wrapPageable(pn, 10, 1, null), key));
        model.put("key", key);
        return "/admin/users/list";
    }

    @GetMapping("/group")
    public String group(ModelMap model ,String key, Integer pn) {
        model.put("page", userService.findGroupPaging(wrapPageable(pn, 5, 0, null), key));
        model.put("key",key);
        return "/admin/users/group";
    }

    @GetMapping("/menu")
    public String menus() {
//        model.put("page", userService.findAuthMenuList(wrapPageable(pn, 1000, 1, null)));
//        model.put("menus",menus);
        return "/admin/users/menu";
    }

    @RequestMapping("/getAllMenus")
    @ResponseBody
    public List<AuthMenu> getAllMenus(Integer pn){
        List<AuthMenu> menus = userService.findAllMenus();
        return menus;
    }
}
