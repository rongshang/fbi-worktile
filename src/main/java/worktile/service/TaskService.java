package worktile.service;

import worktile.repository.dao.WorktileMapper;
import worktile.repository.dao.not_mybatis.MyTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skyline.util.ToolUtil;
import worktile.repository.model.not_mybatis.WorktileAppointShow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Think
 * Date: 13-5-5
 * Time: 上午8:50
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TaskService {
    @Autowired
    private WorktileMapper worktileMapper;
    @Autowired
    private MyTaskMapper myTaskMapper;

    public List<WorktileAppointShow> getTodoWorktileAppointShowList(String strOperPkidPara) {
        return myTaskMapper.getTodoWorktileAppointShowList(strOperPkidPara);
    }

    public List<WorktileAppointShow> getDoneWorktileAppointShowList(String strOperPkidPara) {
        return myTaskMapper.getDoneWorktileAppointShowList(strOperPkidPara);
    }
    public List<WorktileAppointShow> getDoingWorktileAppointShowList(String strOperPkidPara) {
        return myTaskMapper.getDoingWorktileAppointShowList(strOperPkidPara);
    }

    public List<WorktileAppointShow> initDoneWorktileAppointShowList() {
        List<WorktileAppointShow> taskShowList = new ArrayList<WorktileAppointShow>();
        //通过OperatorManager
        String strOperPkidTemp = ToolUtil.getOperatorManager().getOperator().getPkid();

        List<WorktileAppointShow> detailWorktileAppointShowListTemp = getDoneWorktileAppointShowList(strOperPkidTemp);

        return detailWorktileAppointShowListTemp;
    }

    public List<WorktileAppointShow> initTodoWorktileAppointShowList() {
        List<WorktileAppointShow> taskShowList = new ArrayList<WorktileAppointShow>();
        //通过OperatorManager
        String strOperPkidTemp = ToolUtil.getOperatorManager().getOperator().getPkid();

        List<WorktileAppointShow> detailWorktileAppointShowListTemp = getTodoWorktileAppointShowList(strOperPkidTemp);

        return detailWorktileAppointShowListTemp;
    }
    public List<WorktileAppointShow> initDoingWorktileAppointShowList() {
        List<WorktileAppointShow> taskShowList = new ArrayList<WorktileAppointShow>();
        //通过OperatorManager
        String strOperPkidTemp = ToolUtil.getOperatorManager().getOperator().getPkid();

        List<WorktileAppointShow> detailWorktileAppointShowListTemp = getDoingWorktileAppointShowList(strOperPkidTemp);

        return detailWorktileAppointShowListTemp;
    }

}
