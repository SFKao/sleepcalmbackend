package omelcam934.sleepcalmbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "sleep_track")
public class SleepTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Date horaDeInicio;

    private Date horaDeFin;

    private Date dia;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getHoraDeInicio() {
        return horaDeInicio;
    }

    public void setHoraDeInicio(Date horaDeInicio) {
        this.horaDeInicio = horaDeInicio;
    }

    public Date getHoraDeFin() {
        return horaDeFin;
    }

    public void setHoraDeFin(Date horaDeFin) {
        this.horaDeFin = horaDeFin;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SleepTrack that = (SleepTrack) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "horaDeInicio = " + horaDeInicio + ", " +
                "horaDeFin = " + horaDeFin + ", " +
                "dia = " + dia + ")";
    }
}