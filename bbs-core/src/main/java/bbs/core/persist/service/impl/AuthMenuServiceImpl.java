package bbs.core.persist.service.impl;

import bbs.core.data.AuthMenu;
import bbs.core.persist.dao.AuthMenuDao;
import bbs.core.persist.entity.AuthMenuPO;
import bbs.core.persist.service.AuthMenuService;
import bbs.core.persist.utils.BeanMapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AuthMenuServiceImpl implements AuthMenuService {

    @Autowired
    private AuthMenuDao authMenuDao;

    @Override
    public List<AuthMenu> findByParentId(long parentId) {
        // TODO Auto-generated method stub
        List<AuthMenu> authMenus = new ArrayList<>();
        List<AuthMenuPO> authMenuPOs = authMenuDao.findAllByParentIdOrderBySortAsc(parentId);
        if (!authMenuPOs.isEmpty()) {
            for (AuthMenuPO po : authMenuPOs) {
                List<AuthMenuPO> lists = authMenuDao.findAllByParentIdOrderBySortAsc(po.getId());
                if (!lists.isEmpty()) {
                    po.setChildren(lists);
                }
                AuthMenu authMenu = BeanMapUtils.copy(po);
                authMenus.add(authMenu);
            }
        }
        return authMenus;
    }

    @Override
    public List<AuthMenu> tree(Long id) {

        List<AuthMenu> menus = new ArrayList<>();
        AuthMenuPO authMenuPO = authMenuDao.findOne(id);
        AuthMenu authMenu = BeanMapUtils.copy(authMenuPO);
        menus.add(authMenu);
        if (authMenu.getChildren() != null) {
//			List<AuthMenu> sortedList = sort(authMenu.getChildren());
            for (AuthMenu po : authMenu.getChildren()) {
                menus.addAll(tree(po.getId()));
            }
        }
        return menus;
    }

    @Override
    public List<AuthMenu> findAllMenu() {
        List<AuthMenuPO> authMenuPOS = authMenuDao.findAll();
        List<AuthMenu> authMenus = new ArrayList<>();
        authMenuPOS.forEach(authMenuPO -> {
            AuthMenu authMenu = new AuthMenu();
            if(authMenuPO.getSort() != 0) {
                BeanUtils.copyProperties(authMenuPO, authMenu, new String[]{"permission", "parentIds", "icon"});
                authMenus.add(authMenu);
            }
        });
        return authMenus;
    }

    @Override
    public AuthMenu get(Long id) {
        AuthMenu authMenu = BeanMapUtils.copy(authMenuDao.findOne(id));
        return authMenu;
    }

    @Override
    public void save(AuthMenu authMenu) {
        AuthMenuPO po = new AuthMenuPO();
        BeanUtils.copyProperties(authMenu, po);
        authMenuDao.save(po);
    }

    @Override
    public void delete(Long id) {
        AuthMenuPO authMenuPO = authMenuDao.findOne(id);
        if (authMenuPO.getChildren() != null) {
            for (AuthMenuPO po : authMenuPO.getChildren()) {
                delete(po.getId());
            }
        }
        authMenuDao.delete(authMenuPO);
    }

//	private List<AuthMenu> sort(List<AuthMenu> list) {
//		for(int i=0;i<list.size();i++){
//			for(int j=list.size()-1;j>i;j--){
//				if(list.get(i).getSort()>list.get(j).getSort()){
//					AuthMenu temp = list.get(i);
//					list.set(i,list.get(j));
//					list.set(j,temp);
//				}
//			}
//		}
//		return list;
//	}

}
