package bbs.web.controller.admin;

import bbs.base.data.Data;
import bbs.core.data.AuthMenu;
import bbs.core.data.Group;
import bbs.core.data.User;
import bbs.core.persist.service.AuthMenuService;
import bbs.core.persist.service.UserService;
import bbs.web.controller.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * 显示群组分页操作
     *
     * @param model
     * @param key
     * @param pn
     * @return
     */
    @RequestMapping("/group")
    public String group(ModelMap model, String key, Integer pn) {
        model.put("page", userService.findGroupList(wrapPageable(pn, 10, 0, null), key));
        model.put("key", key);
        return "/admin/users/group";
    }

    /**
     * 添加一个群组
     *
     * @param userGroup
     * @param map
     * @return
     */
    @PostMapping("/add_group")
    public String add_group(Group userGroup, ModelMap map) {
        userService.saveGroup(userGroup);

        return "redirect:/admin/users/group";
    }

    /**
     * 删除群组曹组
     *
     * @param userGroupId
     * @return
     */
    @RequestMapping("/delete_group")
    @ResponseBody
    public Data delete_group(String userGroupId) {
        Boolean result = userService.deleteGroup(userGroupId);
        if (result) {
            return Data.success("删除成功", Data.NOOP);
        } else {
            return Data.failure("删除失败");
        }
    }

    /**
     * 用户群组管理的显示
     *
     * @param groupId
     * @return
     */
    @RequestMapping("/group/id/{id}")
    public String getAllByGroupId(@PathVariable(value = "id") Long groupId, ModelMap model, String key) {
        Pageable pageable = wrapPageable();
        Page<User> page = userService.pagingGroupMember(pageable, groupId,key);
        model.put("page", page);
        model.put("groupId", groupId);
        model.put("key", key);
        return "/admin/users/group_members";
    }

    /**
     * 群组信息按钮点击后显示
     *
     * @param groupId
     * @return
     */
    @GetMapping("/group_info")
    @ResponseBody
    public Group GroupInfo(long groupId) {
        return userService.findById(groupId);
    }

    /**
     * 添加群成员 的时候显示用户GroupId为空的数据显示
     *
     * @return
     */
    @GetMapping("/add_group_member_view")
    @ResponseBody
    public List<User> add_group_member_view() {
        return userService.findGroupPOIsNull();
    }

    /**
     * 设置群主的操作
     *
     * @param userId
     * @param groupId
     * @return
     */
    @RequestMapping("/group/member/lord")
    @ResponseBody
    public Data setGroupManager(Long userId, Long groupId) {
        Boolean result = userService.setUserGroupManager(userId, groupId);
        if (result) {
            return Data.success("群主设置成功", Data.NOOP);
        } else {
            return Data.failure("群主已存在操作失败");
        }

    }

    @RequestMapping("/group/member/add")
    @ResponseBody
    public Data addMembers(String ids, Long groupId) {
        String[] str = ids.split(",");
        for (int i = 0; i < str.length; i++) {
            userService.addMembers(Long.parseLong(str[i]), groupId);
        }
        return Data.success("操作成功", null);
    }

    @RequestMapping("/group/member/removeIds")
    @ResponseBody
    public Data removeMembers(String ids) {
        String[] str = ids.split(",");
        for (int i = 0; i < str.length; i++) {
            userService.removeGroupMember(Long.parseLong(str[i]));
        }
        return Data.success("操作成功", null);
    }

    /**
     * message center
     */
    @ResponseBody
    @RequestMapping(value = "/search")
    public List<User> searchUser(String username) {
        List<User> users = userService.findUserLike(username);
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            User user = new User();
            user.setUsername(users.get(i).getUsername());
            user.setId(users.get(i).getId());
            userList.add(user);
        }
        if (userList == null)
            return null;
        else
            return userList;
    }

    @ResponseBody
    @RequestMapping(value = "/searchSingle")
    public String searchSingle(String username) {
        User user = userService.findUserByName(username);
        String result = user.getUsername();
        if (result == null) { // 不存在返回1
            return "1";
        } else
            return "0";
    }
}
