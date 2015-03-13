package worktile.repository.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import worktile.repository.model.Dept;
import worktile.repository.model.DeptExample;

public interface DeptMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.DEPT
     *
     * @mbggenerated Fri Mar 13 10:55:34 CST 2015
     */
    int countByExample(DeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.DEPT
     *
     * @mbggenerated Fri Mar 13 10:55:34 CST 2015
     */
    int deleteByExample(DeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.DEPT
     *
     * @mbggenerated Fri Mar 13 10:55:34 CST 2015
     */
    int deleteByPrimaryKey(String pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.DEPT
     *
     * @mbggenerated Fri Mar 13 10:55:34 CST 2015
     */
    int insert(Dept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.DEPT
     *
     * @mbggenerated Fri Mar 13 10:55:34 CST 2015
     */
    int insertSelective(Dept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.DEPT
     *
     * @mbggenerated Fri Mar 13 10:55:34 CST 2015
     */
    List<Dept> selectByExample(DeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.DEPT
     *
     * @mbggenerated Fri Mar 13 10:55:34 CST 2015
     */
    Dept selectByPrimaryKey(String pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.DEPT
     *
     * @mbggenerated Fri Mar 13 10:55:34 CST 2015
     */
    int updateByExampleSelective(@Param("record") Dept record, @Param("example") DeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.DEPT
     *
     * @mbggenerated Fri Mar 13 10:55:34 CST 2015
     */
    int updateByExample(@Param("record") Dept record, @Param("example") DeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.DEPT
     *
     * @mbggenerated Fri Mar 13 10:55:34 CST 2015
     */
    int updateByPrimaryKeySelective(Dept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.DEPT
     *
     * @mbggenerated Fri Mar 13 10:55:34 CST 2015
     */
    int updateByPrimaryKey(Dept record);
}