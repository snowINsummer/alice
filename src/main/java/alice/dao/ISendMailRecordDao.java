package alice.dao;

import alice.entity.SendMailRecord;
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
public interface ISendMailRecordDao extends JpaRepository<SendMailRecord, Integer> {

//    @Query("FROM SendMailRecord where productName = :productName")
//    SendMailRecord queryByProductName(@Param("productName") String productName);


}
