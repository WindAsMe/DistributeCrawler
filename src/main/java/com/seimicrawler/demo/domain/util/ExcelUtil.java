package com.seimicrawler.demo.domain.util;

import java.io.FileOutputStream;
import java.util.List;

import com.seimicrawler.demo.domain.model.JDModel;
import com.seimicrawler.demo.domain.model.MovieModel;
import com.seimicrawler.demo.service.JDService;
import com.seimicrawler.demo.service.MovieService;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import javax.annotation.Resource;

/**
 * Author     : WindAsMe
 * File       : ExcelUtil.java
 * Time       : Create on 18-9-26
 * Location   : ../Home/SeimiCrawler/ExcelUtil.java
 */
public class ExcelUtil {

    @Resource
    private JDService jdService;

    @Resource
    private MovieService movieService;

    public void createExcel() throws Exception {
        // 建立一个Excel
        Workbook book = new HSSFWorkbook();
        // 在对应的Excel中建立一个分表
        Sheet sheet1 =(Sheet) book.createSheet("分表1");
        // 设置相应的行（初始从0开始）
        Row row =sheet1.createRow(0);
        // 在所在的行设置所在的单元格（相当于列，初始从0开始,对应的就是A列）
        Cell cell = row.createCell(0);
        // 写入相关数据到设置的行列中去。
        cell.setCellValue("相关数据");
        // 保存到计算机相应路径
        book.write( new FileOutputStream("/home/桌面/data.xls"));
    }

    public void saveExcel() throws Exception {
        List<JDModel> jdModels = this.jdService.selectJDModelAll();
        List<MovieModel> movieModels = this.movieService.selectMovieModelAll();
    }
}
