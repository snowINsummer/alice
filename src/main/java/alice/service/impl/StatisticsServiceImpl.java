package alice.service.impl;

import alice.common.operation.DailyPageviewsAndVisitorAndSendMail;
import alice.dao.IPageViewStatisticsDao;
import alice.service.JdbcTemplateService;
import alice.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    IPageViewStatisticsDao iPageViewStatisticsDao;
    @Autowired
    JdbcTemplateService jdbcTemplateService;

    @Override
    public List<DailyPageviewsAndVisitorAndSendMail> queryPageviewsAndVisitorAndSendMail() {
        List<DailyPageviewsAndVisitorAndSendMail> dailyPageviewsAndVisitorAndSendMailList = jdbcTemplateService.queryDailyPageviewsAndVisitorAndSendMail();
//        List<PageViewStatistics> pageViewStatisticsList = iPageViewStatisticsDao.findAll();
        return dailyPageviewsAndVisitorAndSendMailList;
    }
}
