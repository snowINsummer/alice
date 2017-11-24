package alice.service;


import alice.common.operation.DailyPageviewsAndVisitorAndSendMail;

import java.util.List;

public interface StatisticsService {

    List<DailyPageviewsAndVisitorAndSendMail> queryPageviewsAndVisitorAndSendMail();
}
