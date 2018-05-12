package bbs.core.persist.dao;

import bbs.core.persist.entity.PostPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostDao extends JpaRepository<PostPO,Long>,JpaSpecificationExecutor<PostPO> {

//    @Query(value = "DELETE FROM t_pub_collect_page t WHERE t.post_id = ?1 AND t.user_id = ?2", nativeQuery = true)
//    void undoCollect(Long id, long userId);

//    @Query(value = "SELECT FROM t_pub_post t ")
//    List<PostPO> findTimeAndLimit7();

    List<PostPO> findByStatusOrderByCreateTimeDesc(int i);

    List<PostPO> findByPlateId(Long id);

    List<PostPO> findByPlateIdAndStatusOrderByCreateTimeDesc(Long pid, Integer status);

    //SELECT * FROM T_PUB_USER U WHERE U.USER_GROUP_ID=?1", nativeQuery = true
}
