package bbs.core.persist.dao;

import bbs.core.persist.entity.PostPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostDao extends JpaRepository<PostPO,Long>,JpaSpecificationExecutor<PostPO> {
}
