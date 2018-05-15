package bbs.core.persist.dao;

import bbs.core.persist.entity.CommentPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommentDao extends JpaRepository<CommentPO, Long>, JpaSpecificationExecutor<CommentPO> {

    List<CommentPO> findByPostId(Long postId);

    CommentPO findByParentId(Long parentId);
}
