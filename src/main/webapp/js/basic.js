//**************************************************//
//���ļ��Ǳ�ϵͳ���������ļ����κ��˲������Ը���
//���и����뱾����ϵ��������
//**************************************************//
/********************************************************************************
 *
 *      �ļ����� basic.js
 *
 *      ��  �ã�  ϵͳ�����ĵ��÷�����
 *
 *      ��  �ߣ� ������
 *
 *      ʱ  �䣺  yyyy-mm-dd
 *
 *      ��  Ȩ��  leonwoo
 *
 ********************************************************************************/
//�ַ�������
function encode(strIn){
	if(strIn ==undefined)
		return "";
	var intLen=strIn.length;
	var strOut="";
	var strTemp;
	var strFlag="";
	for(var i=0;i<intLen;i++){
		strTemp=strIn.charCodeAt(i);
		if (strTemp>255 || strTemp<0){
			if (strFlag==""){
				strFlag="#";
				strOut=strOut+"[#"+fillLeftWithZero(strTemp.toString(16),4);
			}else if (strFlag=="~"){
				strFlag="#";
				strOut=strOut+"#"+fillLeftWithZero(strTemp.toString(16),4);
			}else if (strFlag=="#"){
				strOut=strOut+fillLeftWithZero(strTemp.toString(16),4);
			}
		}else{
			if (strTemp < 48 || (strTemp > 57 && strTemp < 65) || (strTemp > 90 && strTemp < 97) || strTemp > 122){
				if (strFlag==""){
					strFlag="~";
					strOut=strOut+"[~"+fillLeftWithZero(strTemp.toString(16),2);
				}
				else if (strFlag=="#"){
					strFlag="~";
					strOut=strOut+"~"+fillLeftWithZero(strTemp.toString(16),2);
				}
				else if (strFlag=="~"){
					strOut=strOut+fillLeftWithZero(strTemp.toString(16),2);
				}
			}else{
				if (strFlag=="#" || strFlag=="~"){
					strFlag="";
					strOut=strOut+"]"+strIn.charAt(i);
				}
				else{
					strOut=strOut+strIn.charAt(i);
				}
			}
		}
	}
	return (strOut);
}
//�ַ�������
function decode(strIn){
	if(strIn ==undefined)
		return "";
	var intLen=strIn.length;
	var strOut="";
	var strTemp;
	var strFlag="";
	for(var i=0;i<intLen;i++){
		strTemp=strIn.charAt(i);
		if (strTemp=="["){
			i++;
			strTemp=strIn.charAt(i);
		}
		if (strTemp=="]"){
			strFlag="";
			continue;
		}
		if (strTemp=="~"){
			i++;
			strFlag="~";
		}
		if (strTemp=="#"){
			i++;
			strFlag="#";
		}
		switch (strFlag){
			case "~":{
				strTemp=strIn.substring(i,i+2);
				strTemp=parseInt(strTemp,16);
				strTemp=String.fromCharCode(strTemp);
				strOut=strOut+strTemp;
				i++;
				break;
			}
			case "#":{
				strTemp=strIn.substring(i,i+4);
				if (strTemp.toUpperCase()=="FFFF"){
					i+=4;
					strTemp=strIn.substring(i,i+4);
				}
				strTemp=parseInt(strTemp,16);
				strTemp=String.fromCharCode(strTemp);
				strOut=strOut+strTemp;
				i+=3;
				break;
			}
			case "":{
				strOut=strOut+strTemp;
				break;
			}
		}
	}
	return (strOut);
}

//���ݳ����ұ����0
function fillLeftWithZero(strIn, outLen){
	if (typeof(strIn)!="string") return strIn;
	for (var i=strIn.length;i<outLen;i++)
		strIn="0"+strIn;
	return strIn;
}
//ȥ����߿ظ�
function LTrim(str){
	if ((typeof(str) != "string") || !str) return "";
	for(var i=0; i<str.length; i++){if (str.charCodeAt(i, 10)!=32) break;}
	return str.substring(i, str.length);
}

