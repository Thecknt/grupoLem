
package grupoLem.appGestion.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReservations;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Column(name = "tipo_pension")
    private String typePension;

    @ManyToOne
    @JsonProperty("host")
    @JoinColumn(name = "host_id")
    private Host host;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MediaPayment> mediaPayments;

    public void addMediaPayment(MediaPayment mediaPayment) {
        mediaPayments.add(mediaPayment);
        mediaPayment.setReservation(this);
    }
    
    public List<MediaPayment> getMediaPayments() {
        return mediaPayments;
    }

    public void setMediaPayments(List<MediaPayment> mediaPayments) {
        this.mediaPayments = mediaPayments;
    }

    // Add the correct removeMediaPayment method
    public void removeMediaPayment(MediaPayment mediaPayment) {
        mediaPayment.setReservation(null);
        mediaPayments.remove(mediaPayment);
    }

    public boolean isCheckOutTimePassed() {
        if (checkOutDate == null || checkOutTime == null){
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(LocalDateTime.of(checkOutDate, checkOutTime));
    }

    public boolean isCheckInTimeNotPassed() {
        LocalDateTime now = LocalDateTime.now();
        if (checkInDate == null || checkInTime == null){
            return true;
        }
        return now.isBefore(LocalDateTime.of(checkInDate, checkInTime));
    }
}

