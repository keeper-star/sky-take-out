package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.TurnoverReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

/**
 * 数据统计相关接口
 */
@RestController
@RequestMapping("/admin/report")
@Slf4j
public class ReportController {

    @Autowired
    private TurnoverReportService turnoverReportService;

    /**
     * 营业额统计
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/turnoverStatistics")
    public Result<TurnoverReportVO> turnoverStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end)
    {
        log.info("营业额统计，开始日期：{}，结束日期：{}", begin, end);
        TurnoverReportVO turnoverReportVO = turnoverReportService.getTurnoverStatistics(begin, end);
        return Result.success(turnoverReportVO);
    }

    /**
     *  用户统计
     */
    @GetMapping("/userStatistics")
    public Result<UserReportVO> userStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                               @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end)
    {
        log.info("用户统计，开始日期：{}，结束日期：{}", begin, end);
        UserReportVO userReportVO = turnoverReportService.getUserStatistics(begin, end);
        return Result.success(userReportVO);
    }

    /**
     * 订单统计
     */
    @GetMapping("/ordersStatistics")
    public Result<OrderReportVO> ordersStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end)
    {
        log.info("订单统计，开始日期：{}，结束日期：{}", begin, end);
        OrderReportVO orderReportVO = turnoverReportService.getOrderStatistics(begin, end);
        return Result.success(orderReportVO);
    }

    /**
     *  销售排名top10
     */
    @GetMapping("/top10")
    public Result<SalesTop10ReportVO> top10(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end)
    {
        log.info("销售排名top10，开始日期：{}，结束日期：{}", begin, end);
        SalesTop10ReportVO salesTop10ReportVO = turnoverReportService.getSalesTop10(begin,end);
        return Result.success(salesTop10ReportVO);
    }
}
