package worktile.view.deptOper;

import skyline.security.DESHelper;
import worktile.repository.model.Dept;
import worktile.repository.model.Oper;
import worktile.repository.model.TidKeys;
import worktile.repository.model.not_mybatis.DeptOperShow;
import worktile.service.DeptOperService;
import jxl.write.WriteException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.security.MD5Helper;
import skyline.util.JxlsManager;
import skyline.util.MessageUtil;
import worktile.service.TidKeysService;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by XIANGYANG on 2014/8/11.
 */
@ManagedBean
@ViewScoped
public class DeptOperAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(DeptOperAction.class);
    @ManagedProperty(value = "#{deptOperService}")
    private DeptOperService deptOperService;
    @ManagedProperty(value = "#{tidKeysService}")
    private TidKeysService tidKeysService;

    private TreeNode deptOperShowRoot;
    private TreeNode currentSelectedNode;
    private String strSubmitType;
    private Dept deptAdd;
    private Dept deptUpd;
    private Dept deptDel;
    private Oper operAdd;
    private Oper operUpd;
    private Oper operDel;
    private List<SelectItem> deptSIList;
    private List<SelectItem> operSexSIList;
    private List<SelectItem> operIsSuperSIList;
    private List<SelectItem> enableSIList;
    private List<SelectItem> operTypeSIList;
    private List<SelectItem> selectItemList;
    private String strConfirmPasswd;
    private List<DeptOperShow> deptOperShowFowExcelList;
    private Map beansMap;
    private String strPasswd;

    private List<DeptOperShow> deptOperShowList;

    @PostConstruct
    public void init() {
        try {
            deptOperShowFowExcelList=new ArrayList<>();
            beansMap = new HashMap();
            initVariables();
            initData();
            recursiveForExcel("ROOT");
            beansMap.put("deptOperShowFowExcelList", deptOperShowFowExcelList);
            initDeptOperList();
            selectItemList=deptOperService.getDeptOperSelectItemList();
        }catch (Exception e){
            logger.error("��ʼ��ʧ��", e);
        }
    }

    private void initDeptOperList(){
        deptOperShowList=deptOperService.getDeptOperShowList();
    }

    private void initVariables() {
        deptAdd = new Dept();
        deptUpd = new Dept();
        deptDel = new Dept();
        operAdd = new Oper();
        operUpd = new Oper();
        operDel = new Oper();
    }

    private void initData() {
        try {
            operSexSIList = new ArrayList<>();
            operSexSIList.add(new SelectItem("1", "��"));
            operSexSIList.add(new SelectItem("0", "Ů"));
            operIsSuperSIList = new ArrayList<>();
            operIsSuperSIList.add(new SelectItem("0", "��"));
            operIsSuperSIList.add(new SelectItem("1", "��"));
            enableSIList= new ArrayList<>();
            enableSIList.add(new SelectItem("1", "����"));
            enableSIList.add(new SelectItem("0", "������"));
            operTypeSIList= new ArrayList<>();
            operTypeSIList.add(new SelectItem("2", "ҵ����Ա"));
            operTypeSIList.add(new SelectItem("1", "ϵͳ����Ա"));
            deptSIList=new ArrayList<>();
            DeptOperShow deptOperShowPara=new DeptOperShow();
            List<Dept> deptListTemp=deptOperService.getDeptListByModelShow(deptOperShowPara);
            for(Dept dept:deptListTemp){
                deptSIList.add(new SelectItem(dept.getPkid(),dept.getName()));
            }
            initDeptOper();
        }catch (Exception e){
            logger.error("��ʼ��ʧ��", e);
            MessageUtil.addError("��ʼ��ʧ��");
        }
    }

    private void initDeptOper() {
        DeptOperShow deptOperShowTemp = new DeptOperShow();
        deptOperShowTemp.setPkid("ROOT");
        deptOperShowTemp.setName("������Ա��Ϣ");
        deptOperShowTemp.setType("0");
        deptOperShowRoot=
                deptOperService.getDeptOperTreeTableNode(deptOperShowTemp,currentSelectedNode);
    }

    private void recursiveForExcel(String strParentPkidPara)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<DeptOperShow> deptOperShowTempList= deptOperService.selectDeptAndOperRecords(strParentPkidPara);
        for (DeptOperShow deptOperShowUnit:deptOperShowTempList) {
            DeptOperShow deptOperShowForExcelTemp= (DeptOperShow)BeanUtils.cloneBean(deptOperShowUnit);
            DeptOperShow deptOperShowForExcelTemp2=new DeptOperShow();
            if(("0").equals(deptOperShowForExcelTemp.getType())){
                deptOperShowForExcelTemp2.setDeptId(deptOperShowForExcelTemp.getId());
                deptOperShowForExcelTemp2.setDeptName(deptOperShowForExcelTemp.getName());
            }else{
                deptOperShowForExcelTemp2.setOperId(deptOperShowForExcelTemp.getId());
                deptOperShowForExcelTemp2.setOperName(deptOperShowForExcelTemp.getName());
            }
            deptOperShowFowExcelList.add(deptOperShowForExcelTemp2);
            recursiveForExcel(deptOperShowForExcelTemp.getPkid());
        }
    }

    public void setMaxDeptIdPlusOne(String strMngTypePara){
        try {
            if("Add".equals(strMngTypePara)) {
                deptAdd.setId(deptOperService.getStrMaxOperId());
            }else if("Upd".equals(strMngTypePara)) {
                deptUpd.setId(deptOperService.getStrMaxOperId());
            }
        } catch (Exception e) {
            logger.error("ȡ������ź�ʧ��", e);
            MessageUtil.addError("ȡ������ź�ʧ�ܡ�");
        }
    }
    public void setMaxOperIdPlusOne(String strMngTypePara){
        try {
            if("Add".equals(strMngTypePara)) {
                operAdd.setId(deptOperService.getStrMaxOperId());
            }else if("Upd".equals(strMngTypePara)) {
                operUpd.setId(deptOperService.getStrMaxOperId());
            }
        } catch (Exception e) {
            logger.error("ȡ������û���ʧ��", e);
            MessageUtil.addError("ȡ������û���ʧ�ܡ�");
        }
    }

    public void onNodeCollapse(NodeCollapseEvent event) {
        deptOperService.recursiveDeptOperTreeNodeCollapse(event.getTreeNode());
    }

    public void findSelectedNode(DeptOperShow deptOperShowPara, TreeNode treeNodePara,String strSubmitTypePara) {
        for (TreeNode treeNodeUnit:treeNodePara.getChildren()) {
            if (deptOperShowPara == treeNodeUnit.getData()) {
                if (strSubmitTypePara.contains("Add")){
                    currentSelectedNode = treeNodeUnit;
                }else if (strSubmitTypePara.contains("Upd")||strSubmitTypePara.contains("Del")){
                    currentSelectedNode=treeNodeUnit.getParent();
                }
                return;
            }
            findSelectedNode(deptOperShowPara, treeNodeUnit,strSubmitTypePara);
        }
    }

    public void selectRecordAction(String strSubmitTypePara,DeptOperShow deptOperShowOfCurrentTreeNodePara) {
        try {
            strSubmitType = strSubmitTypePara;
            findSelectedNode(deptOperShowOfCurrentTreeNodePara,deptOperShowRoot,strSubmitTypePara);
            if (strSubmitTypePara.contains("Dept")) {
                if (strSubmitTypePara.contains("Add")) {
                    deptAdd = new Dept();
                    deptAdd.setId(deptOperService.getStrMaxDeptId());
                    deptAdd.setParentpkid(deptOperShowOfCurrentTreeNodePara.getPkid());
                } else {
                    if (strSubmitTypePara.contains("Upd")) {
                        deptUpd = new Dept();
                        deptUpd = (Dept) deptOperService.selectRecordByPkid(deptOperShowOfCurrentTreeNodePara);
                    } else if (strSubmitTypePara.contains("Del")) {
                        deptDel = new Dept();
                        deptDel = (Dept) deptOperService.selectRecordByPkid(deptOperShowOfCurrentTreeNodePara);
                    }
                }
            } else if (strSubmitTypePara.contains("Oper")) {
                if (strSubmitTypePara.contains("Add")){
                    operAdd = new Oper();
                    operAdd.setId(deptOperService.getStrMaxOperId());
                    operAdd.setDeptPkid(deptOperShowOfCurrentTreeNodePara.getPkid());
                }else if (strSubmitTypePara.contains("Upd")) {
                    operUpd = new Oper();
                    operUpd = (Oper) deptOperService.selectRecordByPkid(deptOperShowOfCurrentTreeNodePara);
                    strPasswd=operUpd.getPasswd();
                } else if (strSubmitTypePara.contains("Del")) {
                    operDel = new Oper();
                    operDel = (Oper) deptOperService.selectRecordByPkid(deptOperShowOfCurrentTreeNodePara);
                }
            }
        } catch (Exception e) {
            logger.error("��ʼ����Ϣʧ�ܡ�", e);
            MessageUtil.addError("��ʼ����Ϣʧ�ܡ�");
        }
    }

    public void onClickForMngAction() {
        try {
            if (strSubmitType.contains("Dept")) {
                if (strSubmitType.contains("Add")) {
                    if (!submitDeptPreCheck(deptAdd)) {
                        return;
                    }
                    if (deptOperService.isExistInDeptDb(deptAdd)) {
                        MessageUtil.addError("�ñ�Ż����Ѵ��ڣ�������¼�룡");
                        return;
                    }
                    deptOperService.insertDeptRecord(deptAdd);
                } else if (strSubmitType.contains("Upd")) {
                    if (!submitDeptPreCheck(deptUpd)) {
                        return;
                    }
                    deptOperService.updateDeptRecord(deptUpd);
                } else if (strSubmitType.contains("Del")) {
                    if (deptOperService.findChildRecordsByPkid(deptDel.getPkid())) {
                        MessageUtil.addInfo("�ò������з�֧������Ա�����޷�ɾ����");
                        return;
                    }
                    deptOperService.deleteDeptRecord(deptDel);
                }
            } else if (strSubmitType.contains("Oper")) {
                if (strSubmitType.contains("Add")) {
                    if (!submitOperPreCheck(operAdd)) {
                        return;
                    }
                    TidKeys tidKeysPara =new TidKeys();
                    tidKeysPara.setTid("126");
                    List<TidKeys> tidKeysList = tidKeysService.getRsTidKeysList(tidKeysPara);
                    if(tidKeysList.size()>0){
                        TidKeys tidKeysTemp = tidKeysList.get(0);
                        DESHelper dESHelper = new DESHelper(tidKeysTemp.getEsKey());
                        String strOperCounts=dESHelper.decrypt(tidKeysTemp.getOperCounts());
                        int intUsersCounts=Integer.parseInt(strOperCounts);
                        Oper operTemp=new Oper();
                        int intExistRecordCountsInOperDb=deptOperService.existRecordCountsInOperDb(operTemp);
                        if (intExistRecordCountsInOperDb>=intUsersCounts) {
                            MessageUtil.addError("ϵͳ�����û���["+intUsersCounts+"]��ʵ���û���["
                                    +intExistRecordCountsInOperDb+"],�����޷���������û���");
                            return;
                        }
                    }

                    if (deptOperService.existRecordCountsInOperDb(operAdd)>0) {
                        MessageUtil.addError("�ñ���û��Ѵ��ڣ�������¼�룡");
                        return;
                    }
                    operAdd.setPasswd(MD5Helper.getMD5String(operAdd.getPasswd()));
                    operAdd.setTid("126");
                    deptOperService.insertOperRecord(operAdd);
                } else if (strSubmitType.contains("Upd")) {
                    if (!submitOperPreCheck(operUpd)) {
                        return;
                    }
                    if(!strPasswd.equals(operUpd.getPasswd())) {
                        operUpd.setPasswd(MD5Helper.getMD5String(operUpd.getPasswd()));
                    }
                    operUpd.setTid("126");
                    deptOperService.updateOperRecord(operUpd);
                } else if (strSubmitType.contains("Del")) {
                    deptOperService.deleteOperRecord(operDel);
                }
            }
            initVariables();
            initData();
            switch (strSubmitType){
                case "DeptAdd":MessageUtil.addInfo("�������ӳɹ���");break;
                case "OperAdd":MessageUtil.addInfo("�û����ӳɹ���");break;
                case "DeptUpd":MessageUtil.addInfo("�������³ɹ���");break;
                case "OperUpd":MessageUtil.addInfo("�û����³ɹ���");break;
                case "DeptDel":MessageUtil.addInfo("����ɾ���ɹ���");break;
                case "OperDel":MessageUtil.addInfo("�û�ɾ���ɹ���");break;
            }
        }catch (Exception e){
            logger.error("���ݴ���ʧ�ܡ�", e);
            MessageUtil.addError("���ݴ���ʧ�ܡ�");
        }
    }

    private boolean submitDeptPreCheck(Dept deptPara) {
        if (StringUtils.isEmpty(deptPara.getName())) {
            MessageUtil.addInfo("�����벿�����ƣ�");
            return false;
        }
        if (StringUtils.isEmpty(deptPara.getId())) {
            MessageUtil.addInfo("�����벿�ű�ţ�");
            return false;
        }
        return true;
    }
    private boolean submitOperPreCheck(Oper operPara) {
        if (StringUtils.isEmpty(operPara.getId())) {
            MessageUtil.addInfo("���������Ա��ţ�");
            return false;
        }
        if (StringUtils.isEmpty(operPara.getName())) {
            MessageUtil.addInfo("���������Ա���ƣ�");
            return false;
        }
        if (StringUtils.isEmpty(operPara.getPasswd())) {
            MessageUtil.addInfo("���������Ա���룡");
            return false;
        }
        if (!(StringUtils.isEmpty(operPara.getFile().getFileName()))) {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/upload/operPicture");
            File file = new File(path+"/"+operPara.getFile().getFileName());
            if (file.exists()) {
                MessageUtil.addInfo("�ļ��Ѵ��ڣ����������ļ���");
                return false;
            }
        }
        return true;
    }
    public String onExportExcel()throws IOException, WriteException {
        if (this.deptOperShowFowExcelList.size() == 0) {
            MessageUtil.addWarn("��¼Ϊ��...");
            return null;
        } else {
            String excelFilename = "������Ա��Ϣ��-" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
            JxlsManager jxls = new JxlsManager();
            jxls.exportList(excelFilename, beansMap,"deptOper.xls");
            // ����״̬��Ʊ����Ҫ���ʱ���޸ĵ����ļ���
        }
        return null;
    }
    /*�����ֶ� Start*/

    public TreeNode getDeptOperShowRoot() {
        return deptOperShowRoot;
    }

    public void setDeptOperShowRoot(TreeNode deptOperShowRoot) {
        this.deptOperShowRoot = deptOperShowRoot;
    }

    public DeptOperService getDeptOperService() {
        return deptOperService;
    }

    public void setDeptOperService(DeptOperService deptOperService) {
        this.deptOperService = deptOperService;
    }

    public Dept getDeptAdd() {
        return deptAdd;
    }

    public void setDeptAdd(Dept deptAdd) {
        this.deptAdd = deptAdd;
    }

    public Dept getDeptUpd() {
        return deptUpd;
    }

    public void setDeptUpd(Dept deptUpd) {
        this.deptUpd = deptUpd;
    }

    public Dept getDeptDel() {
        return deptDel;
    }

    public void setDeptDel(Dept deptDel) {
        this.deptDel = deptDel;
    }

    public Oper getOperAdd() {
        return operAdd;
    }

    public void setOperAdd(Oper operAdd) {
        this.operAdd = operAdd;
    }

    public Oper getOperUpd() {
        return operUpd;
    }

    public void setOperUpd(Oper operUpd) {
        this.operUpd = operUpd;
    }

    public Oper getOperDel() {
        return operDel;
    }

    public void setOperDel(Oper operDel) {
        this.operDel = operDel;
    }

    public List<SelectItem> getDeptSIList() {
        return deptSIList;
    }

    public void setDeptSIList(List<SelectItem> deptSIList) {
        this.deptSIList = deptSIList;
    }

    public List<SelectItem> getOperSexSIList() {
        return operSexSIList;
    }

    public void setOperSexSIList(List<SelectItem> operSexSIList) {
        this.operSexSIList = operSexSIList;
    }

    public List<SelectItem> getOperIsSuperSIList() {
        return operIsSuperSIList;
    }

    public void setOperIsSuperSIList(List<SelectItem> operIsSuperSIList) {
        this.operIsSuperSIList = operIsSuperSIList;
    }

    public List<SelectItem> getEnableSIList() {
        return enableSIList;
    }

    public void setEnableSIList(List<SelectItem> enableSIList) {
        this.enableSIList = enableSIList;
    }

    public List<SelectItem> getOperTypeSIList() {
        return operTypeSIList;
    }

    public void setOperTypeSIList(List<SelectItem> operTypeSIList) {
        this.operTypeSIList = operTypeSIList;
    }

    public String getStrConfirmPasswd() {
        return strConfirmPasswd;
    }

    public void setStrConfirmPasswd(String strConfirmPasswd) {
        this.strConfirmPasswd = strConfirmPasswd;
    }

    public Map getBeansMap() {
        return beansMap;
    }

    public void setBeansMap(Map beansMap) {
        this.beansMap = beansMap;
    }

    public TidKeysService getTidKeysService() {
        return tidKeysService;
    }

    public void setTidKeysService(TidKeysService tidKeysService) {
        this.tidKeysService = tidKeysService;
    }

    public List<DeptOperShow> getDeptOperShowList() {
        return deptOperShowList;
    }

    public void setDeptOperShowList(List<DeptOperShow> deptOperShowList) {
        this.deptOperShowList = deptOperShowList;
    }

    public List<SelectItem> getSelectItemList() {
        return selectItemList;
    }

    public void setSelectItemList(List<SelectItem> selectItemList) {
        this.selectItemList = selectItemList;
    }
}
