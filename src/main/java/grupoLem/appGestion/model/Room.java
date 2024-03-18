<<<<<<< HEAD
package grupoLem.appGestion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
=======
package grupoLem.appGestion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
>>>>>>> e294b050ac586c1d154ff242081dc37ef230cbbd
