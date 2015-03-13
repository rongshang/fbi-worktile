package worktile.view.appoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.util.MessageUtil;
import skyline.util.ToolUtil;
import worktile.repository.model.Oper;
import worktile.repository.model.MenuAppoint;
import worktile.repository.model.Ptmenu;
import worktile.repository.model.not_mybatis.DeptOperShow;
import worktile.repository.model.not_mybatis.MenuAppointShow;
import worktile.service.DeptOperService;
import worktile.service.MenuService;
import worktile.service.OperMenuService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class OperAppointMenuAction implements Serializable{
    private static final Logger logger = LoggerFactory.getLogger(OperAppointMenuAction.class);
    @ManagedProperty(value = "#{deptOperService}")
    private DeptOperService deptOperService;
    @ManagedProperty(value = "#{operMenuService}")
    private OperMenuService operMenuService;
    @ManagedProperty(value = "#{menuService}")
    private MenuService menuService;

    private MenuAppointShow menuAppointShowSeled;
    private List<MenuAppointShow> menuAppointShowList;
    private List<MenuAppointShow> filteredMenuAppointShowList;

    private List<MenuAppointShow> menuAppointShowList_Res;
    private List<MenuAppointShow> menuAppointSel;

    @PostConstruct
    public void init() {
        try {
            menuAppointShowList = new ArrayList<>();
            filteredMenuAppointShowList = new ArrayList<>();
            menuAppointShowList_Res = new ArrayList<>();
            menuAppointSel = new ArrayList<>();
            // ��Դ-�û�-����
            initOper();
            filteredMenuAppointShowList.addAll(menuAppointShowList);
        }catch (Exception e){
            MessageUtil.addError(e.getMessage());
            logger.error("��ʼ��ʧ��", e);
        }
    }

    public void selectRecordAction(MenuAppointShow menuAppointShowPara) {
        try {
            menuAppointShowSeled = menuAppointShowPara;
            menuAppointShowList_Res.clear();
            menuAppointSel.clear();
            Ptmenu ptmenuTemp=new Ptmenu();
            List<Ptmenu> ptmenuListTemp=menuService.selectListByModel(ptmenuTemp);
            MenuAppointShow menuAppointShowTemp = new MenuAppointShow();
            menuAppointShowTemp.setOperPkid(menuAppointShowPara.getOperPkid());
            List<MenuAppointShow> operResListTemp=
                    operMenuService.getMenuAppointShowList(menuAppointShowTemp);
            for (Ptmenu ptmenuUnit:ptmenuListTemp){
                MenuAppointShow menuAppoint =new MenuAppointShow();
                menuAppoint.setMenuPkid(ptmenuUnit.getPkid());
                menuAppoint.setMenuName(ptmenuUnit.getMenulabel());
                for (MenuAppointShow menuAppointShowUnit :operResListTemp){
                    if(ptmenuUnit.getPkid().equals(menuAppointShowUnit.getMenuPkid())){
                        menuAppointSel.add(menuAppoint);
                        break;
                    }
                }
                menuAppointShowList_Res.add(menuAppoint);
            }

        } catch (Exception e) {
            MessageUtil.addError(e.getMessage());
        }
    }
    private void initOper(){
        menuAppointShowList.clear();
        // ȷ����Ա�б�
        DeptOperShow deptOperShowPara=new DeptOperShow();
        List<Oper> operListTemp=deptOperService.getOperListByModelShow(deptOperShowPara);
        // ��Ա�б��Ӧ����Դ��Ϣ
        List<MenuAppointShow> operMenuShowListOfMenuAppoint =
                operMenuService.getMenuAppointShowList(new MenuAppointShow());
        for(Oper operUnit:operListTemp){
            String strResName="";
            for(MenuAppointShow item_OperMenuPtmenu: operMenuShowListOfMenuAppoint){
                if(operUnit.getPkid().equals(item_OperMenuPtmenu.getOperPkid())){
                    if(strResName.length()==0){
                        strResName =
                                ToolUtil.getStrIgnoreNull(item_OperMenuPtmenu.getMenuName());
                    }else {
                        strResName = strResName + "," +
                                ToolUtil.getStrIgnoreNull(item_OperMenuPtmenu.getMenuName());
                    }
                }
            }
            MenuAppointShow menuAppointShowTemp =new MenuAppointShow();
            menuAppointShowTemp.setOperPkid(operUnit.getPkid());
            menuAppointShowTemp.setOperName(operUnit.getName());
            menuAppointShowTemp.setMenuName(strResName);
            menuAppointShowList.add(menuAppointShowTemp);
        }
    }

    /**
     * �ύά��Ȩ��
     *
     * @param
     */
    public void onClickForMngAction() {
        try {
            MenuAppoint menuAppointTemp = new MenuAppoint();
            menuAppointTemp.setOperPkid(menuAppointShowSeled.getOperPkid());
            operMenuService.deleteRecordByOperPkid(menuAppointTemp);
            for (MenuAppointShow menuAppointShowUnit : menuAppointSel) {
                menuAppointTemp.setMenuPkid(menuAppointShowUnit.getMenuPkid());
                operMenuService.insertRecord(menuAppointTemp);
            }
            MessageUtil.addInfo("Ȩ����ӳɹ�!");
            initOper();
            //������Ҫ��ԭ����ͬ��
            int selIndex= filteredMenuAppointShowList.indexOf(menuAppointShowSeled);
            filteredMenuAppointShowList.remove(menuAppointShowSeled);
            for(MenuAppointShow menuAppointShowUnit : menuAppointShowList){
                if(menuAppointShowUnit.getOperPkid().equals(menuAppointShowSeled.getOperPkid())){
                    filteredMenuAppointShowList.add(selIndex, menuAppointShowUnit);
                }
            }
        }catch (Exception e){
            MessageUtil.addError(e.getMessage());
            logger.error("��ʼ��ʧ��", e);
        }
    }

    /*�����ֶ� Start*/

    public List<MenuAppointShow> getMenuAppointShowList_Res() {
        return menuAppointShowList_Res;
    }

    public void setMenuAppointShowList_Res(List<MenuAppointShow> menuAppointShowList_Res) {
        this.menuAppointShowList_Res = menuAppointShowList_Res;
    }

    public List<MenuAppointShow> getFilteredMenuAppointShowList() {
        return filteredMenuAppointShowList;
    }

    public void setFilteredMenuAppointShowList(List<MenuAppointShow> filteredMenuAppointShowList) {
        this.filteredMenuAppointShowList = filteredMenuAppointShowList;
    }

    public List<MenuAppointShow> getMenuAppointSel() {
        return menuAppointSel;
    }

    public void setMenuAppointSel(List<MenuAppointShow> menuAppointSel) {
        this.menuAppointSel = menuAppointSel;
    }

    public List<MenuAppointShow> getMenuAppointShowList() {
        return menuAppointShowList;
    }

    public void setMenuAppointShowList(List<MenuAppointShow> menuAppointShowList) {
        this.menuAppointShowList = menuAppointShowList;
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
