package com.site.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.model.sign.SignRecords;
import com.site.repository.SignRecordsRepo;
import com.site.utils.DateUtil;
import com.site.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by wang0 on 2016/9/28.
 */
@RestController
public class SignDetailCrol {

    @Autowired
    private SignRecordsRepo signRecordsRepo;

    @RequestMapping("/signDetail/getData")
    public List<SignRecords> getData(@RequestBody String string) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = (Map<String, String>) mapper.readValue(string, Map.class);
        String starts = map.get("starts");
        String ends = map.get("ends");
        String name = SecurityUtil.getCurrentUsername();
        System.out.println(name);
        long start = new SimpleDateFormat("yyyy-MM-dd").parse(starts).getTime();
        long end = new SimpleDateFormat("yyyy-MM-dd").parse(ends).getTime();

        List<SignRecords> signRecordses = signRecordsRepo.queryByNameTimeDetail(name, new Timestamp(start), new Timestamp(end));
        System.out.println(signRecordses.size());
        if (signRecordses.size() != 0 && signRecordses != null) {
            String total = String.valueOf(DateUtil.formatdate(signRecordsRepo.queryByNameTime(name, new Timestamp(start), new Timestamp(end))));
            SignRecords sign = new SignRecords();
            sign.setStrTime(total);
            signRecordses.add(sign);
        }
        return signRecordses;
    }
}
