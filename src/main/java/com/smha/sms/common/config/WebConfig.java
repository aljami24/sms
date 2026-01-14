package com.smha.sms.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-directory}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //convert file for Windows user
        String uriPath = Paths.get(uploadDir).toUri().toString();
        if (!uriPath.endsWith("/")) {
            uriPath += "/";
        }

        registry.addResourceHandler("/UploadFile/**")
                .addResourceLocations(uriPath);
    }
}
