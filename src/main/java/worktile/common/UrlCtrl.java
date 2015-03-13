package worktile.common;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class UrlCtrl {
    public String getStrJumpUrl(String strJumpUrlPara,String strPkid1,String strPkid2){
        String strJumpUrl="";
        //首页任务执行情况
        if("ToWAOperMng".equals(strJumpUrlPara)) {
            strJumpUrl = "/UI/worktile/appoint/execQryToWAOperMng.xhtml?faces-redirect=true&strWorkorderInfoPkid=" +
                    strPkid1 + "&amp;strWorkorderAppointPkid=" + strPkid2;
        }else if("ToExecQry".equals(strJumpUrlPara)) {
            strJumpUrl = "/UI/worktile/taskDisplay/workorderExecQry.xhtml?faces-redirect=true&strWorkorderInfoPkid=" +
                    strPkid1 + "&amp;strWorkorderAppointPkid=" + strPkid2;
        }
        //工单查询信息
        else if("ToWADetail".equals(strJumpUrlPara)) {
            strJumpUrl = "/UI/worktile/taskUnit/workorderItemQry.xhtml?faces-redirect=true&strWorkorderInfoPkid=" +
                    strPkid1;
        }else if("ToWAInfo".equals(strJumpUrlPara)) {
            strJumpUrl = "/UI/worktile/taskUnit/workorderInfoQry.xhtml?faces-redirect=true&strWorkorderInfoPkid=" +
                    strPkid1;
        }
        return strJumpUrl;
    }
    public static void main(String[] argv) {
        //System.out.println(getDateString("2004-10-20"));
    }
}

