package bbs.core.persist.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "T_PUB_USER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME", unique = true, length = 64)
    private String username;

    @Column(name = "PASSWORD", length = 64)
    private String password;

    @Column(name = "AVATAR")
    private String avatar;

    @Column(name = "NICKNAME", length = 18)
    private String nickname;

    @Column(name = "GENDER")
    private Integer gender;

    @Column(name = "IS_ADMIN")
    private Integer isAdmin;

    @Column(name = "EMAIL", unique = true, length = 128)
    private String email;

    @Column(name = "MOBILE", length = 11)
    private String mobile;

    @Column(name = "CREATED")
    private Date created;

    @Column(name = "LAST_LOGIN")
    private Date lastLogin;

    @Column(name="USER_IP")
    private String user_ip;

    @Column(name = "SIGNATURE")
    private String signature;

    @Column(name = "ACTIVE_EMAIL")
    private Integer activeEmail;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name="USER_GROUP_ID")
    private Long groupId;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinColumn(name="USER_GROUP_ID",referencedColumnName = "ID",insertable = false, updatable = false)
    private GroupPO groupPO;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "T_PUB_USER_ROLE", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<RolePO> roles = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "T_PUB_COLLECT_PAGE", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "POST_ID")})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<PostPO> posts = new ArrayList<>();

    public UserPO() {

    }

    public UserPO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<RolePO> getRoles() {
        return roles;
    }

    public void setRoles(List<RolePO> roles) {
        this.roles = roles;
    }

    public Integer getActiveEmail() {
        return activeEmail;
    }

    public void setActiveEmail(Integer activeEmail) {
        this.activeEmail = activeEmail;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<PostPO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostPO> posts) {
        this.posts = posts;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public GroupPO getGroupPO() {
        return groupPO;
    }

    public void setGroupPO(GroupPO groupPO) {
        this.groupPO = groupPO;
    }
}
