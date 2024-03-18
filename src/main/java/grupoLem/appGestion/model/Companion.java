<<<<<<< HEAD
package grupoLem.appGestion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Companion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompanion;

    private String companionName;
    private String companionLastname;
    private String companionDni;
    @Override
    public String toString() {
        return "Companion{" +
                "idAcompanante=" + idCompanion +
                ", nombreAcompanante='" + companionName + '\'' +
                ", apellidoAcompanante='" + companionLastname + '\'' +
                ", dniAcompanante='" + companionDni + '\'' +

                // Otras propiedades aquí, excluyendo la referencia al huésped
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCompanion); // Solo utilizar el idAcompanante para calcular el hashCode
    }

    @ManyToOne(fetch = FetchType.EAGER) // Cambio de lazy a eager
    @JoinColumn(name = "huesped_id")
    private Host host;
}
=======
package grupoLem.appGestion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Companion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompanion;

    private String companionName;
    private String companionLastname;
    private String companionDni;
    @Override
    public String toString() {
        return "Companion{" +
                "idAcompanante=" + idCompanion +
                ", nombreAcompanante='" + companionName + '\'' +
                ", apellidoAcompanante='" + companionLastname + '\'' +
                ", dniAcompanante='" + companionDni + '\'' +

                // Otras propiedades aquí, excluyendo la referencia al huésped
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCompanion); // Solo utilizar el idAcompanante para calcular el hashCode
    }

    @ManyToOne(fetch = FetchType.EAGER) // Cambio de lazy a eager
    @JoinColumn(name = "huesped_id")
    private Host host;
}
>>>>>>> e294b050ac586c1d154ff242081dc37ef230cbbd
