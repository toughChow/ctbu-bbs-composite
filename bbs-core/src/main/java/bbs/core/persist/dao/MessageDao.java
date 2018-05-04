package bbs.core.persist.dao;

import bbs.core.persist.entity.MessagePO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface MessageDao extends JpaRepository<MessagePO,Long>, JpaSpecificationExecutor<MessagePO>{

    /**
     * 根据状态码查询消息
     * @param pageable
     * @return
     */
    Page<MessagePO> findAllByStatusOrderByPubTimeDesc(int status, Pageable pageable);
}
