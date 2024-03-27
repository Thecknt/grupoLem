
package grupoLem.appGestion.model;

import jakarta.persistence.*;
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
    private double totalDelivered  = 0.0;
    private double totalReservation = 0.0;
    private double remainingTotal = 0.0;
    private double initialPayment= 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public MediaPayment(Reservation reservation){
        this.reservation = reservation;
        reservation.addMediaPayment(this);
    }

    public MediaPayment(Reservation reservation, double totalReservation) {
    }

    public void payment(double amount, String mediaPaymentType){
        switch (mediaPaymentType) {
            case "cash":
                if (amount < 0){
                    throw new IllegalArgumentException("El monto del pago en efectivo no puede ser negativo");
                }
                cash += amount;
                break;
            case "deposit":
                if (amount < 0) {
                    throw new IllegalArgumentException("El monto del depósito no puede ser negativo");
                }
                deposit += amount;
                break;
            case "creditCard":
                if (amount < 0) {
                    throw new IllegalArgumentException("El monto del pago con tarjeta de crédito no puede ser negativo");
                }
                creditCard += amount;
                break;
            default:
                throw new IllegalArgumentException("Tipo de medio de pago no válido: " + mediaPaymentType);
        }

        totalDelivered = cash + deposit + creditCard;
        updateRemainingTotal();
    }

    // Actualizar el pago inicial
    public void updateInitialPayment(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("El pago inicial no puede ser negativo");
        }
        if (amount > totalReservation) {
            throw new IllegalArgumentException("El pago inicial no puede ser mayor al total de la reserva");
        }
        this.initialPayment = amount;
        updateRemainingTotal();
    }

    // Actualizar los gastos extra
    public void updateExtras() {
        this.extras += totalDelivered;
        updateRemainingTotal();
    }

    // Montos pendientes de pago
    public void updateRemainingTotal() {
        this.remainingTotal = this.totalReservation - this.totalDelivered - this.extras;
    }

    // Montos completos de la reserva
    public void setTotalReservation(double totalReservation) {
        if (totalReservation < 0) {
            throw new IllegalArgumentException("El total de la reserva no puede ser negativo");
        }
        this.totalReservation = totalReservation;
        this.remainingTotal = this.totalReservation - this.totalDelivered - this.extras;
    }
}

