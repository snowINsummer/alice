package alice.dao;

import alice.entity.MailSenderInfo;
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
public interface IMailSenderInfoDao extends JpaRepository<MailSenderInfo, Integer> {

    @Query("FROM MailSenderInfo where mailAddress = :senderAdress")
    MailSenderInfo query(@Param("senderAdress") String senderAdress);


}
