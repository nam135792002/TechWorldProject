package com.techworld.admin;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dv063hc5n",
                "api_key", "687437493594597",
                "api_secret", "QWZbIceJjNN9uQMNEhl3xLJ6_G8",
                "secure",true));
    }
}
