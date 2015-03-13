package worktile.view.appoint;

import worktile.common.enums.EnumArchivedFlag;
import worktile.repository.model.MenuAppoint;
import worktile.repository.model.Ptmenu;
import worktile.repository.model.not_mybatis.DeptOperShow;
import worktile.repository.model.not_mybatis.MenuAppointShow;
import worktile.service.*;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.util.MessageUtil;
import skyline.util.ToolUtil;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@ViewScoped
public class MenuAppointOperAction implements Serializable{
    private static final Logger logger = LoggerFactory.getLogger(MenuAppointOperAction.class);
    @ManagedProperty(value = "#{deptOperService}")
    private DeptOperService deptOperService;
    @ManagedProperty(value = "#{operMenuService}")
    private OperMenuService operMenuService;
    @ManagedProperty(value = "#{menuService}")
    private MenuService menuService;

    private MenuAppointShow menuAppointShowSeled;

    private List<MenuAppointShow> menuAppointShowList;
    private List<MenuAppointShow> filteredMenuAppointShowList;

    private List<DeptOperShow> deptOperShowSeledList;
    private TreeNode deptOperShowRoot;

    @PostConstruct
    public void init() {
        try {
            menuAppointShowList = new ArrayList<>();
            filteredMenuAppointShowList = new ArrayList<>();
            deptOperShowSeledList = new ArrayList<>();

            // ��Դ-�û�-����
            initMenu();
            filteredMenuAppointShowList.addAll(menuAppointShowList);
            initDeptOperAppoint();
        }catch (Exception e){
            MessageUtil.addError(e.getMessage());
            logger.error("��ʼ��ʧ��", e);
        }
    }
    private void initMenu(){
        deptOperShowSeledList.clear();
        menuAppointShowList.clear();
        Ptmenu ptmenuTemp=new Ptmenu();
        List<Ptmenu> ptmenuListTemp=menuService.selectListByModel(ptmenuTemp);
        List<MenuAppointShow> menuAppointShowListTemp =
                operMenuService.getMenuAppointShowList(new MenuAppointShow());
        for(Ptmenu ptmenuUnit:ptmenuListTemp){
            String strInputOperName="";
            for(MenuAppointShow menuAppointShowUnit : menuAppointShowListTemp){
                if(ptmenuUnit.getPkid().equals(menuAppointShowUnit.getMenuPkid())){
                    if(strInputOperName.length()==0){
                        strInputOperName =
                                ToolUtil.getStrIgnoreNull(menuAppointShowUnit.getOperName());
                    }else {
                        strInputOperName = strInputOperName + "," +
                                ToolUtil.getStrIgnoreNull(menuAppointShowUnit.getOperName());
                    }
                }
            }
            MenuAppointShow menuAppointShowTemp =new MenuAppointShow();
            menuAppointShowTemp.setMenuPkid(ptmenuUnit.getPkid());
            menuAppointShowTemp.setMenuName(ptmenuUnit.getMenulabel());
            menuAppointShowTemp.setOperName(strInputOperName);
            menuAppointShowList.add(menuAppointShowTemp);
        }
    }

    private void initDeptOperAppoint(){
        deptOperShowSeledList.clear();
        DeptOperShow deptOperShowTemp =new DeptOperShow();
        deptOperShowTemp.setPkid("ROOT");
        deptOperShowTemp.setName("������Ա��Ϣ");
        deptOperShowTemp.setType("0");
        deptOperShowRoot=deptOperService.getDeptOperTreeNode(deptOperShowTemp);
    }

    private void recursiveOperTreeNodeForExpand(
            TreeNode treeNodePara,List<MenuAppointShow> menuAppointShowListPara) {
        if (menuAppointShowListPara ==null|| menuAppointShowListPara.size()==0){
            return;
        }
        if (treeNodePara.getChildCount() != 0) {
            for (int i = 0; i < treeNodePara.getChildCount(); i++) {
                TreeNode treeNodeTemp = treeNodePara.getChildren().get(i);
                DeptOperShow deptOperShowTemp = (DeptOperShow) treeNodeTemp.getData();
                if (deptOperShowTemp.getPkid()!=null&&"1".equals(deptOperShowTemp.getType())){
                    for (int j = 0; j < menuAppointShowListPara.size(); j++) {
                        if (deptOperShowTemp.getPkid().equals(menuAppointShowListPara.get(j).getOperPkid())) {
                            deptOperShowTemp.setIsSeled(true);
                            deptOperShowSeledList.add(deptOperShowTemp);
                            while (!(treeNodeTemp.getParent()==null)){
                                if (!(treeNodeTemp.isExpanded())&&treeNodeTemp.getChildCount()>0){
                                    treeNodeTemp.setExpanded(true);
                                }
                                treeNodeTemp=treeNodeTemp.getParent();
                            }
                            menuAppointShowListPara.remove(j);
                            break;
                        }
                    }
                }
                recursiveOperTreeNodeForExpand(treeNodeTemp, menuAppointShowListPara);
            }
        }
    }

