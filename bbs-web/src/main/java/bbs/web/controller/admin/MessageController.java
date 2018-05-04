package bbs.web.controller.admin;

import bbs.base.data.Data;
import bbs.core.data.Group;
import bbs.core.data.Message;
import bbs.core.persist.service.MessageService;
import bbs.core.persist.service.UserService;
import bbs.web.controller.BaseController;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private SimpMessagingTemplate template;

    // todo 更改为网络路径
    public static final String uploadDesc = "D:/file/";

    /**
     * 查询所有用户群组
     */
    @ResponseBody
    @RequestMapping("/findAllGroups")
    public List<Group> findAllGroup() {
        List<Group> userGroups = userService.findAllGroup();
        return userGroups;
    }

    /**
     * 转发消息
     *
     * @return
     */
    @PostMapping("/transmit_message")
    public String transmit(Message message, Principal principal) {
        sendAnnouncement(principal, message,null);
        return "redirect:/admin/message/log";
    }

    @GetMapping("/transmit_message")
    public String transmitMessage(Long id, ModelMap model) {
        model.put("message", messageService.get(id));
        return "/admin/message/transmit_message";
    }

    @GetMapping("/send")
    public String send(String title, ModelMap model, HttpServletRequest request) {
        return "/admin/message/send";
    }

    /**
     * 根据status获取信息
     *
     * @return
     */
    @PostMapping("/send")
    public String send() {
        return null;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Data delete(Long id) {
        messageService.updateStatus(id, 2);
        Data data = Data.success("操作成功", Data.NOOP);
        return data;
    }

    /**
     * 彻底删除消息记录
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteFromDb")
    @ResponseBody
    public Data deleteFromDb(Long id) {
        messageService.delete(id);
        Data data = Data.success("操作成功", Data.NOOP);
        return data;
    }

    /**
     * 更改状态码为1 已读
     *
     * @param id
     * @return
     */
    @RequestMapping("/changeStatus1")
    @ResponseBody
    public Data changeStatus1(Long id) {
        messageService.updateStatus(id, 1);
        Data data = Data.success("操作成功", Data.NOOP);
        return data;
    }

    /**
     * 重定向到发件箱
     *
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/outbox")
    public String outbox(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("status", "0");
        return "redirect:/admin/message/sendlog";
    }

    /**
     * 查看发件记录
     *
     * @param model
     * @param key
     * @param pageNo
     * @param principal
     * @return
     */
    @RequestMapping("/sendlog")
    public String sendLog(ModelMap model, String key, @RequestParam(value = "pn", defaultValue = "1") String pageNo, Principal principal) {
        Integer pn = pageNo.equals(null) ? 1 : Integer.parseInt(pageNo);
        Page<Message> messages = messageService.pagingOutbox(wrapPageable(pn, null, 1, null), key,
                principal.getName(), "2");
        model.put("page", messages);
        model.put("pn", messages.getNumber() + 1);
        model.put("key", key);
        return "/admin/message/sendlog";
    }

    /**
     * 收件箱 已读
     *
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/inbox")
    public String readed(RedirectAttributes redirectAttributes) {
        return "redirect:/admin/message/log";
    }

    /**
     * 查看收件箱 已删除
     *
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/rubbishbox")
    public String rubbishBox(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("status", "2");
        return "redirect:/admin/message/centerlog";
    }

    /**
     * 查看所有记录
     *
     * @param model
     * @param key
     * @param pageNo
     * @return
     */
    @RequestMapping("/centerlog")
    public String centerLog(ModelMap model, String key, @RequestParam(value = "pn", defaultValue = "1") String pageNo) {
        Integer pn = pageNo.equals(null) ? 1 : Integer.parseInt(pageNo);
        Page<Message> messages = messageService.pagingRubbishbox(wrapPageable(pn, null, 1, null), key, "2");
        model.put("page", messages);
        model.put("pn", messages.getNumber() + 1);
        model.put("key", key);
        return "/admin/message/centerlog";
    }

    /**
     * 查看分页消息日志，并可关键字查询
     *
     * @param model
     * @param key
     * @return
     */
    @RequestMapping("/log")
    public String log(ModelMap model, String key, @RequestParam(value = "status", defaultValue = "0") String status, @RequestParam(value = "pn", defaultValue = "1") String pageNo, Principal principal) {
        status = model.get("status") != null ? (String) model.get("status") : status;
        Integer pn = pageNo.equals(null) ? 1 : Integer.parseInt(pageNo);
        Page<Message> messages = messageService.paging(wrapPageable(pn, null, 1, null), key, "2", principal.getName());
        model.put("page", messages);
        model.put("pn", messages.getNumber() + 1);
        model.put("key", key);
        model.put("status", status);
        return "/admin/message/log";
    }

    /**
     * 点对点站内信息传输
     *
     * @param message
     */
    @MessageMapping("/chat")
    public void handleChatP2P(Principal principal, String jsonStr, Message message) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);
        String groupIdStr = (String) jsonObject.get("groupId");
        if(!groupIdStr.equals("-1")){ // it is sended by group
            long groupId = Long.parseLong(groupIdStr);
            List<String> usernames = userService.getUsersByGroupId(groupId); // get all receiver names
            String thisUser = principal.getName();
            if(usernames.contains(thisUser)){
                usernames.remove(thisUser);// if contents user himself,then remove it.
            }
            sendAnnouncement(principal, message,usernames);
        }else {
            sendAnnouncement(principal, message, null);
        }
    }

    /**
     * 发送公告信息
     * 设置receiverid 为 0
     *
     * @param message
     */
    @MessageMapping("/board")
    public void handleBoard(Principal principal, Message message) {
        String currentUser = principal.getName();
        message.setSender(currentUser);
        template.convertAndSend("/topic/notifications", message.getContent());
        template.convertAndSendToUser(currentUser, "/queue/notifications", "yes");
        // 保存日志
        messageService.saveMessage(currentUser, message,1);
    }

    /**
     * 发送电子邮箱
     *
     * @param message
     * @param subject
     * @return
     */
    @PostMapping("/email")
    public String sendEmail(Principal principal, Message message, String subject) {
        messageService.sendMessage(message, subject);
        messageService.saveMessage(principal.getName(), message, 2);
        return "/admin/message/sendSuccess";
    }

    /**
     * 异步上传文件
     *
     * @param emailFile
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/uploadFile")
    public String uploadFile(MultipartFile[] emailFile) throws IOException {
        StringBuffer sb = new StringBuffer();

        if(!new File(uploadDesc).exists()){
            new File(uploadDesc).mkdir();
        }

        int i = 1; // 添加逗号
        for (MultipartFile file : emailFile) {
            String descPath = uploadDesc + UUID.randomUUID().toString() + file.getOriginalFilename();
            File fileTemp = new File(descPath);
            file.transferTo(fileTemp);
            if (emailFile.length > i) {
                sb.append(descPath + ",");
            } else {
                sb.append(descPath);
            }
            i++;
        }
        return sb.toString();
    }

    /**
     * 发送消息方法抽取
     *
     * @param principal
     * @param message
     */
    public void sendAnnouncement(Principal principal, Message message,List<String> usernames) {
        String currentUser = principal.getName();
        if(usernames == null) { // single receiver
            // 获取receiver用户名
            String receiverName = message.getReceiver();
            if (receiverName.equals(currentUser)) { // 若sender与receiver相同
                template.convertAndSendToUser(currentUser, "/queue/notifications", "no");
            } else {
                message.setSender(currentUser);
                messageService.saveMessage(currentUser, message,0);
                template.convertAndSendToUser(currentUser, "/queue/notifications", "yes");
                template.convertAndSendToUser(receiverName,
                        "/queue/notifications", message.getContent());
            }
        }else{
            for (String str :
                    usernames) {
                if (str.equals(currentUser)) {
                    template.convertAndSendToUser(currentUser, "/queue/notifications", "no");
                    continue;
                } else {
                        message.setSender(currentUser);
                        message.setReceiver(str);
                        messageService.saveMessage(currentUser, message, 3);
                    template.convertAndSendToUser(str,
                            "/queue/notifications", message.getContent());
                }
            }
            template.convertAndSendToUser(currentUser, "/queue/notifications", "yes");
        }
    }
}