package bbs.web.controller;

import bbs.base.lang.Consts;
import bbs.base.print.Printer;
import bbs.core.exceptions.UserException;
import bbs.core.persist.service.UserService;
import bbs.core.persist.utils.GeetestLib;
import bbs.core.persist.utils.MessageHelper;
import bbs.web.controller.desk.Views;
import bbs.web.imagecode.GeetestConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

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

    @Autowired
    MessageHelper messageHelper;

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
            redirectUrl = "redirect:/index";
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

    @RequestMapping("/gt/ajax-validate2")
    @ResponseBody
    public void test1(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());

        String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);

        //从session中获取gt-server状态
        int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);

        //从session中获取userid
        String userid = (String) request.getSession().getAttribute("userid");

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

        int gtResult = 0;

        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证

            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
            System.out.println(gtResult);
        } else {
            // gt-server非正常情况下，进行failback模式验证

            System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
            System.out.println(gtResult);
        }


        if (gtResult == 1) {
            // 验证成功
            PrintWriter out = response.getWriter();
            JSONObject data = new JSONObject();
            try {
                data.put("status", "success");
                data.put("version", gtSdk.getVersionInfo());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            out.println(data.toString());
        } else {
            // 验证失败
            JSONObject data = new JSONObject();
            try {
                data.put("status", "fail");
                data.put("version", gtSdk.getVersionInfo());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PrintWriter out = response.getWriter();
            out.println(data.toString());
        }
    }

    @RequestMapping("/gt/register2")
    public void testStartC(HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());

        String resStr = "{}";

        //自定义userid
        String userid = "test";

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userid设置到session中
//        request.getSession().setAttribute("userid", userid);

        resStr = gtSdk.getResponseStr();

        PrintWriter out = response.getWriter();
        out.println(resStr);
    }

    @RequestMapping("/ipDifferValiCode")
    @ResponseBody
    public Integer sendVeryfyCode(String phone) throws IOException, UserException {
        Integer code = (int) ((Math.random() * 9 + 1) * 100000);
        String codeStr = code.toString();
        session.setAttribute("verifyCode",codeStr);
        try {
            messageHelper.sendCodeDefinedByYourself(phone, codeStr);
            session.setAttribute("msgKey",codeStr);
        } catch (Exception e) {
            return 0; // exception occured , send failed
        }
        return 1; // "验证码已发送,请注意查收!";
    }

}
