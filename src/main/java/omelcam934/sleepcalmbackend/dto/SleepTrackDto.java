package omelcam934.sleepcalmbackend.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class SleepTrackDto implements Serializable {
    private final Date horaDeInicio;
    private final Date horaDeFin;
    private final Date dia;

    public SleepTrackDto(Date horaDeInicio, Date horaDeFin, Date dia) {
        this.horaDeInicio = horaDeInicio;
        this.horaDeFin = horaDeFin;
        this.dia = dia;
    }

    public Date getHoraDeInicio() {
        return horaDeInicio;
    }

    public Date getHoraDeFin() {
        return horaDeFin;
    }

    public Date getDia() {
        return dia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SleepTrackDto entity = (SleepTrackDto) o;
        return Objects.equals(this.horaDeInicio, entity.horaDeInicio) &&
                Objects.equals(this.horaDeFin, entity.horaDeFin) &&
                Objects.equals(this.dia, entity.dia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(horaDeInicio, horaDeFin, dia);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "horaDeInicio = " + horaDeInicio + ", " +
                "horaDeFin = " + horaDeFin + ", " +
                "dia = " + dia + ")";
    }
}
