package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品ID查询套餐ID列表
     * @return
     */
    List<Long> getSetmealDishIds(List<Long> dishIds);

    /**
     * 批量保存套餐和菜品的关联关系
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐ID删除套餐和菜品的关联关系
     * @param setmealId
     */
    void deleteBySetmealId(Long setmealId);

    /**
     * 根据套餐ID查询套餐和菜品的关联关系
     * @param id
     * @return
     */
    List<SetmealDish> getBySetmealId(Long id);

}
