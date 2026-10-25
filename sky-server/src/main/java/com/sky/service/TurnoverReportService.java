package com.sky.service;

import com.sky.vo.TurnoverReportVO;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public interface TurnoverReportService {

    /**
     * 营业额统计
     * @param begin
     * @param end
     * @return
     */
    TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end);





}
