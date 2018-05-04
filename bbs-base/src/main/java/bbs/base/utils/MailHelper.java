package bbs.base.utils;

import bbs.base.lang.BaseException;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class MailHelper {
    @Autowired
    private Environment env;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Autowired
    private JavaMailSender javaMailSender;

    private static Executor executor = Executors.newFixedThreadPool(3);

    /**
     * 发送简单文本邮件(不支持html)
     *
     * @param to      目标邮箱
     * @param subject 邮件主题
     * @param text    邮件内容
     */
    public void sendSimpleMail(String to, String subject, String text) {
        Runnable sendTask = () -> {
            SimpleMailMessage message = new SimpleMailMessage();
            String sender = env.getProperty("spring.mail.username");
            message.setFrom(sender);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            try {
                javaMailSender.send(message);
            } catch (Exception e) {
                throw new BaseException("邮件发送失败", e);
            }
        };
        executor.execute(sendTask);
    }

    /**
     * 发送带有附件的Email
     */
    public void sendAttachmentsMailZip(String to, String subject, String content, String filePath) {
        System.setProperty("mail.mime.splitlongparameters","false");
        MimeMessage message = javaMailSender.createMimeMessage();
        Runnable sendTask = () -> {
            // 多线程发送
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");
                helper.setFrom(env.getProperty("spring.mail.username"));
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(content, true); // 设置内容

                String[] urls = filePath.split(",");
                for (String str : urls) {
                    String fileName = str.substring(str.lastIndexOf("/") + 37, str.length());
                    helper.addAttachment(fileName, new File(str));
                }

                javaMailSender.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        };
        executor.execute(sendTask);
    }

    /**
     * 发送模板邮件
     *
     * @param template
     * @param to
     * @param title
     * @param content
     */
    public void sendEmail(String template, String to, String title, Map<String, Object> content) {
        System.setProperty("mail.mime.splitlongparameters","false");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        Runnable sendTask = () -> {
            // 多线程发送
            try {

                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                helper.setFrom(env.getProperty("spring.mail.username"));
                helper.setTo(to);

                helper.setSubject(title);
                helper.setText(render(template, content), true);

                // 多线程
                javaMailSender.send(mimeMessage);

            } catch (Exception e) {
                throw new BaseException("邮件发送失败", e);
            }
        };
        executor.execute(sendTask);
    }

    public String render(String templateName, Map<String, Object> model) {
        try {
            Template t = freeMarkerConfigurer.getConfiguration().getTemplate(templateName, "UTF-8");
            t.setOutputEncoding("UTF-8");
            return FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        } catch (Exception e) {
            throw new BaseException(e.getMessage(), e);
        }
    }
}
