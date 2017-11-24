package alice.dao;

import alice.entity.PageViewStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangli on 8/5/2017.
 */
@Repository
@Transactional
public interface IPageViewStatisticsDao extends JpaRepository<PageViewStatistics, Integer> {

//    @Query("FROM PageViewStatistics where id = :mailSmtpInfoId")
//    PageViewStatistics query(@Param("mailSmtpInfoId") Integer mailSmtpInfoId);


}
