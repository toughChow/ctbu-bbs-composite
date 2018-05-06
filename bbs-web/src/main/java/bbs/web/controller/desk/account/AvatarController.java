
package bbs.web.controller.desk.account;

import bbs.base.context.AppContext;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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

   /* @RequestMapping(value = "/avatar", method = RequestMethod.POST)
    public String post(String path, Float x, Float y, Float width, Float height, ModelMap model) {
        AccountProfile profile = getSubject().getProfile();

        if (StringUtils.isEmpty(path)) {
            model.put("data", Data.failure("请选择图片"));
            return getView(Views.ACCOUNT_AVATAR);
        }

        if (width != null && height != null) {
            String root = fileRepoFactory.select().getRoot();
            File temp = new File(root + path);
            File scale = null;

            // 目标目录
            String ava100 = appContext.getAvaDir() + getAvaPath(profile.getId(), 100);
            String dest = root + ava100;
            try {
                // 判断父目录是否存在
                File f = new File(dest);
                if (!f.getParentFile().exists()) {
                    f.getParentFile().mkdirs();
                }
                // 在目标目录下生成截图
                String scalePath = f.getParent() + "/" + profile.getId() + ".jpg";
                ImageUtils.truncateImage(temp.getAbsolutePath(), scalePath, x.intValue(), y.intValue(), width.intValue());

                // 对结果图片进行压缩
                ImageUtils.scaleImage(scalePath, dest, 100);

                AccountProfile user = userService.updateAvatar(profile.getId(), ava100);
                putProfile(user);

                scale = new File(scalePath);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                temp.delete();
                if (scale != null) {
                    scale.delete();
                }
            }
        }
        return "redirect:/account/profile";
    }*/

    /**
     * powered by srx
     * @param file
     * @param model
     * @return
     * @throws IOException
     *//*
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


    /**
     * powered by srx
     * @param file
     * @param model
     * @return
     * @throws IOException
     */

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
//
//    public String getAvaPath(long uid, int size) {
//        String base = FilePathUtils.getAvatar(uid);
//        return String.format("/%s_%d.jpg", base, size);
//    }

}
