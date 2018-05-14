package bbs.web.controller.admin;

import bbs.base.data.Data;
import bbs.core.data.*;
import bbs.core.persist.service.PlateService;
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

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/posts")
public class PostController extends BaseController {

    @Autowired
    private PostService postService;

    @Autowired
    private PlateService plateService;

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

    /*
        the following are post management
     */

    /**
     * 查看所有帖子 status 为1
     * @param model
     * @param key
     * @param pn
     * @return
     */
    @GetMapping("/list")
    public String toList(ModelMap model, String key, Integer pn){
        AccountProfile profile = getSubject().getProfile();
        Integer status = 1;
        String username = profile.getUsername();
        Page<Post> page = postService.findPostListByManagerAndStatus(wrapPageable(pn, 10, 0, null), key, username,status);
        model.put("page",page);
        model.put("key",key);
        return "/admin/posts/list";
    }

    /**
     * 审阅帖子 status 为0
     * @param model
     * @param key
     * @param pn
     * @return
     */
    @GetMapping("/check")
    public String toVerify(ModelMap model, String key, Integer pn){
        AccountProfile profile = getSubject().getProfile();
        Integer status = 0;
        String username = profile.getUsername();
        Page<Post> page = postService.findPostListByManagerAndStatus(wrapPageable(pn, 10, 0, null), key, username,status);
        model.put("page",page);
        model.put("key",key);
        return "/admin/posts/verify";
    }

    @RequestMapping("/passCheck")
    @ResponseBody
    public Data pass(Long id){
        Integer status = 1;
        Data data = postService.updatePostStatus(id,status);
        return data;
    }

    @RequestMapping("/refuseCheck")
    @ResponseBody
    public Data refuse(Long id){
        Integer status = 2;
        Data data = postService.updatePostStatus(id,status);
        return data;
    }

    // 举报审核

    /**
     *
     * @param model
     * @param key
     * @param pn
     * @return
     */
    @GetMapping("/tipoff")
    public String toTipOff(ModelMap model, String key, Integer pn){
        AccountProfile profile = getSubject().getProfile();
        String username = profile.getUsername();
        Integer status = 1;
        Page<Post> page = postService.findPostListByTipOffAndStatus(wrapPageable(pn, 10, 0, null), key, username,status);
        model.put("page",page);
        model.put("key",key);
        return "/admin/posts/tipoff";
    }

    // 发帖

    @GetMapping("/send")
    public String sendPost(ModelMap model){
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
        return "/admin/posts/send";
    }

    @PostMapping("/send")
    public String send(Post post, ModelMap model) {
        AccountProfile profile = getSubject().getProfile();

        postService.save(post, profile);
        return "redirect:/";
    }

    @RequestMapping("/commentPost")
    @ResponseBody
    public Data commentPost(Long id,String content){
        AccountProfile profile = getSubject().getProfile();
        long commentorId = profile.getId();
        Data data = postService.commentPost(id,content,commentorId);
        return data;
    }

}
