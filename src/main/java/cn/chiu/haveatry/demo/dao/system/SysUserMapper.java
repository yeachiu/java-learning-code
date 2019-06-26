package cn.chiu.haveatry.demo.dao.system;


import cn.chiu.haveatry.demo.pojo.system.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;
/**
 * Created by yeachiu on 2019/6/10.
 */
@Mapper
public interface SysUserMapper {

    ////////////////////////////////////////////////// 单 表 查 询 //////////////////////////////////////////////////////

    @Select("SELECT id, username, password, status, age, avatar, create_date FROM sys_user WHERE id = #{id};")
    SysUser getById(String id);

    @Select("SELECT id, username, password, status, age, avatar, create_date FROM sys_user;")
    List<SysUser> findAllList();

    ////////////////////////////////////////////////// 联 表 查 询 //////////////////////////////////////////////////////

    @Select("SELECT m.apar_id FROM sys_user u, user_auth a, apartment_member m " +
            "WHERE u.id = a.uid AND a.uid = m.uid AND u.id = #{id}")
    String getAparIdById(String id);

    @Select("SELECT a.stu_id FROM sys_user u, user_auth a WHERE u.id = a.uid AND a.uid = #{id}")
    String getStuIdById(String id);

    ////////////////////////////////////////////////// 表  操  作 ///////////////////////////////////////////////////////

    @Insert("INSERT INTO sys_user (id,username,password,status,age,avatar,create_date) " +
            " VALUES (#{id},#{username},#{password},#{status},#{age},#{avatar},#{createDate});")
    void insert(SysUser user);

    @Delete("DELETE FROM sys_user WHERE id = #{id};")
    void delete(String id);

    @Update("UPDATE sys_user SET username = #{username}, password = #{password}, status = #{status}, age = #{age}, avatar = #{avatar}" +
            " WHERE id = #{id}")
    void update(SysUser user);

}
