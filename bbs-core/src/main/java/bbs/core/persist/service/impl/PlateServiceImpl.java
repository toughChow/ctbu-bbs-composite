package bbs.core.persist.service.impl;

import bbs.core.data.Plate;
import bbs.core.persist.dao.PlateDao;
import bbs.core.persist.entity.PlatePO;
import bbs.core.persist.entity.UserPO;
import bbs.core.persist.service.PlateService;
import bbs.core.persist.utils.BeanMapUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public void save(Plate plate, UserPO userPO) {
        Long id = plate.getId();
        if(id == null) {
            PlatePO platePO = new PlatePO();
            BeanUtils.copyProperties(plate, platePO);
            platePO.setStatus(0);
            Timestamp timeStamp = new Timestamp(new Date().getTime());
            platePO.setCreatTime(timeStamp);
            String username = (String) SecurityUtils.getSubject().getSession().getAttribute("username");
            platePO.setCreator(username);
            platePO.setUserPO(userPO);
            plateDao.save(platePO);
        }else {
            PlatePO platePO = plateDao.getOne(id);
            platePO.setParentId(plate.getParentId());
            platePO.setName(plate.getName());
            platePO.setUserPO(userPO);
            plateDao.save(platePO);
        }
    }

    @Override
    public void delete(Long id) {
        PlatePO one = plateDao.findOne(id);
        if(one != null) {
            deleteItAndChildren(id);
            plateDao.delete(one);
        }
    }

    public void deleteItAndChildren(Long id) {
        List<PlatePO> regionPOS = plateDao.findByParentId(id);
        regionPOS.forEach(po -> {
            deleteItAndChildren(po.getId());
        });
        regionPOS.forEach(po -> {
            plateDao.delete(po.getId());
        });
    }
}
