package bbs.web.controller.desk.account;

import bbs.base.data.Data;
import bbs.base.lang.Consts;
import bbs.base.utils.MailHelper;
import bbs.core.data.AccountProfile;
import bbs.core.data.User;
import bbs.core.persist.service.UserService;
import bbs.web.controller.BaseController;
import bbs.web.controller.desk.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/account")
public class ProfileController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailHelper mailHelper;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String view(ModelMap model) {
        AccountProfile profile = getSubject().getProfile();
        User view = userService.get(profile.getId());
        model.put("view", view);
        return getView(Views.ACCOUNT_PROFILE);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String post(String name, String signature, ModelMap model) {
        Data data;
        AccountProfile profile = getSubject().getProfile();

        try {
            User user = new User();
            user.setId(profile.getId());
            user.setNickname(name);
            user.setSignature(signature);

            putProfile(userService.update(user));

            // put 最新信息
            User view = userService.get(profile.getId());
            model.put("view", view);

            data = Data.success("操作成功", Data.NOOP);
        } catch (Exception e) {
            data = Data.failure(e.getMessage());
        }
        model.put("data", data);
        return "redirect:/account/profile";
    }

    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public String email() {
        return getView(Views.ACCOUNT_EMAIL);
    }

}
