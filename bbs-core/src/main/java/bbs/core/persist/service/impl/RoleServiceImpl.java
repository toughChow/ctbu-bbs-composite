package bbs.core.persist.service.impl;

import bbs.core.data.AuthMenu;
import bbs.core.data.Role;
import bbs.core.persist.dao.AuthMenuDao;
import bbs.core.persist.dao.RoleDao;
import bbs.core.persist.entity.AuthMenuPO;
import bbs.core.persist.entity.RolePO;
import bbs.core.persist.service.RoleService;
import bbs.core.persist.utils.BeanMapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AuthMenuDao authMenuDao;

    @Override
    @Transactional(readOnly = true)
    public Page<Role> paging(Pageable pageable) {
        Page<RolePO> page = roleDao.findAllByOrderByIdDesc(pageable);
        List<Role> roles = new ArrayList<>();
        page.getContent().forEach(po -> {
            roles.add(BeanMapUtils.copy(po));
        });

        return new PageImpl<>(roles, pageable, page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Role get(Long id) {
        RolePO po = roleDao.findOne(id);
        Role role = BeanMapUtils.copy(po);
        return role;
    }

    @Override
    @Transactional
    public void save(Role role) {
        RolePO rolePO = new RolePO();
        List<AuthMenu> authMenus = role.getAuthMenus();
        List<AuthMenuPO> authMenuPOs = new ArrayList<>();
        for (AuthMenu authMenu : authMenus) {
            AuthMenuPO authMenuPO = authMenuDao.findOne(authMenu.getId());
            authMenuPOs.add(authMenuPO);
        }
        BeanUtils.copyProperties(role, rolePO);
        rolePO.setAuthMenus(authMenuPOs);
        roleDao.save(rolePO);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        roleDao.delete(id);
    }

    @Override
    public List<Role> getAll() {
        List<RolePO> list = roleDao.findAll();
        List<Role> roles = new ArrayList<>();
        list.forEach(po -> {
            roles.add(BeanMapUtils.copy(po));
        });
        return roles;
    }

}

