package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 根据条件查询购物车
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 根据id更新购物车数量
     * @param cart
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart cart);

    /**
     * 添加购物车
     * @param shoppingCart
     */
    @Insert("insert into shopping_cart (name, user_id, dish_id, setmeal_id, dish_flavor, number, amount, image, create_time) " +
            " values (#{name},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},#{image},#{createTime})")
    void insert(ShoppingCart shoppingCart);

    /**
     * 删除购物车
     * @param cart
     */
    @Delete("delete from shopping_cart where id = #{id}")
    void delete(ShoppingCart cart);

    /**
     * 根据用户id清空购物车
     * @param userId
     */
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void cleanByUserId(Long userId);
}
