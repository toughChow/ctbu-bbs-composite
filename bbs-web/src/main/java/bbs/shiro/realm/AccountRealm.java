package bbs.shiro.realm;

import bbs.base.lang.Consts;
import bbs.core.data.AccountProfile;
import bbs.core.data.AuthMenu;
import bbs.core.data.Role;
import bbs.core.data.User;
import bbs.core.persist.service.UserService;
import bbs.shiro.authc.AccountAuthenticationInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    public AccountRealm() {
        super(new AllowAllCredentialsMatcher());
        setAuthenticationTokenClass(UsernamePasswordToken.class);
        setCachingEnabled(false);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.fromRealm(getName()).iterator().next();
        User user;
        if (StringUtils.isBlank(username) || (user = userService.getByUsername(username)) == null) return null;

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        for (Role r : user.getRoles()) {
            authorizationInfo.addRole(r.getName());
            for (AuthMenu authMenu : r.getAuthMenus()) {
                if (StringUtils.isBlank(authMenu.getPermission())) continue;
                for (String permission : StringUtils.split(authMenu.getPermission(), ",")) {
                    authorizationInfo.addStringPermission(permission);
                }
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AccountProfile accountProfile = getAccount(userService, token);

        if (accountProfile == null) {
            throw new UnknownAccountException("账户不存在");
        }

        if (accountProfile.getStatus() == Consts.STATUS_LOCKED) {
            throw new LockedAccountException(accountProfile.getName());
        }

        AccountAuthenticationInfo accountAuthenticationInfo
                = new AccountAuthenticationInfo(token.getPrincipal(), token.getCredentials(), getName());
        accountAuthenticationInfo.setProfile(accountProfile);

        return accountAuthenticationInfo;
    }

    protected AccountProfile getAccount(UserService userService, AuthenticationToken token) {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        return userService.login(upToken.getUsername(), String.valueOf(upToken.getPassword()));
    }
}
