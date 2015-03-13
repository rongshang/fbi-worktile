package skyline.platform.db;

import jxl.write.WritableWorkbook;
import org.jdom.*;
import org.jdom.input.SAXBuilder;

import skyline.platform.form.config.*;
import skyline.platform.html.*;
import skyline.platform.utils.*;

import java.io.*;
import java.lang.*;
import java.util.List;

public class DBXML {
    public DBXML() {
    }

    public String getDataTableXML(String xmlStr) {
        String outstr = "";
        Document doc;
        Element rootNode;
        ConnectionManager cm = ConnectionManager.getInstance();
        try {
            cm.get();
            Reader reader = new StringReader(xmlStr);
            SAXBuilder ss = new SAXBuilder();
            doc = ss.build(reader);
            rootNode = doc.getRootElement();
            List list = rootNode.getChildren();
            Element childRoot = (Element) list.get(0);

            DBGrid dbGrid = new DBGrid();
            dbGrid.setGridID(childRoot.getAttributeValue("id"));
            dbGrid.setGridType(childRoot.getAttributeValue("gridType"));
            dbGrid.setfieldSQL(childRoot.getAttributeValue("SQLStr"));
            dbGrid.setfieldcn(childRoot.getAttributeValue("fieldCN"));
            dbGrid.setenumType(childRoot.getAttributeValue("enumType"));
            dbGrid.setvisible(childRoot.getAttributeValue("visible"));
            dbGrid.setfieldName(childRoot.getAttributeValue("fieldname"));
            dbGrid.setfieldWidth(childRoot.getAttributeValue("fieldwidth"));
            dbGrid.setfieldType(childRoot.getAttributeValue("fieldtype"));
            dbGrid.setfieldCheck(childRoot.getAttributeValue("fieldCheck"));
            dbGrid.setcountSQL(childRoot.getAttributeValue("countSQL"));
            dbGrid.setpagesize(Integer.parseInt(childRoot.getAttributeValue("pageSize")));
            dbGrid.setAbsolutePage(Integer.parseInt(childRoot.getAttributeValue("AbsolutePage")));
            dbGrid.setRecordCount(Integer.parseInt(childRoot.getAttributeValue("RecordCount")));
            dbGrid.setCheck(childRoot.getAttributeValue("checkbl").toLowerCase().trim().equals("true"));
            dbGrid.setAlign(childRoot.getAttributeValue("tralign"));
      if (childRoot.getAttributeValue("bottomVisible").toLowerCase().equals("true")){
            dbGrid.setGridBottomVisible(true);
      }else
            dbGrid.setGridBottomVisible(false);
            dbGrid.setWhereStr(Basic.decode(childRoot.getAttributeValue("whereStr")));
            outstr = dbGrid.getEditDataTable();
      } catch (JDOMException ex) {
            System.out.print(ex.getMessage());
        } finally {
            cm.release();
        }
        return outstr;
    }

    public String getDropDownXML(String xmlStr) {
        String outstr = "";
        Document doc;
        Element rootNode;
        try {
            Reader reader = new StringReader(xmlStr);
            SAXBuilder ss = new SAXBuilder();
            doc = ss.build(reader);
            rootNode = doc.getRootElement();
            if (rootNode.getAttributeValue("type").equals("lp")) {
                return getloadspell(rootNode.getText());
            }
            if (rootNode.getAttributeValue("type").equals("lm")) {
                return getLoadName(rootNode.getText());
            }
            List list = rootNode.getChildren();
            Element childRoot = (Element) list.get(0);
            String fieldTitle = childRoot.getAttributeValue("fieldTitle");
            if (rootNode.getAttributeValue("type").equals("enum")) {
                String enumType = childRoot.getAttributeValue("enumType");
                return getEnumType(enumType, fieldTitle);
            }
            if (rootNode.getAttributeValue("type").equals("sql")) {
                String SqlStr = childRoot.getAttributeValue("SqlStr");
                DBTable datatable = new DBTable();
                return datatable.getDropDownStr(SqlStr, fieldTitle);
            }
            if (rootNode.getAttributeValue("type").equals("excel")) {
                String SqlStr = childRoot.getAttributeValue("SQLStr");
                String whereStr = Basic.decode(childRoot.getAttributeValue("whereStr"));
                DBTable datatable = new DBTable();
                return datatable.getExcel(SqlStr, whereStr);
            }
            if (rootNode.getAttributeValue("type").equals("excelhou")) {
                String SqlStr = childRoot.getAttributeValue("SQLStr");
                String fieldCN = childRoot.getAttributeValue("fieldCN");
                String fieldType = childRoot.getAttributeValue("fieldType");
                String whereStr = Basic.decode(childRoot.getAttributeValue("whereStr"));
                DBTable datatable = new DBTable();
                return datatable.writExcel(SqlStr, whereStr, fieldCN, fieldType);
            }
            if (rootNode.getAttributeValue("type").equals("comboBox")) {
                String comboBoxID = childRoot.getAttributeValue("comboBoxID");
                JOption jOption = new JOption(comboBoxID);
                String enumType = childRoot.getAttributeValue("enumType");
                String titleStr = childRoot.getAttributeValue("titleStr");
                String sqlStr = childRoot.getAttributeValue("sqlStr");
                String defaultoption = childRoot.getAttributeValue("defaultOption");
                String titleVisible = childRoot.getAttributeValue("titleVisible");
                String keyVisible = childRoot.getAttributeValue("keyVisible");
                if (defaultoption != null) {
                    String[] options = defaultoption.split(",", -2);

                    if (options.length == 2) {
                        jOption.addOption(options[0], options[1]);
                    }
                }
                if (titleVisible.toLowerCase().trim().equals("false"))
                    jOption.setTitleVisible(false);
                if (keyVisible.toLowerCase().trim().equals("false"))
                    jOption.setKeyVisible(false);
                jOption.setEnumType(enumType);
                jOption.setSQlStr(sqlStr);
                jOption.setTitleStr(titleStr);
                return jOption.getDropHtml();
            }
        } catch (JDOMException ex) {
            System.out.print(ex.getMessage());
        }
        return outstr;
    }

