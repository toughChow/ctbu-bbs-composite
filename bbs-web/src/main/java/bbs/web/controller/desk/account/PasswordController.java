package bbs.web.controller.desk.account;

import bbs.base.data.Data;
import bbs.core.data.AccountProfile;
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
public class PasswordController extends BaseController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public String view() {
        return getView(Views.ACCOUNT_PASSWORD);
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String post(String oldPassword, String password, ModelMap model) {
        Data data;
        try {
            AccountProfile profile = getSubject().getProfile();
            userService.updatePassword(profile.getId(), password);

            data = Data.success("操作成功", Data.NOOP);
        } catch (Exception e) {
            data = Data.failure(e.getMessage());
        }
        model.put("data", data);
        return getView(Views.ACCOUNT_PASSWORD);
    }

}
