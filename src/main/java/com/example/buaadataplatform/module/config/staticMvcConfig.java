package com.example.buaadataplatform.module.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//@Configuration
public class staticMvcConfig extends WebMvcConfigurationSupport {

//    @Value("${static.root.path}")
    String staticPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/static/**").addResourceLocations("file: C:/work/20200530/src/static");
    }
}
