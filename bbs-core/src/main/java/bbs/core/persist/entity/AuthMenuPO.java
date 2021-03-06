package bbs.core.persist.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_PUB_AUTH_MENU")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuthMenuPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "URL")
    private String url;

    @Column(name = "SORT")
    private Integer sort;

    @Column(name = "PERMISSION")
    private String permission;

    @Column(name = "PARENT_ID")
    private Long parentId;

    @Column(name = "PARENT_IDS")
    private String parentIds;

    @Column(name = "ICON")
    private String icon;

    @ManyToMany(mappedBy = "authMenus", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<RolePO> roles = new ArrayList<>();

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<RolePO> getRoles() {
        return roles;
    }

    public void setRoles(List<RolePO> roles) {
        this.roles = roles;
    }

    public List<AuthMenuPO> getChildren() {
        return children;
    }

    public void setChildren(List<AuthMenuPO> children) {
        this.children = children;
    }
}
