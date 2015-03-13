package worktile.view.appoint;

import skyline.util.MessageUtil;
import worktile.common.enums.EnumRecvExecFlag;
import worktile.repository.model.WorktileAppoint;
import worktile.repository.model.not_mybatis.WorktileAppointShow;
import worktile.service.TaskService;
import worktile.service.WorktileAppointService;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
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
public class WorktileExecQryAction {
    private static final Logger logger = LoggerFactory.getLogger(WorktileExecQryAction.class);
    @ManagedProperty(value = "#{taskService}")
    private TaskService taskService;
    @ManagedProperty(value = "#{worktileAppointService}")
    private WorktileAppointService worktileAppointService;

    private TreeNode selfTaskTreeRoot;
    private List<WorktileAppointShow> taskList;

    @PostConstruct
    public void init() {
        //整个任务列表
        try {
            selfTaskTreeRoot = new DefaultTreeNode("ROOT", null);
            WorktileAppointShow taskShow = new WorktileAppointShow();
            taskShow.setRowHeaderText("工单任务");
            TreeNode srTask = new DefaultTreeNode(taskShow, selfTaskTreeRoot);
            srTask.setExpanded(true);
            WorktileAppointShow taskShowFront1 = new WorktileAppointShow();
            taskShowFront1.setRowHeaderText("待执行任务");
            TreeNode todotask = new DefaultTreeNode(taskShowFront1, srTask);
            String todoSize = getChildTodoNode(todotask);
            if(!todoSize.equals("0")){
                taskShowFront1.setRowHeaderText("待执行任务("+todoSize+")");
            }
            WorktileAppointShow taskShowFront2 = new WorktileAppointShow();
            taskShowFront2.setRowHeaderText("执行中任务");
            TreeNode doingtask = new DefaultTreeNode(taskShowFront2, srTask);
            String doingSize =  getChildDoingNode(doingtask);
            if(!doingSize.equals("0")){
                taskShowFront2.setRowHeaderText("执行中任务("+doingSize+")");
            }
            WorktileAppointShow taskShowFront3 = new WorktileAppointShow();
            taskShowFront3.setRowHeaderText("已执行任务");
            TreeNode donetask = new DefaultTreeNode(taskShowFront3, srTask);
            String doneSize =  getChildDoneNode(donetask);
            if(!doneSize.equals("0")){
                taskShowFront3.setRowHeaderText("已执行任务("+doneSize+")");
            }
        } catch (Exception e) {
            logger.error("初始化失败", e);
        }
    }

    //获取结算单最末端节点   传入参数父节点
    private String getChildDoneNode(TreeNode doneTask) throws Exception {
        List<WorktileAppointShow> taskListDone = taskService.initDoneWorktileAppointShowList();
        String resnum = "0";
        if (taskListDone.size() > 0) {
            resnum=String.valueOf(taskListDone.size());
            for (int i = 0; i < taskListDone.size(); i++) {
                WorktileAppointShow taskShow = new WorktileAppointShow();
                WorktileAppointShow taskShowTemp = taskListDone.get(i);
                taskShow.setInfoPkid(taskShowTemp.getInfoPkid());
                taskShow.setInfoId(taskShowTemp.getInfoId());
                taskShow.setInfoName(taskShowTemp.getInfoName());
                taskShow.setWorktileAppointPkid(taskShowTemp.getWorktileAppointPkid());
                taskShow.setRecvExecFlag(EnumRecvExecFlag.RECV_EXEC_FLAG2.getCode());
                taskShow.setCreatedTime(taskShowTemp.getCreatedTime());
                taskShow.setRecvPartName(taskShowTemp.getRecvPartName());
                new DefaultTreeNode(taskShow, doneTask);
            }
        }
        return resnum;

    }

