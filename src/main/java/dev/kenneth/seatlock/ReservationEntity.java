package dev.kenneth.seatlock;

import dev.kenneth.seatlock.ReservationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.Instant;
import dev.kenneth.seatlock.Reservation;


@Entity
@Table(name = "reservation")
public class ReservationEntity {

    @Id
    @SequenceGenerator(name = "reservationIds", sequenceName = "reservation_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservationIds")
    private Long id;

    @Column(name = "seat_id")
    private long seatId;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.PENDING;

    protected ReservationEntity(){}    

    public ReservationEntity(long seatId, Instant createdAt) {
        this.seatId = seatId;
        Reservation reservation = new Reservation(createdAt);
        this.createdAt = reservation.getCreatedAt();
        this.expiresAt = reservation.getExpiresAt();
    }
    public Long getId() {
        return id;
    }
}
