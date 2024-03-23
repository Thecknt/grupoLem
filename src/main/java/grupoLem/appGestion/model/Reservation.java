
package grupoLem.appGestion.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReservations;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_in_time")
    private LocalTime checkInTime;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @Column(name = "check_out_time")
    private LocalTime checkOutTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Column(name = "tipo_pension")
    private String typePension;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "host_id")
    private Host host;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public boolean isCheckOutTimePassed() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(LocalDateTime.of(checkOutDate, checkOutTime));
    }

    public boolean isCheckInTimeNotPassed() {
        LocalDateTime now = LocalDateTime.now();
        return now.isBefore(LocalDateTime.of(checkInDate, checkInTime));
    }
}

