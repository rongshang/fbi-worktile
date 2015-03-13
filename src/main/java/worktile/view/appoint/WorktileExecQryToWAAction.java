package worktile.view.appoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.util.MessageUtil;
import skyline.util.ToolUtil;
import worktile.common.enums.EnumRecvExecFlag;
import worktile.repository.model.WorktileAppoint;
import worktile.repository.model.not_mybatis.WorktileAppointShow;
import worktile.service.DeptOperService;
import worktile.service.WorktileAppointService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/2/13.
 * atuo: huzy
 * 工单指派
 */

@ManagedBean
@ViewScoped
public class WorktileExecQryToWAAction {
    private static final Logger logger = LoggerFactory.getLogger(WorktileExecQryToWAAction.class);
    @ManagedProperty(value = "#{worktileAppointService}")
    private WorktileAppointService worktileAppointService;
    @ManagedProperty(value = "#{deptOperService}")
    private DeptOperService deptOperService;

    private String strWorkorderInfoPkid;
    private String strWorkorderAppointPkid;
    private List<WorktileAppointShow> worktileAppointShowList;

    @PostConstruct
    public void init(){
        Map parammap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if  (parammap.containsKey("strWorkorderInfoPkid")){
            strWorkorderInfoPkid=parammap.get("strWorkorderInfoPkid").toString();
        }
        if  (parammap.containsKey("strWorkorderAppointPkid")){
            strWorkorderAppointPkid=parammap.get("strWorkorderAppointPkid").toString();
        }

        worktileAppointShowList =new ArrayList<>();
        onQueryAction(strWorkorderInfoPkid,strWorkorderAppointPkid);
    }

    public String onQueryAction(String strWorkorderInfoPkidPara,String strWorkorderAppointPkidPara) {
        try {
            WorktileAppointShow wAShow_CurrentAppointRecvContinueAppoint=new WorktileAppointShow();
            List<WorktileAppoint> wAList_CurrentAppointRecvContinueAppoint=new ArrayList<>();

            WorktileAppointShow worktileAppointShowPara =new WorktileAppointShow();
            worktileAppointShowPara.setInfoPkid(strWorkorderInfoPkidPara);
            worktileAppointShowPara.setWorktileAppointPkid(strWorkorderAppointPkidPara);
            worktileAppointShowList =
                    worktileAppointService.getMyWorkorderAppointShowListByModelShow(worktileAppointShowPara);

        } catch (Exception e) {
            logger.error("工单信息查询失败", e);
            MessageUtil.addError("工单信息查询失败");
        }
        return null;
    }

    public void onClickForMngAction(WorktileAppointShow worktileAppointShowNewPara) {
        WorktileAppoint worktileAppointInDB =new WorktileAppoint();

        // 查询某一工单的 数据库中的 全部指派情况
        WorktileAppointShow worktileAppointShowByInfoPkid =new WorktileAppointShow();
        worktileAppointShowByInfoPkid.setInfoPkid(worktileAppointShowNewPara.getInfoPkid());
        List<WorktileAppoint> worktileAppointListByInfoPkid =
                worktileAppointService.getWorkorderAppointListByModelShow(worktileAppointShowByInfoPkid);

        // 存在着的数据（更新或者插入）
        if(worktileAppointShowNewPara.getWorktileAppointPkid()!=null) {
            worktileAppointInDB =
                    worktileAppointService.getWorkorderAppointByPkid(worktileAppointShowNewPara.getWorktileAppointPkid());
            // 画面上的数据已经不存在了
            if (worktileAppointInDB == null) {
                MessageUtil.addError("该记录已经删除，您的页面数据已过时！");
                return;
            }

            //此条记录目前在数据库中的版本和在页面上的版本不一致
            int intRecVersionInDB= ToolUtil.getIntIgnoreNull(worktileAppointInDB.getRecVersion());
            int intRecVersionNew=ToolUtil.getIntIgnoreNull(worktileAppointShowNewPara.getRecVersion());
            if(intRecVersionInDB!=intRecVersionNew) {
                MessageUtil.addError("画面上的数据版本过时，请重新取得后再操作！");
                return;
            }

            // 删除操作
            if(worktileAppointShowNewPara.getRecvPartPkid()==null){
                worktileAppointService.deleteRecord(worktileAppointShowNewPara.getWorktileAppointPkid());
                MessageUtil.addInfo("删除数据成功。");
            }else{// 更新操作
                // 查询某一工单的 画面上的 指派 情况
                WorktileAppointShow worktileAppointShowByInfoAppint =new WorktileAppointShow();
                worktileAppointShowByInfoAppint.setInfoPkid(worktileAppointShowNewPara.getInfoPkid());
                worktileAppointShowByInfoAppint.setRecvPartPkid(worktileAppointShowNewPara.getRecvPartPkid());
                worktileAppointShowByInfoAppint.setRemark(worktileAppointShowNewPara.getRemark());
                List<WorktileAppoint> worktileAppointListByInfoAppint =
                        worktileAppointService.getWorkorderAppointListByModelShow(worktileAppointShowByInfoAppint);
                if(worktileAppointListByInfoAppint.size()>0){
                    MessageUtil.addError("您目前的工单已经如此指派过，请不要反复提交相同操作！");
                    return;
                }
                //更新
                worktileAppointService.updateRecord(worktileAppointShowNewPara);
                    MessageUtil.addInfo("更新数据成功。");
            }
        }else{// 不存在着的数据 插入操作
            worktileAppointShowNewPara.setRecvExecFlag(EnumRecvExecFlag.RECV_EXEC_FLAG0.getCode());
            worktileAppointService.insertRecord(worktileAppointShowNewPara);
            MessageUtil.addInfo("新增数据完成。");
        }
        onQueryAction(strWorkorderInfoPkid,strWorkorderAppointPkid);
    }

    public WorktileAppointService getWorktileAppointService() {
        return worktileAppointService;
    }

    public void setWorktileAppointService(WorktileAppointService worktileAppointService) {
        this.worktileAppointService = worktileAppointService;
    }

    public DeptOperService getDeptOperService() {
        return deptOperService;
    }

    public void setDeptOperService(DeptOperService deptOperService) {
        this.deptOperService = deptOperService;
    }

    public List<WorktileAppointShow> getWorktileAppointShowList() {
        return worktileAppointShowList;
    }

    public void setWorktileAppointShowList(List<WorktileAppointShow> worktileAppointShowList) {
        this.worktileAppointShowList = worktileAppointShowList;
    }

    public String getStrWorkorderInfoPkid() {
        return strWorkorderInfoPkid;
    }

    public void setStrWorkorderInfoPkid(String strWorkorderInfoPkid) {
        this.strWorkorderInfoPkid = strWorkorderInfoPkid;
    }

    public String getStrWorkorderAppointPkid() {
        return strWorkorderAppointPkid;
    }

    public void setStrWorkorderAppointPkid(String strWorkorderAppointPkid) {
        this.strWorkorderAppointPkid = strWorkorderAppointPkid;
    }
}
