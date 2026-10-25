package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     * 根据openid查询用户
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * 插入数据
     */
    void insert(User user);

    /**
     * 根据id查询用户
     */
    @Select("select * from user where id = #{userId}")
    User getById(Long userId);


    /**
     *  根据动态条件查询用户数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);


}
