
package bbs.web.controller.desk;

public interface Views {
    String REDIRECT_HOME = "redirect:/home";

    String REDIRECT_ADMIN = "redirect:/AdminController";

    String LOGIN = "/login";
    String REG = "/reg";
    String REG_RESULT = "/reg_result";
    String OAUTH_REG = "/oauth_reg";

    String FORGOT_APPLY = "/forgot/apply";
    String FORGOT_RESET = "/forgot/reset";
    //忘记密码的结果页面
    String FORGOT_RESULT="/forgot/forgot_result";

    String INDEX = "/index";

    String HOME_NOTIFIES = "/home/notifies";
    String ACCOUNT_AVATAR = "/account/avatar";
    String ACCOUNT_PASSWORD = "/account/password";
    String ACCOUNT_PROFILE = "/account/profile";
    String ACCOUNT_EMAIL = "/account/email";

    String TAGS_TAG = "/tag";

    String BROWSE_SEARCH = "/search";

}
