package bbs.core.persist.service.impl;

import bbs.base.lang.Consts;
import bbs.core.data.AccountProfile;
import bbs.core.data.User;
import bbs.core.persist.dao.UserDao;
import bbs.core.persist.entity.UserPO;
import bbs.core.persist.service.UserService;
import bbs.core.persist.utils.BeanMapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Calendar;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public AccountProfile getProfileByName(String username) {
        UserPO userPO = userDao.findByUsername(username);
        AccountProfile accountProfile;

        Assert.notNull(userPO, "账户不存在");

        Assert.state(userPO.getStatus() != Consts.STATUS_LOCKED, "您的账户被锁定");
        userPO.setLastLogin(Calendar.getInstance().getTime());

        accountProfile = BeanMapUtils.copyPassport(userPO);

        return accountProfile;
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        UserPO userPO = userDao.findByUsername(username);
        User user = null;
        if(userPO != null) {
            user = BeanMapUtils.copy(userPO, 1);
        }
        return user;
    }

    @Override
    public AccountProfile login(String username, String password) {
        UserPO userPO = userDao.findByUsername(username);

        if (userPO == null) {
            throw new UnknownAccountException();
        }

        if (userPO.getStatus() == Consts.STATUS_LOCKED) {
            throw new LockedAccountException();
        }

        if (!StringUtils.equals(userPO.getPassword(), password)) {
            throw new IncorrectCredentialsException();
        }

        userPO.setLastLogin(Calendar.getInstance().getTime());
        userDao.save(userPO);
        return BeanMapUtils.copyPassport(userPO);
    }
}
