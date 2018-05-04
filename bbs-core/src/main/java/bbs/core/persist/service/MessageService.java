package bbs.core.persist.service;

import bbs.core.data.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {

    /**
     * 保存信息
     * @param message
     * @param type
     */
    void saveMessage(String name, Message message, Integer type);

    // todo 优化更新抽取为一个方法
    /**
     * @param message
     * @param P2POrNot 判断是否为点对点发送
     */
    void saveMail(Message message, boolean P2POrNot);

    /**
     * 检索分页
     * @param pageable
     * @param key
     * @return
     */
    Page<Message> paging(Pageable pageable, String key, String status, String username);

    /**
     * 发件箱分页
     * @param pageable
     * @param key
     * @return
     */
    Page<Message> pagingOutbox(Pageable pageable, String key, String sender, String status);

    Page<Message> pagingRubbishbox(Pageable pageable, String key, String s);


    /**
     *
     * @param message
     * @return
     */
    String sendMessage(Message message, String subject);

    /**
     * 更新日志状态
     * @param id
     * @param statusRemoved
     */
    void updateStatus(Long id, int statusRemoved);

    Message get(Long id);

    void delete(Long id);
}
