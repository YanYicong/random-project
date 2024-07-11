package com.example.business.handler;

import com.alibaba.excel.write.handler.AbstractRowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.*;

import java.util.Objects;

/**
 * 表格样式处理
 */
public class CustomCellWriteHandler extends AbstractRowWriteHandler {
    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
        Sheet sheet = writeSheetHolder.getSheet();
        Workbook workbook = sheet.getWorkbook();

        if (Objects.equals(isHead, Boolean.TRUE)) {
            // 设置表头样式
            CellStyle headStyle = workbook.createCellStyle();
            headStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
            headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headStyle.setBorderBottom(BorderStyle.THICK);
            headStyle.setBorderLeft(BorderStyle.THICK);
            headStyle.setBorderRight(BorderStyle.THICK);
            headStyle.setBorderTop(BorderStyle.THICK);
            headStyle.setAlignment(HorizontalAlignment.CENTER);
            headStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            Font headFont = workbook.createFont();
            headFont.setColor(IndexedColors.WHITE.getIndex());
            headFont.setBold(true);
            headStyle.setFont(headFont);

            for (Cell cell : row) {
                cell.setCellStyle(headStyle);
            }
        } else {
            // 设置内容样式
            CellStyle contentStyle = workbook.createCellStyle();
            contentStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            contentStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            contentStyle.setAlignment(HorizontalAlignment.CENTER);
            contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // 设置边框
            contentStyle.setBorderBottom(BorderStyle.THICK);
            contentStyle.setBorderLeft(BorderStyle.THICK);
            contentStyle.setBorderRight(BorderStyle.THICK);
            contentStyle.setBorderTop(BorderStyle.THICK);

            for (Cell cell : row) {
                cell.setCellStyle(contentStyle);
            }
        }
    }
}
