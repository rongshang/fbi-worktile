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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/2/13.
 * atuo: huzy
 * ����ָ��
 */

@ManagedBean
@ViewScoped
public class WorktileAppointAction {
    private static final Logger logger = LoggerFactory.getLogger(WorktileAppointAction.class);
    @ManagedProperty(value = "#{worktileAppointService}")
    private WorktileAppointService worktileAppointService;
    @ManagedProperty(value = "#{deptOperService}")
    private DeptOperService deptOperService;

    private WorktileAppointShow worktileAppointShowQry;
    private List<WorktileAppointShow> worktileAppointShowList;

    @PostConstruct
    public void init(){
        worktileAppointShowQry = new WorktileAppointShow();
        worktileAppointShowList =new ArrayList<>();
    }

    public String onQueryAction(String strQryMsgOutPara) {
        try {
            WorktileAppointShow wAShow_CurrentAppointRecvContinueAppoint=new WorktileAppointShow();
            List<WorktileAppoint> wAList_CurrentAppointRecvContinueAppoint=new ArrayList<>();
            worktileAppointShowList =
                    worktileAppointService.getMyWorkorderAppointShowListByModelShow(worktileAppointShowQry);
            if(strQryMsgOutPara.equals("true"))  {
                if (worktileAppointShowList.isEmpty()) {
                    MessageUtil.addWarn("û�в�ѯ�����ݡ�");
                }
            }
        } catch (Exception e) {
            logger.error("������Ϣ��ѯʧ��", e);
            MessageUtil.addError("������Ϣ��ѯʧ��");
        }
        return null;
    }

    public void onClickForMngAction(WorktileAppointShow worktileAppointShowNewPara) {
        WorktileAppoint worktileAppointInDB =new WorktileAppoint();

        // ��ѯĳһ������ ���ݿ��е� ȫ��ָ�����
        WorktileAppointShow worktileAppointShowByInfoPkid =new WorktileAppointShow();
        worktileAppointShowByInfoPkid.setInfoPkid(worktileAppointShowNewPara.getInfoPkid());
        List<WorktileAppoint> worktileAppointListByInfoPkid =
                worktileAppointService.getWorkorderAppointListByModelShow(worktileAppointShowByInfoPkid);

        // �����ŵ����ݣ����»��߲��룩
        if(worktileAppointShowNewPara.getWorktileAppointPkid()!=null) {
            worktileAppointInDB =
                    worktileAppointService.getWorkorderAppointByPkid(worktileAppointShowNewPara.getWorktileAppointPkid());
            // �����ϵ������Ѿ���������
            if (worktileAppointInDB == null) {
                MessageUtil.addError("�ü�¼�Ѿ�ɾ��������ҳ�������ѹ�ʱ��");
                return;
            }

            //������¼Ŀǰ�����ݿ��еİ汾����ҳ���ϵİ汾��һ��
            int intRecVersionInDB= ToolUtil.getIntIgnoreNull(worktileAppointInDB.getRecVersion());
            int intRecVersionNew=ToolUtil.getIntIgnoreNull(worktileAppointShowNewPara.getRecVersion());
            if(intRecVersionInDB!=intRecVersionNew) {
                MessageUtil.addError("�����ϵ����ݰ汾��ʱ��������ȡ�ú��ٲ�����");
                return;
            }

            // ɾ������
            if(worktileAppointShowNewPara.getRecvPartPkid()==null){
                worktileAppointService.deleteRecord(worktileAppointShowNewPara.getWorktileAppointPkid());
                MessageUtil.addInfo("ɾ�����ݳɹ���");
            }else{// ���²���
                // ��ѯĳһ������ �����ϵ� ָ�� ���
                WorktileAppointShow worktileAppointShowByInfoAppint =new WorktileAppointShow();
                worktileAppointShowByInfoAppint.setInfoPkid(worktileAppointShowNewPara.getInfoPkid());
                worktileAppointShowByInfoAppint.setRecvPartPkid(worktileAppointShowNewPara.getRecvPartPkid());
                worktileAppointShowByInfoAppint.setRemark(worktileAppointShowNewPara.getRemark());
                List<WorktileAppoint> worktileAppointListByInfoAppint =
                        worktileAppointService.getWorkorderAppointListByModelShow(worktileAppointShowByInfoAppint);
                if(worktileAppointListByInfoAppint.size()>0){
                    MessageUtil.addError("��Ŀǰ�Ĺ����Ѿ����ָ�ɹ����벻Ҫ�����ύ��ͬ������");
                    return;
                }
                //����
                worktileAppointService.updateRecord(worktileAppointShowNewPara);
                    MessageUtil.addInfo("�������ݳɹ���");
            }
        }else{// �������ŵ����� �������
            if(ToolUtil.getStrIgnoreNull(worktileAppointShowNewPara.getRecvPartPkid()).equals("")){
                MessageUtil.addError("��ѡ������ߡ�����");
                return;
            }
            worktileAppointShowNewPara.setRecvExecFlag(EnumRecvExecFlag.RECV_EXEC_FLAG0.getCode());
            worktileAppointService.insertRecord(worktileAppointShowNewPara);
            MessageUtil.addInfo("����������ɡ�");
        }
        onQueryAction("false");
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

    public WorktileAppointShow getWorktileAppointShowQry() {
        return worktileAppointShowQry;
    }

    public void setWorktileAppointShowQry(WorktileAppointShow worktileAppointShowQry) {
        this.worktileAppointShowQry = worktileAppointShowQry;
    }

    public List<WorktileAppointShow> getWorktileAppointShowList() {
        return worktileAppointShowList;
    }

    public void setWorktileAppointShowList(List<WorktileAppointShow> worktileAppointShowList) {
        this.worktileAppointShowList = worktileAppointShowList;
    }
}
