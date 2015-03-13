package worktile.service;

import skyline.util.ToolUtil;
import worktile.repository.dao.WorktileAppointHisMapper;
import worktile.repository.dao.OperMapper;
//import worktile.repository.dao.not_mybatis.MyFlowHisMapper;
import org.springframework.stereotype.Service;
import worktile.repository.model.WorktileAppointHis;
import worktile.repository.model.WorktileAppointHisExample;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-1-31
 * Time: ÏÂÎç6:31
 * To change this template use File | Settings | File Templates.
 */
@Service
public class WorktileAppointHisService {
    @Resource
    private WorktileAppointHisMapper worktileAppointHisMapper;
    @Resource
    private OperMapper operMapper;

    public List<WorktileAppointHis> getListByModel(WorktileAppointHis worktileAppointHisPara) {
        WorktileAppointHisExample example= new WorktileAppointHisExample();
        WorktileAppointHisExample.Criteria criteria = example.createCriteria();
        criteria.andInfoPkidLike("%" + ToolUtil.getStrIgnoreNull(worktileAppointHisPara.getInfoPkid()) + "%");
        if (!ToolUtil.getStrIgnoreNull(worktileAppointHisPara.getCreatedBy()).equals("")){
            criteria.andCreatedByLike("%"+ worktileAppointHisPara.getCreatedBy()+"%");
        }
        if (!ToolUtil.getStrIgnoreNull(worktileAppointHisPara.getCreatedTime()).equals("")){
            criteria.andCreatedTimeLike("%"+ worktileAppointHisPara.getCreatedTime()+"%");
        }
        if (!ToolUtil.getStrIgnoreNull(worktileAppointHisPara.getRemark()).equals("")){
            criteria.andRemarkLike("%"+ worktileAppointHisPara.getRemark()+"%");
        }
        example.setOrderByClause("INFO_PKID ASC,CREATED_TIME ASC") ;
        return worktileAppointHisMapper.selectByExample(example);
    }
    /*
    public List<FlowHis> getSubStlListByFlowHis(String powerPkid,String periodNo){
        return myFlowHisMapper.getSubStlListByFlowHis(powerPkid,periodNo);
    }

    public List<FlowHis> selectListByModel(String strInfoType,String strInfoPkid) {
        FlowHisExample example= new FlowHisExample();
        FlowHisExample.Criteria criteria = example.createCriteria();
        criteria.andInfoTypeLike("%" + strInfoType + "%")
                .andInfoPkidLike("%" + strInfoPkid + "%");
        return flowHisMapper.selectByExample(example);
    }*/

    public void insertRecord(WorktileAppointHis worktileAppointHisPara) {
        String strOperatorPkidTemp= ToolUtil.getOperatorManager().getOperator().getPkid();
        String strOperatorNameTemp=ToolUtil.getOperatorManager().getOperator().getName();
        String strLastUpdTimeTemp=ToolUtil.getStrLastUpdTime();
        worktileAppointHisPara.setCreatedBy(strOperatorPkidTemp);
        worktileAppointHisPara.setCreatedByName(strOperatorNameTemp);
        worktileAppointHisPara.setCreatedTime(strLastUpdTimeTemp);
        worktileAppointHisMapper.insertSelective(worktileAppointHisPara);
    }
}
