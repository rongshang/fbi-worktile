package worktile.view.worktile;

import worktile.repository.model.not_mybatis.WorktileShow;
import worktile.service.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.util.MessageUtil;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Think
 * Date: 13-2-4
 * Time: 下午4:53
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class WorktileAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(WorktileAction.class);
    @ManagedProperty(value = "#{worktileService}")
    private WorktileService worktileService;

    private WorktileShow worktileShowQry;
    private WorktileShow worktileShowSel;
    private WorktileShow worktileShowAdd;
    private WorktileShow worktileShowUpd;
    private WorktileShow worktileShowDel;
    private List<WorktileShow> worktileShowList;

    private String strSubmitType;

    @PostConstruct
    public void init() {
        try {
            initData();
        }catch (Exception e){
            logger.error("初始化失败", e);
        }
    }
    public void initData() {
        try {
            this.worktileShowList = new ArrayList<>();
            worktileShowQry = new WorktileShow();
            worktileShowSel = new WorktileShow();
            worktileShowAdd = new WorktileShow();
            worktileShowUpd = new WorktileShow();
            worktileShowDel = new WorktileShow();
            strSubmitType = "";
        }catch (Exception e){
            logger.error("初始化失败", e);
            MessageUtil.addError("初始化失败");
        }
    }

    public String onQueryAllAction(String strQryMsgOutPara) {
        try {
            this.worktileShowList.clear();
            worktileShowList =
                    worktileService.getWorkorderInfoShowListByModelShow(worktileShowQry);
            if(strQryMsgOutPara.equals("true"))  {
                if (worktileShowList.isEmpty()) {
                    MessageUtil.addWarn("没有查询到数据。");
                }
            }
        } catch (Exception e) {
            logger.error("工单信息查询失败", e);
            MessageUtil.addError("工单信息查询失败");
        }
        return null;
    }
    public String onQueryFinishAction(String strQryMsgOutPara) {
        try {
            this.worktileShowList.clear();
            worktileShowList =
                    worktileService.getWorkorderInfoShowListByModelShow(worktileShowQry);
            if(strQryMsgOutPara.equals("true"))  {
                if (worktileShowList.isEmpty()) {
                    MessageUtil.addWarn("没有查询到数据。");
                }
            }
        } catch (Exception e) {
            logger.error("工单信息查询失败", e);
            MessageUtil.addError("工单信息查询失败");
        }
        return null;
    }

    public void initForAdd(){
        strSubmitType="Add";
        worktileShowAdd = new WorktileShow();
        worktileShowAdd.setId(worktileService.getMaxNoPlusOne());
    }
    public void selectRecordAction(String strSubmitTypePara,WorktileShow worktileShowPara) {
        try {
            strSubmitType = strSubmitTypePara;
            // 查询
            if (strSubmitTypePara.equals("Sel")) {
                worktileShowSel = (WorktileShow) BeanUtils.cloneBean(worktileShowPara);
            }else if (strSubmitTypePara.equals("Upd")) {
                worktileShowUpd = (WorktileShow) BeanUtils.cloneBean(worktileShowPara);
            }else if (strSubmitTypePara.equals("Del")){
                worktileShowDel = (WorktileShow) BeanUtils.cloneBean(worktileShowPara);
            }
        } catch (Exception e) {
            MessageUtil.addError(e.getMessage());
        }
    }

    /**
     * 必须输入项目检查
     */
    private boolean submitPreCheck(WorktileShow worktileShowPara) {
        if (StringUtils.isEmpty(worktileShowPara.getId())) {
            MessageUtil.addError("请输入工单号！");
            return false;
        }
        return true;
    }
    /**
     * 提交维护权限
     *
     * @param
     */
    public void onClickForMngAction() {
        if (strSubmitType.equals("Del")) {
            deleteRecordAction(worktileShowDel);
            MessageUtil.addInfo("删除数据完成。");
        }else {
            if (strSubmitType.equals("Add")) {
                if (!submitPreCheck(worktileShowAdd)) {
                    return;
                }
                addRecordAction(worktileShowAdd);
                MessageUtil.addInfo("新增数据完成。");
                initForAdd();
            } else if (strSubmitType.equals("Upd")) {
                if (!submitPreCheck(worktileShowUpd)) {
                    return;
                }
                updRecordAction(worktileShowUpd);
                MessageUtil.addInfo("更新数据完成。");
            }
        }
        onQueryAllAction("false");
    }

    private void addRecordAction(WorktileShow worktileShowPara) {
        try {
            worktileService.insertRecord(worktileShowPara);
        } catch (Exception e) {
            logger.error("新增数据失败，", e);
            MessageUtil.addError(e.getMessage());
        }
    }
    private void updRecordAction(WorktileShow worktileShowPara) {
        try {
            worktileService.updateRecord(worktileShowPara);
        } catch (Exception e) {
            logger.error("更新数据失败，", e);
            MessageUtil.addError(e.getMessage());
        }
    }
    private void deleteRecordAction(WorktileShow worktileShowPara) {
        try {
            int deleteRecordNumOfCtt=
                    worktileService.deleteRecord(worktileShowPara.getPkid());
            if (deleteRecordNumOfCtt<=00){
                MessageUtil.addInfo("该记录已删除。");
                return;
            }
        } catch (Exception e) {
            logger.error("删除数据失败，", e);
            MessageUtil.addError(e.getMessage());
        }
    }

    /*智能字段 Start*/
    public WorktileService getWorktileService() {
        return worktileService;
    }

    public void setWorktileService(WorktileService worktileService) {
        this.worktileService = worktileService;
    }

    public WorktileShow getWorktileShowQry() {
        return worktileShowQry;
    }

    public void setWorktileShowQry(WorktileShow worktileShowQry) {
        this.worktileShowQry = worktileShowQry;
    }

    public WorktileShow getWorktileShowAdd() {
        return worktileShowAdd;
    }

    public void setWorktileShowAdd(WorktileShow worktileShowAdd) {
        this.worktileShowAdd = worktileShowAdd;
    }

    public WorktileShow getWorktileShowDel() {
        return worktileShowDel;
    }

    public void setWorktileShowDel(WorktileShow worktileShowDel) {
        this.worktileShowDel = worktileShowDel;
    }

    public List<WorktileShow> getWorktileShowList() {
        return worktileShowList;
    }

    public String getStrSubmitType() {
        return strSubmitType;
    }

    public WorktileShow getWorktileShowUpd() {
        return worktileShowUpd;
    }

    public void setWorktileShowUpd(WorktileShow worktileShowUpd) {
        this.worktileShowUpd = worktileShowUpd;
    }

    public WorktileShow getWorktileShowSel() {
        return worktileShowSel;
    }

    public void setWorktileShowSel(WorktileShow worktileShowSel) {
        this.worktileShowSel = worktileShowSel;
    }

/*智能字段 End*/
}
