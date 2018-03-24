package bbs.core.persist.service;

import bbs.core.data.AuthMenu;

import java.util.List;

public interface AuthMenuService {

    List<AuthMenu> findByParentId(long parentId);

    List<AuthMenu> tree(Long id);

    List<AuthMenu> listAll();

    AuthMenu get(Long id);

    void save(AuthMenu authMenu);

    void delete(Long id);
}
