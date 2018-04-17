package bbs.core.persist.service.impl;

import bbs.core.data.Plate;
import bbs.core.persist.dao.PlateDao;
import bbs.core.persist.entity.PlatePO;
import bbs.core.persist.service.PlateService;
import bbs.core.persist.utils.BeanMapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlateServiceImpl implements PlateService{

    @Autowired
    private PlateDao plateDao;

    @Override
    public List<Plate> findAll() {
        List<PlatePO> all = plateDao.findAll();
        List<Plate> plates = new ArrayList<>();
        all.forEach(platePO -> {
            Plate copy = BeanMapUtils.copy(platePO);
            plates.add(copy);
        });
        return plates;
    }

    @Override
    public Plate findOne(Long id) {
        PlatePO one = plateDao.findOne(id);
        return BeanMapUtils.copy(one);
    }
}
