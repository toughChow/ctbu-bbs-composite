package bbs.core.persist.dao;

import bbs.core.persist.entity.PostTypePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostTypeDao extends JpaRepository<PostTypePO,Long>,JpaSpecificationExecutor<PostTypePO> {
}
