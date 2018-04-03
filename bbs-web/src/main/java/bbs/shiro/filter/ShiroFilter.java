package bbs.shiro.filter;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import java.io.IOException;

public class ShiroFilter extends GenericFilterBean {
    private Filter delegate;

    protected void initFilterBean() throws ServletException {
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        delegate = (Filter) wac.getBean(this.getFilterName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        delegate.doFilter(request, response, chain);
    }

}
