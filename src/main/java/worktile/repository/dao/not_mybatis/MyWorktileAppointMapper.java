package worktile.repository.dao.not_mybatis;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import worktile.repository.model.not_mybatis.WorktileAppointShow;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 13-2-13
 * Time: ÏÂÎç8:10
 * To change this template use File | Settings | File Templates.
 */
@Component
public interface MyWorktileAppointMapper {
    @Select("select"+
            "     wi.PKID                                              as infoPkid,"+
            "     wi.ID                                                as infoId,"+
            "     wi.subject                                           as infoName,"+
            "     wi.START_TIME                                        as infoStartTime,"+
            "     wi.END_TIME                                          as infoEndTime,"+
            "     wa.PKID                                              as worktileAppointPkid,"+
            "     wa.RECV_PART_PKID                                    as recvPartPkid,"+
            "     (select name from oper where PKID=wa.RECV_PART_PKID) as recvPartName,"+
            "     wa.RECV_EXEC_FLAG                                    as recvExecFlag,"+
            "     wa.CREATED_BY                                        as createdBy,"+
            "     (select name from oper where PKID=wa.CREATED_BY)     as createdByName,"+
            "     wa.CREATED_TIME                                      as createdTime,"+
            "     wa.LAST_UPD_BY                                       as lastUpdBy,"+
            "     (select name from oper where PKID=wa.LAST_UPD_BY)    as lastUpdByName,"+
            "     wa.LAST_UPD_TIME                                     as lastUpdTime,"+
            "     wa.REMARK                                            as remark,"+
            "     wa.REC_VERSION                                       as recVersion,"+
            "     wa.TID                                               as tid," +
            "     (case when nvl(wa.RECV_EXEC_FLAG,'0')='0' then '0' else '1' end) as execedFlag"+
            " from"+
            "     WORKTILE wi"+
            " left join"+
            "     WORKTILE_APPOINT wa"+
            " on"+
            "     wa.INFO_PKID=wi.PKID" +
            " where" +
            "     wi.ID like '%'||#{infoId}||'%'" +
            " and" +
            "     wi.SUBJECT like '%'||#{subject}||'%'" +
            " and" +
            "     nvl(wa.PKID,' ') like '%'||#{worktileAppointPkid}||'%'" +
            " order by wi.ID,wa.RECV_EXEC_FLAG")
    List<WorktileAppointShow> getWorkorderAppointShowList(@Param("infoId") String infoIdPara,
                                                           @Param("subject") String subjectPara,
                                                           @Param("worktileAppointPkid") String worktileAppointPkidPara);
}
