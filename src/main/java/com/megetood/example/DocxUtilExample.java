package com.megetood.example;

import com.megetood.util.DocxUtil;
import com.megetood.util.FileUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DocxUtil Example
 *
 * @author Chengdong Lei
 * @date 2021/1/26
 */
public class DocxUtilExample {
    public static void main(String[] args) {
        String filePath = FileUtil.getSharedDataDir() + "docx\\template.docx";
        String outputPath = FileUtil.getSharedDataDir() + "docx\\template01.docx";

        HashMap<String, String> textMap = new HashMap<>();
        textMap.put("poNum", "poNum123");
        textMap.put("supplierName", "供应商123");
        textMap.put("amount", "10000");

        Map<String, String> rowMap = new HashMap<>();
        rowMap.put("num", "1");
        rowMap.put("itemCode", "wl123");
        rowMap.put("uomName", "元");
        rowMap.put("quantity", "20");
        Map<String, String> rowMap2 = new HashMap<>();
        rowMap2.put("num", "2");
        rowMap2.put("itemCode", "wl223");
        rowMap2.put("uomName", "元");
        rowMap2.put("quantity", "30");
        List<Map<String, String>> table = Arrays.asList(rowMap, rowMap2);

        DocxUtil.WordVariable wordVariable = new DocxUtil.WordVariable(textMap, Arrays.asList(table));

        DocxUtil.builder(filePath)
                .addPattern(new DocxUtil.Pattern("#{", "}"))
                .replaceTemplate(wordVariable)
                .toFilePath(outputPath);
    }
}
