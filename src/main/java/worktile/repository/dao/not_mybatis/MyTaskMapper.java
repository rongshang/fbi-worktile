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
 * Time:
 * To change this template use File | Settings | File Templates.
 */
@Component
public interface MyTaskMapper {

    // Todo  待做任务  RECV_EXEC_FLAG=‘0’ ，RECV_PART_PKID=登陆者或者SEND_WORKTILE_PART_PKID=登陆者，但是RECV_PART_PKID为空
    @Select(" select " +
        "   aa.pkid as infopkid, " +    //工单信息主键
        "   aa.id   as infoid," +
        "   aa.subject as infoname," +
        "   bb.created_time  as createdTime," +
        "   bb.pkid     as  worktileAppointPkid " +                       //流程控制表主键
        "   from WORKTILE  aa" +
        "   INNER JOIN" +
        "   WORKTILE_APPOINT bb" +
        "   ON  bb.info_pkid = aa.pkid" +
        "   and" +
        "  （bb.RECV_PART_PKID = #{strOperPkid} or bb.RECV_PART_PKID =''）" +
        "   and bb.RECV_EXEC_FLAG ='0 '" + //  待执行
        "   group by aa.pkid，aa.id ，aa.subject，bb.created_time,bb.pkid" +
        "   order by infopkid ")
    List<WorktileAppointShow> getTodoWorktileAppointShowList(@Param("strOperPkid") String strOperPkid);
// DoneTask  已完成任务 RECV__EXEC_FLAG=‘1’ ，RECV__PART_PKID=登陆者
    @Select(" select " +
            "   aa.pkid as infopkid, " +
            "   aa.id   as infoid," +
            "   aa.subject as infoname," +
            "   bb.created_time  as createdTime," +
            "   bb.pkid     as  worktileAppointPkid " +
            "   from WORKTILE  aa" +
            "   INNER JOIN" +
            "   WORKTILE_APPOINT bb" +
            "   ON  bb.info_pkid = aa.pkid" +
            "   and" +
            "   bb.RECV_PART_PKID = #{strOperPkid}" +
            "   and bb.RECV_EXEC_FLAG ='2 '" +//  已执行
            "   group by aa.pkid，aa.id ，aa.subject，bb.created_time,bb.pkid" +
            "   order by infopkid ")
    List<WorktileAppointShow> getDoneWorktileAppointShowList(@Param("strOperPkid") String strOperPkid);
    // DoneTask  已完成任务 RECV__EXEC_FLAG=‘1’ ，RECV__PART_PKID=登陆者
    @Select(" select " +
            "   aa.pkid as infopkid, " +
            "   aa.id   as infoid," +
            "   aa.subject as infoname," +
            "   bb.created_time  as createdTime," +
            "   bb.pkid     as  worktileAppointPkid " +
            "   from WORKTILE aa" +
            "   INNER JOIN" +
            "   WORKTILE_APPOINT bb" +
            "   ON  bb.info_pkid = aa.pkid" +
            "   and" +
            "   bb.RECV_PART_PKID = #{strOperPkid}" +
            "   and bb.RECV_EXEC_FLAG ='1 '" +//  执行中
            "   group by aa.pkid，aa.id ，aa.subject，bb.created_time,bb.pkid" +
            "   order by infopkid ")
    List<WorktileAppointShow> getDoingWorktileAppointShowList(@Param("strOperPkid") String strOperPkid);


}