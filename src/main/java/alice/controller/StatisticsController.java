package alice.controller;

import alice.common.constants.Constants;
import alice.common.entity.RspData;
import alice.common.operation.DailyPageviewsAndVisitorAndSendMail;
import alice.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zhangli on 8/5/2017.
 */

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    /**
     * 每天访客量、浏览量、发送量
     * @return
     */
    @RequestMapping(value = "/queryDailyPageviewsAndVisitorAndSendMail", method = RequestMethod.POST)
    public RspData queryDailyPageviewsAndVisitorAndSendMail(){
        RspData rspData = new RspData();
        try {
//            String json = JSONFormat.getObjectToJson(reqData.getData());
//            SendRandomMail sendRandomMail = JSONFormat.fromJson(json, SendRandomMail.class);
            List<DailyPageviewsAndVisitorAndSendMail> dailyPageviewsAndVisitorAndSendMailList = statisticsService.queryPageviewsAndVisitorAndSendMail();
            rspData.setData(dailyPageviewsAndVisitorAndSendMailList);
            rspData.setCode(Constants.CODE_SUCCESS);
        }catch (Exception e){
            rspData.setMessage(e.getMessage());
        }
        return rspData;
    }

}
