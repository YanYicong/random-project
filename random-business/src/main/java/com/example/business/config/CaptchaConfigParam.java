package com.example.business.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kaptcha")
public class CaptchaConfigParam {
    private int imageWidth;
    private int imageHeight;
    private int fontSize;
    private String textProducerFontName;
    private int textProducerFontSize;
    private int textProducerCharLength;
    private int textProducerCharSpace;
    private String backgroundColor;
    private String textProducerFontColor;
    private String noiseImplClassName;
}
