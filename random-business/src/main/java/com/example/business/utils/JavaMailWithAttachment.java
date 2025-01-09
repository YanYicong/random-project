package com.example.business.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

/**
 * 发送邮件验证码
 */
public class JavaMailWithAttachment {
    private MimeMessage message;
    private Session session;
    private Transport transport;

    private Properties properties = new Properties();
    static Logger logger = LogManager.getLogger(JavaMailWithAttachment.class.getName());

    public JavaMailWithAttachment(boolean debug) {

//        以下配置由于qq的SMTP服务需要SSL加密
//        启用认证
        properties.put("mail.smtp.auth", "true");
//        启用SSL加密
        properties.put("mail.smtp.ssl.enable", "true");
//        SSL加密端口
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.starttls.enable", "true"); // 启用STARTTLS加密（如果服务器支持）
        properties.put("mail.smtp.host", "smtp.qq.com");  // 设置SMTP服务器
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  // 设置SSL套接字工厂

        session = Session.getInstance(properties);
//        开启后有调试信息
//        session.setDebug(debug);
        message = new MimeMessage(session);
    }

    /**
     * 发送邮件
     *
     * @param subject     邮件主题
     * @param sendHtml    邮件内容
     * @param receiveUser 收件人地址
     * @param attachment  附件
     */
    public void doSendHtmlEmail(String subject, String sendHtml, String receiveUser, String cs, File attachment,
                                String emailHost, String emailUser, String emailPassword) throws Exception {
        try {
            // 发件人
            InternetAddress from = new InternetAddress(emailUser);
            message.setFrom(from);

            // 收件人
            InternetAddress[] internetAddressTo = InternetAddress.parse(receiveUser);
            message.setRecipients(Message.RecipientType.TO, internetAddressTo);

            // 抄送
            if (cs != null && !cs.isEmpty()) {
                message.setRecipients(Message.RecipientType.CC, cs); // 抄送人
            }

            // 邮件主题
            message.setSubject(subject);

            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 添加邮件正文
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
            multipart.addBodyPart(contentPart);

            // 添加附件的内容
//            if (attachment != null) {
//                BodyPart attachmentBodyPart = new MimeBodyPart();
//                DataSource source = new FileDataSource(attachment);
//                attachmentBodyPart.setDataHandler(new DataHandler(source));
//                // MimeUtility.encodeWord 可以避免文件名乱码
//                attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
//                multipart.addBodyPart(attachmentBodyPart);
//            }

            // 将multipart对象放到message中
            message.setContent(multipart);
            // 保存邮件
            message.saveChanges();

            transport = session.getTransport("smtp");
            // SMTP验证，就是你用来发邮件的邮箱用户名密码
            transport.connect(emailHost, emailUser, emailPassword);
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());

            logger.info(receiveUser + " -- 邮件发送成功！");
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}