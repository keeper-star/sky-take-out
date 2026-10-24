package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理超时订单
     */
    @Scheduled(cron = "0 * * * * ?")
    public void processTimeoutOrder() {
        log.info("定时处理超时订单:{}", LocalDateTime.now());

        LocalDateTime Time = LocalDateTime.now().plusMinutes(-15);

        List<Orders> byStatusAndOrderTimeLT = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, Time);

        if(byStatusAndOrderTimeLT != null && byStatusAndOrderTimeLT.size() > 0) {
            log.info("存在超时订单，开始处理：{}", byStatusAndOrderTimeLT);
            for (Orders orders : byStatusAndOrderTimeLT) {
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelReason("订单超时，自动取消");
                orders.setCancelTime(LocalDateTime.now());
                orderMapper.update(orders);
            }
        }
    }

    /**
     * 处理一直处于派送中的订单
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void processDeliveryOrder() {
        log.info("定时处理处于派送中的订单:{}", LocalDateTime.now());
        List<Orders> byStatusAndOrderTimeLT = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, LocalDateTime.now().plusHours(-1));
        log.info("存在处于派送中的订单，开始处理：{}", byStatusAndOrderTimeLT);
        for (Orders orders : byStatusAndOrderTimeLT) {
            orders.setStatus(Orders.COMPLETED);
            orderMapper.update(orders);
        }
    }
}
