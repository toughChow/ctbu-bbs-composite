package bbs.core.data;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements Serializable {

    private static final long serialVersionUID = 5632204097877392537L;

    private Long id;
    private Integer type; // 消息类型：1 短信 2 邮件
    private String content; // 消息内容
    private Long receiverId; // 接收方id
    private Long senderId; // 发送方id
    private Timestamp pubTime; // 发送时间
    private Integer status; // 状态码
    private String url; // 资源路径
    private String receiver; // 接收方姓名
    private String sender; // 发送方姓名

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Timestamp getPubTime() {
        return pubTime;
    }

    public void setPubTime(Timestamp pubTime) {
        this.pubTime = pubTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }


}
