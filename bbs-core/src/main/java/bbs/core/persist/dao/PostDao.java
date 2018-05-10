package bbs.core.persist.dao;

import bbs.core.persist.entity.PostPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PostDao extends JpaRepository<PostPO,Long>,JpaSpecificationExecutor<PostPO> {

    @Query(value = "DELETE FROM t_pub_collect_page t WHERE t.post_id = ?1 AND t.user_id = ?2", nativeQuery = true)
    void undoCollect(Long id, long userId);
    //SELECT * FROM T_PUB_USER U WHERE U.USER_GROUP_ID=?1", nativeQuery = true
}
