package alice.dao;

import alice.entity.DailyMailSenderRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangli on 8/5/2017.
 */
@Repository
@Transactional
public interface IDailyMailSenderRecordDao extends JpaRepository<DailyMailSenderRecord, Integer> {

    @Query("FROM DailyMailSenderRecord where mailSenderInfoId = :mailSenderInfoId and dateString = :dateString")
    DailyMailSenderRecord query(@Param("mailSenderInfoId") Integer mailSenderInfoId,
                                                @Param("dateString") String dateString);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE DailyMailSenderRecord set status = ?1 where mailSenderInfoId = ?2 and dateString = ?3")
    void update(Integer invalid, Integer mailSenderInfoId, String dateString);
}
