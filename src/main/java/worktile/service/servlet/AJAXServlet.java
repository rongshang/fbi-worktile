package worktile.service.servlet;

import skyline.platform.form.config.SystemAttributeNames;
import skyline.platform.security.MenuBean;
import skyline.platform.security.OperatorManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by XIANGYANG on 2014/8/15.
 */
public class AJAXServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("AJAX�����Ѿ�����Sevlet��������");
        // ���AJAX����������
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        MenuBean menuBean=new MenuBean();
        try {
            String strJson=menuBean.generateJsonStream(
                    ((OperatorManager)request.getSession().getAttribute(SystemAttributeNames.USER_INFO_NAME)).getOperator().getPkid()
                    ,"default");
            response.getWriter().write(strJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
