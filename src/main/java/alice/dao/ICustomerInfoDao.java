package alice.dao;

import alice.entity.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangli on 8/5/2017.
 */
@Repository
@Transactional
public interface ICustomerInfoDao extends JpaRepository<CustomerInfo, Integer> {

//    @Query("FROM SendMailRecord where productName = :productName")
//    SendMailRecord queryByProductName(@Param("productName") String productName);


}
