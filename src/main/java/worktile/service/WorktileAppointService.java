package worktile.service;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skyline.util.ToolUtil;
import worktile.common.enums.EnumArchivedFlag;
import worktile.common.enums.EnumRecvExecFlag;
import worktile.repository.dao.OperMapper;
import worktile.repository.dao.WorktileAppointMapper;
import worktile.repository.dao.not_mybatis.MyWorktileAppointMapper;
import worktile.repository.model.WorktileAppoint;
import worktile.repository.model.WorktileAppointExample;
import worktile.repository.model.not_mybatis.WorktileAppointShow;
import worktile.repository.model.not_mybatis.WorktileShow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/2/13.
 */
@Service
public class WorktileAppointService {

    private static final Logger logger = LoggerFactory.getLogger(WorktileAppointService.class);
    @Autowired
    private WorktileAppointMapper worktileAppointMapper;
    @Autowired
    private MyWorktileAppointMapper myWorktileAppointMapper;
    @Autowired
    private OperMapper operMapper;

    public String getUserName(String operPkidPara){
        if(ToolUtil.getStrIgnoreNull(operPkidPara).equals("")){
            return "";
        }else {
            return operMapper.selectByPrimaryKey(operPkidPara).getName();
        }
    }

    public List<WorktileAppoint> getWorkorderAppointListByModelShow(WorktileAppointShow worktileAppointShowPara) {
        WorktileAppointExample example= new WorktileAppointExample();
        WorktileAppointExample.Criteria criteria = example.createCriteria();
        //可以为NULL的项
        // 指派Pkid
        if(!ToolUtil.getStrIgnoreNull(worktileAppointShowPara.getWorktileAppointPkid()).equals("")){
            criteria.andPkidEqualTo(worktileAppointShowPara.getWorktileAppointPkid());
        }
        // 工单主题
        if(!ToolUtil.getStrIgnoreNull(worktileAppointShowPara.getInfoPkid()).equals("")){
            criteria.andInfoPkidEqualTo(worktileAppointShowPara.getInfoPkid());
        }
        // 接收者Pkid
        if(!ToolUtil.getStrIgnoreNull(worktileAppointShowPara.getRecvPartPkid()).equals("")){
            criteria.andRecvPartPkidEqualTo(worktileAppointShowPara.getRecvPartPkid());
        }
        // 备注内容
        if(!ToolUtil.getStrIgnoreNull(worktileAppointShowPara.getRemark()).equals("")){
            criteria.andRemarkLike("%"+ worktileAppointShowPara.getRemark()+"%");
        }
        example.setOrderByClause("CREATED_TIME ASC") ;
        return worktileAppointMapper.selectByExample(example);
    }

    public List<WorktileAppoint> getWorkorderAppointListByModel(WorktileAppoint worktileAppointPara) {
        return getWorkorderAppointListByModelShow(fromModelToModelShow(worktileAppointPara));
    }
    public List<WorktileAppointShow> getWorkorderAppointShowListByModel(WorktileAppoint worktileAppointPara) {
        List<WorktileAppoint> worktileAppointListTemp =getWorkorderAppointListByModel(worktileAppointPara);
        List<WorktileAppointShow> worktileAppointShowListTemp =new ArrayList<>();
        for(WorktileAppoint worktileAppointUnit : worktileAppointListTemp){
            worktileAppointShowListTemp.add(fromModelToModelShow(worktileAppointUnit));
        }
        return worktileAppointShowListTemp;
    }
    public List<WorktileAppointShow> getWorkorderAppointShowListByModelShow(
            WorktileAppointShow worktileAppointShowPara) {
        List<WorktileAppoint> worktileAppointListTemp =getWorkorderAppointListByModelShow(worktileAppointShowPara);
        List<WorktileAppointShow> worktileAppointShowListTemp =new ArrayList<>();
        for(WorktileAppoint worktileAppointUnit : worktileAppointListTemp){
            worktileAppointShowListTemp.add(fromModelToModelShow(worktileAppointUnit));
        }
        return worktileAppointShowListTemp;
    }

