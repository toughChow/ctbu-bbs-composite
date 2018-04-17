package bbs.core.persist.dao;

import bbs.core.persist.entity.PlatePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlateDao extends JpaRepository<PlatePO,Long>,JpaSpecificationExecutor<PlatePO> {

}
