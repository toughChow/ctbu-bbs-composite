package bbs.core.persist.dao;

import bbs.core.persist.entity.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserDao extends JpaRepository<UserPO, Long>, JpaSpecificationExecutor<UserPO>{

    UserPO findByUsername(String username);

    UserPO findByEmail(String email);

    UserPO findByMobile(String phone);

    List<UserPO> findByGroupId(long l);

    Long countNumberByGroupId(long id);

    List<UserPO> findByGroupPOIsNull();
}
