package bbs.core.persist.service;

import bbs.core.data.Plate;
import bbs.core.persist.entity.PlatePO;
import bbs.core.persist.entity.UserPO;

import java.util.List;

public interface PlateService {
    List<Plate> findAll();

    Plate findOne(Long id);

    void save(Plate plate, UserPO userPO);

    void delete(Long id);

}