    private String getChildTodoNode(TreeNode todoTask)
            throws Exception {
        List<WorktileAppointShow> taskListTodo = taskService.initTodoWorktileAppointShowList();
        String resnum = "0";
        if (taskListTodo.size() > 0) {
            resnum=String.valueOf(taskListTodo.size());
            for (int i = 0; i < taskListTodo.size(); i++) {
                WorktileAppointShow taskShow = new WorktileAppointShow();
                WorktileAppointShow taskShowTemp = taskListTodo.get(i);
                taskShow.setInfoPkid(taskShowTemp.getInfoPkid());
                taskShow.setInfoId(taskShowTemp.getInfoId());
                taskShow.setInfoName(taskShowTemp.getInfoName());
                taskShow.setWorktileAppointPkid(taskShowTemp.getWorktileAppointPkid());
                taskShow.setRecvExecFlag(EnumRecvExecFlag.RECV_EXEC_FLAG0.getCode());
                taskShow.setCreatedTime(taskShowTemp.getCreatedTime());
                taskShow.setRecvPartName(taskShowTemp.getRecvPartName());
                new DefaultTreeNode(taskShow, todoTask);
            }
        }
        return resnum;
    }
    private String getChildDoingNode(TreeNode doingTask)
            throws Exception {
        List<WorktileAppointShow> taskListTodo = taskService.initDoingWorktileAppointShowList();
        String resnum = "0";
        if (taskListTodo.size() > 0) {
            resnum=String.valueOf(taskListTodo.size());
            for (int i = 0; i < taskListTodo.size(); i++) {
                WorktileAppointShow taskShow = new WorktileAppointShow();
                WorktileAppointShow taskShowTemp = taskListTodo.get(i);
                taskShow.setInfoPkid(taskShowTemp.getInfoPkid());
                taskShow.setInfoId(taskShowTemp.getInfoId());
                taskShow.setInfoName(taskShowTemp.getInfoName());
                taskShow.setWorktileAppointPkid(taskShowTemp.getWorktileAppointPkid());
                taskShow.setRecvExecFlag(EnumRecvExecFlag.RECV_EXEC_FLAG1.getCode());
                taskShow.setCreatedTime(taskShowTemp.getCreatedTime());
                taskShow.setRecvPartName(taskShowTemp.getRecvPartName());
                new DefaultTreeNode(taskShow, doingTask);
            }
        }
        return resnum;
    }
    public void onClickDoingAction(String pkid){
       try{
            if (!(pkid.equals("")) ||( pkid !=null )) {
                // 状态标志：
                WorktileAppoint worktileAppoint = worktileAppointService.getWorkorderAppointByPkid(pkid);
                worktileAppoint.setRecvExecFlag(EnumRecvExecFlag.RECV_EXEC_FLAG1.getCode());
                worktileAppointService.updateBypkid(worktileAppoint);
                MessageUtil.addInfo("操作成功！");
                init();
            }
        }catch(Exception e){
            logger.error("操作失败", e);
         MessageUtil.addError("操作失败");
        }
    return  ;
    }
    public void onClickDoneAction(String pkid){
        try{
            if ((!pkid.equals("")) ||( pkid !=null )) {
                // 状态标志：
                WorktileAppoint worktileAppoint = worktileAppointService.getWorkorderAppointByPkid(pkid);
                worktileAppoint.setRecvExecFlag(EnumRecvExecFlag.RECV_EXEC_FLAG2.getCode());
                worktileAppointService.updateBypkid(worktileAppoint);
                MessageUtil.addInfo("操作成功！");
                init();
            }
        }catch(Exception e){
            logger.error("操作失败", e);
            MessageUtil.addError("操作失败");
        }
        return  ;
    }
    public void onClickUndoAction(String pkid){
        try{
            if ((pkid != "") ||( pkid !=null )) {
                // 状态标志：
                WorktileAppoint worktileAppoint = worktileAppointService.getWorkorderAppointByPkid(pkid);
                worktileAppoint.setRecvExecFlag(EnumRecvExecFlag.RECV_EXEC_FLAG0.getCode());
                worktileAppointService.updateBypkid(worktileAppoint);
                MessageUtil.addInfo("操作成功！");
                init();
            }
        }catch(Exception e){
            logger.error("操作失败", e);
            MessageUtil.addError("操作失败");
        }
        return  ;
    }

    public TreeNode getSelfTaskTreeRoot() {
        return selfTaskTreeRoot;
    }

    public void setSelfTaskTreeRoot(TreeNode selfTaskTreeRoot) {
        this.selfTaskTreeRoot = selfTaskTreeRoot;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public List<WorktileAppointShow> getStlPowerList() {
        return taskList;
    }

    public void setStlPowerList(List<WorktileAppointShow> stlPowerList) {
        this.taskList = stlPowerList;
    }

    public WorktileAppointService getWorktileAppointService() {
        return worktileAppointService;
    }

    public void setWorktileAppointService(WorktileAppointService worktileAppointService) {
        this.worktileAppointService = worktileAppointService;
    }
}