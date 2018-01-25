package com.site.utils;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author 王萍
 * @date 2018/1/25 0025
 */
public class SecurityUtil {

    public static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
