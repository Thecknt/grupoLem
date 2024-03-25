package grupoLem.appGestion.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RoomStatePerDay {
    private LocalDate date;
    private RoomState state;

    public RoomStatePerDay(LocalDate date, RoomState state) {
        this.date = date;
        this.state = state;
    }
}
