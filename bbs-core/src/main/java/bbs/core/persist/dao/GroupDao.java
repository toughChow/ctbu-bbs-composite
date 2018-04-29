package bbs.core.persist.dao;

import bbs.core.persist.entity.GroupPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GroupDao extends JpaRepository<GroupPO, Long>, JpaSpecificationExecutor<GroupPO> {

    GroupPO findById(Long groupId);
}
