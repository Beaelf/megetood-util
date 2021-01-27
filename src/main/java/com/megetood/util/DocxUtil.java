package com.megetood.util;

import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TableVariable;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variable;
import pl.jsolve.templ4docx.variable.Variables;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * DocxUtil
 *
 * @author Chengdong Lei
 * @date 2021/1/18
 */
public class DocxUtil {
    private Docx docx;
    private Pattern pattern;

    private DocxUtil(Docx docx) {
        this.docx = docx;
    }

    public static DocxUtil builder(String filePath) {
        return new DocxUtil(new Docx(filePath));
    }

    public static DocxUtil builder(InputStream inputStream) {
        return new DocxUtil(new Docx(inputStream));
    }

    public DocxUtil replaceTemplate(WordVariable wordVariable) {
        this.docx = replaceTemplate(this.pattern, wordVariable);
        return this;
    }

    private Docx replaceTemplate(Pattern pattern, WordVariable wordVariable) {
        if (pattern == null) {
            this.pattern = new Pattern();
        }

        Variables variables = buildVariables(pattern, wordVariable);
        this.docx.setVariablePattern(pattern.toVariablePattern());
        this.docx.fillTemplate(variables);

        return this.docx;
    }

    public byte[] toByteArray() {
        byte[] res = null;

        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            this.docx.save(outputStream);
            res = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    public OutputStream toStream() {
        OutputStream outputStream = new ByteArrayOutputStream();
        this.docx.save(outputStream);
        return outputStream;
    }

    public void toFilePath(String outputPath) {
        this.docx.save(outputPath);
    }

    public DocxUtil addPattern(Pattern pattern) {
        this.pattern = pattern;
        return this;
    }

    private Variables buildVariables(Pattern pattern, WordVariable wordVariable) {
        Variables variables = new Variables();
        addTextVariable(pattern, wordVariable, variables);
        addTableVariable(pattern, wordVariable, variables);
        return variables;
    }

    private void addTextVariable(Pattern pattern, WordVariable wordVariable, Variables variables) {
        Map<String, String> textVariableMap = wordVariable.getTextVariableMap();
        for (Map.Entry<String, String> entry : textVariableMap.entrySet()) {
            variables.addTextVariable(new TextVariable(pattern.format(entry.getKey()), entry.getValue()));
        }
    }

    private void addTableVariable(Pattern pattern, WordVariable wordVariable, Variables variables) {
        TableVariable tableVariable = new TableVariable();
        // 遍历所有table
        List<List<Map<String, String>>> tableVariableList = wordVariable.getTableList();
        for (List<Map<String, String>> table : tableVariableList) {
            // 获取table的列名
            Set<String> colNameSet = table.get(0).keySet();

            // 给每一列赋值
            for (String colName : colNameSet) {
                String formatedColName = pattern.format(colName);

                // 取出每一行，key为colName的值
                List<Variable> colList = new LinkedList<>();
                for (Map<String, String> row : table) {
                    if (row.containsKey(colName)) {
                        colList.add(new TextVariable(formatedColName, row.get(colName)));
                    }
                }

                tableVariable.addVariable(colList);
            }
        }

        variables.addTableVariable(tableVariable);
    }

    public static class WordVariable {
        private Map<String, String> textVariableMap;
        private List<List<Map<String, String>>> tableList;

        public WordVariable() {
        }

        public WordVariable(Map<String, String> textVariableMap, List<List<Map<String, String>>> tableList) {
            this.textVariableMap = textVariableMap;
            this.tableList = tableList;
        }

        public Map<String, String> getTextVariableMap() {
            return textVariableMap;
        }

        public void setTextVariableMap(Map<String, String> textVariableMap) {
            this.textVariableMap = textVariableMap;
        }

        public List<List<Map<String, String>>> getTableList() {
            return tableList;
        }

        public void setTableList(List<List<Map<String, String>>> tableList) {
            this.tableList = tableList;
        }
    }

    public static class Pattern {
        private String prefix;
        private String suffix;

        public Pattern() {
        }

        public Pattern(String prefix, String suffix) {
            this.prefix = prefix;
            this.suffix = suffix;
        }

        public VariablePattern toVariablePattern() {
            return new VariablePattern(this.prefix, this.suffix);
        }

        public String format(String value) {
            if ((this.prefix == null && this.suffix == null) || value == null) {
                return value;
            }

            StringBuilder strBuiler = new StringBuilder();

            if (this.prefix != null) {
                strBuiler.append(this.prefix);
            }

            strBuiler.append(value);

            if (this.suffix != null) {
                strBuiler.append(this.suffix);
            }

            return strBuiler.toString();
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }
    }
}