    public List<WorktileAppointShow> getMyWorkorderAppointShowListByModelShow(
            WorktileAppointShow worktileAppointShowPara) {
       return myWorktileAppointMapper.getWorkorderAppointShowList(
               ToolUtil.getStrIgnoreNull(worktileAppointShowPara.getInfoId()),
               ToolUtil.getStrIgnoreNull(worktileAppointShowPara.getInfoName()),
               ToolUtil.getStrIgnoreNull(worktileAppointShowPara.getWorktileAppointPkid()));
    }

    public WorktileAppoint fromModelShowToModel(WorktileAppointShow worktileAppointShowPara) {
        WorktileAppoint worktileAppointTemp = new WorktileAppoint();
        worktileAppointTemp.setPkid(worktileAppointShowPara.getWorktileAppointPkid());
        worktileAppointTemp.setInfoPkid(worktileAppointShowPara.getInfoPkid());
        worktileAppointTemp.setRecvPartPkid(worktileAppointShowPara.getRecvPartPkid());
        worktileAppointTemp.setRecvExecFlag(worktileAppointShowPara.getRecvExecFlag());
        worktileAppointTemp.setCreatedBy(worktileAppointShowPara.getCreatedBy());
        worktileAppointTemp.setCreatedTime(worktileAppointShowPara.getCreatedTime());
        worktileAppointTemp.setLastUpdBy(worktileAppointShowPara.getLastUpdBy());
        worktileAppointTemp.setLastUpdTime(worktileAppointShowPara.getLastUpdTime());
        worktileAppointTemp.setRemark(worktileAppointShowPara.getRemark());
        worktileAppointTemp.setRecVersion(worktileAppointShowPara.getRecVersion());
        worktileAppointTemp.setTid(worktileAppointShowPara.getTid());
        return worktileAppointTemp;
    }
    public WorktileAppointShow fromModelToModelShow(WorktileAppoint worktileAppointPara) {
        WorktileAppointShow worktileAppointShowTemp = new WorktileAppointShow();
        worktileAppointShowTemp.setWorktileAppointPkid(worktileAppointPara.getPkid());
        worktileAppointShowTemp.setInfoPkid(worktileAppointPara.getInfoPkid());
        //worktileAppointShowTemp.setInfoName(workorderAppointPara.get());
        worktileAppointShowTemp.setRecvPartPkid(worktileAppointPara.getRecvPartPkid());
        worktileAppointShowTemp.setRecvPartName(getUserName(worktileAppointPara.getRecvPartPkid()));
        worktileAppointShowTemp.setRecvExecFlag(worktileAppointPara.getRecvExecFlag());
        worktileAppointShowTemp.setRecvExecFlagName(
                EnumRecvExecFlag.getValueByKey(worktileAppointPara.getRecvExecFlag()).getTitle());
        worktileAppointShowTemp.setCreatedBy(worktileAppointPara.getCreatedBy());
        worktileAppointShowTemp.setCreatedByName(getUserName(worktileAppointPara.getCreatedBy()));
        worktileAppointShowTemp.setCreatedTime(worktileAppointPara.getCreatedTime());
        worktileAppointShowTemp.setLastUpdBy(worktileAppointPara.getLastUpdBy());
        worktileAppointShowTemp.setLastUpdByName(getUserName(worktileAppointPara.getLastUpdBy()));
        worktileAppointShowTemp.setLastUpdTime(worktileAppointPara.getLastUpdTime());
        worktileAppointShowTemp.setRemark(worktileAppointPara.getRemark());
        worktileAppointShowTemp.setRecVersion(worktileAppointPara.getRecVersion());
        worktileAppointShowTemp.setTid(worktileAppointPara.getTid());
        return worktileAppointShowTemp;
    }

    public TreeNode getWorkorderAppointShowTreeNode(WorktileShow worktileShowPara){
        TreeNode workorderAppointShowRoot= new DefaultTreeNode(null, null);
        WorktileAppointShow worktileAppointShowPara =new WorktileAppointShow();
        worktileAppointShowPara.setInfoPkid(worktileShowPara.getPkid());
        List<WorktileAppointShow> worktileAppointShowListTemp =getWorkorderAppointShowListByModelShow(worktileAppointShowPara);
        if(worktileAppointShowListTemp !=null&& worktileAppointShowListTemp.size()>0) {
            WorktileAppointShow worktileAppointShowTemp = worktileAppointShowListTemp.get(0);
            WorktileAppointShow worktileAppointShow_TreeNode =new WorktileAppointShow();
            worktileAppointShow_TreeNode.setStrTreeNodeContent(worktileAppointShow_TreeNode.getRecvPartName());
            worktileAppointShow_TreeNode.setRecvExecFlag(worktileAppointShowTemp.getRecvExecFlag());
            workorderAppointShowRoot = new DefaultTreeNode(worktileAppointShow_TreeNode, null);
            workorderAppointShowRoot.setExpanded(true);
            //recursiveTreeNode(worktileShowPara.getPkid(), worktileAppointShow_TreeNode.getRecvPartPkid(),workorderAppointShowRoot);
        }
        return workorderAppointShowRoot;
    }

