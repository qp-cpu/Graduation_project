package life.majiang.community.dao;

import life.majiang.community.entity.NtificationEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NtificationEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ntification
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ntification
     *
     * @mbggenerated
     */
    int insert(NtificationEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ntification
     *
     * @mbggenerated
     */
    int insertSelective(NtificationEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ntification
     *
     * @mbggenerated
     */
    NtificationEntity selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ntification
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(NtificationEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ntification
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(NtificationEntity record);
}