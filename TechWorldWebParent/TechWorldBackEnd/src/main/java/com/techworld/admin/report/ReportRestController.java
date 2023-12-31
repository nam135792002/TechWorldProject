package com.techworld.admin.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportRestController {
    @Autowired
    private MasterOrderReportService masterOrderReportService;
    @Autowired
    private OrderDetailReportService orderDetailReportService;

    @GetMapping("/sales_by_date/{period}")
    public List<ReportItem> getReportDateByDatePeriod(@PathVariable(name = "period") String period) {
        switch (period) {
            case "last_7_days":
                return masterOrderReportService.getReportDateLast7Days(ReportType.DAY);
            case "last_28_days":
                return masterOrderReportService.getReportDateLast28Days(ReportType.DAY);
            case "last_6_months":
                return masterOrderReportService.getReportDataLast6Months(ReportType.MONTH);
            case "last_year":
                return masterOrderReportService.getReportDataLastYear(ReportType.MONTH);

            default:
                return masterOrderReportService.getReportDateLast7Days(ReportType.DAY);
        }

    }

    @GetMapping("/sales_by_date/{startDate}/{endDate}")
    public List<ReportItem> getReportDateByDatePeriod(@PathVariable(name = "startDate") String startDate,
                                                      @PathVariable(name = "endDate") String endDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = dateFormat.parse(startDate);
        Date endTime = dateFormat.parse(endDate);

        return masterOrderReportService.getReportDateByDateRange(startTime, endTime, ReportType.DAY);
    }

    @GetMapping("/{groupBy}/{period}")
    public List<ReportItem> getReportDataByCategoryOrProduct(@PathVariable("groupBy") String groupBy,
                                                                @PathVariable("period") String period){
        ReportType reportType = ReportType.valueOf(groupBy.toUpperCase());

        switch (period) {
            case "last_7_days":
                return orderDetailReportService.getReportDateLast7Days(reportType);
            case "last_28_days":
                return orderDetailReportService.getReportDateLast28Days(reportType);
            case "last_6_months":
                return orderDetailReportService.getReportDataLast6Months(reportType);
            case "last_year":
                return orderDetailReportService.getReportDataLastYear(reportType);

            default:
                return orderDetailReportService.getReportDateLast7Days(reportType);
        }
    }

    @GetMapping("/{groupBy}/{startDate}/{endDate}")
    public List<ReportItem> getReportDateByCategoryOrProductDateRange(@PathVariable("groupBy") String groupBy, @PathVariable(name = "startDate") String startDate,
                                                      @PathVariable(name = "endDate") String endDate) throws ParseException {
        ReportType reportType = ReportType.valueOf(groupBy.toUpperCase());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = dateFormat.parse(startDate);
        Date endTime = dateFormat.parse(endDate);

        return orderDetailReportService.getReportDateByDateRange(startTime, endTime, reportType);
    }
}
