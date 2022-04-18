package com.piggy.pgb.repository;

import com.piggy.pgb.entity.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<CustomerDetails,Integer> {

    @Modifying
    @Query("update CustomerDetails set balance=?2 where id=?1")
    void updateCustomerDetails(Integer id, Double balance);


    @Modifying
    @Query("update CustomerDetails set isDeleted = 1 where id=?1")
    int deleteMobileDetails(Integer id);
}
