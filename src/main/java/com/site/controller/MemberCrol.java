package com.site.controller;

import com.site.repository.MemberRepo;
import com.site.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by wang0 on 2016/9/13.
 */
@Controller
public class MemberCrol {

    @Autowired
    private MemberRepo memberRepo;

    @RequestMapping("/member/setpwd")
    public String setPwd(@RequestParam String oldpwd,
                         @RequestParam String newpwd1,
                         @RequestParam String newpwd2) {
        String name = SecurityUtil.getCurrentUsername();
        if (oldpwd.equals(memberRepo.findPwdByName(name)) && newpwd1.equals(newpwd2)) {
            memberRepo.setPwd(newpwd1, name);
        }
        return "redirect:/signDetail";
    }
}
