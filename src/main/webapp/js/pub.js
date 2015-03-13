/***********************************************************************************************************************
 * �ļ���������js������ 
 * ���ߣ�by leonwoo
 * ���ܣ�
 **********************************************************************************************************************/
/** ----------------------------------------------------------------------------------------------------------------- */
/**
 * ��ʾ�Ի���
 */
function showDialog(width, height, url, arg, resizable) {
    // ���÷��
    if (resizable == undefined) {
        resizable = "no";
    }
    var sfeature = "dialogwidth:" + width + "px; dialogheight:" + height + "px;center:yes;help:no;resizable:"
            + resizable + ";scroll:no;status:no";
    var date = new Date();
    // ����URL��չ������IEû������ÿ��ˢ�£�������ʾ��ҳ������
    var time = getDateString(date) + " " + getTimeString(date);
    if (url.indexOf("?") > 0) {
        url += "&showDialogTime=" + time;
    } else {
        url += "?&showDialogTime=" + time;
    }
    return window.showModalDialog(url, arg, sfeature);
}

/**
 * ��ʾ�Ի���
 */
function dialog(url, arg, sfeature) {
    var sfeature = sfeature;
    var date = new Date();
    // ����URL��չ������IEû������ÿ��ˢ�£�������ʾ��ҳ������
    var time = getDateString(date) + " " + getTimeString(date);
    if (url.indexOf("?") > 0) {
        url += "&showDialogTime=" + time;
    } else {
        url += "?&showDialogTime=" + time;
    }
    return window.showModalDialog(url, arg, sfeature);
}

/**
 * �¿�һ����
 */
function openWin(url) {
    var date = new Date();
    // ����URL��չ������IEû������ÿ��ˢ�£�������ʾ��ҳ������
    var time = getDateString(date) + " " + getTimeString(date);
    if (url.indexOf("?") > 0) {
        url += "&showDialogTime=" + time;
    } else {
        url += "?&showDialogTime=" + time;
    }
    return window.open(url);
}

/**
 * ��ȡ����
 */
function getDateString(date) {
    var years = date.getFullYear();
    var months = date.getMonth() + 1;
    var days = date.getDate();
    if (months < 10)
        months = "0" + months;
    if (days < 10)
        days = "0" + days;
    return years + "-" + months + "-" + days;
}

/**
 * ��ȡʱ��
 */
function getTimeString(date) {
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var seconds = date.getSeconds();
    if (hours < 10)
        hours = "0" + hours;
    if (minutes < 10)
        minutes = "0" + minutes;
    if (seconds < 10)
        seconds = "0" + seconds;
    return hours + ":" + minutes + ":" + seconds;
}

/**
 * ȡ��ϵͳ��״̬
 * 
 * @param lockstatus
 *            text
 * @param input
 *            type
 * @param input
 *            value
 * @param action
 *            name
 * @param action
 *            method name
 */
function getSysLockStatus() {
    var lockStatus = "";
    var rtnXml = createExecuteform(queryForm, "update", "sys01", "getSysLockStatus")
    if (rtnXml != "false") {
        var dom = createDomDocument();
        dom.loadXML(rtnXml);
        var fieldList = dom.getElementsByTagName("record")[0];
        for ( var i = 0; i < fieldList.childNodes.length; i++) {
            if (fieldList.childNodes[i].nodeType == 1) {
                oneRecord = fieldList.childNodes[i];
                attrName = oneRecord.getAttribute("name");
                if (attrName == "sysLockStatus") {
                    lockStatus = decode(oneRecord.getAttribute("value"));
                }
            }
        }
    }
    return lockStatus;
}
