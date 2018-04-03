package bbs.core.persist.utils;

import bbs.core.data.AccountProfile;
import bbs.core.data.AuthMenu;
import bbs.core.data.Role;
import bbs.core.data.User;
import bbs.core.persist.entity.AuthMenuPO;
import bbs.core.persist.entity.RolePO;
import bbs.core.persist.entity.UserPO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class BeanMapUtils {

    private static String[] USER_IGNORE = new String[]{"password", "extend", "roles"};

    public static AuthMenu copy(AuthMenuPO po) {
        AuthMenu am = new AuthMenu();
        /*BeanUtils.copyProperties(po, am, "children");*/
        BeanUtils.copyProperties(po, am);
        return am;
    }

    public static Role copy(RolePO po) {
        Role r = new Role();
        BeanUtils.copyProperties(po, r, "users", "authMenus");
        List<AuthMenu> authMenus = new ArrayList<>();
        for (AuthMenuPO authMenuPO : po.getAuthMenus()) {
            AuthMenu authMenu = new AuthMenu();
            BeanUtils.copyProperties(authMenuPO, authMenu, "roles", "children");
            authMenus.add(authMenu);
        }
        r.setAuthMenus(authMenus);
        return r;
    }

    public static User copy(UserPO po, int level) {
        if (po == null) {
            return null;
        }
        User ret = new User();
        BeanUtils.copyProperties(po, ret, USER_IGNORE);

        if (level > 0) {
            List<RolePO> rolePOs = po.getRoles();
            List<Role> roles = new ArrayList<>();
            for (RolePO rolePo : rolePOs) {
                Role role = copy(rolePo);
                roles.add(role);
            }
            ret.setRoles(roles);
        }
        return ret;
    }

    public static AccountProfile copyPassport(UserPO po) {
        AccountProfile passport = new AccountProfile(po.getId(), po.getUsername());
        passport.setName(po.getNickname());
        passport.setEmail(po.getEmail());
        passport.setAvatar(po.getAvatar());
        passport.setLastLogin(po.getLastLogin());
        passport.setStatus(po.getStatus());
        passport.setActiveEmail(po.getActiveEmail());

        List<AuthMenu> menus = new ArrayList<>();
        List<RolePO> rolePOs = po.getRoles();
        List<Role> roles = new ArrayList<>();
        for (RolePO rolePo : rolePOs) {
            Role role = copy(rolePo);
            roles.add(role);
        }
        for (Role role : roles) {
            List<AuthMenu> authMenus = role.getAuthMenus();
            menus.addAll(authMenus);
        }
        passport.setAuthMenus(menus);
        return passport;
    }

}
