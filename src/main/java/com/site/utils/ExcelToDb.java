package com.site.utils;

import com.site.model.login.Member;
import com.site.repository.MemberRepo;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wang0 on 2016/10/7.
 */

/**
 * 将excel中的信息导入数据库
 */
//@Service
public class ExcelToDb {

    @Autowired
    private MemberRepo memberRepo;

    public void exceltodb() throws IOException, BiffException {
        List<Member> members = new LinkedList<>();
        Workbook rwb;

        InputStream inputStream = new FileInputStream(new File("E:/桌面文档资料/Desktop/成员信息总汇.xls"));

        rwb = Workbook.getWorkbook(inputStream);
        Sheet rs = rwb.getSheet(0);
        int rsRows = rs.getRows();
        for (int i = 1; i < rsRows; i++) {
            Member member = new Member();
            String cellName = rs.getCell(0, i).getContents();

            String cellSex = rs.getCell(1, i).getContents();
            String cellGroup = rs.getCell(2, i).getContents();
            String cellNumber = rs.getCell(3, i).getContents();
            String cellGrade = rs.getCell(4, i).getContents();
            String cellPhone = rs.getCell(5, i).getContents();

            member.setName(cellName);
            if (cellSex.equals("男")){
                member.setSex(1);
            }else {
                member.setSex(2);
            }
            if (cellGroup.equals("嵌入式")){
                member.setGroup(2);
            }else if (cellGroup.equals("移动开发")){
                member.setGroup(3);
            }else if (cellGroup.equals("web前端")){
                member.setGroup(1);
            }else if (cellGroup.equals("web后端")){
                member.setGroup(4);
            }else {
                member.setGroup(0);
            }
            member.setStuId(Long.valueOf(cellNumber));
            member.setPhone(cellPhone);
            member.setGrade(Integer.valueOf(cellGrade.substring(2,4)));
            members.add(member);
        }
        System.out.println("已经读取数据");

        for (Member member:members){
            memberRepo.save(member);
        }
        System.out.println("导入成功");
    }
}
