package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;
    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {

        //判断当前商品加入到购物车中的商品是否已经存在了
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);

        //获取当前用户id并设置到购物车对象中
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        //如果已经存在，则数量加一
        if (list!=null && list.size()>0) {
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(cart);
        } else {
            //如果不存在，需要插入一条购物车数据
            //判断本次添加到购物车的是菜品还是套餐
            Long dishId = shoppingCart.getDishId();
            Long setmealId = shoppingCart.getSetmealId();

            if (dishId != null) {
                //本次添加到购物车的是菜品
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());

            } else {
                //本次添加到购物车的是套餐
                Setmeal setmeal = setmealMapper.getById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());

            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }
    }

    /**
     * 查看购物车
     */
    public List<ShoppingCart> showShoppingCart() {

        //获取当前微信用户id
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .build();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;
    }
    /**
     * 减少购物车中的数量
     */
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {

        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);

        // 获取并设置当前用户id
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        ShoppingCart cart = list.get(0);

        if(list != null && list.size() > 0) {

            Integer number = cart.getNumber();
            if (number == 1) {
                //当前商品在购物车中的份数为1，直接删除当前记录
                shoppingCartMapper.delete(cart);
            } else {
                //当前商品在购物车中的份数不为1，修改份数即可
                cart.setNumber(cart.getNumber() - 1);
                shoppingCartMapper.updateNumberById(cart);
            }
        }
    }

    /**
     * 清空购物车
     */
    public void clean() {

        // 获取当前用户id
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.cleanByUserId(userId);
    }
}
