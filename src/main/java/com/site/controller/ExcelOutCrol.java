package com.site.controller;

import com.site.dtos.RecordsDto;
import com.site.model.sign.SignRecords;
import com.site.repository.BuqianRepo;
import com.site.repository.MemberRepo;
import com.site.repository.SignRecordsRepo;
import com.site.utils.DateUtil;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import jxl.write.Number;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wang0 on 2016/9/25.
 */
@Controller
@Slf4j
@RequestMapping("/excelout")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
public class ExcelOutCrol {

    @Autowired
    private SignRecordsRepo signRecordsRepo;

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private BuqianRepo buqianRepo;

    @RequestMapping("")
    public String showpage(){
        return "excelout";
    }

    @RequestMapping("/excel")
    @ResponseBody
    public void outToExcel(HttpServletResponse response,
                           @RequestParam(name = "name", required = false) String name,
                           @RequestParam String starts,
                           @RequestParam String ends,
                           @RequestParam(name = "grade", required = false) Integer grade) throws Exception {

        response.setContentType("application/vnd.ms-excel");
        Date date = new Date();
        SimpleDateFormat formatter = new  SimpleDateFormat("yyyy-MM-dd");
        String strdate = formatter.format(date);
        response.setHeader("Content-Disposition", "attachment;filename="+strdate+".xls");
        if (name.equals("")) {
            name = null;
        }
        long start = new SimpleDateFormat("yyyy-MM-dd").parse(starts).getTime();
        long end = new SimpleDateFormat("yyyy-MM-dd").parse(ends).getTime();

        log.debug(name);
        log.debug(starts);
        log.debug(ends);
        log.debug(String.valueOf(grade));
        List<RecordsDto> dtos = new ArrayList<RecordsDto>();

        if (name != null) {
            RecordsDto recordsDto = new RecordsDto();
            recordsDto.setName(name);
            Long time = signRecordsRepo.queryByNameTime(name, new Timestamp(start), new Timestamp(end));
            System.out.println(time);
            Integer buqian = buqianRepo.findAllBuqian(new Timestamp(start), new Timestamp(end),name);
            System.out.println(buqian);
            recordsDto.setBuqian(buqian);
            if (time != null) {
                StringBuilder strtime = DateUtil.formatdate(time);
                recordsDto.setTotaltime(strtime);
            } else {
                recordsDto.setTotaltime(new StringBuilder("没有记录"));
            }
            dtos.add(recordsDto);
        } else {
            if (grade == 0) {
                List<String> names = memberRepo.findAllNames();
                if (names != null) {
                    for (String string : names) {
                        RecordsDto recordsDto = new RecordsDto();
                        recordsDto.setName(string);
                        Long time = signRecordsRepo.queryByNameTime(string, new Timestamp(start), new Timestamp(end));
                        System.out.println(time);
                        Integer buqian = buqianRepo.findAllBuqian(new Timestamp(start), new Timestamp(end),name);
                        System.out.println(buqian);
                        recordsDto.setBuqian(buqian);
                        if (time != null) {
                            StringBuilder strtime = DateUtil.formatdate(time);
                            recordsDto.setTotaltime(strtime);
                        } else {
                            recordsDto.setTotaltime(new StringBuilder("没有记录"));
                        }
                        dtos.add(recordsDto);
                    }
                }
            } else {
                if (grade != null) {
                    List<String> names = memberRepo.findNameByGrade(grade);
                    if (names != null) {
                        for (String string : names) {
                            RecordsDto recordsDto = new RecordsDto();
                            recordsDto.setName(string);
                            Long time = signRecordsRepo.queryByNameTime(string, new Timestamp(start), new Timestamp(end));
                            Integer buqian = buqianRepo.findAllBuqian(new Timestamp(start), new Timestamp(end),name);
                            System.out.println(buqian);
                            recordsDto.setBuqian(buqian);
                            if (time != null) {
                                StringBuilder strtime = DateUtil.formatdate(time);
                                recordsDto.setTotaltime(strtime);
                            } else {
                                recordsDto.setTotaltime(new StringBuilder("没有记录"));
                            }
                            dtos.add(recordsDto);
                        }
                    }
                } else {
                    List<String> names = memberRepo.findAllNames();
                    if (names != null) {
                        for (String string : names) {

                            RecordsDto recordsDto = new RecordsDto();
                            recordsDto.setName(string);
                            Long time = signRecordsRepo.queryByNameTime(string, new Timestamp(start), new Timestamp(end));
                            Integer buqian = buqianRepo.findAllBuqian(new Timestamp(start), new Timestamp(end),name);
                            System.out.println(buqian);
                            recordsDto.setBuqian(buqian);
                            if (time != null) {
                                StringBuilder strtime = DateUtil.formatdate(time);
                                recordsDto.setTotaltime(strtime);
                            } else {
                                recordsDto.setTotaltime(new StringBuilder("没有记录"));
                            }
                            dtos.add(recordsDto);
                        }
                    }
                }
            }
        }
        WritableWorkbook wk = Workbook.createWorkbook(response.getOutputStream());
        WritableSheet sheet = wk.createSheet("签到表", 0);
        sheet.mergeCells(0, 0, 2, 0);
        WritableFont titleFont = new WritableFont(WritableFont.createFont("黑体"), 12, WritableFont.BOLD,
                false, UnderlineStyle.NO_UNDERLINE, Colour.LIGHT_BLUE);
        WritableCellFormat titleFormat = new WritableCellFormat();
        titleFormat.setFont(titleFont);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        titleFormat.setBackground(Colour.GRAY_25);
        titleFormat.setWrap(true);
        Label lab_00 = new Label(0, 0, "签到表", titleFormat);
        sheet.addCell(lab_00);
        WritableCellFormat cloumnTitleFormat = new WritableCellFormat();
        cloumnTitleFormat.setFont(new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD, false));
        cloumnTitleFormat.setAlignment(Alignment.CENTRE);