//ȥ���ұ߿ظ�
function RTrim(str){
	if ((typeof(str) != "string") || !str) return "";
	for(var i=str.length-1; i>=0; i--){if (str.charCodeAt(i, 10)!=32) break;}
	return str.substr(0, i+1);
}

//ȥ���ظ�
function trimStr(str){
	if ((typeof(str) != "string") || !str) return "";
	return LTrim(RTrim(str));
}

//�õ�ĳ��Ԫ�ص���ʼ���X����
function absLeft(ele)
{
	var e = ele, left = 0;
	while(e.tagName != "BODY"){
		left += e.offsetLeft;
		e = e.offsetParent;
	}
	return left;
}

//�õ�ĳ��Ԫ�ص���ʼ���Y����
function absTop(ele){
	var e = ele,top = 0;
	while(e.tagName != "BODY"){
		top += e.offsetTop;
		e = e.offsetParent;
	}
	return top;
}
//���ĳ��������Ϊ��������ô���˵����з������ַ�
function onKeyPressInputInteger(){
	var nKey = window.event.keyCode;
	if ((nKey < 48 && nKey!=13 && nKey!=3 ) || nKey > 57 ){
		window.event.keyCode = 0;
	}
}
//�ؼ���ť����ƽ�
function button_onmouseover(){
	try{
		if (event.srcElement.disabled) return;
		event.srcElement.className="buttonGrooveActive";
	}
	catch(e){
	}
}
//�ؼ���ť����Ƴ�
function button_onmouseout(){
	try{
		if (event.srcElement.disabled) return;
		event.srcElement.className="buttonGrooveDisable";
	}
	catch(e){
	}
}

//��ȡ�����TD
function getOwnerTD(element){
	while (element.tagName.toUpperCase() != "TD" && element.tagName.toUpperCase() != "TH")	{
		element = element.parentNode;
		if (element == null)
			break;
	}
	return(element);
}
//��ȡ����TR
function getOwnerTR(element){
	try{//�� TD��Ԫ�� TH��ͷ
		while (element.tagName.toUpperCase() != "TR"){
			element = element.parentNode;
			if (element == null)
				break;
		}
		return(element);
	}
	catch(e){
		return(element);
	}
}
//��ȡ����TBODY
function getOwnerBody(element){
	var tag = element.tagName.toUpperCase();
	while (tag != "TABLE" && tag != "TBODY" && tag != "TH" && tag != "THEAD" && tag != "TFOOT"){
		element = element.parentNode;
		if (element == null)
			break;
		tag = element.tagName.toUpperCase();
	}
	return(element);
}
//��ȡ����Table
function getOwnerTable(element){
    //�� TD��Ԫ�� TH��ͷ
	while (element.tagName.toUpperCase() != "TABLE")	{
		element = element.parentNode;
		if (element == null)
			break;
	}
	return(element);
}
//���TD�����ݲ���Ϊ��
function checkTDNull(element){
	var value = RTrim(element.innerText);
	if (value.length == 0){
		TRMove(getOwnerTR(element));
		alert("��  ' "+ document.all("Title_"+getOwnerTable(element).id).rows[0].cells[element.cellIndex].innerText+" '�е�ֵ����Ϊ��");
		return "false";
	}
	return "true";
}

