package bbs.web.controller.desk;

import bbs.base.lang.Consts;
import bbs.core.data.Plate;
import bbs.core.data.Post;
import bbs.core.data.User;
import bbs.core.persist.service.PlateService;
import bbs.core.persist.service.PostService;
import bbs.core.persist.service.UserService;
import bbs.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController extends BaseController{

    @Autowired
    PostService postService;

    @Autowired
    PlateService plateService;

    @Autowired
    UserService userService;

    @RequestMapping(value = {"/", "/index"})
    public String root(ModelMap model, HttpServletRequest request) {
        String order = ServletRequestUtils.getStringParameter(request, "ord", Consts.order.NEWEST);
        model.put("ord",order);

        List<Post> newPosts = postService.findPostListByTime();
        model.put("newPosts",newPosts);

        List<Plate> plates = plateService.findByParent();
        model.put("plates",plates);

        List<User> usersAll = userService.findAllUsers();
        List<User> users = new ArrayList<>();
        if(usersAll.size() > 7){
            for (int i = 0; i < 7; i ++){
                users.add(usersAll.get(i));
            }
            model.put("users",users);
        }else{
            model.put("users",usersAll);
        }

        model.put("newuser",usersAll.get(usersAll.size()-1));

        return getView(Views.INDEX);
    }
}
