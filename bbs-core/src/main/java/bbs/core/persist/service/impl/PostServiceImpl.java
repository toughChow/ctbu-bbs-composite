package bbs.core.persist.service.impl;

import bbs.base.data.Data;
import bbs.core.data.AccountProfile;
import bbs.core.data.Plate;
import bbs.core.data.Post;
import bbs.core.data.PostType;
import bbs.core.persist.dao.*;
import bbs.core.persist.entity.*;
import bbs.core.persist.service.PostService;
import bbs.core.persist.utils.BeanMapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostTypeDao postTypeDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PlateDao plateDao;

    @Autowired
    private CommentDao commentDao;

    @Override
    public Page<PostType> findTypeList(Pageable pageable, String key) {
        Page<PostTypePO> page = postTypeDao.findAll(
                (Root<PostTypePO> root, CriteriaQuery<?> cq, CriteriaBuilder cb)
                        -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (StringUtils.isNotBlank(key)) {
                        List<Predicate> subPredicates = new ArrayList<>();
                        String tag = "%" + key + "%";
                        subPredicates.add(cb.like(root.get("name"), tag));
                        predicates.add(cb.or(subPredicates.toArray(new Predicate[]{})));
                    }
                    return cb.and(predicates.toArray(new Predicate[]{}));
                }, pageable
        );
        List<PostType> types = new ArrayList<>();
        page.getContent().forEach(po -> {
            types.add(BeanMapUtils.copy(po));
        });
        return new PageImpl<>(types, pageable, page.getTotalElements());
    }

    @Override
    public void updatePostType(PostType type) {
        Long id = type.getId();
        PostTypePO one = postTypeDao.findOne(id);
        one.setName(type.getName());
        postTypeDao.save(one);
    }

    @Override
    public Data deletePostType(Long id) {
        postTypeDao.delete(id);
        return Data.success("删除成功", Data.NOOP);
    }

    @Override
    public void savePostType(PostType type) {
        PostTypePO po = new PostTypePO();
        BeanUtils.copyProperties(type,po);
        po.setCreateTime(new Timestamp(System.currentTimeMillis()));
        postTypeDao.save(po);
    }

