package bbs.core.persist.service.impl;

import bbs.base.data.Data;
import bbs.core.data.*;
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

                    if (StringUtils.isNotBlank(key)) {
                        String tag = "%" + key + "%";
                        subPredicates.add(cb.like(root.get("content"), tag));
                    }

                    subPredicates.add(cb.equal(root.get("status"),status));
                    predicates.add(cb.and(subPredicates.toArray(new Predicate[]{})));


                    if(platePOS != null && platePOS.size() > 0) {
                        platePOS.forEach(po -> {
                            subPredicates.add(cb.equal(root.get("plateId"),po.getId()));
                        });
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
        po.setUserId(profile.getId());
        po.setTipOff(0);
        po.setUpvote(0);
        if(profile.getRoleId() == 1) {
            po.setStatus(1);
        }
        po.setStatus(0);
        postDao.save(po);
    }

    @Override
    public Data commentPost(Long id, String content, long commentorId) {
        // 获取post的user
        PostPO one = postDao.findOne(id);
        Long userId = one.getUserId();

        CommentPO commentPO = new CommentPO();
        commentPO.setReceiverId(userId);
        commentPO.setContent(content);
        commentPO.setCommentorId(commentorId);
        commentPO.setPostId(id);
        commentPO.setCommentTime(new Timestamp(System.currentTimeMillis()));
        commentPO.setTipOff(0);
        commentPO.setUpvote(0);
        commentPO.setParentId(0L);
        commentDao.save(commentPO);
        return Data.success("评论成功", Data.NOOP);
    }

    @Override
    public List<Comment> findCommentByPostId(Long postId) {
        List<CommentPO> commentPOS = commentDao.findByPostId(postId);
        List<Comment> comments = new ArrayList<>();
        commentPOS.forEach(po -> {
            // 获取评论者信息
            Long commentorId = po.getCommentorId();
            UserPO one = userDao.findOne(commentorId);
            User copy = BeanMapUtils.copy(one, 0);
            Comment comment = BeanMapUtils.copy(po);
            comment.setUser(copy);
            comments.add(comment);
        });
        return comments;
    }

    @Override
    public Data collectPost(Long id, long thisUserId) {
        UserPO one = userDao.findOne(thisUserId);
        List<PostPO> posts = one.getPosts();
        PostPO two = postDao.findOne(id);
        posts.add(two);
        one.setPosts(posts);
        userDao.save(one);
        return Data.success("收藏成功", Data.NOOP);
    }

    // 获取帖子的评论 以及 帖子的回复
    @Override
    public Page<Comment> findComments(Pageable pageable, String key, long userId) {
        List<PostPO> posts = postDao.findByUserId(userId);
        Pageable p=new PageRequest(pageable.getPageNumber(),pageable.getPageSize(), Sort.Direction.DESC,"commentTime");
        Page<CommentPO> page = commentDao.findAll(
                (Root<CommentPO> root, CriteriaQuery<?> cq, CriteriaBuilder cb)
                        -> {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> subPredicates = new ArrayList<>();

                    posts.forEach( post -> {
                        Long postId = post.getId();
                        subPredicates.add(cb.equal(root.get("postId"),postId));
//                        predicates.add((cb.and(subPredicates.toArray(new Predicate[]{}))));
                    });

                    // 接收者id
                    subPredicates.add(cb.equal(root.get("receiverId"),userId));

                    if (StringUtils.isNotBlank(key)) {
                        String tag = "%" + key + "%";
                        subPredicates.add(cb.like(root.get("content"), tag));
                    }
                    predicates.add(cb.or(subPredicates.toArray(new Predicate[]{})));

                    return cb.and(predicates.toArray(new Predicate[]{}));
                }, p
        );
        List<Comment> comments = new ArrayList<>();
        page.getContent().forEach(po -> {
            Comment copy = BeanMapUtils.copy(po);
            comments.add(copy);
        });
        return new PageImpl<>(comments, pageable, page.getTotalElements());
    }

    @Override
    public Data replyComment(Long id, String content, long commentorId) {

        CommentPO commentOne = commentDao.findOne(id);
        // 获取commentorId 设置为此次回复的 receiverId
        Long parentId = commentOne.getParentId();
        CommentPO comment = commentDao.findByParentId(parentId); // parent

        Long thisReiveicerId = comment.getCommentorId();
        CommentPO commentPO = new CommentPO();
        commentPO.setReceiverId(thisReiveicerId);
        commentPO.setContent(content);
        commentPO.setCommentorId(commentorId);
        commentPO.setPostId(comment.getPostId());
        commentPO.setCommentTime(new Timestamp(System.currentTimeMillis()));
        commentPO.setTipOff(0);
        commentPO.setUpvote(0);
        commentPO.setParentId(id);
        commentDao.save(commentPO);
//
//        CommentPO one = commentDao.findOne(commentPO.getId());
//        one.setParentId(one.getId());
//        commentDao.save(one);
        return Data.success("评论成功", Data.NOOP);
    }

    @Override
    public Data deleteMyComment(Long id) {
        commentDao.delete(id);
        return Data.success("删除成功", Data.NOOP);
    }

    @Override
    public Data deletePost(Long id){
        postDao.delete(id);
        return Data.success("删除成功", Data.NOOP);
    }
}
