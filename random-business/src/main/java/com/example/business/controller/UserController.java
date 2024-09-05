package com.example.business.controller;

import com.example.business.constants.ExceptionInfoConstants;
import com.example.business.constants.UtilsConstants;
import com.example.business.entity.UserEntity;
import com.example.business.exception.UserInfoException;
import com.example.business.mapper.RandomUserMapper;
import com.example.business.utils.JwtUtils;
import com.example.business.utils.Result;
import com.example.business.utils.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static com.example.business.utils.PasswordUtils.hashPassword;
import static com.example.business.utils.PasswordUtils.verifyPassword;

@RestController
@RequestMapping("/api")
public class UserController {

    @Resource
    private RandomUserMapper randomUserMapper;


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
    public Result userLogin(HttpServletResponse response, @RequestBody UserEntity user) throws NoSuchAlgorithmException, InvalidKeySpecException, UserInfoException {
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
    public Result registerUser(@RequestBody UserEntity user) throws NoSuchAlgorithmException, InvalidKeySpecException, UserInfoException {
//        判断用户是否存在
        int size = randomUserMapper.queryUserDetailNum(user);
        if(size != UtilsConstants.DATA_ZERO_SIZE){
            throw new UserInfoException(ExceptionInfoConstants.USER_ALREADY_EXISTS);
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
}