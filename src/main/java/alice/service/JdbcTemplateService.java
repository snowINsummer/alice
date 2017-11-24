package alice.service;


import alice.common.operation.DailyPageviewsAndVisitorAndSendMail;

import java.util.List;

/**
 * Created by zhangli on 24/7/2017.
 */
public interface JdbcTemplateService {

    List<DailyPageviewsAndVisitorAndSendMail> queryDailyPageviewsAndVisitorAndSendMail();

}
