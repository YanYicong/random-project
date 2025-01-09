package com.example.business.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * email发送配置
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "email")
public class EmailConfig {
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String sendHtml;
    /**
     * 收件人
     */
    private String receiveUser;
    /**
     * 抄送
     */
    private String cs;
    /**
     * 附件
     */
    private File attachment;
    /**
     * 邮件服务器主机
     */
    private String emailHost;
    /**
     * 发送人
     */
    private String emailUser;
    /**
     * 发送人密码
     */
    private String emailPassword;
}
