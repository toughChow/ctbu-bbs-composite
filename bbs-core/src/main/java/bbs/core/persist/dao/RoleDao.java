package bbs.core.persist.dao;

import bbs.core.persist.entity.RolePO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleDao extends JpaRepository<RolePO, Long>, JpaSpecificationExecutor<RolePO> {
    Page<RolePO> findAllByOrderByIdDesc(Pageable pageable);
}
