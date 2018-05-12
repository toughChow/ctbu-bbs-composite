package bbs.core.persist.service.impl;

import bbs.base.lang.Consts;
import bbs.base.print.Printer;
import bbs.core.data.AccountProfile;
import bbs.core.data.Group;
import bbs.core.data.User;
import bbs.core.persist.dao.*;
import bbs.core.persist.entity.GroupPO;
import bbs.core.persist.entity.PostPO;
import bbs.core.persist.entity.RolePO;
import bbs.core.persist.entity.UserPO;
import bbs.core.persist.service.UserService;
import bbs.core.persist.utils.BeanMapUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.security.auth.login.AccountNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthMenuDao authMenuDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PostDao postDao;

    @Override
    public AccountProfile getProfileByName(String username) {
        UserPO userPO = userDao.findByUsername(username);
        AccountProfile accountProfile;

        Assert.notNull(userPO, "账户不存在");

        Assert.state(userPO.getStatus() != Consts.STATUS_LOCKED, "您的账户被锁定");
        userPO.setLastLogin(Calendar.getInstance().getTime());

        accountProfile = BeanMapUtils.copyPassport(userPO);

        return accountProfile;
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        UserPO userPO = userDao.findByUsername(username);
        User user = null;
        if (userPO != null) {
            user = BeanMapUtils.copy(userPO, 1);
        }
        return user;
    }

    @Override
    public AccountProfile login(String username, String password) {
        UserPO userPO = userDao.findByUsername(username);

        if (userPO == null) {
            throw new UnknownAccountException();
        }

        if (userPO.getStatus() == Consts.STATUS_LOCKED) {
            throw new LockedAccountException();
        }

        if (!StringUtils.equals(userPO.getPassword(), password)) {
            throw new IncorrectCredentialsException();
        }

        userPO.setLastLogin(Calendar.getInstance().getTime());
        userDao.save(userPO);
        return BeanMapUtils.copyPassport(userPO);
    }

    @Override
    @Transactional
    public User register(User user) {
        Assert.notNull(user, "Parameter user can not be null!");

        Assert.hasLength(user.getUsername(), "用户名不能为空!");
        Assert.hasLength(user.getPassword(), "密码不能为空!");

        UserPO check = userDao.findByUsername(user.getUsername());

        Assert.isNull(check, "用户名已经存在!");

        if (StringUtils.isNotBlank(user.getEmail())) {
            check = userDao.findByEmail(user.getEmail());
            Assert.isNull(check, "邮箱已经被注册!");
        }

        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(user, userPO);
        userPO.setPassword(DigestUtils.sha1Hex(user.getPassword()));
        userPO.setStatus(Consts.USER_ENABLED);
        userPO.setActiveEmail(Consts.USER_ENABLED);
        userPO.setCreated(Calendar.getInstance().getTime());
        // set role
        List<RolePO> rolePOS = new ArrayList<>();
        RolePO one = roleDao.findOne(2L);
        rolePOS.add(one);
        userPO.setRoles(rolePOS);
        userDao.save(userPO);

        return BeanMapUtils.copy(userPO, 0);
    }

    @Override
    public boolean findUserByMobile(String phone) {
        UserPO userPO = userDao.findByMobile(phone);
        if (userPO != null)
            return true; // this phone exist
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findUserPaging(Pageable pageable, String key) {
        Page<UserPO> page
                = userDao.findAll((Root<UserPO> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (StringUtils.isNotBlank(key)) {
                        List<Predicate> subPredicates = new ArrayList<>();
                        String tag = "%" + key + "%";
                        subPredicates.add(cb.like(root.get("username"), tag));
                        subPredicates.add(cb.like(root.get("nickname"), tag));
                        subPredicates.add(cb.like(root.get("mobile"), tag));
                        predicates.add(cb.or(subPredicates.toArray(new Predicate[]{})));
                    }
                    return cb.and(predicates.toArray(new Predicate[]{}));
                }, pageable
        );

        List<User> users = new ArrayList<>();
        for (UserPO po : page.getContent()) {
            User user = BeanMapUtils.copy(po, 1);
            users.add(user);
        }

        return new PageImpl<>(users, pageable, page.getTotalElements());
    }

    @Override
    public Page<Group> findGroupPaging(Pageable pageable, String key) {
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        List<UserPO> userPOS = userDao.findAll();
        List<User> user = new ArrayList<>();
        userPOS.forEach(userPO -> {
            User userTmp = BeanMapUtils.copy(userPO, 0);
            user.add(userTmp);
        });
        return user;
    }

    @Override
    public UserPO findUserById(Long managerId) {
        return userDao.findOne(managerId);
    }

    /**
     * 查询群组列表
     *
     * @param pageable
     * @return 返回page封装对象
     */
    @Override
    @Transactional(readOnly = true)
    @CacheEvict(value = "GroupList", key = "#key")
    public Page<Group> findGroupList(Pageable pageable, String key) {
        Page<GroupPO> page = groupDao.findAll(
                (Root<GroupPO> root, CriteriaQuery<?> cq, CriteriaBuilder cb)
                        -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (StringUtils.isNotBlank(key)) {
                        List<Predicate> subPredicates = new ArrayList<>();
                        Boolean result = key.matches("^[0-9]*$");
                        if (result) {
                            String tag = "%" + key + "%";
                            subPredicates.add(cb.equal(root.get("id").as(Integer.class), key));
                        } else {
                            String tag = "%" + key + "%";
                            subPredicates.add(cb.like(root.get("name"), tag));
                        }
                        predicates.add(cb.or(subPredicates.toArray(new Predicate[]{})));
                    }
                    return cb.and(predicates.toArray(new Predicate[]{}));
                }, pageable
        );
        groupDao.findAll();
        List<Group> userGroups = new ArrayList<>();
        List userGroupNum = new ArrayList();
        for (GroupPO userGroupPO : page.getContent()) {
            Group userGroup = BeanMapUtils.copy(userGroupPO);
            Long num = userDao.countNumberByGroupId(userGroup.getId());
            userGroup.setCountNum(num);
            if (userGroup.getUserid() != null) {
                UserPO userPO = userDao.findOne(Long.parseLong(userGroup.getUserid()));
                if (userPO != null) {
                    userGroup.setMobile(userPO.getMobile());
                    userGroup.setUserGroupName(userPO.getUsername());
                } else {
                    Printer.info("数据库表字段类型不匹配");
                }
            } else {
                Printer.info("用户组暂时没有群主");
            }
            userGroups.add(userGroup);
        }

        return new PageImpl<>(userGroups, pageable, page.getTotalElements());
    }

    @Override
    public void saveGroup(Group userGroup) {
        GroupPO userGroupPO = new GroupPO();
        BeanUtils.copyProperties(userGroup, userGroupPO);
        userGroupPO.setCreate_time(new Timestamp(System.currentTimeMillis()));
        groupDao.save(userGroupPO);
    }

    @Override
    public Boolean deleteGroup(String ids) {
        if (ids != null) {
            String id[] = ids.split(",");
            for (int i = 0; i < id.length; i++) {
                String groupId = id[i];
                List<UserPO> user = userDao.findByGroupId(Long.parseLong(groupId));
                if (user.size() != 0) {
                    for (int j = 0; j < user.size(); j++) {
                        user.get(j).setGroupId(null);
                        userDao.save(user.get(j));
                        if (j == (user.size() - 1)) {
                            Long gid = Long.parseLong(groupId);
                            groupDao.delete(gid);
                        }
                    }
                } else {
                    Long gid = Long.parseLong(groupId);
                    groupDao.delete(gid);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Page<User> pagingGroupMember(Pageable pageable, Long groupId, String key) {
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(key)) {
                    String tag = "%" + key + "%";
                    predicates.add(cb.and(cb.like(root.get("username"), tag)));
                }
                predicates.add(cb.equal(root.get("groupPO").get("id"), groupId));
                return cb.and(predicates.toArray(new Predicate[]{}));
            }
        };
        Page<UserPO> page = userDao.findAll(specification, pageable);
        List<User> users = new ArrayList<>();
        for (UserPO userPO : page.getContent()) {
            User user = new User();
            BeanUtils.copyProperties(userPO, user);
            GroupPO userGroupPO = groupDao.findById(groupId);
            Long userId = null;
            if (userGroupPO.getUserid() != null) {
                userId = Long.parseLong(userGroupPO.getUserid());
            }
            if (user.getId() == userId) {
                user.setIsGroupManager(0);  //0表示群主  1表示普通成员
            } else {
                user.setIsGroupManager(1);
            }
            users.add(user);
        }
        return new PageImpl<>(users, pageable, page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Group findById(long groupId) {
        GroupPO userGroupP = groupDao.findById(groupId);
        Group userGroup = new Group();
        BeanUtils.copyProperties(userGroupP, userGroup);
        return userGroup;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findGroupPOIsNull() {
        List<User> userlist = new ArrayList<>();
        List<UserPO> userPOS = userDao.findByGroupPOIsNull();
        for (UserPO userPO : userPOS) {
            User user = new User();
            BeanUtils.copyProperties(userPO, user, "password", "avatar", "roles", "activeEmail");
            userlist.add(user);
        }
        return userlist;
    }

    @Override
    @Transactional
    public Boolean setUserGroupManager(Long userId, Long groupId) {
        GroupPO userGroupPo = groupDao.findById(groupId);
        userGroupPo.setUserid(userId.toString());
        groupDao.save(userGroupPo);
        UserPO userPO = userDao.findOne(userId);
        userPO.setGroupId(groupId);
        userDao.save(userPO);
        return true;
    }

    @Override
    public void addMembers(Long id, Long groupId) {
        UserPO user = userDao.findOne(id);
        user.setGroupId(groupId);
        userDao.save(user);
    }

    @Override
    public void removeGroupMember(Long id) {
        UserPO user = userDao.findOne(id);
        user.setGroupId(null);
        userDao.save(user);
    }

    @Override
    public List<User> findUserLike(String username) {
        List<UserPO> usersPO = userDao.findAll((Root<UserPO> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.and(cb.like(root.get("username"), "%" + username + "%"));
        });
        List<User> users = new ArrayList<>();
        usersPO.forEach(po -> {
            User user = new User();
            user.setId(po.getId());
            user.setUsername(po.getUsername());
            users.add(user);
        });
        return users;
    }

    @Override
    public User findUserByName(String username) {

        User user = new User();
        try {
            UserPO userPO = userDao.findByUsername(username);
            user.setUsername(userPO.getUsername());
            user.setId(userPO.getId());
        } catch (NullPointerException e) {
        }
        if (user != null) {
            return user;
        }
        return null;
    }

    @Override
    public List<String> getUsersByGroupId(Long groupId) {
        List<UserPO> users = userDao.findGroupId(groupId);
        List<String> usernames = new ArrayList<>();
        users.forEach(userPO -> {
            usernames.add(userPO.getUsername());
        });
        return usernames;
    }

    @Override
    public List<Group> findAllGroup() {
        List<GroupPO> userGroupPOS = groupDao.findAll();
        List<Group> userGroups = new ArrayList<>();
        userGroupPOS.forEach(userGroupTemp -> {
            Group userGroup = new Group();
            userGroup.setId(userGroupTemp.getId());
            userGroup.setName(userGroupTemp.getName());
            userGroups.add(userGroup);
        });
        return userGroups;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "usersCaches", key = "#userId")
    public User get(Long id) {
        UserPO userPO = userDao.findOne(id);
        User ret = null;
        if (userPO != null) {
            ret = BeanMapUtils.copy(userPO, 1);
        }
        return ret;
    }

    @Override
    public AccountProfile update(User user) {
        UserPO userPO = userDao.findOne(user.getId());
        if (userPO != null) {
            userPO.setNickname(user.getNickname());
            userPO.setSignature(user.getSignature());
            userDao.save(userPO);
        }
        return BeanMapUtils.copyPassport(userPO);
    }

    @Override
    public void updatePassword(long id, String oldPassword, String password) {
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException();
        }

        UserPO userPO = userDao.findById(id);
        if (userPO == null) {
            try {
                throw new AccountNotFoundException();
            } catch (AccountNotFoundException e) {
                e.printStackTrace();
            }
        }
        // TODO: 2018/4/28 是否加setCityId
        userPO.setPassword(DigestUtils.sha1Hex(password));
        userDao.save(userPO);
    }

    @Override
    public User findByPostId(Long id) {
        PostPO one = postDao.findOne(id);
        Long userId = one.getUserId();
        UserPO userPO = userDao.findOne(userId);
        User copy = BeanMapUtils.copy(userPO, 0);
        return copy;
    }


}
