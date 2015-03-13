package worktile.view.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.util.ToolUtil;
import worktile.repository.model.not_mybatis.WorktileAppointShow;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XIANGYANG on 2014/8/11.
 */
@ManagedBean
@ViewScoped
public class ScrollInfoAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ScrollInfoAction.class);
    private String strCurrentViewMng;
    private String strLastViewMng;

    @PostConstruct
    public void init() {
        try {
            getViewMsg();
            if ("".equals(ToolUtil.getStrIgnoreNull(strCurrentViewMng))){
                strCurrentViewMng="��ӭ������";
            }
            strLastViewMng=strCurrentViewMng;
        }catch (Exception e){
            logger.error("��ʼ��ʧ��", e);
        }
    }

    public void getViewMsg() {
        List<WorktileAppointShow> taskShowTempList =new ArrayList<>();//taskService.initTodoTaskShowList();
        if (taskShowTempList != null) {
            String strViewMngTemp="����������";
            for (WorktileAppointShow taskShowItem : taskShowTempList) {
                if (taskShowItem.getInfoName()!=null){
                    strViewMngTemp+=taskShowItem.getInfoName()+";";
                }
            }
            if ("����������".equals(strViewMngTemp)){
                strCurrentViewMng="������������";
            }else {
                if (!(strViewMngTemp.equals(strLastViewMng))){
                    strCurrentViewMng=strViewMngTemp;
                    strLastViewMng=strCurrentViewMng;
                }
            }
        }
    }

    /*�����ֶ� Start*/

    public String getStrCurrentViewMng() {
        return strCurrentViewMng;
    }

    public void setStrCurrentViewMng(String strCurrentViewMng) {
        this.strCurrentViewMng = strCurrentViewMng;
    }
}
