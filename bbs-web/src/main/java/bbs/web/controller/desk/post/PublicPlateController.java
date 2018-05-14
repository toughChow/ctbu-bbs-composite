package bbs.web.controller.desk.post;

import bbs.core.data.*;
import bbs.core.persist.service.PlateService;
import bbs.core.persist.service.PostService;
import bbs.core.persist.service.UserService;
import bbs.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PublicPlateController extends BaseController{

    @Autowired
    private PostService postService;

    @Autowired
    private PlateService plateService;

    @Autowired
    private UserService userService;

    @RequestMapping("/plates/{id}")
    public String showPalte(@PathVariable(required = false) Long id, ModelMap model, Integer pn, String key){
        Page<Post> posts = postService.findByPlateIdOrderByCreateTimeDesc(wrapPageable(pn, 10, 0, null), key,id, 1);
        model.put("posts",posts);

        Plate parent = plateService.findByParent(id);
        model.put("parent",parent.getName());

        Plate plate = plateService.findOne(id);
        model.put("plate",plate);

        // 验证是否授权
        AccountProfile profile = getSubject().getProfile();
        model.put("profile",profile);

        // 获取帖子类型
        List<PostType> postTypeList = postService.findTypeList();
        model.put("postTypes",postTypeList);

        // 获取板块类型
        List<Plate> plateList = plateService.findAll();
        for (int i = 0; i < plateList.size(); i ++) {
            if(plateList.get(i).getParentId() == 0) {
                plateList.remove(i);
            }
        }
        model.put("plates",plateList);

        return "/default/plate/list";
    }

    @RequestMapping("/post/{id}")
    public String showDetail(@PathVariable(required = false) Long id, ModelMap model){

        Plate plate = postService.findPlateByPost(id);
        model.put("parent",plate);

        Plate grandpa = plateService.findByParent(plate.getId());
        model.put("grandpa",grandpa.getName());

        Post post = postService.findOne(id);
        model.put("post",post);

        User user = userService.findByPostId(id);
        model.put("author",user);

        // 验证是否授权
        AccountProfile profile = getSubject().getProfile();
        model.put("profile",profile);
        return "/default/plate/detail";
    }
}
