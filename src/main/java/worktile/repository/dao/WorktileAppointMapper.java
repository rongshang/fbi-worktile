package worktile.repository.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import worktile.repository.model.WorktileAppoint;
import worktile.repository.model.WorktileAppointExample;

public interface WorktileAppointMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.WORKTILE_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int countByExample(WorktileAppointExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.WORKTILE_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int deleteByExample(WorktileAppointExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.WORKTILE_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int deleteByPrimaryKey(String pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.WORKTILE_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int insert(WorktileAppoint record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.WORKTILE_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int insertSelective(WorktileAppoint record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.WORKTILE_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    List<WorktileAppoint> selectByExample(WorktileAppointExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.WORKTILE_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    WorktileAppoint selectByPrimaryKey(String pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.WORKTILE_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int updateByExampleSelective(@Param("record") WorktileAppoint record, @Param("example") WorktileAppointExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.WORKTILE_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int updateByExample(@Param("record") WorktileAppoint record, @Param("example") WorktileAppointExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.WORKTILE_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int updateByPrimaryKeySelective(WorktileAppoint record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.WORKTILE_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int updateByPrimaryKey(WorktileAppoint record);
}