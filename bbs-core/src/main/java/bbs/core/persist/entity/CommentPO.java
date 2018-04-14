package bbs.core.persist.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "T_PUB_COMMENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CommentPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "COMMENT_TIME")
    private Timestamp commentTime;

    @Column(name = "TIP_OFF")
    private Integer tipOff;

    @Column(name = "UPVOTE")
    private Integer upvote;

    @Column(name = "PARENT_ID")
    private Long parentId;

    @Column(name = "COMMENTOR_ID")
    private Long commentorId;

    @Column(name = "POST_ID")
    private Long postId;

    @Column(name = "SEQUENCE")
    private Integer sequence;

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
}
