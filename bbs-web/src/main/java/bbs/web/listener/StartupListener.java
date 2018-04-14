package bbs.web.listener;

import bbs.base.context.AppContext;
import bbs.base.lang.Consts;
import bbs.base.print.Printer;
import bbs.base.utils.PropertiesLoader;
import bbs.core.data.Config;
import bbs.core.persist.service.ConfigService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.*;

@Component
public class StartupListener implements InitializingBean, ServletContextAware {
    @Autowired
    private ConfigService configService;
//    @Autowired
//    private GroupService groupService;
    @Autowired
    private AppContext appContext;

    private ServletContext servletContext;

    private void loadParams() {
        Printer.info("加载配置-Loading Configuration ...");
        try {
            PropertiesLoader p = new PropertiesLoader(Consts.BBS_CONFIG);
            System.setProperty(Consts.SYSTEM_VERSION, p.getProperty(Consts.SYSTEM_VERSION));
        } catch (Exception e) {
            Printer.error("Load Configuration Exception: {} ", e.getMessage());
            System.exit(0);
        }

        Printer.info("Loading Configuration End");
    }

    private void loadConfig() {
        Timer timer = new Timer("loadConfig", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Printer.info("Loading start...");

                List<Config> configs = configService.findAll();
                Map<String, String> configMap = new HashMap<>();

                if (configs.isEmpty()) {
                    Printer.error("");
                    System.exit(1);
                } else {

                    if (configs.size() < 13) {
                        Printer.warn("Config not exist, config size in int: {}, config size in String: {}", configs.size(), String.valueOf(configs.size()));
                    }
                    configs.forEach(conf -> configMap.put(conf.getKey(), conf.getValue()));
                }

                appContext.setConfig(configMap);

                Printer.info("Loading finish");
            }
        }, 3 * Consts.TIME_MIN);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void afterPropertiesSet() {
        loadParams();
        loadConfig();
    }
}
