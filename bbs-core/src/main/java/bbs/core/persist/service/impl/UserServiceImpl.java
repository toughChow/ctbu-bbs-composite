package bbs.core.persist.service.impl;

import bbs.base.lang.Consts;
import bbs.core.data.AccountProfile;
import bbs.core.data.User;
import bbs.core.persist.dao.UserDao;
import bbs.core.persist.entity.UserPO;
import bbs.core.persist.service.UserService;
import bbs.core.persist.utils.BeanMapUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.BeanUtils;
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

    @Override
    @Transactional
    public User register(User user) {
        Assert.notNull(user, "Parameter user can not be null!");

        Assert.hasLength(user.getUsername(), "用户名不能为空!");
        Assert.hasLength(user.getPassword(), "密码不能为空!");

        UserPO check = userDao.findByUsername(user.getUsername());

        Assert.isNull(check, "用户名已经存在!");

        if (StringUtils.isNotBlank(user.getEmail())) {
            check = userDao.findByEmail(user.getEmail());
            Assert.isNull(check, "邮箱已经被注册!");
        }

        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(user, userPO);
        userPO.setPassword(DigestUtils.sha1Hex(user.getPassword()));
        userPO.setStatus(Consts.USER_ENABLED);
        userPO.setActiveEmail(Consts.USER_ENABLED);
        userPO.setCreated(Calendar.getInstance().getTime());
        userDao.save(userPO);

        return BeanMapUtils.copy(userPO, 0);
    }

    @Override
    public boolean findUserByMobile(String phone) {
        UserPO userPO = userDao.findByMobile(phone);
        if(userPO != null)
            return true; // this phone exist
        return false;
    }
}
