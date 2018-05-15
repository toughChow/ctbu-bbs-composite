package bbs.web.controller.desk;

import bbs.base.data.Data;
import bbs.base.utils.MailHelper;
import bbs.core.data.User;
import bbs.core.exceptions.UserException;
import bbs.core.persist.service.UserService;
import bbs.core.persist.utils.MessageHelper;
import bbs.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/forgot")
public class ForgotController extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageHelper messageHelper;

//    @RequestMapping("email")
//    @ResponseBody
//    public String email(String email,ModelMap model){
//        User user=userService.getByEmail(email);
//        if(user!=null){
//            model.put("email",email);
//            return "success";
//        }else{
//            return "failed";
//        }
//    }

    @GetMapping("/apply")
    public String apply() {
        return getView(Views.FORGOT_APPLY);
    }




    @PostMapping("/reset")
    public String reset(Long userId,String password,ModelMap model){
       Data data;
       try {
//           Long userId = Long.parseLong(userIdStr);
           Assert.notNull("userId","缺少必要的参数");
           userService.updatePassword(userId, password);
//           mp.put("message","密码更新成功");
           data = Data.success("恭喜您! 密码重置成功。",Data.NOOP);
           data.addLink("login", "去登录");
       }catch (Exception e){
           data = Data.failure(e.getMessage());
//           mp.put("message","密码更新失败");
       }
        model.put("data", data);
        return getView(Views.FORGOT_RESULT);
    }

/* @RequestMapping("/reset")
    public String reset(Long userId, String token, String password, ModelMap model) {
        Data data;

        try {
            Assert.notNull(userId, "缺少必要的参数");
            Assert.hasLength(token, "缺少必要的参数");

            verifyService.verifyToken(userId, Consts.VERIFY_FORGOT, token);
            userService.updatePassword(userId, password);

            data = Data.success("恭喜您! 密码重置成功。");
            data.addLink("login", "去登陆");

        } catch (Exception e) {
            data = Data.failure(e.getMessage());
        }

        model.put("data", data);
        model.put("userId", userId);
        model.put("token", token);
        return getView(Views.REG_RESULT);
    }*/

//    public Integer verifyUsername(){
//
//    }


    @RequestMapping("/checkUserGetPhone")
    @ResponseBody
    public User identifyUserAndGetPhone(String username){
        User user = userService.findUserByName(username);
        if(user != null)
            return user; // user name exist
        return null;

    }

    @RequestMapping("/valiVerifyCode")
    @ResponseBody
    public Integer validateVerifyCode(String messageCode,String phone){
//            Integer msgKey = messageHelper.
            Integer code = (int) ((Math.random() * 9 + 1) * 100000);
            String codeStr = code.toString();
            session.setAttribute("verifyCode",codeStr);
            try {
                messageHelper.sendCodeDefinedByYourself(phone, codeStr);
                return 1; // success
            }catch (Exception e){
                return 0; // error
            }

    }
}
