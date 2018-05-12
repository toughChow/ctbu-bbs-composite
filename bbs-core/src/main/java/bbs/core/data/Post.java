package bbs.core.data;

import java.io.Serializable;
import java.sql.Timestamp;

public class Post implements Serializable{

    private static final long serialVersionUID = 7376960406814509745L;

    private Long id;

    private String title;

    private String content;

    private Timestamp createTime;

    private Integer isVerified;

    private Integer status;

    private Long plateId; // 多对一

    private String owner;

    private Long postTypeId; // 一对一

    private Integer tipOff;

    private Integer upvote;

    private String postType;

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getPlateId() {
        return plateId;
    }

    public void setPlateId(Long plateId) {
        this.plateId = plateId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getPostTypeId() {
        return postTypeId;
    }

    public void setPostTypeId(Long postTypeId) {
        this.postTypeId = postTypeId;
    }

    public Integer getTipOff() {
        return tipOff;
    }

    public void setTipOff(Integer tipOff) {
        this.tipOff = tipOff;
    }

    public Integer getUpvote() {
        return upvote;
    }

    public void setUpvote(Integer upvote) {
        this.upvote = upvote;
    }
}
