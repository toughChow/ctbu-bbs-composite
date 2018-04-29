package bbs.web.controller.admin;

import bbs.base.data.Data;
import bbs.core.data.AccountProfile;
import bbs.core.data.PostType;
import bbs.core.persist.service.PostService;
import bbs.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin/posts")
public class PostController extends BaseController {

    @Autowired
    private PostService postService;

    @GetMapping("/type")
    public String toType(ModelMap model, String key, Integer pn){
        Page<PostType> page = postService.findTypeList(wrapPageable(pn, 10, 0, null), key);
        model.put("page",page);
        model.put("key",key);
        return "/admin/posts/type";
    }

    @PostMapping("/edit_postType")
    public String editPostType(PostType type){
        postService.updatePostType(type);
        return "redirect:/admin/posts/type";
    };

    @GetMapping("/delete_postType")
    @ResponseBody
    public Data deletePostType(Long id){
        Data data = postService.deletePostType(id);
        return data;
    }

    @PostMapping("/add_postType")
    public String addPostType(PostType type){
        AccountProfile profile = getSubject().getProfile();
        String username = profile.getUsername();
        type.setTypeCreator(username);
        postService.savePostType(type);
        return "redirect:/admin/posts/type";
    }
}
