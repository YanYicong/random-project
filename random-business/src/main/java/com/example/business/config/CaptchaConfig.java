package com.example.business.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.impl.NoNoise;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.code.kaptcha.util.Config;
import javax.annotation.Resource;
import java.util.Properties;

@Configuration
public class CaptchaConfig {

    @Resource
    private CaptchaConfigParam captchaConfigParam;

    @Bean
    public DefaultKaptcha defaultKaptcha() {
        // 1. 获取从配置文件中绑定的配置
        Properties properties = new Properties();
        properties.put("kaptcha.image.width", String.valueOf(captchaConfigParam.getImageWidth()));
        properties.put("kaptcha.image.height", String.valueOf(captchaConfigParam.getImageHeight()));
        properties.put("kaptcha.textproducer.font.size", String.valueOf(captchaConfigParam.getFontSize()));
        properties.put("kaptcha.textproducer.font.name", captchaConfigParam.getTextProducerFontName());
        properties.put("kaptcha.textproducer.font.size", String.valueOf(captchaConfigParam.getTextProducerFontSize()));
        properties.put("kaptcha.textproducer.char.length", String.valueOf(captchaConfigParam.getTextProducerCharLength()));
        properties.put("kaptcha.textproducer.char.space", String.valueOf(captchaConfigParam.getTextProducerCharSpace()));
        properties.put("kaptcha.background.clear.from", captchaConfigParam.getBackgroundColor());
        properties.put("kaptcha.textproducer.font.color", captchaConfigParam.getTextProducerFontColor());
//        properties.put("kaptcha.noise.impl", captchaConfigParam.getNoiseImplClassName());
        properties.setProperty("kaptcha.noise.impl", NoNoise.class.getName());
        // 2. 创建配置对象
        Config config = new Config(properties);

        // 3. 创建验证码生成器并设置配置
        DefaultKaptcha captchaProducer = new DefaultKaptcha();
        captchaProducer.setConfig(config);
        return captchaProducer;
    }
}