//    @Override
//    public Page<Post> findPostListByManager(Pageable pageable, String key, String username) {
//        UserPO userPO = userDao.findByUsername(username);
//        List<PlatePO> platePOS = plateDao.findByUserPO(userPO);
//
//        Page<PostPO> page = postDao.findAll(
//                (Root<PostPO> root, CriteriaQuery<?> cq, CriteriaBuilder cb)
//                        -> {
//                    List<Predicate> predicates = new ArrayList<>();
//                    List<Predicate> subPredicates = new ArrayList<>();
//
//                    if(platePOS != null && platePOS.size() > 0) {
//                        platePOS.forEach(po -> {
//                        subPredicates.add(cb.equal(root.get("plateId"),po.getId()));
//                        });
//                    }
//
//                    if (StringUtils.isNotBlank(key)) {
//                        String tag = "%" + key + "%";
//                        subPredicates.add(cb.like(root.get("content"), tag));
//                    }
//                    predicates.add(cb.or(subPredicates.toArray(new Predicate[]{})));
//                    return cb.and(predicates.toArray(new Predicate[]{}));
//                }, pageable
//        );
//        List<Post> posts = new ArrayList<>();
//        page.getContent().forEach(po -> {
//            Long userId = po.getUserId();
//            UserPO one = userDao.findOne(userId);
//            Post copy = BeanMapUtils.copy(po);
//            copy.setOwner(one.getUsername());
//            posts.add(copy);
//
//        });
//        return new PageImpl<>(posts, pageable, page.getTotalElements());
//    }

    @Override
    public Page<Post> findPostListByManagerAndStatus(Pageable pageable, String key, String username, Integer status) {
        UserPO userPO = userDao.findByUsername(username);
        List<PlatePO> platePOS = plateDao.findByUserPO(userPO);

        Page<PostPO> page = postDao.findAll(
                (Root<PostPO> root, CriteriaQuery<?> cq, CriteriaBuilder cb)
                        -> {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> subPredicates = new ArrayList<>();

                    subPredicates.add(cb.equal(root.get("status"),status));
                    predicates.add(cb.and(subPredicates.toArray(new Predicate[]{})));


                    if(platePOS != null && platePOS.size() > 0) {
                        platePOS.forEach(po -> {
                            subPredicates.add(cb.equal(root.get("plateId"),po.getId()));
                        });
                    }

                    if (StringUtils.isNotBlank(key)) {
                        String tag = "%" + key + "%";
                        subPredicates.add(cb.like(root.get("content"), tag));
                    }
                    predicates.add(cb.or(subPredicates.toArray(new Predicate[]{})));
                    return cb.and(predicates.toArray(new Predicate[]{}));
                }, pageable
        );
        List<Post> posts = new ArrayList<>();
        page.getContent().forEach(po -> {
            Long userId = po.getUserId();
            UserPO one = userDao.findOne(userId);
            Post copy = BeanMapUtils.copy(po);
            copy.setOwner(one.getUsername());
            posts.add(copy);
        });
        return new PageImpl<>(posts, pageable, page.getTotalElements());
    }

    @Override
    public Data updatePostStatus(Long id, Integer status) {
        PostPO one = postDao.getOne(id);
        one.setStatus(status);
        postDao.save(one);
        return Data.success("执行成功", Data.NOOP);
    }

    @Override
    public Page<Post> findPostListByTipOffAndStatus(Pageable pageable, String key, String username, Integer status) {
        UserPO userPO = userDao.findByUsername(username);
        List<PlatePO> platePOS = plateDao.findByUserPO(userPO);

        Page<PostPO> page = postDao.findAll(
                (Root<PostPO> root, CriteriaQuery<?> cq, CriteriaBuilder cb)
                        -> {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> subPredicates = new ArrayList<>();

                    subPredicates.add(cb.equal(root.get("status"),status));
                    subPredicates.add(cb.gt(root.get("tipOff"),10));
                    predicates.add(cb.and(subPredicates.toArray(new Predicate[]{})));


                    if(platePOS != null && platePOS.size() > 0) {
                        platePOS.forEach(po -> {
                            subPredicates.add(cb.equal(root.get("plateId"),po.getId()));
                        });
                    }

                    if (StringUtils.isNotBlank(key)) {
                        String tag = "%" + key + "%";
                        subPredicates.add(cb.like(root.get("content"), tag));
                    }
                    predicates.add(cb.or(subPredicates.toArray(new Predicate[]{})));
                    return cb.and(predicates.toArray(new Predicate[]{}));
                }, pageable
        );
        List<Post> posts = new ArrayList<>();
        page.getContent().forEach(po -> {
            Long userId = po.getUserId();
            UserPO one = userDao.findOne(userId);
            Post copy = BeanMapUtils.copy(po);
            copy.setOwner(one.getUsername());
            posts.add(copy);
        });
        return new PageImpl<>(posts, pageable, page.getTotalElements());
    }

    // Mine Model

    @Override
    public Page<Post> findPostListByUser(Pageable pageable, String key, String username) {
        UserPO userPO = userDao.findByUsername(username);
        Long id = userPO.getId();
        Pageable p=new PageRequest(pageable.getPageNumber(),pageable.getPageSize(), Sort.Direction.DESC,"createTime");
        Page<PostPO> page = postDao.findAll(
                (Root<PostPO> root, CriteriaQuery<?> cq, CriteriaBuilder cb)
                        -> {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> subPredicates = new ArrayList<>();
                    subPredicates.add(cb.equal(root.get("userId"),id));
                    predicates.add(cb.and(subPredicates.toArray(new Predicate[]{})));
                    if (StringUtils.isNotBlank(key)) {
                        String tag = "%" + key + "%";
                        subPredicates.add(cb.like(root.get("content"), tag));
                    }
                    predicates.add(cb.or(subPredicates.toArray(new Predicate[]{})));
                    return cb.and(predicates.toArray(new Predicate[]{}));
                }, p
        );
        List<Post> posts = new ArrayList<>();
        page.getContent().forEach(po -> {
            Post copy = BeanMapUtils.copy(po);
            posts.add(copy);
        });
        return new PageImpl<>(posts, pageable, page.getTotalElements());

    }

    @Override
    public void updatePost(Post post) {
        Long id = post.getId();
        PostPO one = postDao.findOne(id);
        one.setTitle(post.getTitle());
        one.setContent(post.getContent());
        one.setCreateTime(new Timestamp(System.currentTimeMillis()));
        postDao.save(one);
    }

    @Override
    public Page<Post> findCollectedPosts(Pageable pageable, String key, String username) {
        UserPO userPO = userDao.findByUsername(username);
        Long id = userPO.getId();

        Page<PostPO> postPOS=postDao.findAll(
                (Root<PostPO> root, CriteriaQuery<?> cq, CriteriaBuilder cb)
                ->{
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> subpredicates = new ArrayList<>();
                    ListJoin<PostPO,UserPO> join  = root.join(root.getModel().getList("userPOS",UserPO.class),JoinType.LEFT);
                    Predicate predicate=cb.equal(join.get("id").as(Long.class),id);
                    predicates.add(cb.and(predicate));
                    return cb.and(predicates.toArray(new Predicate[]{}));
                },pageable
        );
        List<Post> posts = new ArrayList<>();
        postPOS.getContent().forEach(po -> {
            Long userId = po.getUserId();
            UserPO one = userDao.findOne(userId);
            Post copy = BeanMapUtils.copy(po);
            copy.setOwner(one.getUsername());
            posts.add(copy);
        });
        return new PageImpl<>(posts, pageable, postPOS.getTotalElements());
    }

    @Override
    public Data undoCollect(Long id, long userId) {
        PostPO one = postDao.findOne(id);
        UserPO userPO = userDao.findOne(userId);
        List<UserPO> userPOS = one.getUserPOS();
        userPOS.remove(userPO);
        one.setUserPOS(userPOS);
        postDao.save(one);
        return Data.success("操作成功", Data.NOOP);
    }

    @Override
    public List<Post> findPostListByTime() {
//        List<PostPO> postPOSs = postDao.findTimeAndLimit7();
        List<PostPO> postPOS = postDao.findByStatusOrderByCreateTimeDesc(1);
        List<Post> posts = new ArrayList<>();
        if(postPOS.size()>7) {
            for (int i = 0; i < 7; i++) {
                PostPO postPO = postPOS.get(i);
                Long postTypeId = postPO.getPostTypeId();
                PostTypePO one = postTypeDao.findOne(postTypeId);
                String name = one.getName();
                Post copy = BeanMapUtils.copy(postPO);
                copy.setPostType(name);
                posts.add(copy);
            }
        } else {
            for (int i = 0; i < postPOS.size(); i++) {
                PostPO postPO = postPOS.get(i);
                Long postTypeId = postPO.getPostTypeId();
                PostTypePO one = postTypeDao.findOne(postTypeId);
                String name = one.getName();
                Post copy = BeanMapUtils.copy(postPO);
                copy.setPostType(name);
                posts.add(copy);
            }
        }
        return posts;
    }

    @Override
    public Page<Post> findByPlateIdOrderByCreateTimeDesc(Pageable pageable, String key, Long pid,Integer status) {
        Pageable p=new PageRequest(pageable.getPageNumber(),pageable.getPageSize(), Sort.Direction.DESC,"createTime");
        Page<PostPO> page = postDao.findAll(
                (Root<PostPO> root, CriteriaQuery<?> cq, CriteriaBuilder cb)
                        -> {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> subPredicates = new ArrayList<>();

                    subPredicates.add(cb.equal(root.get("plateId"),pid));
                    subPredicates.add(cb.equal(root.get("status"),status));
                    predicates.add((cb.and(subPredicates.toArray(new Predicate[]{}))));

                    if (StringUtils.isNotBlank(key)) {
                        String tag = "%" + key + "%";
                        subPredicates.add(cb.like(root.get("content"), tag));
                    }
                    predicates.add(cb.or(subPredicates.toArray(new Predicate[]{})));

                    return cb.and(predicates.toArray(new Predicate[]{}));
                }, p
        );
        List<Post> posts = new ArrayList<>();
        page.getContent().forEach(po -> {
            Post copy = BeanMapUtils.copy(po);
            Long userId = po.getUserId();
            UserPO one = userDao.findOne(userId);
            copy.setOwner(one.getUsername());
            posts.add(copy);
        });
        return new PageImpl<>(posts, pageable, page.getTotalElements());
    }

    @Override
    public Plate findPlateByPost(Long id) {
        PostPO one = postDao.findOne(id);
        Long plateId = one.getPlateId();
        PlatePO platePO = plateDao.findOne(plateId);
        Plate plate = BeanMapUtils.copy(platePO);
        return plate;
    }

    @Override
    public Post findOne(Long id) {
        PostPO one = postDao.findOne(id);
        Post copy = BeanMapUtils.copy(one);
        return copy;
    }

    @Override
    public List<PostType> findTypeList() {
        List<PostTypePO> all = postTypeDao.findAll();
        List<PostType> types = new ArrayList<>();
        all.forEach(po -> {
            PostType copy = BeanMapUtils.copy(po);
            types.add(copy);
        });
        return types;
    }

    @Override
    public void save(Post post, AccountProfile profile) {
        PostPO po = new PostPO();
        BeanUtils.copyProperties(post,po);
        po.setCreateTime(new Timestamp(System.currentTimeMillis()));
        po.setIsVerified(0);
        po.setStatus(0);
        po.setUserId(profile.getId());
        po.setTipOff(0);
        po.setUpvote(0);
        postDao.save(po);
    }

    @Override
    public Data commentPost(Long id, String content, long commentorId) {
        CommentPO commentPO = new CommentPO();
        commentPO.setContent(content);
        commentPO.setCommentorId(commentorId);
        commentPO.setPostId(id);
        commentPO.setCommentTime(new Timestamp(System.currentTimeMillis()));
        commentPO.setTipOff(0);
        commentPO.setUpvote(0);
        commentPO.setPostId(0L);
        commentDao.save(commentPO);
        return Data.success("评论成功", Data.NOOP);
    }
}
