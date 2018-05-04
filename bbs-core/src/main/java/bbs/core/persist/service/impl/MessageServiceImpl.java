package bbs.core.persist.service.impl;

import bbs.base.utils.MailHelper;
import bbs.core.data.Message;
import bbs.core.persist.dao.MessageDao;
import bbs.core.persist.dao.UserDao;
import bbs.core.persist.entity.MessagePO;
import bbs.core.persist.entity.UserPO;
import bbs.core.persist.service.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailHelper mailHelper;

    public static final String NOTICE_NAME = "notice";

    /**
     *
     * @param message
     * @param type 0 站内信 1 公告 2 邮件 3 群发
     */
    @Override
    public void saveMessage(String name, Message message, Integer type) {
        MessagePO messagePO = new MessagePO();
        UserPO senderUser = userDao.findByUsername(name);
        message.setSenderId(senderUser.getId()); // set sender_id
        message.setPubTime(new Timestamp(System.currentTimeMillis())); // set send time
        message.setSender(name); // sender name
        message.setStatus(0); // status unread
        switch (type){
            case 0:
                message.setType(0); // type message in sys
                UserPO receiverUser = userDao.findByUsername(message.getReceiver());
                message.setReceiverId(receiverUser.getId());
                message.setReceiver(receiverUser.getUsername());
                break;
            case 1:
                message.setType(0); // type message in sys
                message.setReceiverId(0L);
                message.setReceiver(NOTICE_NAME);
                break;
            case 2:
                message.setStatus(-1);
                message.setType(2); // type message in sys
                if(message.getUrl()!=null){
                    message.setUrl(message.getUrl());
                }
                break;
            case 3:
                message.setType(0); // type message in sys
                UserPO receiverUser2 = userDao.findByUsername(message.getReceiver());
                message.setReceiverId(receiverUser2.getId());
                break;
            default:
                break;
        }
        BeanUtils.copyProperties(message, messagePO);
        messageDao.save(messagePO);
    }

    @Override
    @Transactional
    public void saveMail(Message message, boolean P2POrNot) {
        MessagePO messagePO = new MessagePO();
        UserPO senderUser = userDao.findByUsername(message.getSender());
        message.setSenderId(senderUser.getId());
        message.setType(1);
        message.setStatus(0); // 设置为未读
        Timestamp pubTime = new Timestamp(System.currentTimeMillis());
        message.setPubTime(pubTime);
        if(P2POrNot) {
            UserPO receiverUser = userDao.findByUsername(message.getReceiver());
            message.setReceiverId(receiverUser.getId());
        }
        // judge if its p2p or p2m
        if(P2POrNot==false){ // P2M
            message.setReceiverId(0L);
            message.setReceiver(NOTICE_NAME);
        }
        if(message.getUrl()!=null){
            message.setUrl(message.getUrl());
        }
        BeanUtils.copyProperties(message, messagePO);
        messageDao.save(messagePO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Message> paging(Pageable pageable, String key,String status,String username) {
        Page<MessagePO> page
                = messageDao.findAll((Root<MessagePO> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> subPredicates = new ArrayList<>();
                    if (StringUtils.isNotBlank(key)) {
                        String tag = "%" + key + "%";
                        subPredicates.add(cb.like(root.get("content"), tag));
                        predicates.add(cb.or(subPredicates.toArray(new Predicate[]{})));
                    }
                    if (StringUtils.isNotBlank(status)) {
                        subPredicates.add(cb.notEqual(root.get("status"), Integer.parseInt(status)));
                        predicates.add(cb.and(subPredicates.toArray(new Predicate[]{})));
                    }
                    Predicate predicate = cb.or(cb.equal(root.get("receiver"),NOTICE_NAME),cb.equal(root.get("receiver"),username));
                    subPredicates.add(predicate);
                    predicates.add(cb.and(subPredicates.toArray(new Predicate[]{})));
                    return cb.and(predicates.toArray(new Predicate[]{}));
                }, pageable
        );
        List<Message> messages = new ArrayList<>();
        for (MessagePO po : page.getContent()) {
            Message message = new Message();
            BeanUtils.copyProperties(po, message, new String[]{"receiver"});
            messages.add(message);
        }
        return new PageImpl<>(messages, pageable, page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Message> pagingOutbox(Pageable pageable, String key, String sender,String status) {
        Page<MessagePO> page
                = messageDao.findAll((Root<MessagePO> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> subPredicates = new ArrayList<>();
                    if (StringUtils.isNotBlank(key)) {
                        String tag = "%" + key + "%";
                        subPredicates.add(cb.like(root.get("content"), tag));
                        predicates.add(cb.or(subPredicates.toArray(new Predicate[]{})));
                    }
                    if (StringUtils.isNotBlank(status)) {
                        subPredicates.add(cb.notEqual(root.get("status"), Integer.parseInt(status)));
                        predicates.add(cb.and(subPredicates.toArray(new Predicate[]{})));
                    }
                    subPredicates.add(cb.equal(root.get("sender"),sender));
                    predicates.add(cb.and(subPredicates.toArray(new Predicate[]{})));
                    return cb.and(predicates.toArray(new Predicate[]{}));
                }, pageable
        );
        List<Message> messages = new ArrayList<>();
        for (MessagePO po : page.getContent()) {
            Message message = new Message();
            BeanUtils.copyProperties(po, message);
            messages.add(message);
        }
        return new PageImpl<>(messages, pageable, page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Message> pagingRubbishbox(Pageable pageable, String key,String status) {
        Page<MessagePO> page
                = messageDao.findAll((Root<MessagePO> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> subPredicates = new ArrayList<>();
                    if (StringUtils.isNotBlank(key)) {
                        String tag = "%" + key + "%";
                        subPredicates.add(cb.like(root.get("content"), tag));
                        predicates.add(cb.or(subPredicates.toArray(new Predicate[]{})));
                    }
                    if (StringUtils.isNotBlank(status)) {
                        subPredicates.add(cb.equal(root.get("status"), Integer.parseInt(status)));
                        predicates.add(cb.and(subPredicates.toArray(new Predicate[]{})));
                    }
                    return cb.and(predicates.toArray(new Predicate[]{}));
                }, pageable
        );
        List<Message> messages = new ArrayList<>();
        for (MessagePO po : page.getContent()) {
            Message message = new Message();
            BeanUtils.copyProperties(po, message);
            messages.add(message);
        }
        return new PageImpl<>(messages, pageable, page.getTotalElements());
    }

    @Override
    public String sendMessage(Message message,String subject) {
        /* 分为几类邮件格式发送
         * 1.若url为空 发送普通文本格式邮件
         * 2.若url不为空 发送多媒体形式文件
         */
        if(message.getUrl().length()<1){ // 发送simple的email
            mailHelper.sendSimpleMail(message.getReceiver(),subject,message.getContent());
        }else{
            mailHelper.sendAttachmentsMailZip(message.getReceiver(),subject,message.getContent(),message.getUrl());
        }
        return "success";
    }

    @Override
    public void updateStatus(Long id, int statusRemoved) {
        MessagePO messagePO = messageDao.findOne(id);
        if(messagePO != null){
            messagePO.setStatus(statusRemoved);
            messageDao.save(messagePO);
        }
    }

    @Override
    public Message get(Long id) {
        MessagePO messagePO = messageDao.findOne(id);
        Message message = new Message();
        BeanUtils.copyProperties(messagePO,message);
        return message;
    }

    @Override
    public void delete(Long id) {
        messageDao.delete(id);
    }

}
