package bbs.core.persist.dao;

import bbs.core.persist.entity.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDao extends JpaRepository<UserPO, Long>, JpaSpecificationExecutor<UserPO>{

    UserPO findByUsername(String username);
}
