package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.service.TurnoverReportService;
import com.sky.vo.TurnoverReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TurnoverReportServiceImpl implements TurnoverReportService {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 获取指定时间区间的营业额数据
     * @param begin
     * @param end
     * @return
     */
    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {

        //当前集合用于存放从begin到end范围内的每天日期
        List<LocalDate> dateList=new ArrayList<>();

        dateList.add(begin);
        while(!begin.equals(end))
        {
            //日期计算，计算指定日期的后一天对应的日期
            begin=begin.plusDays(1);
            dateList.add(begin);
        }

        //存放每天的营业额
        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate localDate : dateList) {

            LocalDateTime beginTime = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(localDate, LocalTime.MAX);

            Map map=new HashMap<>();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);
            //根据日期查询营业额数据
            Double turnover = orderMapper.sumByMap(map);
            turnoverList.add(turnover == null ? 0.0 : turnover);
        }

        return TurnoverReportVO
                .builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();

    }
}
