package com.sky.controller.user;


import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user/shoppingCart")
@Slf4j
@RestController
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/add")
    public Result<String> add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车：{}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 查看购物车
     */
    @GetMapping("/list")
    public Result<List<ShoppingCart>> list() {
        log.info("购物车列表");
        List<ShoppingCart> list = shoppingCartService.showShoppingCart();
        return Result.success(list);
    }

    /**
     * 减少购物车数量
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/sub")
    public Result subShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO){

        log.info("减少购物车数量：{}", shoppingCartDTO);
        shoppingCartService.subShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 清空购物车
     */
    @DeleteMapping("/clean")
    public Result clean() {
        log.info("清空购物车");
        shoppingCartService.clean();
        return Result.success();
    }
}
