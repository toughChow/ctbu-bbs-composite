package bbs.core.persist.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_PUB_PLATE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlatePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PLATE_NAME")
    private String name;

    @Column(name = "PLATE_CREATOR")
    private String creator;

    @Column(name = "CREATE_TIME")
    private Timestamp creatTime;

    @Column(name = "PARENT_ID")
    private Long parentId;

    @Column(name = "STATUS")
    private Integer status;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "MANAGER_ID", referencedColumnName = "ID")
    private UserPO userPO; // 多对一

    @Transient
    private List<AuthMenuPO> children = new ArrayList<>();

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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Timestamp getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Timestamp creatTime) {
        this.creatTime = creatTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public UserPO getUserPO() {
        return userPO;
    }

    public void setUserPO(UserPO userPO) {
        this.userPO = userPO;
    }

    public List<AuthMenuPO> getChildren() {
        return children;
    }

    public void setChildren(List<AuthMenuPO> children) {
        this.children = children;
    }
}
