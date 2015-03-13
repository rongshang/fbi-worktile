package worktile.service;

import org.apache.commons.lang.StringUtils;
import worktile.common.enums.EnumArchivedFlag;
import worktile.repository.dao.OperMapper;
import worktile.repository.dao.WorktileMapper;
import worktile.repository.dao.not_mybatis.MyWorktileMapper;
import worktile.repository.model.*;
import worktile.repository.model.not_mybatis.WorktileShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skyline.util.ToolUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-1-31
 * Time: 下午6:31
 * To change this template use File | Settings | File Templates.
 */
@Service
public class WorktileService {
    @Autowired
    private WorktileMapper worktileMapper;
    @Autowired
    private MyWorktileMapper myWorktileMapper;
    @Autowired
    private OperMapper operMapper;

    public String getUserName(String operPkidPara){
        if(ToolUtil.getStrIgnoreNull(operPkidPara).equals("")){
            return "";
        }else {
            return operMapper.selectByPrimaryKey(operPkidPara).getName();
        }
    }

    public List<Worktile> getWorkorderInfoListByModelShow(WorktileShow worktileShowPara) {
        WorktileExample example= new WorktileExample();
        WorktileExample.Criteria criteria = example.createCriteria();
        //可以为NULL的项
        // 工单编号
        if(!ToolUtil.getStrIgnoreNull(worktileShowPara.getId()).equals("")){
            criteria.andIdLike("%"+ worktileShowPara.getId()+"%");
        }
        // 工单主题
        if(!ToolUtil.getStrIgnoreNull(worktileShowPara.getSubject()).equals("")){
            criteria.andSubjectLike("%" + worktileShowPara.getSubject() + "%");
        }
        // 开始时间
        if(!ToolUtil.getStrIgnoreNull(worktileShowPara.getStartTime()).equals("")){
            criteria.andStartTimeLike("%"+ worktileShowPara.getStartTime()+"%");
        }
        // 截止时间
        if(!ToolUtil.getStrIgnoreNull(worktileShowPara.getEndTime()).equals("")){
            criteria.andEndTimeLike("%"+ worktileShowPara.getEndTime()+"%");
        }
        // 备注内容
        if(!ToolUtil.getStrIgnoreNull(worktileShowPara.getRemark()).equals("")){
            criteria.andRemarkLike(worktileShowPara.getRemark());
        }
        example.setOrderByClause("ID ASC") ;
        return worktileMapper.selectByExample(example);
    }
    public List<WorktileShow> getWorkorderInfoShowListByModelShow(WorktileShow worktileShowPara) {
        List<Worktile> worktileListTemp =getWorkorderInfoListByModelShow(worktileShowPara);
        List<WorktileShow> worktileShowListTemp =new ArrayList<>();
        for(Worktile worktileUnit : worktileListTemp){
            worktileShowListTemp.add(fromModelToModelShow(worktileUnit));
        }
        return worktileShowListTemp;
    }

    public Worktile getWorkorderInfoByPkId(String strPkid) {
        return worktileMapper.selectByPrimaryKey(strPkid);
    }
    public WorktileShow getWorkorderInfoShowByPkId(String strPkid) {
        return fromModelToModelShow(getWorkorderInfoByPkId(strPkid));
    }

    /*public FlowHis fromCttInfoToFlowCtrlHis(CttInfo cttInfoPara,String strOperTypePara){
        FlowCtrlHis flowCtrlHisTemp =new FlowCtrlHis();
        flowCtrlHisTemp.setInfoType(cttInfoPara.getCttType());
        flowCtrlHisTemp.setInfoPkid(cttInfoPara.getPkid());
        flowCtrlHisTemp.setInfoId(cttInfoPara.getId());
        flowCtrlHisTemp.setInfoName(cttInfoPara.getName());
        flowCtrlHisTemp.setFlowStatus(cttInfoPara.getFlowStatus());
        flowCtrlHisTemp.setFlowStatusReason(cttInfoPara.getFlowStatusReason());
        flowCtrlHisTemp.setFlowStatusRemark(cttInfoPara.getFlowStatusRemark());
        flowCtrlHisTemp.setCreatedBy(cttInfoPara.getCreatedBy());
        flowCtrlHisTemp.setCreatedByName(getUserName(cttInfoPara.getCreatedBy()));
        flowCtrlHisTemp.setCreatedTime(cttInfoPara.getCreatedTime());
        flowCtrlHisTemp.setRemark(cttInfoPara.getRemark());
        flowCtrlHisTemp.setOperType(strOperTypePara);
        return flowCtrlHisTemp;
    }*/

