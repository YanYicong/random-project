package com.example.business.config;

/**
 * 邮件模板
 */
public class EmailTemplateConfig {

    /**
     * 根据模板获取邮件
     * @param code
     * @return
     */
    public static String getEmailTemplate(String code) {
        String context = "   <!DOCTYPE html>\n" +
                "            <html lang=\"en\">\n" +
                "            <head>\n" +
                "            <meta charset=\"UTF-8\">\n" +
                "            <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "            <title>验证码</title>\n" +
                "            <style>\n" +
                "              .container {\n" +
                "                width: 80%; /* 容器宽度 */\n" +
                "                margin: 50px auto; /* 居中对齐 */\n" +
                "                padding: 20px;\n" +
                "                background-color: #333; /* 深灰色背景 */\n" +
                "                border-radius: 10px; /* 圆角样式 */\n" +
                "                color: #fff; /* 白色字体 */\n" +
                "                text-align: center; /* 内容居中 */\n" +
                "              }\n" +
                "              .code-box {\n" +
                "                background-color: #444; /* 灰色背景 */\n" +
                "                border: 2px solid #666; /* 灰色边框 */\n" +
                "                padding: 20px;\n" +
                "                margin: 20px auto;\n" +
                "                border-radius: 8px;\n" +
                "              }\n" +
                "              .code {\n" +
                "                font-size: 48px; /* 大字体 */\n" +
                "                color: #1e90ff; /* 蓝色字体 */\n" +
                "                font-weight: bold;\n" +
                "                letter-spacing: 5px; /* 增大字符间距 */\n" +
                "              }\n" +
                "            </style>\n" +
                "          </head>\n" +
                "          <body>\n" +
                "            <div class=\"container\">\n" +
                "              <p>欢迎使用random系统，恭喜您成为本系统第9527位家人！</p>\n" +
                "\t            <p>验证码如下，请在一分钟内使用</p>\n" +
                "              <div class=\"code-box\">\n" +
                "                <span class=\"code\">"+ code +"</span>\n" +
                "              </div>\n" +
                "\t            <p>有任何建议请随时联系我，反正不一定听。使用过程中如果遇到问题，我不表示歉意并且你最好忍一忍^_^</p>\n" +
                "\t            <p>感恩家人</p>\n" +
                "            </div>\n" +
                "          </body>\n" +
                "        </html>";
        return context;
    }
}