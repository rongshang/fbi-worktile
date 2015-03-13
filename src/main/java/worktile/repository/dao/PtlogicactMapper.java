package worktile.repository.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import worktile.repository.model.Ptlogicact;
import worktile.repository.model.PtlogicactExample;

public interface PtlogicactMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.PTLOGICACT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int countByExample(PtlogicactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.PTLOGICACT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int deleteByExample(PtlogicactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.PTLOGICACT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int deleteByPrimaryKey(String pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.PTLOGICACT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int insert(Ptlogicact record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.PTLOGICACT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int insertSelective(Ptlogicact record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.PTLOGICACT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    List<Ptlogicact> selectByExample(PtlogicactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.PTLOGICACT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    Ptlogicact selectByPrimaryKey(String pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.PTLOGICACT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int updateByExampleSelective(@Param("record") Ptlogicact record, @Param("example") PtlogicactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.PTLOGICACT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int updateByExample(@Param("record") Ptlogicact record, @Param("example") PtlogicactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.PTLOGICACT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int updateByPrimaryKeySelective(Ptlogicact record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.PTLOGICACT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int updateByPrimaryKey(Ptlogicact record);
}