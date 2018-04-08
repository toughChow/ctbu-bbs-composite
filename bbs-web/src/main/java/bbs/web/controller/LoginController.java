package bbs.web.controller;

import bbs.base.lang.Consts;
import bbs.base.print.Printer;
import bbs.core.persist.service.UserService;
import bbs.web.controller.desk.Views;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController extends BaseController{

    @Autowired
    private UserService userService;

    int count = 0;

    String imagecode = "imageCount";

    @GetMapping(value = "/login")
    public String view(){
        return getView(Views.LOGIN);
    }

    @PostMapping(value = "/login")
    public String login(String username, String password, @RequestParam(value = "rememberMe", defaultValue = "0") int rememberMe,
                        @RequestParam(value="messageCode",required = false)String messageCode, ModelMap modelMap)  {
        String redirectUrl = getView(Views.LOGIN);

        modelMap.put("username",username);

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return redirectUrl;
        }

//        if(messageCode != null) {
//            if (!codeStr.equals(messageCode)) {
//                modelMap.put("message", "验证码错误");
//                return redirectUrl;
//            } // :end validate msg code
//        }

        AuthenticationToken authenticationToken = createAuthToken(username, password);
        if (authenticationToken == null) {
            modelMap.put("message", "用户名或密码错误");
            modelMap.put(imagecode, count++);
            return redirectUrl;
        }

        if (rememberMe == Consts.LOGIN_REMEMBER_ME) {
            ((UsernamePasswordToken) authenticationToken).setRememberMe(true);
            Printer.info("remeberMe={}", ((UsernamePasswordToken) authenticationToken).isRememberMe());
        }

        try {
            SecurityUtils.getSubject().login(authenticationToken);

            // save the new ip address
//            if(ipAddress) {
//                User user = new User();
//                user.setUsername(username);
//                user.setUser_ip(v4IP);
//                userService.updateUser(user);
//            }
            redirectUrl = Views.INDEX;
        } catch (AuthenticationException e) {
            if (e instanceof UnknownAccountException) {
                modelMap.put("message", "账户不存在");
                modelMap.put(imagecode, count++);
            } else if (e instanceof LockedAccountException) {
                modelMap.put("message", "账户被锁定");
                modelMap.put(imagecode, count++);
            } else if (e instanceof IncorrectCredentialsException) {
                modelMap.put("message", "密码错误");
                modelMap.put(imagecode, count++);
            } else {
                modelMap.put("message", "认证失败");
                modelMap.put("imageCount", count++);
            }
        }
        Printer.info("imageCount={}", count);
        return redirectUrl;
    }

}
