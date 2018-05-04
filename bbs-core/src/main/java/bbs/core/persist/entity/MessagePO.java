package bbs.core.persist.entity;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "T_PUB_MESSAGE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MessagePO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "TYPE")
  private Integer type; // 消息类型：1 短信 2 邮件

  @Column(name = "CONTENT", length = 500)
  private String content; // 消息内容

  @Column(name = "RECEIVER_ID")
  private Long receiverId; // 接收方

  @Column(name = "SENDER_ID")
  private Long senderId; // 发送方

  @Column(name = "PUB_TIME")
  private Timestamp pubTime; // 发送时间

  @Column(name = "STATUS")
  private Integer status; // 消息状态 0 未读 1 已读 2 已删除

  @Column(name = "URL")
  private String url;// 存放附件路径

  @Column(name = "SENDER_NAME")
  private String sender; // 发送方

  @Column(name = "RECEIVER_NAME")
  private String receiver; // 接收方

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

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }
}
