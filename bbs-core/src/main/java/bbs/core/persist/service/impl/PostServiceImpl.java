package bbs.core.persist.service.impl;

import bbs.base.data.Data;
import bbs.core.data.PostType;
import bbs.core.persist.dao.PostTypeDao;
import bbs.core.persist.entity.PostTypePO;
import bbs.core.persist.service.PostService;
import bbs.core.persist.utils.BeanMapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostTypeDao postTypeDao;

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
}
