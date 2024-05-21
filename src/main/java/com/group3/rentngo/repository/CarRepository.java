package com.group3.rentngo.repository;

import com.group3.rentngo.model.entity.Booking;
import com.group3.rentngo.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    public List<Car> findByCarOwner_Id(Long id);

    @Override
    Optional<Car> findById(Long id);

    Car findByLicensePlate(String licensePlate);

    @Query("SELECT c FROM Car c " +
            "WHERE c.address LIKE %:location%  " +
            "AND c.id NOT IN (" +
            "    SELECT b.car.id FROM Booking b " +
            "    WHERE (:startDateTime BETWEEN b.startDateTime AND b.endDateTime) " +
            "       OR (:endDateTime BETWEEN b.startDateTime AND b.endDateTime) " +
            "       OR (b.startDateTime BETWEEN :startDateTime AND :endDateTime) " +
            "       OR (b.endDateTime BETWEEN :startDateTime AND :endDateTime)" +
            ")")
    List<Car> findAvailableCars(@Param("startDateTime") LocalDateTime startDateTime,
                                @Param("endDateTime") LocalDateTime endDateTime,
                                @Param("location") String location);
}
