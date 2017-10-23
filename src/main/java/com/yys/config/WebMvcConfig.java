package com.yys.config;

import me.jiangcai.lib.resource.ResourceSpringConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by xyr on 2017/10/16.
 */
@Configuration
@EnableWebMvc
@Import(ResourceSpringConfig.class)
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ImageConfig imageConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/images/*").addResourceLocations("file:" + imageConfig.rootPath());
        registry.addResourceHandler("/manage/images/*").addResourceLocations("file:" + imageConfig.rootPath());
        registry.addResourceHandler("/manage/editGoodHtml/images/*").addResourceLocations("file:" + imageConfig.rootPath());
    }

}
