package com.site.utils;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * @author 王萍
 * @date 2018/1/25 0025
 */
public class Md5Util {

    private final static Md5PasswordEncoder encoder = new Md5PasswordEncoder();

    private final static String salt = "wanwanppwanwan";

    public static String encode(String pwd) {
        return encoder.encodePassword(pwd, salt);
    }
}