    public void selectRecordAction(MenuAppointShow menuAppointShowPara) {
        try {
            menuAppointShowSeled = menuAppointShowPara;
            initDeptOperAppoint();
            MenuAppointShow menuAppointShowTemp =new MenuAppointShow();
            menuAppointShowTemp.setMenuPkid(menuAppointShowSeled.getMenuPkid());
            List<MenuAppointShow> menuAppointShowListTemp =
                    operMenuService.getMenuAppointShowList(menuAppointShowTemp);
            recursiveOperTreeNodeForExpand(deptOperShowRoot, menuAppointShowListTemp);
        } catch (Exception e) {
            MessageUtil.addError(e.getMessage());
        }
    }
    public void selOperRecordAction(DeptOperShow deptOperShowPara){
        if (deptOperShowPara.getIsSeled()){
            deptOperShowSeledList.add(deptOperShowPara);
        }else{
            deptOperShowSeledList.remove(deptOperShowPara);
        }
    }

    /**
     * �ύά��Ȩ��
     *
     * @param
     */
    public void onClickForMngAction(String strSubmitTypePara) {
        try {
            if (strSubmitTypePara.equals("Power")) {
                MenuAppoint menuAppointTemp = new MenuAppoint();
                menuAppointTemp.setMenuPkid(menuAppointShowSeled.getMenuPkid());
                operMenuService.deleteRecordByMenuPkid(menuAppointTemp);
                menuAppointTemp.setArchivedFlag(EnumArchivedFlag.ARCHIVED_FLAG0.getCode());
                for (DeptOperShow deptOperShowUnit : deptOperShowSeledList) {
                    menuAppointTemp.setOperPkid(deptOperShowUnit.getPkid());
                    operMenuService.insertRecord(menuAppointTemp);
                }
                MessageUtil.addInfo("Ȩ����ӳɹ�!");
            }
            initMenu();
            //������Ҫ��ԭ����ͬ��
            int selIndex= filteredMenuAppointShowList.indexOf(menuAppointShowSeled);
            filteredMenuAppointShowList.remove(menuAppointShowSeled);
            for(MenuAppointShow menuAppointShowUnit : menuAppointShowList){
                if(menuAppointShowUnit.getMenuPkid().equals(menuAppointShowSeled.getMenuPkid())){
                    filteredMenuAppointShowList.add(selIndex, menuAppointShowUnit);
                }
            }
        }catch (Exception e){
            MessageUtil.addError(e.getMessage());
            logger.error("��ʼ��ʧ��", e);
        }
    }

    /*�����ֶ� Start*/

    public TreeNode getDeptOperShowRoot() {
        return deptOperShowRoot;
    }

    public void setDeptOperShowRoot(TreeNode deptOperShowRoot) {
        this.deptOperShowRoot = deptOperShowRoot;
    }

    public List<DeptOperShow> getDeptOperShowSeledList() {
        return deptOperShowSeledList;
    }

    public void setDeptOperShowSeledList(List<DeptOperShow> deptOperShowSeledList) {
        this.deptOperShowSeledList = deptOperShowSeledList;
    }

    public List<MenuAppointShow> getMenuAppointShowList() {
        return menuAppointShowList;
    }

    public void setMenuAppointShowList(List<MenuAppointShow> menuAppointShowList) {
        this.menuAppointShowList = menuAppointShowList;
    }

    public List<MenuAppointShow> getFilteredMenuAppointShowList() {
        return filteredMenuAppointShowList;
    }

    public void setFilteredMenuAppointShowList(List<MenuAppointShow> filteredMenuAppointShowList) {
        this.filteredMenuAppointShowList = filteredMenuAppointShowList;
    }

    public MenuAppointShow getMenuAppointShowSeled() {
        return menuAppointShowSeled;
    }

    public void setMenuAppointShowSeled(MenuAppointShow menuAppointShowSeled) {
        this.menuAppointShowSeled = menuAppointShowSeled;
    }

    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public DeptOperService getDeptOperService() {
        return deptOperService;
    }

    public void setDeptOperService(DeptOperService deptOperService) {
        this.deptOperService = deptOperService;
    }

    public OperMenuService getOperMenuService() {
        return operMenuService;
    }

    public void setOperMenuService(OperMenuService operMenuService) {
        this.operMenuService = operMenuService;
    }
}
