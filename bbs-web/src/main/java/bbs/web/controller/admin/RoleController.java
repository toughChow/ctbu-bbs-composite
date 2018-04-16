package bbs.web.controller.admin;

import bbs.core.data.AuthMenu;
import bbs.core.data.Role;
import bbs.core.persist.service.AuthMenuService;
import bbs.core.persist.service.RoleService;
import bbs.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/roles")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthMenuService authMenuService;

    @ModelAttribute("role")
    public Role get(@RequestParam(required = false) String id) {
        if (id != null && !id.equals("0")) {
            return roleService.get(Long.valueOf(id));
        } else {
            return new Role();
        }
    }

    @RequestMapping("/list")
    public String list(ModelMap model) {
        Pageable pageable = wrapPageable();
        Page<Role> page = roleService.paging(pageable);
        model.put("page", page);
        return "/admin/roles/list";
    }

    @RequestMapping(value = "add")
    public String view(Role role, Model model) {
        model.addAttribute("role", role);
        return "/admin/roles/add";
    }

    @RequestMapping("/save")
    public String save(Role role, Model model, String menus) {
        String[] menusIds = menus.split(",");
        List<AuthMenu> menuList = new ArrayList<>();
        for (String menuId : menusIds) {
            if (menuId != null && !menuId.equals("")) {
                Long id = Long.valueOf(menuId);
                AuthMenu menu = authMenuService.get(id);
                menuList.add(menu);
            }
        }
        role.setAuthMenus(menuList);
        roleService.save(role);
        return "redirect:/admin/roles/list";
    }

    @RequestMapping("/delete")
    public String delete(Long id, Model model) {
        roleService.delete(id);
        return "redirect:/admin/roles/list";
    }
}
