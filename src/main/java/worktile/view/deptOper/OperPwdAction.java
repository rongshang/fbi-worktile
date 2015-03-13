package worktile.view.deptOper;

import worktile.repository.model.Oper;
import worktile.service.DeptOperService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.platform.security.OperatorManager;
import skyline.security.MD5Helper;
import skyline.util.MessageUtil;
import skyline.util.ToolUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

;

/**
 * Created by XIANGYANG on 2014/8/11.
 */
@ManagedBean
@ViewScoped
public class OperPwdAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(OperPwdAction.class);
    @ManagedProperty(value = "#{deptOperService}")
    private DeptOperService deptOperService;

    private Oper operUpd;
    private String strPasswd;
    private String strNewPasswd;
    private String strConfirmPasswd;

    @PostConstruct
    public void init() {
        try {
            operUpd = new Oper();
            OperatorManager operatorManagerTemp=ToolUtil.getOperatorManager();
            BeanUtils.copyProperties(operUpd,operatorManagerTemp.getOperator());
        }catch (Exception e){
            logger.error("��ʼ��ʧ��", e);
        }
    }

    public void onClickForMngAction() {
        try {
            if (!submitOperPreCheck(operUpd)) {
                return;
            }
            operUpd.setPasswd(MD5Helper.getMD5String(strNewPasswd));
            deptOperService.updateOperRecord(operUpd);
            MessageUtil.addInfo("���ݴ���ɹ���");
        }catch (Exception e){
            logger.error("���ݴ���ʧ�ܡ�", e);
            MessageUtil.addError("���ݴ���ʧ�ܡ�");
        }
    }

    private boolean submitOperPreCheck(Oper operPara) {
        if (StringUtils.isEmpty(strPasswd)) {
            MessageUtil.addInfo("������ԭ���룡");
            return false;
        }
        if (StringUtils.isEmpty(strNewPasswd)) {
            MessageUtil.addInfo("�����������룡");
            return false;
        }
        if (StringUtils.isEmpty(strConfirmPasswd)) {
            MessageUtil.addInfo("������������ȷ�ϣ�");
            return false;
        }
        if(!operPara.getPasswd().equals(MD5Helper.getMD5String(strPasswd))){
            MessageUtil.addInfo("ԭ��������������������ϵ����Ա�޸����룡��");
            return false;
        }
        if(!strNewPasswd.equals(strConfirmPasswd)){
            MessageUtil.addInfo("�����������벻��ͬ�����������룡��");
            return false;
        }
        return true;
    }

    /*�����ֶ� Start*/

    public DeptOperService getDeptOperService() {
        return deptOperService;
    }

    public void setDeptOperService(DeptOperService deptOperService) {
        this.deptOperService = deptOperService;
    }

    public Oper getOperUpd() {
        return operUpd;
    }

    public void setOperUpd(Oper operUpd) {
        this.operUpd = operUpd;
    }

    public String getStrConfirmPasswd() {
        return strConfirmPasswd;
    }

    public void setStrConfirmPasswd(String strConfirmPasswd) {
        this.strConfirmPasswd = strConfirmPasswd;
    }

    public String getStrPasswd() {
        return strPasswd;
    }

    public void setStrPasswd(String strPasswd) {
        this.strPasswd = strPasswd;
    }

    public String getStrNewPasswd() {
        return strNewPasswd;
    }

    public void setStrNewPasswd(String strNewPasswd) {
        this.strNewPasswd = strNewPasswd;
    }
}
