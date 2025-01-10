package com.example.business.controller;

import com.example.business.config.EmailConfig;
import com.example.business.config.EmailTemplateConfig;
import com.example.business.constants.ExceptionInfoConstants;
import com.example.business.constants.UtilsConstants;
import com.example.business.entity.UserEntity;
import com.example.business.exception.UserInfoException;
import com.example.business.mapper.RandomUserMapper;
import com.example.business.utils.JavaMailWithAttachment;
import com.example.business.utils.JwtUtils;
import com.example.business.utils.Result;
import com.example.business.utils.StringUtils;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.example.business.utils.PasswordUtils.hashPassword;
import static com.example.business.utils.PasswordUtils.verifyPassword;

@RestController
@RequestMapping("/api")
public class UserController {

    @Resource
    private RandomUserMapper randomUserMapper;

//    获取验证码类
    @Resource
    private DefaultKaptcha captchaProducer;

    @Resource
    private EmailConfig emailConfig;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取验证码
     * @param response
     * @throws IOException
     */
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response, HttpServletRequest request) {
//        设置响应头，禁用缓存
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
//        IP作为key
        String key = request.getRemoteAddr();
//        生成验证码文本
        String text = captchaProducer.createText();

//        将验证码存储到 Redis，并设置过期时间
        redisTemplate.opsForValue().set(key, text, 5, TimeUnit.MINUTES);

//        生成验证码图片
        BufferedImage image = captchaProducer.createImage(text);

//        将图片输出到响应流
        response.setContentType("image/jpeg");
//        确保资源会自动关闭
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            ImageIO.write(image, "jpeg", outputStream);
        } catch (IOException e) {
            throw new RuntimeException("验证码加载失败");
        }
    }

    /**
     * 获取邮箱验证码
     *  这里邮件发送方法部分参数内容暂未使用，不传参
     *  邮件内容不通过次配置类相关位置引入，由于是HTML网页所以单独引入
     * @param email
     * @return
     */
    @GetMapping("/emailCaptcha/{email}")
    public Result getEmailCaptcha(@PathVariable String email) throws Exception {
        JavaMailWithAttachment javaMailWithAttachment = new JavaMailWithAttachment(true);
//        获取验证码并存入redis
        String captcha = String.valueOf(new Random().nextInt(9000) + 1000);
        redisTemplate.opsForValue().set(email, captcha, 1, TimeUnit.MINUTES);
//        发送邮件
        try{
            javaMailWithAttachment
                    .doSendHtmlEmail(emailConfig.getSubject(),
                            EmailTemplateConfig.getEmailTemplate(captcha),
                            email,
                            null,
                            new File(""),
                            emailConfig.getEmailHost(),
                            emailConfig.getUserName(),
                            emailConfig.getEmailUser(),
                            emailConfig.getEmailPassword());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserInfoException(ExceptionInfoConstants.EMAIL_ERROR);
        }
        return Result.success(UtilsConstants.RESULT_SUCCESS);
    }

    /**
     * 登录
     * @param response
     * @param user
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws UserInfoException
     */
    @PostMapping("/login")
    public Result userLogin(HttpServletResponse response,
                            HttpServletRequest request,
                            @RequestBody UserEntity user,
                            @RequestParam String captcha) throws NoSuchAlgorithmException, InvalidKeySpecException, UserInfoException {
//        IP作为key
        String key = request.getRemoteAddr();
//        验证码
        boolean captchaValid = validateCaptcha(key, captcha);
        if (!captchaValid) {
            throw new UserInfoException(ExceptionInfoConstants.CAPTCHA_ERROR);
        }
        UserEntity usernameParam = randomUserMapper.queryUserDetailByAll(user);
        user.setEmail(user.getUsername());
        user.setUsername(UtilsConstants.EMPTY_STR);
        UserEntity userEmailParam = randomUserMapper.queryUserDetailByAll(user);
        UserEntity result;
        if (usernameParam != null){
//            使用username登录
//            恢复置空的username
            user.setUsername(user.getEmail());
            if(usernameParam.getUsername().equals(user.getUsername())
                    && verifyPassword(user.getPassword(), usernameParam.getPassword())){
                result = usernameParam;
            }else {
                throw new UserInfoException(ExceptionInfoConstants.USERNAME_PASSWORD_ERROR);
            }
        }else if( userEmailParam != null){
//            使用email登录
            if(userEmailParam.getEmail().equals(user.getEmail())
                && verifyPassword(user.getPassword(), userEmailParam.getPassword())){
                result = userEmailParam;
            }else {
                throw new UserInfoException(ExceptionInfoConstants.USERNAME_PASSWORD_ERROR);
            }
        }else {
            throw new UserInfoException(ExceptionInfoConstants.USERNAME_NOT_FOUND);
        }
//        生成token并存入响应头
        String token = JwtUtils.generateToken(result.getUsername());
        response.setHeader("token", token);
        return Result.success(UtilsConstants.LOGIN_SUCCESS);
    }

    /**
     * 注册
     * @param user
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws UserInfoException
     */
    @PostMapping("/register")
    public Result registerUser(@RequestBody UserEntity user, @RequestParam String emailCaptcha) throws NoSuchAlgorithmException, InvalidKeySpecException, UserInfoException {
//        判断用户是否存在
        int size = randomUserMapper.queryUserDetailNum(user);
        if(size != UtilsConstants.DATA_ZERO_SIZE){
            throw new UserInfoException(ExceptionInfoConstants.USER_ALREADY_EXISTS);
        }
//        验证邮箱
        String captcha = redisTemplate.opsForValue().get(user.getEmail());
        if (captcha == null) {
            throw new UserInfoException(ExceptionInfoConstants.EMAIL_TIMEOUT);
        }
        if(emailCaptcha == null || captcha == null || !captcha.equals(emailCaptcha)){
            throw new UserInfoException(ExceptionInfoConstants.CAPTCHA_ERROR);
        }

//        加密
        user.setPassword(hashPassword(user.getPassword()));
//        数据组装
        user.setId(StringUtils.getUUID());
//        保存用户到数据库
        int log = randomUserMapper.addUserDetail(user);
        System.out.println(log);
        return Result.success(UtilsConstants.REGISTER_SUCCESS);
    }

    /**
     * 注销
     * @param request
     * @param user
     * @return
     */
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request, @RequestBody UserEntity user) {
        String token = request.getHeader("token");

//        删除token(清空临时USERNAME)
        JwtUtils.USERNAME = "";
//        返回注销成功的响应
        return Result.success(UtilsConstants.LOGOUT_SUCCESS);

    }

    /**
     * 验证验证码
     * @param captchaKey
     * @param userInputCaptcha
     * @return
     */
    private boolean validateCaptcha(String captchaKey, String userInputCaptcha) {
        // 从 Redis 获取存储的验证码
        String correctCaptcha = redisTemplate.opsForValue().get(captchaKey);

        // 如果验证码为空或不匹配，返回验证失败
        if (correctCaptcha == null || !correctCaptcha.equalsIgnoreCase(userInputCaptcha)) {
            return false;
        }

        // 删除 Redis 中的验证码，防止重复使用
        redisTemplate.delete(captchaKey);
        return true;
    }
}