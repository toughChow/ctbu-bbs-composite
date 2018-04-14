package bbs.core.persist.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//@Entity
@Table(name = "T_PUB_GROUP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CREATE_TIME", updatable = false, insertable = true)
    @CreatedDate
    private Timestamp create_time = new Timestamp(System.currentTimeMillis());

    @Column(name = "UPDATE_TIME", updatable = true, insertable = false)
    private Timestamp updateTime;

    @Column(name = "DELETE_TIME")
    private Timestamp deleteTime;

    @Column(name = "USER_ID")
    private String userid;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<UserPO> userPOList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Timestamp getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Timestamp deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<UserPO> getUserPOList() {
        return userPOList;
    }

    public void setUserPOList(List<UserPO> userPOList) {
        this.userPOList = userPOList;
    }
}
