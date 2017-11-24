package alice.dao;

import alice.entity.MailSmtpInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangli on 8/5/2017.
 */
@Repository
@Transactional
public interface IMailSmtpInfoDao extends JpaRepository<MailSmtpInfo, Integer> {

    @Query("FROM MailSmtpInfo where id = :mailSmtpInfoId")
    MailSmtpInfo query(@Param("mailSmtpInfoId") Integer mailSmtpInfoId);


}
