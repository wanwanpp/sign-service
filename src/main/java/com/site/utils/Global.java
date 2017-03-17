package com.site.utils;

/**
 * Created by wang0 on 2016/9/13.
 */
public class Global {

    public static int REGISTER_SUCCESS = 201;
    public static int SIGN_SUCCESS = 200;
    public static int INVALID_SIGN = -1;
    public static int HAVE_REGISTERED = 402;
    static int[] array = new int[]{0, 1, 2, 3, 4, 5, 6};

    public static boolean contain(int hours) {
        for (int i : array) {
            if (i == hours) return true;

        }
        return false;
    }

}
