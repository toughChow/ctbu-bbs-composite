package bbs.core.persist.service;

import bbs.core.data.AccountProfile;
import bbs.core.data.AuthMenu;
import bbs.core.data.Group;
import bbs.core.data.User;
import bbs.core.persist.entity.UserPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    AccountProfile getProfileByName(String username);

    User getByUsername(String username);

    AccountProfile login(String username, String s);

    User register(User post);

    boolean findUserByMobile(String phone);

    Page<User> findUserPaging(Pageable pageable, String key);

    Page<Group> findGroupPaging(Pageable pageable, String key);


    List<User> findAllUsers();

    UserPO findUserById(Long managerId);

    Page<Group> findGroupList(Pageable pageable, String key);

    void saveGroup(Group userGroup);

    Boolean deleteGroup(String userGroupId);

    Page<User> pagingGroupMember(Pageable pageable, Long groupId, String key);

    Group findById(long groupId);

    List<User> findGroupPOIsNull();

    Boolean setUserGroupManager(Long userId, Long groupId);

    void addMembers(Long l, Long groupId);

    void removeGroupMember(Long l);

    List<User> findUserLike(String username);

    User findUserByName(String username);

    List<String> getUsersByGroupId(Long groupId);

    List<Group> findAllGroup();

    User get(Long id);

    AccountProfile update(User user);

    void updatePassword(long id, String password);

    User findByPostId(Long id);

    AccountProfile updateAvatar(Long id, String path);
}
