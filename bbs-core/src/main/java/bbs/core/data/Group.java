package bbs.core.data;

import bbs.core.persist.entity.UserPO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Group implements Serializable {
    private static final long serialVersionUID = 4264359286718556524L;

    private long id;

    private String name;

    private Timestamp create_time;

    private Timestamp update_time;

    private Timestamp delete_time;

    private List<UserPO> userPOList=new ArrayList<UserPO>();

    private long countNum;

    private String userid;

    private String mobile;

    private String userGroupName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }

    public Timestamp getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(Timestamp delete_time) {
        this.delete_time = delete_time;
    }

    public List<UserPO> getUserPOList() {
        return userPOList;
    }

    public void setUserPOList(List<UserPO> userPOList) {
        this.userPOList = userPOList;
    }

    public long getCountNum() {
        return countNum;
    }

    public void setCountNum(long countNum) {
        this.countNum = countNum;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }
}
