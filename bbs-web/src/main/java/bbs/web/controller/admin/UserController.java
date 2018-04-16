package bbs.web.controller.admin;

import bbs.core.data.AuthMenu;
import bbs.core.persist.service.AuthMenuService;
import bbs.core.persist.service.UserService;
import bbs.web.controller.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthMenuService authMenuService;

    List<AuthMenu>  authMenuOnly = new ArrayList<>(); // 装生成节点树容器

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
        List<AuthMenu> menus = authMenuService.findAllMenu();
        return menus;
    }

    /**
     * toughchow
     *
     * @param pid
     * @return
     */
    @GetMapping(value = {"/menu/add/pid/{pid}", "/menu/add"})
    public String toAdd(@PathVariable(value = "pid", required = false) Long pid, ModelMap model) {
        if(!authMenuOnly.isEmpty()){
            authMenuOnly.clear();
        }
        List<AuthMenu> authMenuTmp = authMenuService.findAllMenu();
        List<AuthMenu> authMenus = getRegionTree(authMenuTmp, 0L);
        model.put("authMenus", authMenus);
        if (pid != null) {
            AuthMenu authMenu = authMenuService.get(pid);
            model.put("authMenu", authMenu);
        }
        return "/admin/users/addMenu";
    }

    private List<AuthMenu> getRegionTree(List<AuthMenu> menus, Long parentId){
        for(int i = 0; i < menus.size(); i ++) {
            if(menus.get(i).getParentId() == parentId) {
                authMenuOnly.add(menus.get(i));
                getRegionTree(menus, menus.get(i).getId());
            }
        }
        return authMenuOnly;
    }

    @RequestMapping("/menu/save")
    public String save(AuthMenu authMenu, Model model) {
        Long id = authMenu.getId();
        if(id != null) {
            AuthMenu menu = authMenuService.get(id);
            BeanUtils.copyProperties(authMenu,menu,new String[]{"icon","parents"});
        }
        authMenuService.save(authMenu);
        return "redirect:/admin/users/menu";
    }

    @RequestMapping("/menu/delete")
    public String delete(Long id, Model model) {
        authMenuService.delete(id);
        return "redirect:/admin/users/menu";
    }

    @GetMapping("/menu/edit")
    public String toEdit(Long id, Model model) {
        AuthMenu thisMenu = authMenuService.get(id);
        model.addAttribute("authMenu", thisMenu);
        return "/admin/users/editMenu";
    }

}
