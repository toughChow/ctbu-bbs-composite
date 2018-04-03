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
    private long id;

    @Column(name = "USERNAME", unique = true, length = 64)
    private String username;

    @Column(name = "PASSWORD", length = 64)
    private String password;

    @Column(name = "AVATAR")
    private String avatar;

    @Column(name = "NICKNAME", length = 18)
    private String nickname;

    @Column(name = "GENDER")
    private int gender;

    @Column(name = "IS_ADMIN")
    private int isAdmin;

    @Column(name = "EMAIL", unique = true, length = 128)
    private String email;

    @Column(name = "MOBILE", length = 11)
    private String mobile;

    @Column(name = "CREATED")
    private Date created;

    @Column(name = "LAST_LOGIN")
    private Date lastLogin;

    @Column(name = "SIGNATURE")
    private String signature;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "T_PUB_USER_ROLE", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<RolePO> roles = new ArrayList<>();

    @Column(name = "ACTIVE_EMAIL")
    private int activeEmail;

    @Column(name = "STATUS")
    private int status;

    public UserPO() {

    }

    public UserPO(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public List<RolePO> getRoles() {
        return roles;
    }

    public void setRoles(List<RolePO> roles) {
        this.roles = roles;
    }

    public int getActiveEmail() {
        return activeEmail;
    }

    public void setActiveEmail(int activeEmail) {
        this.activeEmail = activeEmail;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }
}