//���TD�����ݳ���
function checkTDStrLength(element,StrLength){
	var value = RTrim(element.innerText);
	if (value.length >StrLength*1 ){
		TRMove(getOwnerTR(element));
		alert("��  ' "+ document.all("Title_"+getOwnerTable(element).id).rows[0].cells[element.cellIndex].innerText+" '�е�ֵ�ĳ��Ȳ��ܴ���" + StrLength);
		return "false";
	}
	return "true";
}
//���TD�����ݳ���
function checkTDStrMinLength(element,StrLength){
	var value = RTrim(element.innerText);
	if (value.length < StrLength*1 ){
		TRMove(getOwnerTR(element));
		alert("��  ' "+ document.all("Title_"+getOwnerTable(element).id).rows[0].cells[element.cellIndex].innerText+" '�е�ֵ�ĳ��Ȳ���С��" + StrLength);
		return "false";
	}
	return "true";
}
//���TD���ݵ�int�ĳ���
function checkTDIntLength(element,IntLength){
	var value = RTrim(element.innerText);
	for (var j = 0; j<  value.length; j++){
		var ch = value.substr(j, 1);
		if (ch < "0" || ch > "9" ){
			TRMove(getOwnerTR(element));
			alert("��  ' "+ document.all("Title_"+getOwnerTable(element).id).rows[0].cells[element.cellIndex].innerText+ "  '�е�ֵ���Ϸ�");
			return "false";
		}
	}
	if (value.length > IntLength*1){
		TRMove(getOwnerTR(element));
		alert("��  ' "+ document.all("Title_"+getOwnerTable(element).id).rows[0].cells[element.cellIndex].innerText+"  '�е�ֵ���ܳ���" + IntLength + "λ");
		return "false";
	}
	return "true";
}
//���TD���ݵ�float�͵�������С�����ֵĳ���
function checkTDfloatLength(element,IntLength,floatLength){
	var nPointIndex = -1;
	var value = RTrim(element.innerText);
	for (var j = 0; j <  value.length; j++){
		var ch = value.substr(j, 1);
		if (ch < "0" || ch > "9" ){
			if (ch == "."){
				if (nPointIndex != -1){
					TRMove(getOwnerTR(element));
					alert("��  ' "+ document.all("Title_"+getOwnerTable(element).id).rows[0].cells[element.cellIndex].innerText+"  '�е�ֵֻ����һ��С����");
					return "false";
				}else
					nPointIndex = j;
			}
		}
	}
	if (nPointIndex == -1)
		nPointIndex = value.length;
	if ((value.substring(0, nPointIndex) * 1).toString().length > IntLength*1)	{
			TRMove(getOwnerTR(element));
			alert("��  ' "+ document.all("Title_"+getOwnerTable(element).id).rows[0].cells[element.cellIndex].innerText+"  '�е�ֵ����λ�����ܳ���" + IntLength + "λ");
			return "false";
	}
	var strFrac = value.substring(nPointIndex + 1, value.length);
	if (strFrac.length > 0){
		if ( strFrac.length >floatLength*1 ){
			TRMove(getOwnerTR(element));
			alert("��  ' "+ document.all("Title_"+getOwnerTable(element).id).rows[0].cells[element.cellIndex].innerText+"  '�е�ֵС��λ�����ܳ���" + floatLength + "λ");
			return "false";
		}
	}
	return "true";
}

//���email������Ϣ
function checkTDemail(element){
	var nPointIndex = -1;
	var value = RTrim(element.innerText);
	if(value=="")
		return "true";
	for (var j = 0; j <  value.length; j++){
		var ch = value.substr(j, 1);
		if (ch == "@"){
			if (nPointIndex != -1){
				TRMove(getOwnerTR(element));
				alert("��  ' "+ document.all("Title_"+getOwnerTable(element).id).rows[0].cells[element.cellIndex].innerText+"  '�е�ֵֻ����һ��@");
				return "false";
			}else
				nPointIndex = j;
		}
	}
	if (nPointIndex == -1){
		TRMove(getOwnerTR(element));
		alert("��  ' "+ document.all("Title_"+getOwnerTable(element).id).rows[0].cells[element.cellIndex].innerText+"  '�е�ֵ������@");
		return "false";
	}
	return "true";
}

