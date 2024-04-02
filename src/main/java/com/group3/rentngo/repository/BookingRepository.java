package com.group3.rentngo.repository;

import com.group3.rentngo.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Modifying
    @Transactional

    @Query("UPDATE Booking SET status = false WHERE id = :id")
    public void updateById(int id);


}

