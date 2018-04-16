package bbs.boot;

import bbs.shiro.authc.AccountSubjectFactory;
import bbs.shiro.realm.AccountRealm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public AccountSubjectFactory accountSubjectFactory() {
        return new AccountSubjectFactory();
    }

    @Bean
    public DefaultWebSecurityManager securityManager(CookieRememberMeManager rememberMeManager, CacheManager cacheShiroManager, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(this.shiroAccountRealm());
        securityManager.setCacheManager(cacheShiroManager);
        securityManager.setRememberMeManager(rememberMeManager);
        securityManager.setSessionManager(sessionManager);
        securityManager.setSubjectFactory(this.accountSubjectFactory());
        return securityManager;
    }

    @Bean
    public DefaultWebSessionManager defaultWebSecurityManager(CacheManager cacheShiroManager) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(cacheShiroManager);
        sessionManager.setSessionValidationInterval(1800 * 1000);
        sessionManager.setGlobalSessionTimeout(900 * 1000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        cookie.setName("shiroCache");
        cookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }

    @Bean
    public CacheManager getCacheShiroManager(EhCacheManagerFactoryBean ehcache) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(ehcache.getObject());
        return ehCacheManager;
    }

    @Bean
    public AccountRealm shiroAccountRealm() {
        return new AccountRealm();
    }

    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie) {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCipherKey(Base64.decode("Z3VucwAAAAAAAAAAAAAAAA=="));
        manager.setCookie(rememberMeCookie);
        return manager;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(7 * 24 * 60 * 60);//7天
        return simpleCookie;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        shiroFilter.setLoginUrl("/login");
        shiroFilter.setSuccessUrl("/");
        shiroFilter.setUnauthorizedUrl("/error/reject.html");

        Map<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("/assets/**", "anon");
        hashMap.put("/login", "anon");
//        hashMap.put("/home*", "user");
//        hashMap.put("/home/**", "user");
//        hashMap.put("/post/**", "user");
//        hashMap.put("/account/**", "user");

        hashMap.put("/admin", "authc,perms[admin]");
        hashMap.put("/admin/", "authc,perms[admin]");
        hashMap.put("/admin/index", "authc,perms[admin]");
//        hashMap.put("/AdminController/monitor", "authc,perms[monitor:view]");

        /*用户管理*/
        hashMap.put("/admin/users/**", "authc,perms[users:view]");
//        hashMap.put("/AdminController/users/update**", "authc,perms[users:edit]");
//        hashMap.put("/AdminController/users/pwd**", "authc,perms[users:edit]");

        /*角色管理*/
        hashMap.put("/admin/roles/list", "authc,perms[roles:view]");
        hashMap.put("/admin/roles/save", "authc,perms[roles:edit]");
        hashMap.put("/admin/roles/add", "authc,perms[roles:edit]");
//
//        /*控制器管理*/
//        hashMap.put("/AdminController/control/**", "authc,perms[control:view]");
//
//        /*维修管理*/
//        hashMap.put("/AdminController/repair/**", "authc,perms[repair:view]");
//
//        /*监控管理*/
//        hashMap.put("/AdminController/monitor/**", "authc,perms[monitor:view]");
//
//        /*应急管理*/
//        hashMap.put("/AdminController/contingency/**", "authc,perms[contingency:view]");
//
//        /*消息中心*/
//        hashMap.put("/AdminController/message/**", "authc,perms[message:view]");
//
//        /*统计分析*/
//        hashMap.put("/AdminController/statistics/**", "authc,perms[statistics:view]");
//

//
//        /*菜单管理*/
//        hashMap.put("/AdminController/authMenus/list", "authc,perms[authMenus:view]");
//        hashMap.put("/AdminController/authMenus/save", "authc,perms[authMenus:edit]");
//        hashMap.put("/AdminController/authMenus/view", "authc,perms[authMenus:edit]");

        shiroFilter.setFilterChainDefinitionMap(hashMap);
        return shiroFilter;
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager securityManager) {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        bean.setArguments(new Object[]{securityManager});
        return bean;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
