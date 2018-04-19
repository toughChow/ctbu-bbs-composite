package bbs.core.data;

import bbs.core.data.User;
import bbs.core.persist.entity.UserPO;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Post {
    private Long id;

    private String content;

    private Timestamp createTime;

    private Integer isVerified;

    private Integer status;

    private User userPO; // 多对一

    private Plate platePO; // 多对一

    private PostType postType; // 一对一

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

    public Plate getPlatePO() {
        return platePO;
    }

    public void setPlatePO(Plate platePO) {
        this.platePO = platePO;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }
}
