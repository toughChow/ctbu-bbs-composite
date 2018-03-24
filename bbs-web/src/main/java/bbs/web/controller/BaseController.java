package bbs.web.controller;

import bbs.base.context.AppContext;
import bbs.base.upload.FileRepoFactory;
import bbs.core.data.AccountProfile;
import bbs.shiro.authc.AccountSubject;
import bbs.web.formatter.StringEscapeEditor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseController {
    @Autowired
    protected HttpSession session;
    @Autowired
    protected AppContext appContext;
    @Autowired
    protected FileRepoFactory fileRepoFactory;

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
    }

    protected AccountSubject getSubject() {
        return (AccountSubject) SecurityUtils.getSubject();
    }

    protected void putProfile(AccountProfile profile) {
        SecurityUtils.getSubject().getSession(true).setAttribute("profile", profile);
    }

    protected AuthenticationToken createAuthToken(String username, String password) {
        return new UsernamePasswordToken(username, DigestUtils.sha1Hex(password));
    }

    protected Pageable wrapPageable() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return new PageRequest(
                ServletRequestUtils.getIntParameter(request, "pn", 1) - 1,
                ServletRequestUtils.getIntParameter(request, "pageSize", 10)
        );
    }

    protected Pageable wrapPageable(Integer pn, Integer pageSize) {
        return new PageRequest(
                pn == null || pn == 0 ? 0 : pn - 1,
                pageSize == null || pageSize == 0 ? 10 : pageSize
        );
    }

    /**
     * @param pn       当前页
     * @param pageSize 分页数
     * @param sort     0-asc;1-desc
     * @param filter
     * @return
     */
    protected Pageable wrapPageable(Integer pn, Integer pageSize, Integer sort, String filter) {
        return new PageRequest(
                pn == null || pn == 0 ? 0 : pn - 1,
                pageSize == null || pageSize == 0 ? 10 : pageSize,
                new Sort(
                        sort == null || sort == 0 ? Sort.Direction.ASC : Sort.Direction.DESC,
                        StringUtils.isBlank(filter) ? "id" : filter
                )
        );
    }

    protected String getSuffix(String name) {
        int pos = name.lastIndexOf(".");
        return name.substring(pos);
    }

    protected String getView(String view) {
        return "/default" + view;
    }

    protected String routeView(String route, String group) {
        String format = "/default" + route;
        return String.format(format, group);
    }

}
