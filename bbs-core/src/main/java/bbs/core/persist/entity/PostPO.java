package bbs.core.persist.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_PUB_POST")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PostPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CREATE_TIME")
    private Timestamp createTime;

    @Column(name = "is_verified")
    private Integer isVerified;

    @Column(name = "status")
    private Integer status;

    @Column(name = "postType_id")
    private Long postTypeId;

    @Column(name = "plate_id")
    private Long plateId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "TIP_OFF")
    private Integer tipOff;

    @Column(name = "UPVOTE")
    private Integer upvote;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "T_PUB_COLLECT_PAGE", joinColumns = {@JoinColumn(name = "POST_ID")}, inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    @Fetch(FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<UserPO> userPOS = new ArrayList<>();

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

    public Long getPostTypeId() {
        return postTypeId;
    }

    public void setPostTypeId(Long postTypeId) {
        this.postTypeId = postTypeId;
    }

    public Long getPlateId() {
        return plateId;
    }

    public void setPlateId(Long plateId) {
        this.plateId = plateId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public List<UserPO> getUserPOS() {
        return userPOS;
    }

    public void setUserPOS(List<UserPO> userPOS) {
        this.userPOS = userPOS;
    }
}
