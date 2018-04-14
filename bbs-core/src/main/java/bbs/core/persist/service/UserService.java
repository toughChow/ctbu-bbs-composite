package bbs.core.persist.service;

import bbs.core.data.AccountProfile;
import bbs.core.data.AuthMenu;
import bbs.core.data.Group;
import bbs.core.data.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    AccountProfile getProfileByName(String username);

    User getByUsername(String username);

    AccountProfile login(String username, String s);

    User register(User post);

    boolean findUserByMobile(String phone);

    Page<User> findUserPaging(Pageable pageable, String key);

    Page<Group> findGroupPaging(Pageable pageable, String key);


}
