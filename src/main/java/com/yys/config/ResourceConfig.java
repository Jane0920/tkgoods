package com.yys.config;

import me.jiangcai.lib.resource.service.ResourceService;
import me.jiangcai.lib.resource.service.impl.VFSResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Created by xyr on 2017/10/23.
 */
@Configuration
public class ResourceConfig {

    private String uri = "http://";
    private String home = "";
    private int port = 8080;
    private String prefix = "tkGoods/images/";

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ImageConfig imageConfig;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Bean
    public ResourceService resourceService() {
        /*if (webApplicationContext == null)
            throw new IllegalStateException("ResourceService required web Environment.");
        try {
            String serverName = webApplicationContext.getServletContext().getVirtualServerName();
            uri  += serverName.substring(serverName.indexOf("/")+1, serverName.length());
        } catch (AbstractMethodError ignored) {
            uri = "http://localhost";
        }
        uri = uri  + ":" + port + "/" + prefix;*/
        home += imageConfig.rootPath();
        uri = imageConfig.getUriRoot();
        ResourceService resourceService = new VFSResourceService(uri, home, port, webApplicationContext, prefix);
        return resourceService;
    }

}
