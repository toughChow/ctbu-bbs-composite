package bbs.core.persist.service;

import bbs.core.data.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {

    Page<Role> paging(Pageable pageable);

    Role get(Long aLong);

    void save(Role role);

    void delete(Long id);

    List<Role> getAll();
}