    private void recursiveTreeNode(String strInfoPkidPara,String strSendPartPkidPara,TreeNode parentNode){
        WorktileAppointShow worktileAppointShowPara = new WorktileAppointShow();
        worktileAppointShowPara.setInfoPkid(strInfoPkidPara);
        List<WorktileAppointShow> worktileAppointShowListTemp =
                getWorkorderAppointShowListByModelShow(worktileAppointShowPara);
        for (WorktileAppointShow worktileAppointShowUnit : worktileAppointShowListTemp) {
            worktileAppointShowUnit.setStrTreeNodeContent(
                    worktileAppointShowUnit.getRecvPartName()+"("+ worktileAppointShowUnit.getRecvExecFlagName()+")");
            TreeNode childNodeTemp = new DefaultTreeNode(worktileAppointShowUnit, parentNode);
            childNodeTemp.setExpanded(true);
            recursiveTreeNode(strInfoPkidPara, worktileAppointShowUnit.getRecvPartPkid(),childNodeTemp);
        }
    }

    public WorktileAppoint getWorkorderAppointByPkid(String pkid ){
        return  worktileAppointMapper.selectByPrimaryKey(pkid);
    }

    public void  updateBypkid(WorktileAppoint worktileAppoint){
        worktileAppointMapper.updateByPrimaryKey(worktileAppoint);
    }

    @Transactional
    public void insertRecord(WorktileAppointShow worktileAppointShowPara) {
        insertRecord(fromModelShowToModel(worktileAppointShowPara));
    }
    @Transactional
    public void insertRecord(WorktileAppoint worktileAppointPara) {
        String strOperatorIdTemp=ToolUtil.getOperatorManager().getOperator().getPkid();
        String strLastUpdTimeTemp=ToolUtil.getStrLastUpdTime();
        worktileAppointPara.setCreatedBy(strOperatorIdTemp);
        worktileAppointPara.setCreatedTime(strLastUpdTimeTemp);
        worktileAppointPara.setLastUpdBy(strOperatorIdTemp);
        worktileAppointPara.setLastUpdTime(strLastUpdTimeTemp);
        worktileAppointMapper.insertSelective(worktileAppointPara);
    }
    @Transactional
    public String updateRecord(WorktileAppointShow worktileAppointShowPara){
        // 为了防止异步操作数据
        return updateRecord(fromModelShowToModel(worktileAppointShowPara));
    }
    @Transactional
    public String updateRecord(WorktileAppoint worktileAppointPara){
        WorktileAppoint worktileAppointTemp =
                worktileAppointMapper.selectByPrimaryKey(worktileAppointPara.getPkid());
        if(worktileAppointTemp !=null){
            //此条记录目前在数据库中的版本
            int intRecVersionInDB=ToolUtil.getIntIgnoreNull(worktileAppointTemp.getRecVersion());
            int intRecVersion=ToolUtil.getIntIgnoreNull(worktileAppointPara.getRecVersion());
            if(intRecVersionInDB!=intRecVersion) {
                return "1";
            }
        }
        worktileAppointPara.setRecVersion(
                ToolUtil.getIntIgnoreNull(worktileAppointPara.getRecVersion())+1);
        worktileAppointPara.setLastUpdBy(ToolUtil.getOperatorManager().getOperator().getPkid());
        worktileAppointPara.setLastUpdTime(ToolUtil.getStrLastUpdTime());
        worktileAppointMapper.updateByPrimaryKey(worktileAppointPara);
        return "0";
    }
    @Transactional
    public int deleteRecord(String strPkidPara){
        return worktileAppointMapper.deleteByPrimaryKey(strPkidPara);
    }
}
