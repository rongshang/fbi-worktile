package worktile.repository.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import worktile.repository.model.MenuAppoint;
import worktile.repository.model.MenuAppointExample;

public interface MenuAppointMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.MENU_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int countByExample(MenuAppointExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.MENU_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int deleteByExample(MenuAppointExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.MENU_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int deleteByPrimaryKey(String pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.MENU_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int insert(MenuAppoint record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.MENU_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int insertSelective(MenuAppoint record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.MENU_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    List<MenuAppoint> selectByExample(MenuAppointExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.MENU_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    MenuAppoint selectByPrimaryKey(String pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.MENU_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int updateByExampleSelective(@Param("record") MenuAppoint record, @Param("example") MenuAppointExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.MENU_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int updateByExample(@Param("record") MenuAppoint record, @Param("example") MenuAppointExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.MENU_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int updateByPrimaryKeySelective(MenuAppoint record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WORKTILE.MENU_APPOINT
     *
     * @mbggenerated Fri Mar 13 14:57:38 CST 2015
     */
    int updateByPrimaryKey(MenuAppoint record);
}