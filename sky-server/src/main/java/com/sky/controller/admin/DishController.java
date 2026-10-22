package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/admin/dish")
@Slf4j
//查询操作都一定要写泛型！！！！！
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品
     * @param dishDTO
     */
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO){

        log.info("新增菜品：{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);

        //清理缓存数据
        String key="dish_" + dishDTO.getCategoryId();
        cleanCache(key);
        return Result.success();
    }
    /**
     * 批量删除菜品
     */
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids){
        log.info("批量删除菜品：{}",ids);
        dishService.delete(ids);

        //将所有的菜品缓存数据清理掉，所有以dish_开头的key
        cleanCache("*dish_*");
        return Result.success();
    }

    /**
     * 菜品起售停售
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    public Result<String> statusOrStop(@PathVariable Integer status,Long id){
        log.info("修改菜品状态：{}",id);
        dishService.statusOrStop(status, id);

        //将所有的菜品缓存数据清理掉，所有以dish_开头的key
         cleanCache("*dish_*");
         return Result.success();
    }

    /**
     * 分页查询菜品
     */
    @GetMapping("/page")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO){

        log.info("分页查询菜品：{}",dishPageQueryDTO);
        PageResult pageResult=dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);

    }

    /**
     * 根据id查询菜品
     */
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id){
        log.info("根据id查询菜品：{}",id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    /**
     * 修改菜品
     */
    @PutMapping
    public Result update(@RequestBody DishDTO dishDTO){

        log.info("修改菜品：{}",dishDTO);
        dishService.updateWithFlavor(dishDTO);
        //将所有的菜品缓存数据清理掉，所有以dish_开头的key
        cleanCache("*dish_*");
        return Result.success();
   }

    /**
     * 清理缓存数据
     * @param pattern
     */
   private  void cleanCache(String pattern){
        log.info("清理缓存：{}",pattern);
       Set keys = redisTemplate.keys(pattern);
       redisTemplate.delete(keys);
   }
}
