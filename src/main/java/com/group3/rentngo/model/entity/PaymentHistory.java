package com.group3.rentngo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payment_history")
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private BigDecimal amount;

    @Column
    private String type;

    @Column
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "booking_no")
    private Booking booking;

    @Column
    private String note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
