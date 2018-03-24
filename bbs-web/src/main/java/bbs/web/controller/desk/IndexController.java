package bbs.web.controller.desk;

import bbs.base.lang.Consts;
import bbs.web.controller.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController{

    @RequestMapping(value = {"/", "/index"})
    public String root(ModelMap model, HttpServletRequest request) {
//        String order = ServletRequestUtils.getStringParameter(request, "ord", Consts.order.NEWEST);
//        model.put("ord",order);
        return getView(Views.INDEX);
    }
}
