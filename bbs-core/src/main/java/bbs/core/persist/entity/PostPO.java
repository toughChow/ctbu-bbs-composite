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

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CREATE_TIME")
    private Timestamp createTime;

    @Column(name = "is_verified")
    private Integer isVerified;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATOR_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private UserPO userPO; // 多对一

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "PLATE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private PlatePO platePO; // 多对一

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "T_PUB_COLLECT_PAGE", joinColumns = {@JoinColumn(name = "POST_ID")}, inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    @Fetch(FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<UserPO> users = new ArrayList<>();

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

    public List<UserPO> getUsers() {
        return users;
    }

    public void setUsers(List<UserPO> users) {
        this.users = users;
    }

    public UserPO getUserPO() {
        return userPO;
    }

    public void setUserPO(UserPO userPO) {
        this.userPO = userPO;
    }

    public PlatePO getPlatePO() {
        return platePO;
    }

    public void setPlatePO(PlatePO platePO) {
        this.platePO = platePO;
    }
}