    public String doExcelHou(String xmlStr, WritableWorkbook wwb) {
        String outstr = "";
        Document doc;
        Element rootNode;
        try {
            Reader reader = new StringReader(xmlStr);
            SAXBuilder ss = new SAXBuilder();
            doc = ss.build(reader);
            rootNode = doc.getRootElement();
            if (rootNode.getAttributeValue("type").equals("lp")) {
                return getloadspell(rootNode.getText());
            }
            if (rootNode.getAttributeValue("type").equals("lm")) {
                return getLoadName(rootNode.getText());
            }
            List list = rootNode.getChildren();
            Element childRoot = (Element) list.get(0);
            String fieldTitle = childRoot.getAttributeValue("fieldTitle");
            if (rootNode.getAttributeValue("type").equals("enum")) {
                String enumType = childRoot.getAttributeValue("enumType");
                return getEnumType(enumType, fieldTitle);
            }
            if (rootNode.getAttributeValue("type").equals("sql")) {
                String SqlStr = childRoot.getAttributeValue("SqlStr");
                DBTable datatable = new DBTable();
                return datatable.getDropDownStr(SqlStr, fieldTitle);
            }
            if (rootNode.getAttributeValue("type").equals("excel")) {
                String SqlStr = childRoot.getAttributeValue("SQLStr");
                String whereStr = Basic.decode(childRoot.getAttributeValue("whereStr"));
                DBTable datatable = new DBTable();
                return datatable.getExcel(SqlStr, whereStr);
            }
            if (rootNode.getAttributeValue("type").equals("excelhou")) {
                String SqlStr = childRoot.getAttributeValue("SQLStr");
                String fieldCN = childRoot.getAttributeValue("fieldCN");
                String fieldType = childRoot.getAttributeValue("fieldType");
                String fieldWidth = childRoot.getAttributeValue("fieldWidth");
                String visible = childRoot.getAttributeValue("visible");
                String enumType = childRoot.getAttributeValue("enumType");
                String whereStr = Basic.decode(childRoot.getAttributeValue("whereStr"));
                DBTable datatable = new DBTable();
                return datatable.writeExcel_new(wwb, SqlStr, whereStr, fieldCN, fieldType,fieldWidth,visible,enumType);
            }
            if (rootNode.getAttributeValue("type").equals("comboBox")) {
                String comboBoxID = childRoot.getAttributeValue("comboBoxID");
                JOption jOption = new JOption(comboBoxID);
                String enumType = childRoot.getAttributeValue("enumType");
                String titleStr = childRoot.getAttributeValue("titleStr");
                String sqlStr = childRoot.getAttributeValue("sqlStr");
                String defaultoption = childRoot.getAttributeValue("defaultOption");
                String titleVisible = childRoot.getAttributeValue("titleVisible");
                String keyVisible = childRoot.getAttributeValue("keyVisible");
                if (defaultoption != null) {
                    String[] options = defaultoption.split(",", -2);
                    if (options.length == 2) {
                        jOption.addOption(options[0], options[1]);
                    }
                }
                if (titleVisible.toLowerCase().trim().equals("false"))
                    jOption.setTitleVisible(false);
                if (keyVisible.toLowerCase().trim().equals("false"))
                    jOption.setKeyVisible(false);
                jOption.setEnumType(enumType);
                jOption.setSQlStr(sqlStr);
                jOption.setTitleStr(titleStr);
                return jOption.getDropHtml();
            }
        } catch (JDOMException ex) {
            System.out.print(ex.getMessage());
            ex.printStackTrace();
        }
        return outstr;
    }
    // //��ȡ·ƴ��Ϣ
    public String getloadspell(String words) {
        return "";
    }
    // ///��ȡö������Ϣ
    public String getEnumType(String enumType, String fieldTitle) {
        try {
            EnumerationBean enumBean = EnumerationType.getEnu(enumType);
            Object[] keys = enumBean.getKeys().toArray();
            StringBuffer StrBuf = new StringBuffer();
            String[] fieldTitlearr = fieldTitle.split(",");
            StrBuf.append("<table id='Data_dropDown' class=\"dropDownTable\"  width='100%' border='0'  cellspacing='1' cellpadding='1' >");
            StrBuf.append("<tr height='20'   class=\"dropDownHead\" >");
            for (int i = 0; i < fieldTitlearr.length; i++) {
                StrBuf.append("<td align=\"center\">" + fieldTitlearr[i] + "</td> ");
            }
            StrBuf.append("</tr>");
            for (int i = 0; i < keys.length; i++) {
                Object key = keys[i];
                StrBuf.append("<tr  onclick=\"TRClick(this)\" height='20' class=\"gridEvenRow\">");
                String[] enumStr = ((String) enumBean.getValue(key)).split(";");
                StrBuf.append("<td  align =\"left\" >" + key.toString() + "</td>");
                StrBuf.append("<td  align =\"left\" >" + enumStr[0] + "</td>");
                StrBuf.append("</tr>");
            }
            StrBuf.append("</table>");
            return StrBuf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // ////����·��
    public String getLoadName(String spells) {
        return "";
    }
}