        Label lab_01 = new Label(0, 1, "序号", cloumnTitleFormat);
        Label lab_11 = new Label(1, 1, "姓名", cloumnTitleFormat);
        Label lab_21 = new Label(2, 1, "签到总时间", cloumnTitleFormat);

        sheet.addCell(lab_01);
        sheet.addCell(lab_11);
        sheet.addCell(lab_21);

        int j = 0;
        for (int index = 2; index < dtos.size() + 2; index++) {
            sheet.addCell(new Number(0, index, j + 1));
            sheet.addCell(new Label(1, index, dtos.get(j).getName()));
            sheet.addCell(new Label(2, index, String.valueOf(dtos.get(j).getTotaltime())));
            j++;
        }
        wk.write();
        wk.close();
        System.out.println("成功导出一次");
    }


    @RequestMapping("/excelDetail")
    @ResponseBody
    public void outDetailToExcel(HttpServletResponse response,
                                 @RequestParam(name = "name", required = false) String name,
                                 @RequestParam String starts,
                                 @RequestParam String ends,
                                 @RequestParam(name = "grade", required = false) Integer grade) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        Date date = new Date();
        SimpleDateFormat formatter = new  SimpleDateFormat("yyyy-MM-dd");
        String strdate = formatter.format(date);
        response.setHeader("Content-Disposition", "attachment;filename="+strdate+"detail.xls");
        if (name.equals("")) {
            name = null;
        }
        long start = new SimpleDateFormat("yyyy-MM-dd").parse(starts).getTime();
        long end = new SimpleDateFormat("yyyy-MM-dd").parse(ends).getTime();
        log.info(name);
        log.info(starts);
        log.debug(ends);
        log.debug(String.valueOf(grade));
        List<SignRecords> signRecordses = new ArrayList<SignRecords>();

        if (name != null) {
            signRecordses = signRecordsRepo.queryByNameTimeDetail(name, new Timestamp(start), new Timestamp(end));
        } else {
            if (grade == 0) {
                signRecordses = signRecordsRepo.findAllOrder(new Timestamp(start), new Timestamp(end));
            } else {
                if (grade != null) {
                    signRecordses = signRecordsRepo.queryByGradeTimeDetail(new Timestamp(start), new Timestamp(end), grade);
                } else {
                    signRecordses = signRecordsRepo.queryByTimeDetailOrder(new Timestamp(start), new Timestamp(end));
                }
            }
        }

        DateFormat df = new DateFormat("yyyy-MM-dd hh:mm:ss");
        WritableCellFormat datewcf = new WritableCellFormat(df);
        WritableWorkbook wk = Workbook.createWorkbook(response.getOutputStream());
        WritableSheet sheet = wk.createSheet("签到详情表", 0);
        sheet.mergeCells(0, 0, 4, 0);
        WritableFont titleFont = new WritableFont(WritableFont.createFont("黑体"), 12, WritableFont.BOLD,
                false, UnderlineStyle.NO_UNDERLINE, Colour.RED);
        WritableCellFormat titleFormat = new WritableCellFormat();
        titleFormat.setFont(titleFont);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        titleFormat.setBackground(Colour.GRAY_25);
        titleFormat.setWrap(true);
        Label lab_00 = new Label(0, 0, "签到详情表", titleFormat);
        sheet.addCell(lab_00);
        WritableCellFormat cloumnTitleFormat = new WritableCellFormat();
        cloumnTitleFormat.setFont(new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD, false));
        cloumnTitleFormat.setAlignment(Alignment.CENTRE);

        Label lab_01 = new Label(0, 1, "序号", cloumnTitleFormat);
        Label lab_11 = new Label(1, 1, "姓名", cloumnTitleFormat);
        Label lab_21 = new Label(2, 1, "签到时间", cloumnTitleFormat);
        Label lab_31 = new Label(3, 1, "签退时间", cloumnTitleFormat);
        Label lab_41 = new Label(4, 1, "总时间", cloumnTitleFormat);

        sheet.addCell(lab_01);
        sheet.addCell(lab_11);
        sheet.addCell(lab_21);
        sheet.addCell(lab_31);
        sheet.addCell(lab_41);

        int j = 0;
        for (int index = 2; index < signRecordses.size() + 2; index++) {
            sheet.addCell(new Number(0, index, j + 1));
            sheet.addCell(new Label(1, index, signRecordses.get(j).getName()));
            sheet.addCell(new DateTime(2, index, signRecordses.get(j).getComeTime(), datewcf));
            sheet.addCell(new DateTime(3, index, signRecordses.get(j).getLeaveTime(), datewcf));
            sheet.addCell(new Label(4, index, signRecordses.get(j).getStrTime()));
            j++;
        }
        wk.write();
        wk.close();
        System.out.println("成功导出一次");
    }
}
