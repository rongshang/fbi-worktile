<%@ page contentType="text/html; charset=GBK" %>

<%
    skyline.platform.security.OperatorManager _om =
            (skyline.platform.security.OperatorManager)session.getAttribute(skyline.platform.form.config.SystemAttributeNames.USER_INFO_NAME);
  if ( _om == null ) {
      String path = request.getContextPath();
      out.println("<script language=\"javascript\">alert ('����Ա��ʱ��������ǩ����'); if(top){ top.location.href='"+path+"/pages/security/loginPage.jsp'; } else { location.href = '"+
              path+"/pages/security/loginPage.jsp';} </script>");
      return;
  }%>