//���Input�����ݲ���Ϊ��
function checkInputNull(element){
	if (element.tagName.toUpperCase()=="SELECT")	{
		if (element.selectedIndex ==-1){
			if (element.isNullTitle != undefined)
				alert(element.isNullTitle);
			else if (element.alertTitle != undefined)
				alert(element.alertTitle+":ѡ��ֵ����Ϊ��");
			else
				alert("�ڸ���ѡ��ֵ����Ϊ��");
			return "false";
		}
		return "true";
	}
	var value = RTrim(element.value);
	if (value.length == 0){
		element.focus();
		if (element.isNullTitle != undefined)
			alert(element.isNullTitle);
		else if (element.alertTitle != undefined)
			alert(element.alertTitle+":����ֵ����Ϊ��");
		else
			alert("�ڸ�������ֵ����Ϊ��");
		return "false";
	}
	return "true";
}

//���Input�����ݳ���
function checkInputStrLength(element,StrLength){
	var value = RTrim(element.value);
	if (value.length >StrLength*1 ){
		element.focus();
		if (element.textLengthTitle != undefined)
			alert(element.textLengthTitle);
		else if (element.alertTitle != undefined)
			alert(element.alertTitle+":����ֵ�ĳ��Ȳ��ܴ���" + StrLength);
		else
			alert("�ڸ�������ֵ�ĳ��Ȳ��ܴ���" + StrLength);
		return "false";
	}
	return "true";
}
//���Input������Min����
function checkInputStrMinLength(element,StrLength){
	var value = RTrim(element.value);
	if (value.length < StrLength*1 ){
		element.focus();
		if (element.minLengthTitle != undefined)
			alert(element.minLengthTitle);
		else if (element.alertTitle != undefined)
			alert(element.alertTitle+":����ֵ�ĳ��Ȳ���С��" + StrLength);
		else
			alert("�ڸ�������ֵ�ĳ��Ȳ���С��" + StrLength);
		return "false";
	}
	return "true";
}
//���Input��int�ĳ���
function checkInputIntLength(element,IntLength){
	var value = RTrim(element.value);
	for (var j = 0; j<  value.length; j++){
		var ch = value.substr(j, 1);
		if (ch < "0" || ch > "9" ){
			element.focus();
			if (element.alertTitle != undefined)
				alert(element.alertTitle+":����Ϸ�����");
			else
				alert("�ڸ�������Ϸ�����");
			return "false";
		}
	}
	if (value.length > IntLength*1){
		element.focus();
		if (element.alertTitle != undefined)
			alert(element.alertTitle+":���������λ�����ܳ���" + IntLength + "λ");
		else
			alert("�ڸ������������λ�����ܳ���" + IntLength + "λ");
		return "false";
	}
	return "true";
}
//���Input��float�͵�������С�����ֵĳ���
function checkInputfloatLength(element,IntLength,floatLength){
	var nPointIndex = -1;
	var value = RTrim(element.value);
	for (var j = 0; j <  value.length; j++){
		var ch = value.substr(j, 1);
		if (ch < "0" || ch > "9" ){
			if (ch == "."){
				if (nPointIndex != -1){
					element.focus();
					if (element.alertTitle != undefined)
						alert(element.alertTitle+":���������ֻ����һ��С����");
					else
						alert("�ڸ������������ֻ����һ��С����");
					return "false";
				}else
					nPointIndex = j;
			}
		}
	}
	if (nPointIndex == -1)
		nPointIndex = value.length;
	if ((value.substring(0, nPointIndex) * 1).toString().length > IntLength*1)	{
			element.focus();
			if (element.alertTitle != undefined)
				alert(element.alertTitle+":���������λ�����ܳ���" + IntLength + "λ");
			else
				alert("�ڸ������������λ�����ܳ���" + IntLength + "λ");
			return "false";
	}
	var strFrac = value.substring(nPointIndex + 1, value.length);
	if (strFrac.length > 0){
		if ( strFrac.length >floatLength*1 ){
			element.focus();
			if (element.alertTitle != undefined)
				alert(element.alertTitle+":�����С��λ�����ܳ���" + floatLength + "λ");
			else
				alert("�ڸ��������С��λ�����ܳ���" + floatLength + "λ");
			return "false";
		}
	}
	return "true";
}
//���email������Ϣ
function  checkInputemail(element){
	var nPointIndex = -1;
	var value = RTrim(element.value);
	if(value=="")
		return "true";
	for (var j = 0; j <  value.length; j++){
		var ch = value.substr(j, 1);
		if (ch == "@"){
			if (nPointIndex != -1){
				element.focus();
				if (element.alertTitle != undefined)
					alert(element.alertTitle+":�����ֵֻ����һ��@");
				else
					alert("�ڸ��������ֵֻ����һ��@");
				return "false";
			}else
				nPointIndex = j;
		}
	}
	if (nPointIndex == -1){
		element.focus();
		if (element.alertTitle != undefined)
			alert(element.alertTitle+":�����ֵ������@");
		else
			alert("�ڸ��������ֵ������@");
		return "false";
	}
	return "true";
}
//���Input����������
function checkInputValue(element){
	if ((element.isNull != undefined)&&(element.isNull.toLowerCase() =="false")){
		if (checkInputNull(element) =="false")	{
			return "false";
		}
	}
     if ((element.intLength != undefined)&&(element.floatLength != undefined))
          if (checkInputfloatLength(element,element.intLength,element.floatLength)=="false")
               return "false";
     if ((element.intLength != undefined)&&(element.floatLength == undefined))
          if (checkInputIntLength(element,element.intLength)=="false")
               return "false";
     if (element.textLength != undefined)
          if (checkInputStrLength(element,element.textLength)=="false")
               return "false";
     if (element.minLength != undefined)
          if (checkInputStrMinLength(element,element.minLength)=="false")
               return "false";
     if ((element.eMail != undefined)&&(element.eMail.toLowerCase()=="true"))
          if (checkInputemail(element)=="false")
               return "false";
	return "true";
}
//���FORM����������
function checkForm(formelement){
	for (var i=0; i< formelement.length; i++){
		var element= formelement.item(i);
          if (checkInputValue(element)=="false")
              return "false";
	}
	return "true";
}
//��ȡ����λ��
function getAbsPosition(obj, offsetObj){
	var _offsetObj=(offsetObj)?offsetObj:document.body;
	var x=obj.offsetLeft;
	var y=obj.offsetTop;
	var tmpObj=obj.offsetParent;
	while ((tmpObj!=_offsetObj) && tmpObj){
		x+=tmpObj.offsetLeft+tmpObj.clientLeft-tmpObj.scrollLeft;
		y+=tmpObj.offsetTop+tmpObj.clientTop-tmpObj.scrollTop;
		tmpObj=tmpObj.offsetParent;
	}
	return ([x, y]);
}
function get_status_label(text){
	if (!text) text="������������...";
	if (typeof(_status_label)=="undefined"){
		document.body.insertAdjacentHTML("beforeEnd", "<marquee id=_status_label isvisible=1  scrollamount=3 behavior=alternate direction=right"+
			" style=\"position: absolute; visibility: hidden; width: 128; height: 22; background-color: #d4d0c8; font-size: 12; border: 1 solid silver; padding-top:3; z-index: 10000; filter:alpha(opacity=70)\"></marquee>");
	}else{
	}
	_status_label.innerHTML=text;
}

