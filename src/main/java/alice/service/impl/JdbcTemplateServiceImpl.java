package alice.service.impl;

import alice.common.operation.DailyPageviewsAndVisitorAndSendMail;
import alice.service.JdbcTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by zhangli on 24/7/2017.
 */
@Service
public class JdbcTemplateServiceImpl implements JdbcTemplateService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<DailyPageviewsAndVisitorAndSendMail> queryDailyPageviewsAndVisitorAndSendMail() {
        String sql = "SELECT t1.dateStr,pv.DAILY_VISITOR dailyVisitor,pv.DAILY_PAGEVIEWS dailyPageviews,t1.sendMail FROM\n" +
                "  PAGE_VIEW_STATISTICS pv\n" +
                "LEFT JOIN\n" +
                "  (\n" +
                "  SELECT DATE_FORMAT(CREATE_TIME,'%Y-%m-%d') dateStr, count(*) sendMail FROM SEND_MAIL_RECORD GROUP BY DATE_FORMAT(CREATE_TIME,'%Y-%m-%d')\n" +
                "  ) t1\n" +
                "on pv.DATE = t1.dateStr";
        return (List<DailyPageviewsAndVisitorAndSendMail>) jdbcTemplate.query(sql, new RowMapper<DailyPageviewsAndVisitorAndSendMail>(){

            @Override
            public DailyPageviewsAndVisitorAndSendMail mapRow(ResultSet rs, int rowNum) throws SQLException {
                DailyPageviewsAndVisitorAndSendMail dailyPageviewsAndVisitorAndSendMail = new DailyPageviewsAndVisitorAndSendMail();
                dailyPageviewsAndVisitorAndSendMail.setDateStr(rs.getNString(1));
                dailyPageviewsAndVisitorAndSendMail.setDailyVisitor(rs.getInt(2));
                dailyPageviewsAndVisitorAndSendMail.setDailyPageviews(rs.getInt(3));
                dailyPageviewsAndVisitorAndSendMail.setSendMail(rs.getInt(4));
                return dailyPageviewsAndVisitorAndSendMail;
            }

        });
    }


}
