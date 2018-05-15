
package bbs.web.controller.desk.account;

import bbs.base.context.AppContext;
import bbs.base.data.Data;
import bbs.core.data.AccountProfile;
import bbs.core.persist.service.UserService;
import bbs.web.controller.BaseController;
import bbs.web.controller.desk.Views;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/account")
public class AvatarController extends BaseController {
    @Autowired
    private AppContext appContext;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/avatar", method = RequestMethod.GET)
    public String view() {
        return getView(Views.ACCOUNT_AVATAR);
    }

    @RequestMapping(value = "/avatar", method = RequestMethod.POST)
    public String post(HttpServletRequest request, MultipartFile avatar) throws IOException {
        AccountProfile accountProfile = getSubject().getProfile();
        String s = uploadPic(request, avatar);
        userService.updateAvatar(accountProfile.getId(),s);
        getSubject().getSession().setAttribute("profile",userService.get(accountProfile.getId()));
        return "redirect:/account/profile";
    }

    public String uploadPic(HttpServletRequest request, MultipartFile avatar) throws IOException {
        String fileName = avatar.getOriginalFilename();

        String path = request.getServletContext().getRealPath("/uploadFiles");
        if (!new File(path).exists()) {
            new File(path).mkdirs();
        }
        //图片名：智慧路灯图片+yyyyMMddhhmmss+文件后缀
        String newFileName = UUID.randomUUID() + fileName.substring(fileName.indexOf("."), fileName.length());
        //图片存储地址
        String descPath = path + File.separator + newFileName;
        File newFile = new File(descPath);
        avatar.transferTo(newFile);
        return "/uploadFiles/" + newFileName;
    }


//    /**
//     * powered by srx
//     * @param file
//     * @param model
//     * @return
//     * @throws IOException
//     */
    /*
    @RequestMapping(value = "/avatar", method = RequestMethod.POST)
    public String post(@RequestParam("avater") MultipartFile file, ModelMap model, HttpServletRequest request) throws IOException {
        if (file == null || file.getSize() == 0) {
            model.put("data", Data.failure("请选择您待上传的图片"));
            return getView(Views.ACCOUNT_AVATAR);
        }

        AccountProfile p = getSubject().getProfile();
        BufferedImage src = ImageIO.read(file.getInputStream());
        int w = src.getWidth();
        double scala = 128.0/w;
        int h = (int)(src.getHeight()*scala);
        BufferedImage tag = new BufferedImage(128, h, BufferedImage.TYPE_INT_RGB);
        tag.getGraphics().drawImage(src.getScaledInstance(128, h, Image.SCALE_SMOOTH), 0, 0, null);
        String tmpPath = request.getServletContext().getRealPath("/upload/tmpAvater.jpg");
        File destFile = new File(tmpPath);
        FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(tag); // JPEG编码
        out.close();

        String url_suffix = "路灯运维平台/avater/" + p.getId() + ".jpg";
        String url_prefix = OSSUtil.ENDPOINT_URL;
        //使用OSS存储图片
        OSSUtil.instance.upload(OSSUtil.BUCKET, file.getInputStream(), url_suffix);
        String url = url_prefix + "/" + OSSUtil.BUCKET +"/" + url_suffix;
        OSSUtil.instance.upload(OSSUtil.BUCKET, tmpPath, url_suffix);
        userService.updateAvatar(p.getId(), url);
        return "redirect:/account/avatar";
    }*/


//    /**
//     * powered by srx
//     * @param file
//     * @param model
//     * @return
//     * @throws IOException
//     */

//    @RequestMapping(value = "/avatar", method = RequestMethod.POST)
//    public String post(@RequestParam("avater") MultipartFile file, ModelMap model, HttpServletRequest request) throws IOException {
//        if (file == null || file.getSize() == 0) {
//            model.put("data", Data.failure("请选择您待上传的图片"));
//            return getView(Views.ACCOUNT_AVATAR);
//        }
//
//        AccountProfile p = getSubject().getProfile();
//        /*String tmpPath = request.getServletContext().getRealPath("/upload/tmpAvater.jpg");
//        File destFile = new File(tmpPath);
//        Files.copy(file.getInputStream(), Paths.get(destFile.getPath()), StandardCopyOption.REPLACE_EXISTING);*/
//
//        String url_suffix = "路灯运维平台/avater/" + p.getId() + ".jpg";
//        String url_prefix = OSSUtil.ENDPOINT_URL;
//        //使用OSS存储图片
//        OSSUtil.instance.upload(OSSUtil.BUCKET, file.getInputStream(), url_suffix);
//        String url = url_prefix + "/" + OSSUtil.BUCKET +"/" + url_suffix;
//        userService.updateAvatar(p.getId(), url);
//        return "redirect:/account/avatar";
//    }

}
