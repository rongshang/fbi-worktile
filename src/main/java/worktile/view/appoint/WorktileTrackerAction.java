package worktile.view.appoint;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.util.MessageUtil;
import worktile.repository.model.not_mybatis.WorktileShow;
import worktile.service.WorktileAppointService;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Created by Administrator on 2015/2/13.
 * atuo: huzy
 * 工单指派
 */

@ManagedBean
@ViewScoped
public class WorktileTrackerAction {
    private static final Logger logger = LoggerFactory.getLogger(WorktileTrackerAction.class);
    @ManagedProperty(value = "#{worktileAppointService}")
    private WorktileAppointService worktileAppointService;

    private TreeNode root;
    /*任务跟踪显示标志*/
    private String strTaskTrackerFlag;

    @PostConstruct
    public void init(){
        strTaskTrackerFlag="false";
    }

    public void initTree(WorktileShow worktileShowPara) {
        root = worktileAppointService.getWorkorderAppointShowTreeNode(worktileShowPara);
        strTaskTrackerFlag="true";
    }

    public void selectRecordAction(WorktileShow worktileShowPara) {
        try {
            initTree(worktileShowPara);
        } catch (Exception e) {
            MessageUtil.addError(e.getMessage());
            logger.info("WorkorderAssignAction类中的selectRecordAction异常:"+e.toString());
        }
    }
    public void onNodeSelect(SelectEvent event) {
        TreeNode node = (TreeNode) event.getObject();

        //populate if not already loaded
        if(node.getChildren().isEmpty()) {
            //Object label = node.getLabel();


        }
    }

    public void onNodeDblselect(SelectEvent event) {
        //this.selectedNode = (TreeNode) event.getObject();
    }

    public TreeNode getRoot() {
        return root;
    }
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public void setWorktileAppointService(WorktileAppointService worktileAppointService) {
        this.worktileAppointService = worktileAppointService;
    }

    public String getStrTaskTrackerFlag() {
        return strTaskTrackerFlag;
    }

    public void setStrTaskTrackerFlag(String strTaskTrackerFlag) {
        this.strTaskTrackerFlag = strTaskTrackerFlag;
    }
}