function show_status_label(parent_window, text, center){
	parent_window.get_status_label(text);
	if (!_status_label) return;
	parent_window.document_onmousemove();
	parent_window._status_label.style.visibility="visible";
	if (center){
		parent_window._status_label.style.posLeft=(document.body.clientWidth - _status_label.offsetWidth) / 2;
		parent_window._status_label.style.posTop=(document.body.clientHeight - _status_label.offsetHeight) / 2;
	}
	else{
		parent_window.document.onmousemove=document_onmousemove;
	}
}

function hide_status_label(parent_window){
	if (!parent_window) return;
	parent_window.document.onmousemove=null;
	if (!parent_window._status_label) return;
	parent_window._status_label.style.visibility="hidden";
}

function document_onmousemove(){
  if(event != null){
    locate_status_label(event.x + document.body.scrollLeft, event.y + document.body.scrollTop);
  }
}

function locate_status_label(x, y){
	if (!_status_label) return;
	if (x==0 && y==0) return;
	var posX=document.body.clientWidth + document.body.scrollLeft -_status_label.offsetWidth;
	var posY=document.body.clientHeight + document.body.scrollTop -_status_label.offsetHeight
	posX=(x<posX)?x:posX;
	posY=(y<posY)?y:posY;
	_status_label.style.posLeft=posX;
	_status_label.style.posTop=posY;
}
function print_DBGrid(id){
	var tab = document.all(id);
	if (tab.RecordCount/1==0)	{
		alert("���Ȳ�ѯ!");
		return;
	}
	if (tab.RecordCount/1>=2000){
		alert("��ļ�¼����,�뾫ȷ��ѯ!");
		return;
	}
    var arguments = g_jsContextPath+"/UI/system/public/print/makeprt.jsp";
    var dialogArg = "dialogwidth:550px; Dialogheight:450px;center:yes;help:no;resizable:no;scroll:no;status:no";
	var arg = new Object();
	arg.fieldcn = encode(tab.fieldCN);
	arg.visible = encode(tab.visible);
	arg.filedwidth = encode(tab.fieldwidth);
	arg.align = encode(tab.tralign);
	arg.sqlstr = encode(tab.printSQL);
	arg.wherestr = tab.whereStr;
	arg.ischeck  = tab.checkbl;
	arg.printtitle  = tab.printtitle;
    var  dd = window.showModalDialog(arguments, arg, dialogArg);
}
function  getUserSerial (operID){
     var xmlDoc = createDomDocument("<root/>");
     var rootNode = xmlDoc.documentElement;
     var actionNode = appendNode(xmlDoc, xmlDoc.documentElement, "action");
     appendAttri(xmlDoc, actionNode, "actionname","sm0083");
     appendAttri(xmlDoc, actionNode, "methodname","gettUserSerial");
     var recNode = appendNode(xmlDoc, xmlDoc.documentElement, "recorder");
     appendAttri(xmlDoc, recNode, "type", "select");
	 var fieldNode = createFieldNode(xmlDoc,"userid","text",operID);
     recNode.appendChild(fieldNode);
     actionNode.appendChild(recNode);
     rootNode.appendChild(actionNode);
     var retStr = ExecServerPrgm(g_jsContextPath+"/BI/util/SqlSelectJsp.jsp","POST","sys_request_xml="+xmlDoc.xml);
    var userSerial =analyzeReturnXML(retStr);
    return userSerial;
}
function fireUserEvent(function_name, param){
	var result;
    var paramstr="";
    if (arguments.length == 2)
          for(i=0; i<param.length; i++){
               if (i==0)
                    paramstr="param["+i+"]";
                else
                    paramstr=paramstr+",param["+i+"]";
          }
     if (isfireuserEvent(function_name)){
     	eval("result="+function_name+"("+paramstr+");");
     }
	return result;
}

