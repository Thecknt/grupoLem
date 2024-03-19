
package grupoLem.appGestion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "habitaciones")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRoom;

    @Column(name = "numero_habitacion")
    private String roomNumber;

    @Column(name = "estado_habitaciones")
    @Enumerated(EnumType.STRING)
    private RoomState roomState;

    @Column(name = "precio")
    private double price;

    @Column(name = "plazas")
    private int roomBeds;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Reservation> reservations;
}

