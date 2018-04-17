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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

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
        return "/admin/roles/view";
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

    @RequestMapping("tree")
    @ResponseBody
    public List<AuthMenu.Node> tree(@RequestParam(required = false) Long roleId) {
        HashMap<Long, AuthMenu> map = new LinkedHashMap<>();

        if (roleId != null && roleId != 0) {
            Role role = roleService.get(roleId);
            List<AuthMenu> authedMenus = role.getAuthMenus();

            if (authedMenus != null)
                authedMenus.forEach(n -> map.put(n.getId(), n));
        }
        List<AuthMenu> list = authMenuService.findAllMenu();

        List<AuthMenu.Node> results = new LinkedList<>();

        for (AuthMenu a : list) {
            AuthMenu.Node m = a.toNode();

            if (!map.isEmpty() && map.get(m.getId()) != null)
                m.setChecked(true);

            results.add(m);
        }
        return results;
    }

    @RequestMapping(value = "view")
    public String viewSingle(Role role, Model model) {
        model.addAttribute("role", role);
        return "/admin/roles/view";
    }

}
