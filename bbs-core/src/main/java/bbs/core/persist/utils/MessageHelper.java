package bbs.core.persist.utils;

import bbs.base.print.Printer;
import bbs.core.exceptions.UserException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessageHelper {

    final static String product = "Dysmsapi";//固定的模式
    final static String domain = "dysmsapi.aliyuncs.com";//访问的域名

    final static String accessKeyId = "LTAIV4HGQE1rHHxP";//短信里面的AKI
    final static String accessKeySecret = "Q7Wz34xQzx3qHlKwmMYwU0cZHTZYHc";//短信里面的AKS

    static String signName = "CTBU论坛";//短信发过去的开始名字
    static String code = "SMS_128565078";//短信模板的code码

    static String phone = "17623677587";//用户手机号

    /**
     * send code defined by yourself
     * @param phone
     * @param codeStr
     * @throws IOException
     * @throws UserException
     */
    public static void sendCodeDefinedByYourself(String phone,String codeStr) {
        Printer.info("messageCodeFromServer={}",codeStr);

        try {
            //初始化ascClient,暂时不支持多region（请勿修改）
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                    accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(signName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(code);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            request.setTemplateParam("{ \"code\":\""+codeStr+"\"}");
            //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
            //  request.setSmsUpExtendCode(paramMap.get("extendCode"));
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            //request.setSmsUpExtendCode("90997");
            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            System.out.println("Message -> " + sendSmsResponse.getCode()); // 查看返回代码
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                //请求成功
                System.out.println("success");
            } else {
                System.out.println("false");
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
