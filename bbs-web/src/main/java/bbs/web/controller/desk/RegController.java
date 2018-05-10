package bbs.web.controller.desk;

import bbs.base.data.Data;
import bbs.base.lang.Consts;
import bbs.core.data.AccountProfile;
import bbs.core.data.User;
import bbs.core.exceptions.UserException;
import bbs.core.persist.service.UserService;
import bbs.core.persist.utils.MessageHelper;
import bbs.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class RegController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private MessageHelper messageHelper;

    private static Long startTime = 0L;

    protected static String codeStr = "fake"; // default validation Code

    @GetMapping(value = "/reg")
    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    public String view() {
        AccountProfile profile = getSubject().getProfile();
        if (profile != null) {
            return "redirect:/home";
        }
        return getView(Views.REG);
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public String reg(User post, ModelMap model) {
        Data data;
        String ret = getView(Views.REG);

        try {
            post.setAvatar(Consts.AVATAR);
            User user = userService.register(post);

            data = Data.success("恭喜您! 注册成功, 已经给您的邮箱发了验证码, 赶紧去完成邮箱绑定吧。", Data.NOOP);
            data.addLink("login", "先去登陆尝尝鲜");

            ret = getView(Views.REG_RESULT);

        } catch (Exception e) {
            model.addAttribute("post", post);
            data = Data.failure(e.getMessage());
        }
        model.put("data", data);
        return ret;
    }

    /**
     *
     * @param phone
     * @return
     */
    @RequestMapping("/regCode")
    @ResponseBody
    public Integer sendVeryfyCode(String phone){
        boolean isExist = userService.findUserByMobile(phone);
        if (isExist == true) {
            return 0; // "该手机号已被注册!";
        }
        /*
         * 发送短信 若出现异常 重定向到之前页面
         * */
        Integer code = (int) ((Math.random() * 9 + 1) * 100000);
        startTime = System.currentTimeMillis();
        codeStr = code.toString();
        messageHelper.sendCodeDefinedByYourself(phone,codeStr);

        return 1; // "验证码已发送,请注意查收!";
    }

    @RequestMapping("/valiUser")
    @ResponseBody
    public Integer valiUser(String username){
        User user = userService.findUserByName(username);
        if(user != null)
            return 1; // user name exist
        return 0;


    }
}
