package bbs.core.data;

import bbs.core.data.User;
import bbs.core.persist.entity.UserPO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Post implements Serializable{

    private static final long serialVersionUID = 7376960406814509745L;

    private Long id;

    private String content;

    private Timestamp createTime;

    private Integer isVerified;

    private Integer status;

    private User userPO; // 多对一

    private Long plateId; // 多对一

    private Long postTypeId; // 一对一

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUserPO() {
        return userPO;
    }

    public void setUserPO(User userPO) {
        this.userPO = userPO;
    }

    public Long getPlateId() {
        return plateId;
    }

    public void setPlateId(Long plateId) {
        this.plateId = plateId;
    }

    public Long getPostTypeId() {
        return postTypeId;
    }

    public void setPostTypeId(Long postTypeId) {
        this.postTypeId = postTypeId;
    }
}