function isfireuserEvent(function_name){
     var result;
     eval("result=(typeof("+function_name+")!=\"undefined\");");
    return result;
}
//////////////////////////���֤����///////////////////////////
//*********************************************************************************
//  ��������
//            validatezjhm
//  ����˵����
//            ���鴫����ַ����Ƿ��Ǹ�ʽ��ȷ�����֤����
//            ��鳤�ȡ��Ƿ���������֡�������������ȷ�ԡ�18λ����λ���
//  ���������
//            strValue2            ��У������֤����
//  ����ֵ��
//            false��          �����ϱ�׼��ͬʱ������ʾ
//            true��          ���ϱ�׼��û����ʾ��Ϣ
//*********************************************************************************
function validatezjhm( strValue2 ){
       if((strValue2.length != 15) && (strValue2.length != 18)){
              alert("���֤���볤�Ȳ���ȷ��");
              return false;
       }
       //�ж������Ƿ�ȫ������
       nLen = strValue2.length;
       for(i = 0; i < nLen; i++){
              strCh = strValue2.charAt(i);
              if((i == 17) && (strCh == "X")){
                     continue;
              }
              nValue2 = parseInt(strCh, 10);
              if(isNaN(nValue2)){
                     break;
              }
       }
       if( i < nLen){
              alert("���֤�������з����֣�");
              return false;
       }
       if(strValue2.length == 15){
              strValue3 = strValue2.substr(6,6);
              strValue3 = "19" + strValue3;
       }else{
              strValue3 = strValue2.substr(6,8);
       }
       strTmp = strValue3.substr(0,4) + "-" + strValue3.substr(4,2) + "-" + strValue3.substr(6,2);
       strValue3 = strTmp;
       if(Validate(2, strValue3) == ""){
              alert("���֤�����е����ڲ���ȷ��");
              return false;
       }
       if(strValue2.length == 18){
              i = CheckIDAsMod11(strValue2);
              if(i != 1){
                     alert("���֤����У��������������룡");
                     return false;
              }
       }
       return true;
}
//*********************************************************************************
//  ��������
//            CheckIDAsMod11
//  ����˵����
//            18λ���֤�������λ����
//  ���������
//            strID    ��У������֤����
//  ����ֵ��
//           0��          �����ϱ�׼
//           1��          ���ϱ�׼
//*********************************************************************************
function CheckIDAsMod11(strID){
       var arrWeight;
       var arrCheck;
       var nLen;
       var i,k;
       var nTotal;
       var nCheckNum;
       var strCh;

       arrWeight = new Array(1, 2, 4, 8, 5, 10, 9 ,7, 3, 6);
       arrCheck = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
       nTotal = 0;
       nLen = strID.length;
       strCh = strID.charAt(nLen - 1);
       for(i = 1; i < nLen; i++){
              k = parseInt(strID.charAt(nLen - i - 1));
              nTotal = nTotal + arrWeight[i % 10] * k;
       }
       nCheckNum = nTotal % 11;
       if(arrCheck[nCheckNum] == strCh){
              return 1;
       }return 0;
}
//*********************************************************************************
//  ��������
//            Validate
//  ����˵����
//            У���������ݵĺϷ���
//  ���������
//            nType             �洢У�����ͣ�1 �����Ƿ�Ϊ�գ�2 ���ڼ���
//            strField            ��У����ַ���
//  ����ֵ��
//            ""��          �����ϱ�׼,���ؿ��ַ���
//      strTrim��       ���ϱ�׼�����ؾ����淶����strField
//*********************************************************************************
function Validate(nType,strField){
       var strTrim;
       var i;
       var strSeparator;
       strTrim = trimStr(strField);
       switch(nType){
              case 1:           //����ǿ�
                     return strTrim;
                     break;
              case 2:           //����
                     if (strTrim ==""){//��������ڴ�Ϊ��
                            return strTrim;
                     }
                     //���������ռ�ķָ���
                     for( i = 0; i < strTrim.length; i++ ){
                            if( ( strTrim.charAt(i) > "9" || strTrim.charAt(i) < "0") && strTrim.charAt(i) != " " ){
                                   strSeparator = strTrim.charAt(i);
                                   break;
                            }
                     }
                     var strYear;
                     var strMonth;
                     var strDate;
                     var arrDate;
                     arrDate = strTrim.split(strSeparator);
                     //������֮��û����ȷ�ķָ���
                     if (!(arrDate.length == 3)){
                            return "";
                     };
                     strYear = trimStr(arrDate[0]);
                     strMonth = trimStr(arrDate[1]);
                     strDate = trimStr(arrDate[2]);
                     //�ꡢ�¡��ձ�ʾ����ȷ
                     if (isNaN(strYear)||isNaN(strMonth)||isNaN(strDate)){
                            return "";
                     }
                     if ((strYear.charAt(0)=="0")||(strMonth.length>2)||(strDate.length>2)||(strYear.length>4)){
                            return "";
                     }
                     if ((strYear=="")||(strMonth=="")||(strDate=="")){
                            return "";
                     }
                     if (strMonth.charAt(0)=="0"){
                            strMonth = strMonth.substring(1,2);
                            if (strMonth==""){
                                   return "";
                            }
                     }
                     if(strDate.charAt(0)=="0"){
                            strDate = strDate.substring(1,2);
                            if (strDate == ""){
                                   return "";
                            }
                     }
                     //�·ݱ�ʾ���ϸ�
                     if ((parseInt(strMonth) < 1)||(parseInt(strMonth) > 12)){
                            return "";
                     }
                     //���ڱ�ʾ���ϸ�
                     if (parseInt(strDate) < 1) {
                            return "";
                     }

                     switch (strMonth) {
                            case "1":
                            case "01":
                            case "3":
                            case "03":
                            case "5":
                            case "05":
                            case "7":
                            case "07":
                            case "8":
                            case "08":
                            case "10":
                            case "12":
                                    {      //���ڱ�ʾ���ϸ�
                                           if (parseInt(strDate) > 31) {
                                                  return "";
                                           }
                                    }
                                break;
                            case "4":
                            case "04":
                            case "6":
                            case "06":
                            case "9":
                            case "09":
                            case "11":
                                {
                                    //���ڱ�ʾ���ϸ�
                                    if (parseInt(strDate) > 30){
                                              return "";
                                    }
                                }
                                break;

                            case "2":
                            case "02":
                                {
                                   if (((parseInt(strYear) % 4 == 0) && (!(parseInt(strYear) % 100==0)))||(parseInt(strYear)% 400 == 0)){
                                          //����2��
                                          if ((parseInt(strDate) > 29)){
                                                 return "";
                                          }
                                   }
                                   else{//ƽ��2��
                                      if ((parseInt(strDate) > 28)) {
                                             return "";
                                      }
                                   }
                                }
                                break;
                     }      //switch2
                     strTrim = strYear + "-" + strMonth + "-" + strDate;
                     return strTrim;
                     break;
       }//switch1
}
function onbodyprivateenvent(){
 	if (document.body != null&& typeof(document.body.onload)=="function"){
	}
}
function checkLogin(){
	return;
	var auth = new ActiveXObject("Msxml2.XMLHTTP") ;
	auth.open("POST","/pages/security/uionline.jsp",false) ;
	var xmlstr="<root><action></action></root>";		
	auth.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	auth.send(xmlstr);
   if(trimStr(analyzeReturnXML(auth.responseText))!="true"){
   		alert ('����Ա��ʱ��������ǩ����'); 
   		if(top)
   			{ top.location.href='/admin/*'; } 
   		else 
   			{ location.href = '/admin/*';}
   		return false;
   }
	return true;
}
function valuereplase(strValue,oldrp,newrp){
	try {
		strValue = strValue +"";
		var rpvalue = strValue.replace(/;/g,newrp);
		return rpvalue;
	} catch ( Exception ) {
			return strValue;
	}
}
/////body�����¼� ���ؿؽ��� ��ʼ����Ϣ
function body_Click(){
  ////����·�ؽ���Ϣ
 if (typeof(document.all["_load_box"])!="undefined")
	 droploadInit();
///�������ڿؼ���Ϣ
if (typeof(document.all["dropdown_button"])!="undefined")
	 CalInit();
////���������˵���Ϣ
if (typeof(document.all["dropdownText_button"])!="undefined")
	 dropInit();
////�����ҽ��˵���Ϣ
if (typeof(document.all["MenuContainer"])!="undefined")
	 hide();
////�����¿ؼ���Ϣ
if (typeof(document.all["_dropMonth_frame"])!="undefined")
	 MonthInit();
onbodyprivateenvent();

}