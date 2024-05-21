package com.group3.rentngo.repository;

import com.group3.rentngo.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Transactional
    @Modifying
    @Query("update Booking b set b.status = ?1 where b.id = ?2")
    int updateStatusById(String status, Long id);

    @Query("select b from Booking b where b.customer.id = ?1 order by b.id DESC")
    List<Booking> findByCustomer_Id(Long id);
}

