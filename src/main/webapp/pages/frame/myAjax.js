//����XMLHttpRequest����ʵ��
var http_request = false;
/*************************************************************************
 * ����˵�����ɸ��õ�http�����ͺ���
 * ����˵����
 *����method������ʽ(GET/POST)
 *����url��Ŀ��URL
 *����content����POST��ʽ��������ʱ�봫�������������ݣ�
 *�����������������������Բ�ѯ�ִ��ķ�ʽ�г����磺name=value&anothername=othervalue��
 *����������������GET��ʽ���봫null
 *����responseType����Ӧ���ݵĸ�ʽ(text/xml)
 *����callback��Ҫ�ص��ĺ���
 *************************************************************************/
function sendRequest(method, url, content, responseType, callback) {
    http_request = false;
    //��ʼ��ʼ��XMLHttpRequest����
    if (window.XMLHttpRequest) { //Mozilla �����
        http_request = new XMLHttpRequest();
        if (http_request.overrideMimeType) {//����MiME���
            http_request.overrideMimeType("text/xml");
        }
    } else {
        if (window.ActiveXObject) { // IE�����
            try {
                http_request = new ActiveXObject("Msxml2.XMLHTTP");
            }
            catch (e) {
                try {
                    http_request = new ActiveXObject("Microsoft.XMLHTTP");
                }
                catch (e) {
                }
            }
        }
    }
    if (!http_request) { // �쳣,��������ʵ��ʧ��
        //"���ܴ���XMLHttpRequest����ʵ��"
        window.alert("/u4e0d/u80fd/u521b/u5efaXMLHttpRequest/u5bf9/u8c61/u5b9e/u4f8b.");
        returnfalse;
    }
    if (responseType.toLowerCase() == "text") {
        http_request.onreadystatechange = callback;
    } else {
        if (responseType.toLowerCase() == "xml") {
            http_request.onreadystatechange = callback;
        }else {
            //"��Ӧ����������"
            window.alert("/u54cd/u5e94/u7c7b/u522b/u53c2/u6570/u9519/u8bef/u3002");
            return false;
        }
    }

    // ȷ����������ķ�ʽ��URL�Լ��Ƿ��첽ִ���¶δ���
    if (method.toLowerCase() == "get") {
        http_request.open(method, url, true);
    } else {
        if (method.toLowerCase() == "post") {
            http_request.open(method, url, true);
            http_request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        } else {
            //http��������������
            window.alert("http/u8bf7/u6c42/u7c7b/u522b/u53c2/u6570/u9519/u8bef/u3002");
            return false;
        }
    }

    //��ʼ�����������
    http_request.send(content);
}
/*************************************************************************
 *
 *������˵�����ص�����(��������Ϣ�ĺ���)ģ��
 *
 *************************************************************************/
function processResponse() {
    // ���������
    if (http_request.readyState == 4) {
        // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
        if (http_request.status == 200) {
            //���ص����ı���ʽ��Ϣ
            alert(http_request.responseText);
            //���ص�XML��ʽ�ĵ�����alert(http_request.responseXML);
        } else { //ҳ�治����
            //"���������ҳ�����쳣"
            alert("/u60a8/u6240/u8bf7/u6c42/u7684/u9875/u9762/u6709/u5f02/u5e38/u3002");
        }
    }
} 
