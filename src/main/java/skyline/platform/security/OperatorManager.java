package skyline.platform.security;

import worktile.repository.model.Dept;
import worktile.repository.model.Oper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.platform.db.ConnectionManager;
import skyline.security.MD5Helper;
import skyline.util.ToolUtil;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Title: OperatorManager.java
 * </p>
 * <p>
 * Description: This class includes the basic behaviors of oper.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author WangHaiLei
 * @version 1.6 $ UpdateDate: Y-M-D-H-M: 2003-12-02-09-50 2004-03-01-20-35 $
 */
public class OperatorManager implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(OperatorManager.class);
    /**
     * operatorid是从login(operatorid, password)中得到的。
     */
    private String operatorid = null;
    /*
    20100820 zhanrui
    当前权限下的按照targetmachine分类的菜单项
    目前只分为两大类
    1、default：主要是业务菜单（targetmachine 为空或 为default）
    2、system：主要是系统管理相关菜单
     */
    private Map jsonMap = new HashMap();

    private MenuBean mb;

    private Oper oper;

    private String remoteAddr = null;

    private String remoteHost = null;

    private String loginTime = ToolUtil.getStrLastUpdTime();

    private boolean isLogin = false;

    public OperatorManager() {
    }

    /**
     * 返回一个Operator Object。这个Ojbect中包含该操作员的基本信息，包括：id, email, enabled, sex,
     * status, opername。
     *
     * @return Operator Ojbect.
     */
    public Oper getOperator() {
        return oper;
    }

    /**
     * 得到当前操作员的operatorname。
     *
     * @return
     */
    public String getOperatorName() {
        return oper.getName();
    }

    /**
     *
     * @return
     */
    public String getOperatorId() {
        return operatorid;
    }

    /**
     * 操作员签到，验证operid+passwd是否正确 签到成功后 1.isLogin=true 2.取得该操作员相关的所有角色 3.初始化资源列表
     * 4.取得操作员的菜单
     *
     * @param idPara
     * @param passwordPara
     * @return boolean
     * @roseuid 3F80B6360281
     */
    public boolean login(String idPara, String passwordPara) {
        ConnectionManager cm = ConnectionManager.getInstance();
        try {
            String loginWhere = "where id='" + idPara
                    + "' and passwd ='" + MD5Helper.getMD5String(passwordPara) + "'and enabled='1'";
            this.operatorid = idPara;
            oper = new Oper();
            oper = (Oper) oper.findFirstByWhere(loginWhere);
            if (oper == null) {
                isLogin = false;
                return false;
            }

            String sss = "登录时间 :" + loginTime + " IP: " + remoteAddr
                    + " 机器名称 : " + remoteHost;

            oper.setOnlineSituation(sss);

            Dept ptpdet = new Dept();
            oper.setDept((Dept) ptpdet
                    .findFirstByWhere("where pkid='" + oper.getDeptPkid() + "'"));
            isLogin = true;
            // 初始化菜单。
            try {
                mb = new MenuBean();
                this.jsonMap.put("default", mb.generateJsonStream(oper.getPkid(),"default"));
                this.jsonMap.put("system", mb.generateJsonStream(oper.getPkid(),"system"));
            } catch (Exception ex3) {
                ex3.printStackTrace();
                System.err.println("Wrong when getting menus of oper: [ "
                        + ex3 + "]");
            }
            return isLogin;
        } catch (Exception e) {
            logger.error("获取登录信息错误！", e);
            return false;
        } finally {
            cm.release();
        }
    }

    /**
     * @return boolean
     * @roseuid 3F80B71A00BC
     */
    public boolean isLogin() {
        return isLogin;
    }

    /**
     * 签退
     */
    public void logout() {
        isLogin = false;
        oper = null;
        operatorid = null;
        mb = null;
        remoteHost = null;
        remoteAddr = null;
        loginTime = null;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public String getJsonString(String target){
        return (String)this.jsonMap.get(target);
    }
}
