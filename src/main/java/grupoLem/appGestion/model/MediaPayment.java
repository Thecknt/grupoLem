package grupoLem.appGestion.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMediaPayments;
    private double cash = 0.0;
    private double deposit = 0.0;
    private double creditCard = 0.0;
    private double extras = 0.0;
    private double totalDelivered = 0.0;
    private double totalReservation = 0.0;
    private double remainingTotal = 0.0;

    public void payment(double amount, String mediaPayment){
        switch (mediaPayment) {
            case "cash":
                cash += amount;
                break;
            case "deposit":
                deposit += amount;
                break;
            case "creditCard":
                creditCard += amount;
                break;
            default:
                break;
        }

        updateTotalDelivered();
    }

    public void updateExtras() {
        extras += totalDelivered;
    }

    public void remainingAccount() {
        remainingTotal = totalReservation - totalDelivered;
    }


    public void setTotalReservation(double totalReservation) {
        if (totalReservation >= 0) {
            this.totalReservation = totalReservation;
            remainingAccount();
        } else {
            throw new IllegalArgumentException("El total de la reserva no puede ser negativo");
        }
    }

    public void updateTotalDelivered() {

        totalDelivered = cash + deposit + creditCard;
        remainingAccount();
    }

    public void addExtras(double amount) {
        // Manejo de errores si el monto de los gastos es negativo
        if (amount < 0) {
            throw new IllegalArgumentException("El monto de los gastos extras no puede ser negativo");
        }

        // Agregar gastos extras
        extras += amount;
        totalDelivered += amount;
        // Calcular el saldo restante
        remainingAccount();
    }
}
