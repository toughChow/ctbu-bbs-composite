package bbs.core.persist.service;

import bbs.core.data.AccountProfile;
import bbs.core.data.User;

public interface UserService {
    AccountProfile getProfileByName(String username);

    User getByUsername(String username);

    AccountProfile login(String username, String s);

    User register(User post);
}
