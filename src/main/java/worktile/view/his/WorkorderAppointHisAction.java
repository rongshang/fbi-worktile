package worktile.view.his;

import worktile.repository.model.WorktileAppointHis;
import worktile.service.WorktileAppointHisService;
import worktile.service.WorktileService;
/*import worktile.service.FlowHisService;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.util.MessageUtil;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
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
public class WorkorderAppointHisAction {
    private static final Logger logger = LoggerFactory.getLogger(WorkorderAppointHisAction.class);

    @ManagedProperty(value = "#{worktileAppointHisService}")
    private WorktileAppointHisService worktileAppointHisService;
    @ManagedProperty(value = "#{worktileService}")
    private WorktileService worktileService;

    private WorktileAppointHis worktileAppointHis;
    private List<WorktileAppointHis> worktileAppointHisList;

    private String strRendered1;
    private String strRendered2;
    private String strLabel1;
    private String strLabel2;
    private List<SelectItem> esInitCtt1List;
    private List<SelectItem> esInitCtt2List;

    @PostConstruct
    public void init() {
        worktileAppointHis =new WorktileAppointHis();
        this.worktileAppointHisList = new ArrayList<WorktileAppointHis>();
        esInitCtt1List=new ArrayList<SelectItem> ();
        esInitCtt2List=new ArrayList<SelectItem> ();
        strRendered1="false";
        strRendered2="false";
/*        resetAction();*/
    }

    public String onQueryAction(String strQryMsgOutPara) {
        try {
            this.worktileAppointHisList = worktileAppointHisService.getListByModel(worktileAppointHis);
            if(strQryMsgOutPara.equals("true")){
                if (worktileAppointHisList.isEmpty()) {
                    MessageUtil.addWarn("没有查询到数据。");
                }
            }
        } catch (Exception e) {
            logger.error("信息查询失败", e);
            MessageUtil.addError("信息查询失败");
        }
        return null;
    }

    public WorktileAppointHisService getWorktileAppointHisService() {
        return worktileAppointHisService;
    }

    public void setWorktileAppointHisService(WorktileAppointHisService worktileAppointHisService) {
        this.worktileAppointHisService = worktileAppointHisService;
    }

    public WorktileService getWorktileService() {
        return worktileService;
    }

    public void setWorktileService(WorktileService worktileService) {
        this.worktileService = worktileService;
    }

    public String getStrRendered1() {
        return strRendered1;
    }

    public void setStrRendered1(String strRendered1) {
        this.strRendered1 = strRendered1;
    }

    public String getStrRendered2() {
        return strRendered2;
    }

    public void setStrRendered2(String strRendered2) {
        this.strRendered2 = strRendered2;
    }

    public String getStrLabel1() {
        return strLabel1;
    }

    public void setStrLabel1(String strLabel1) {
        this.strLabel1 = strLabel1;
    }

    public String getStrLabel2() {
        return strLabel2;
    }

    public void setStrLabel2(String strLabel2) {
        this.strLabel2 = strLabel2;
    }

    public List<SelectItem> getEsInitCtt1List() {
        return esInitCtt1List;
    }

    public void setEsInitCtt1List(List<SelectItem> esInitCtt1List) {
        this.esInitCtt1List = esInitCtt1List;
    }

    public List<SelectItem> getEsInitCtt2List() {
        return esInitCtt2List;
    }

    public void setEsInitCtt2List(List<SelectItem> esInitCtt2List) {
        this.esInitCtt2List = esInitCtt2List;
    }

    public WorktileAppointHis getWorktileAppointHis() {
        return worktileAppointHis;
    }

    public void setWorktileAppointHis(WorktileAppointHis worktileAppointHis) {
        this.worktileAppointHis = worktileAppointHis;
    }

    public List<WorktileAppointHis> getWorktileAppointHisList() {
        return worktileAppointHisList;
    }

    public void setWorktileAppointHisList(List<WorktileAppointHis> worktileAppointHisList) {
        this.worktileAppointHisList = worktileAppointHisList;
    }
}
