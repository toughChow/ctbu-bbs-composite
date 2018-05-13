package bbs.core.persist.service;

import bbs.base.data.Data;
import bbs.core.data.AccountProfile;
import bbs.core.data.Plate;
import bbs.core.data.Post;
import bbs.core.data.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    Page<PostType> findTypeList(Pageable pageable, String key);

    void updatePostType(PostType type);

    Data deletePostType(Long id);

    void savePostType(PostType type);

//    Page<Post> findPostListByManager(Pageable pageable, String key, String username);

    Page<Post> findPostListByManagerAndStatus(Pageable pageable, String key, String username, Integer status);

    Data updatePostStatus(Long id, Integer status);

    Page<Post> findPostListByTipOffAndStatus(Pageable pageable, String key, String username, Integer status);

    Page<Post> findPostListByUser(Pageable pageable, String key, String username);

    void updatePost(Post post);

    Page<Post> findCollectedPosts(Pageable pageable, String key, String username);

    Data undoCollect(Long id, long userId);

    List<Post> findPostListByTime();

    Page<Post> findByPlateIdOrderByCreateTimeDesc(Pageable pageable, String key,Long pid,Integer status);

    Plate findPlateByPost(Long id);

    Post findOne(Long id);

    List<PostType> findTypeList();

    void save(Post post, AccountProfile profile);
}