    @Transactional
    public void insertRecord(WorktileShow worktileShowPara) {
        insertRecord(fromModelShowToModel(worktileShowPara));
    }
    @Transactional
    public void insertRecord(Worktile worktilePara) {
        String strOperatorIdTemp=ToolUtil.getOperatorManager().getOperator().getPkid();
        String strLastUpdTimeTemp=ToolUtil.getStrLastUpdTime();
        worktilePara.setArchivedFlag(EnumArchivedFlag.ARCHIVED_FLAG0.getCode());
        worktilePara.setCreatedBy(strOperatorIdTemp);
        worktilePara.setCreatedTime(strLastUpdTimeTemp);
        worktilePara.setLastUpdBy(strOperatorIdTemp);
        worktilePara.setLastUpdTime(strLastUpdTimeTemp);
        worktileMapper.insertSelective(worktilePara);
    }
    @Transactional
    public String updateRecord(WorktileShow worktileShowPara){
        // 为了防止异步操作数据
        return updateRecord(fromModelShowToModel(worktileShowPara));
    }
    @Transactional
    public String updateRecord(Worktile worktilePara){
        Worktile worktileTemp =getWorkorderInfoByPkId(worktilePara.getPkid());
        if(worktileTemp !=null){
            //此条记录目前在数据库中的版本
            int intRecVersionInDB=ToolUtil.getIntIgnoreNull(worktileTemp.getRecVersion());
            int intRecVersion=ToolUtil.getIntIgnoreNull(worktilePara.getRecVersion());
            if(intRecVersionInDB!=intRecVersion) {
                return "1";
            }
        }
        worktilePara.setRecVersion(
                ToolUtil.getIntIgnoreNull(worktilePara.getRecVersion())+1);
        worktilePara.setArchivedFlag("0");
        worktilePara.setLastUpdBy(ToolUtil.getOperatorManager().getOperator().getPkid());
        worktilePara.setLastUpdTime(ToolUtil.getStrLastUpdTime());
        worktileMapper.updateByPrimaryKey(worktilePara);
        /*flowCtrlHisService.insertRecord(
                fromCttInfoToFlowCtrlHis(cttInfoPara,EnumOperType.OPER_TYPE1.getCode()));*/
        return "0";
    }
    @Transactional
    public int deleteRecord(String strCttInfoPkidPara){
        return worktileMapper.deleteByPrimaryKey(strCttInfoPkidPara);
    }

    public String getMaxNoPlusOne() {
        Integer intTemp;
        String strMaxId = myWorktileMapper.getStrMaxCttId() ;
        if (StringUtils.isEmpty(ToolUtil.getStrIgnoreNull(strMaxId))) {
            strMaxId = "ORDER" + ToolUtil.getStrToday() + "0001";
        } else {
            String strTemp = strMaxId.substring(strMaxId.length() - 4).replaceFirst("^0+", "");
            if (ToolUtil.strIsDigit(strTemp)) {
                intTemp = Integer.parseInt(strTemp);
                intTemp = intTemp + 1;
                strMaxId = strMaxId.substring(0, strMaxId.length() - 4) + StringUtils.leftPad(intTemp.toString(), 4, "0");
            }
        }
        return strMaxId;
    }
    public Worktile fromModelShowToModel(WorktileShow worktileShowPara) {
        Worktile worktileTemp = new Worktile();
        worktileTemp.setPkid(worktileShowPara.getPkid());
        worktileTemp.setId(worktileShowPara.getId());
        worktileTemp.setSubject(worktileShowPara.getSubject());
        worktileTemp.setContent(worktileShowPara.getContent());
        worktileTemp.setStartTime(worktileShowPara.getStartTime());
        worktileTemp.setEndTime(worktileShowPara.getEndTime());
        worktileTemp.setAttachment(worktileShowPara.getAttachment());
        worktileTemp.setArchivedFlag(worktileShowPara.getArchivedFlag());
        worktileTemp.setCreatedBy(worktileShowPara.getCreatedBy());
        worktileTemp.setCreatedTime(worktileShowPara.getCreatedTime());
        worktileTemp.setLastUpdBy(worktileShowPara.getLastUpdBy());
        worktileTemp.setLastUpdTime(worktileShowPara.getLastUpdTime());
        worktileTemp.setRemark(worktileShowPara.getRemark());
        worktileTemp.setRecVersion(worktileShowPara.getRecVersion());
        worktileTemp.setTid(worktileShowPara.getTid());
        return worktileTemp;
    }
    public WorktileShow fromModelToModelShow(Worktile worktilePara) {
        WorktileShow worktileShowTemp = new WorktileShow();
        worktileShowTemp.setPkid(worktilePara.getPkid());
        worktileShowTemp.setId(worktilePara.getId());
        worktileShowTemp.setSubject(worktilePara.getSubject());
        worktileShowTemp.setContent(worktilePara.getContent());
        worktileShowTemp.setStartTime(worktilePara.getStartTime());
        worktileShowTemp.setEndTime(worktilePara.getEndTime());
        worktileShowTemp.setAttachment(worktilePara.getAttachment());
        worktileShowTemp.setArchivedFlag(worktilePara.getArchivedFlag());
        worktileShowTemp.setCreatedBy(worktilePara.getCreatedBy());
        worktileShowTemp.setCreatedByName(getUserName(worktilePara.getCreatedBy()));
        worktileShowTemp.setCreatedTime(worktilePara.getCreatedTime());
        worktileShowTemp.setLastUpdBy(worktilePara.getLastUpdBy());
        worktileShowTemp.setLastUpdByName(getUserName(worktilePara.getLastUpdBy()));
        worktileShowTemp.setLastUpdTime(worktilePara.getLastUpdTime());
        worktileShowTemp.setRemark(worktilePara.getRemark());
        worktileShowTemp.setRecVersion(worktilePara.getRecVersion());
        worktileShowTemp.setTid(worktilePara.getTid());
        return worktileShowTemp;
    }
}
