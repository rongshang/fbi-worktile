<%@ page import="worktile.repository.model.Oper" %>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/pages/security/loginassistor.jsp" %>
<%
    response.setContentType("text/html; charset=GBK");
    String contextPath = request.getContextPath();

    /*2011-4-7 Cookie������ӵ�loginassistor.jsp��*/
    String jsonDefaultMenu = null;
    String jsonSystemMenu = null;
    try {
        jsonDefaultMenu = om.getJsonString("default");
        jsonSystemMenu = om.getJsonString("system");
    } catch (Exception e) {
        System.out.println("jsp" + e + "\n");
    }
    String operid = null;
    String strType=null;
    if (om != null) {
        if (om.getOperator() != null) {
            username = om.getOperatorName();
            operid = om.getOperator().getId();
            strType=om.getOperator().getType();
        }
    }
%>
<%--<!DOCTYPE html>--%>
<html>
    <head>
        <title>�������ϵͳ</title>
        <meta http-equiv="X-UA-Compatible" content="IE=5" />

        <script src="../../dhtmlx/dhtmlxTabbar/codebase/dhtmlxcommon.js" type="text/javascript"></script>
        <script src="../../dhtmlx/dhtmlxTabbar/codebase/dhtmlxcontainer.js" type="text/javascript"></script>

        <link rel="stylesheet" type="text/css" href="../../dhtmlx/dhtmlxLayout/codebase/dhtmlxlayout.css"/>
        <link rel="stylesheet" type="text/css" href="../../dhtmlx/dhtmlxLayout/codebase/skins/dhtmlxlayout_dhx_skyblue.css"/>
        <script src="../../dhtmlx/dhtmlxLayout/codebase/dhtmlxlayout.js" type="text/javascript"></script>

        <link rel="stylesheet" type="text/css" href="../../dhtmlx/dhtmlxAccordion/codebase/skins/dhtmlxaccordion_dhx_skyblue.css"/>
        <script src="../../dhtmlx/dhtmlxAccordion/codebase/dhtmlxaccordion.js" type="text/javascript"></script>

        <link rel="stylesheet" type="text/css" href="../../dhtmlx/dhtmlxTree/codebase/dhtmlxtree.css"/>
        <script src="../../dhtmlx/dhtmlxTree/codebase/dhtmlxtree.js" type="text/javascript"></script>
        <script src="../../dhtmlx/dhtmlxTree/codebase/ext/dhtmlxtree_json.js" type="text/javascript"></script>

        <link rel="stylesheet" type="text/css" href="../../dhtmlx/dhtmlxTabbar/codebase/dhtmlxtabbar.css"/>
        <script src="../../dhtmlx/dhtmlxTabbar/codebase/dhtmlxtabbar.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/diytabbar.css">

        <script type="text/javascript" src="homePage_layout.js"></script>
        <script type="text/javascript" src="homePage_tab.js"></script>
        <script type="text/javascript" src="myAjax.js"></script>

        <script type="text/javascript">
            var contextPath = '<%=contextPath%>';
            var defaultMenuStr = '<%=jsonDefaultMenu%>';
            var systemMenuStr = '<%=jsonSystemMenu%>';
            function doOnLoad() {
                bizdhxLayout = new dhtmlXLayoutObject("bizLayout", "2U", "dhx_skyblue");
                doBizLoad();
                sysdhxLayout = new dhtmlXLayoutObject("sysLayout", "2U", "dhx_skyblue");
                doSysLoad();
                var strOperType = '<%=strType%>';
                if(strOperType=="1"){                      //ϵͳ����Ա
                    document.getElementById('sys').style.display = 'inline';
                    tabbarhide("sysLayout");
                    document.getElementById("sys").setAttribute("active", "true");
                    document.getElementById("sys").className = "tabs-item-active";
                }else if(strOperType=="0"){               //����ϵͳ����Ա
                    document.getElementById('todoTask').style.display = 'inline';
                    document.getElementById('biz').style.display = 'inline';
                    document.getElementById('sys').style.display = 'inline';
                    tabbarhide("todoTaskLayout");
                    document.getElementById("todoTask").setAttribute("active", "true");
                    document.getElementById("todoTask").className = "tabs-item-active";
                }else{                                    //�����û�
                    document.getElementById('todoTask').style.display = 'inline';
                    document.getElementById('biz').style.display = 'inline';
                    tabbarhide("todoTaskLayout");
                    document.getElementById("todoTask").setAttribute("active", "true");
                    document.getElementById("todoTask").className = "tabs-item-active";
                }
            }
            function relogin() {
                parent.window.reload = "true";
                parent.window.location.replace("<%=contextPath%>/pages/security/logout.jsp");
            }
            function myRequest() {
                var url = "ajaxRequest.do?time=" + Math.random();
                //Ҫ�ύ��������������
                var content = "userName=ni";
                //�����쳣�����ύ�ĺ���
                sendRequest("POST", url, content, "TEXT", myProcessResponse);
            }
            function myProcessResponse() {
                // ���������
                if (http_request.readyState == 4) {
                    // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
                    if (http_request.status == 200) {
                        //���ص����ı���ʽ��Ϣ
                        myDoBizLoad(http_request.responseText);
                    } else { //ҳ�治����
                        //"���������ҳ�����쳣"
                    }
                }
            }
        </script>
    </head>
    <body onload="doOnLoad()">
        <%--�ر�tabʱ������һ�������tab--%>
        <input type="hidden" id="lasttabdivid">
        <div class="skin-top-right">
            <table width="100%" cellpadding="0" cellspacing="0" style="margin:0px;padding:0px;height:100%;">
                <tr style="width:100%; height:45px">
                    <td width="5%" rowspan="2">
                        &nbsp;
                        <img src="../../images/tts.png" height="50px">
                    </td>
                    <td style="text-align:right" class="headfont">
                        <span>����,<%=operid%>[<%=username%>]! </span>
                        <span onclick="changepwd()" onMouseOver="this.style.cursor='hand'">�޸�����</span>
                        <span onclick="relogin()" onMouseOver="this.style.cursor='hand'">| �˳�&nbsp;&nbsp;</span>
                    </td>
                </tr>
                <tr style="width:100%; height:25px">
                    <td style="height:25px;">
                        <div onclick="tabbarclk(this);document.frames('todoTaskWorkFrame').location.href='<%=contextPath%>/UI/worktile/taskDisplay/workorderExecQry.xhtml;'"
                             active="true" id="todoTask" class="tabs-item-active"
                             style="float:left;width:80px;margin-left:12px;display: none">
                            <span style="width:100%;">ǰ̨ҳ��</span>
                        </div>
                        <div id="middleId2" style="float:left;width:2px;"></div>
                        <div onclick="myRequest();tabbarclk(this);"
                             active="false" id="biz" class="tabs-item"
                             style="float:left;width:80px;display: none">
                            <span style="width:100%;">��̨����</span>
                        </div>
                        <div id="middleId4" style="float:left;width:2px;"></div>
                        <div onclick="myRequest();tabbarclk(this);"
                             active="false" id="sys" class="tabs-item"
                             style="float:left;width:80px;display: none">
                            <span style="width:100%;">ϵͳ����</span>
                        </div>
                        <div id="middleId5" style="float:left;width:2px;"></div>
                        <div id="dynamicInfo" style="float:left;width:300px;">
                            <%--<iframe id="scrollInfoWorkFrame"
                                    src="<%=contextPath%>/UI/worktile/scrollInfo/scrollInfo.xhtml"
                                    width="100%"
                                    height="25px;"
                                    frameborder="no"
                                    marginwidth="0" marginheight="8"
                                    scrolling="no"
                                    allowtransparency="true">
                            </iframe>--%>
                        </div>
                    </td>
                </tr>
                <tr style="width:100%; height:4px">
                    <td width="100%"
                        style="height:4px;
                        background-color: #3169AD;"
                        colspan="4">
                    </td>
                </tr>
                <tr style="width:100%">
                    <td width="100%" colspan="4">
                        <div class="divlayout" id="todoTaskLayout">
                            <iframe id="todoTaskWorkFrame"
                                    src="<%=contextPath%>/UI/worktile/taskDisplay/workorderExecQry.xhtml"
                                    width="100%" height="100%"
                                    frameborder="no"
                                    border="0"
                                    marginwidth="0" marginheight="0"
                                    scrolling="no">
                            </iframe>
                        </div>
                        <div class="divlayout" id="bizLayout"></div>
                        <div class="divlayout" id="sysLayout">
                            <br/>ϵͳ������Ϣ...
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>