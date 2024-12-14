package com.ecommerce.HerbalJeevan.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {
	private final Environment env;

    public CloudinaryConfig(Environment env) {
        this.env = env;
    }
	

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", env.getProperty("cloudnary.cloud.name"),
            "api_key", env.getProperty("cloudnary.api.key"),
            "api_secret", env.getProperty("cloudnary.api.secret"),
            "secure", true
        ));
    }
}
