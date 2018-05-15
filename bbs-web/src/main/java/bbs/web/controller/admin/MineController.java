package bbs.web.controller.admin;

import bbs.base.data.Data;
import bbs.core.data.AccountProfile;
import bbs.core.data.Comment;
import bbs.core.data.Post;
import bbs.core.persist.service.PostService;
import bbs.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/mine")
public class MineController extends BaseController {

    @Autowired
    private PostService postService;

    @RequestMapping("/posts")
    public String toMinePosts(ModelMap model,String key,Integer pn){
        AccountProfile profile = getSubject().getProfile();
        String username = profile.getUsername();
        Page<Post> page = postService.findPostListByUser(wrapPageable(pn, 10, 0, null), key, username);
        model.put("page",page);
        model.put("key",key);
        return "/admin/mine/posts";
    }

    @RequestMapping("/updatePost")
    public String updatePost(Post post){
        postService.updatePost(post);
        return "redirect:/admin/mine/posts";
    }

    // collection

    @RequestMapping("/collection")
    public String toMineCollections(ModelMap model,String key,Integer pn){
        AccountProfile profile = getSubject().getProfile();
        String username = profile.getUsername();
        Page<Post> page = postService.findCollectedPosts(wrapPageable(pn, 10, 0, null), key, username);
        model.put("page",page);
        model.put("key",key);
        return "/admin/mine/collection";
    }

    @RequestMapping("/undoCollect")
    @ResponseBody
    public Data undoCollect(Long id){
        AccountProfile profile = getSubject().getProfile();
        long userId = profile.getId();
        Data data = postService.undoCollect(id,userId);
        return data;
    }

    // comments

    @RequestMapping("/comments")
    public String toComments(ModelMap model,String key,Integer pn){
        AccountProfile profile = getSubject().getProfile();
        long userId = profile.getId();
        Page<Comment> page = postService.findComments(wrapPageable(pn, 10, 0, null), key, userId);
//        Page<Post> page = postService.findCollectedPosts(wrapPageable(pn, 10, 0, null), key, username);
        model.put("page",page);
        model.put("key",key);
        return "/admin/mine/comments";
    }

    @GetMapping("/delete_mycomment")
    @ResponseBody
    public Data deleteMyComment(Long id){
        Data data = postService.deleteMyComment(id);
        return data;
    }

}
