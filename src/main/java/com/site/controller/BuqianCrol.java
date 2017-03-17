package com.site.controller;

import com.site.model.sign.Buqian;
import com.site.model.sign.SignRecords;
import com.site.repository.BuqianRepo;
import com.site.repository.SignRecordsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * Created by wang0 on 2016/10/16.
 */

@Controller
@RequestMapping("/buqian")
public class BuqianCrol {

    @Autowired
    private BuqianRepo buqianRepo;

    @Autowired
    private SignRecordsRepo signRecordsRepo;

    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @RequestMapping(value = "/apply", produces = "application/text")
    @ResponseBody
    public String apply(@RequestParam(required = false) String reason,@RequestParam Integer number) throws IOException {

        String name = getCurrentUsername();
        Buqian buqian = new Buqian(name, number + "小时",reason);

        if (buqianRepo.save(buqian)!=null){
            return "申请成功";
        }else {
            return "申请失败";
        }
    }

    @RequestMapping("/showReviewPage")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public String showReviewPage(){
        return "reviewBuqian";
    }

    @RequestMapping("/review")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    @ResponseBody
    public List<Buqian> review(){
        List<Buqian> buqians = buqianRepo.findByStatusEq0();
        return buqians;
    }

    @RequestMapping("/setBuqianSuccessful")
    @ResponseBody
    public void setBuqianSuccessful(@RequestParam Long id){
        Buqian buqian = buqianRepo.findOne(id);
        buqianRepo.setBuqianSuccessful(buqian.getId());

        SignRecords signRecords = new SignRecords();
        signRecords.setName(buqian.getName());
        signRecords.setComeTime(buqian.getCreateTime());
        signRecords.setLeaveTime(buqian.getCreateTime());
        signRecords.setTotalMill(Long.valueOf(Integer.valueOf(buqian.getDate().substring(0,1))*3600*1000));
        signRecords.setStrTime(buqian.getDate());

        signRecordsRepo.save(signRecords);
    }

    @RequestMapping("/setBuqianFailed")
    @ResponseBody
    public void setBuqianFailed(@RequestParam Long id){
        buqianRepo.setBuqianFailed(id);
    }
}
