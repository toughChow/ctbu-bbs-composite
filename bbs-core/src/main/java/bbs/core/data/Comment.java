package bbs.core.data;

import java.io.Serializable;
import java.sql.Timestamp;

public class Comment implements Serializable{

    private Long id;

    private String content;

    private Timestamp commentTime;

    private Integer tipOff;

    private Integer upvote;

    private Long parentId;

    private Long commentorId;

    private Long postId;

    private Integer sequence;

    public User user;

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

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getCommentorId() {
        return commentorId;
    }

    public void setCommentorId(Long commentorId) {
        this.commentorId = commentorId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
