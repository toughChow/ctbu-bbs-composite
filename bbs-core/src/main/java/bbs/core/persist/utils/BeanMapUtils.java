package bbs.core.persist.utils;

import bbs.core.data.AuthMenu;
import bbs.core.persist.entity.AuthMenuPO;
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

}
