package bbs.core.persist.service;

import bbs.base.data.Data;
import bbs.core.data.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<PostType> findTypeList(Pageable pageable, String key);

    void updatePostType(PostType type);

    Data deletePostType(Long id);

    void savePostType(PostType type);
}
