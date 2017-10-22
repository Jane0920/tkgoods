package com.yys.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/10/22.
 */
@Data
@Component
@ConfigurationProperties(prefix = "uploadImage")
public class ImageConfig {

    private String root;

    private String path;

    public String rootPath() {
        return root + path;
    }

}
