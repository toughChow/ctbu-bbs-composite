package bbs.core.persist.service.impl;

import bbs.core.data.Plate;
import bbs.core.data.User;
import bbs.core.persist.dao.PlateDao;
import bbs.core.persist.dao.PostDao;
import bbs.core.persist.entity.PlatePO;
import bbs.core.persist.entity.PostPO;
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

    @Autowired
    private PostDao postDao;

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
        UserPO userPO = one.getUserPO();
        Plate plate = BeanMapUtils.copy(one);
        plate.setManagerId(userPO.getId());
        return plate;
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

    @Override
    public List<Plate> findByParent() {
        List<PlatePO> all = plateDao.findAll();
        List<Plate> plates = new ArrayList<>();
        all.forEach(po -> {
            Plate copy = BeanMapUtils.copy(po);
            // get manager name
            Long parentId = po.getParentId();
            if(parentId == 0) {
                UserPO userPO = po.getUserPO();
                copy.setManager(userPO.getUsername());
            }
            plates.add(copy);
        });
        if(!palteOnly.isEmpty()){
            palteOnly.clear();
        }
        List<Plate> plateTree = getPlateTree(plates, 0L);
        return plateTree;
    }

    @Override
    public Plate findByParent(Long id) {
        PlatePO platePOS = plateDao.findOne(id);
        Long parentId = platePOS.getParentId();
        PlatePO one = plateDao.findOne(parentId);
        Plate copy = BeanMapUtils.copy(one);
        return copy;
    }

    List<Plate>  palteOnly = new ArrayList<>();
    private List<Plate> getPlateTree(List<Plate> menus, Long parentId){
        for(int i = 0; i < menus.size(); i ++) {
            if(menus.get(i).getParentId() == parentId) {
                palteOnly.add(menus.get(i));
                getPlateTree(menus, menus.get(i).getId());
            }
        }
        return palteOnly;
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
