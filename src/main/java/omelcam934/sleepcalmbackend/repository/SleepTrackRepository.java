package omelcam934.sleepcalmbackend.repository;

import omelcam934.sleepcalmbackend.models.SleepTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SleepTrackRepository extends JpaRepository<SleepTrack, Long> {
    Optional<SleepTrack> findByHoraDeInicioGreaterThanEqualAndUser_IdEqualsAndHoraDeFinLessThanEqual(Date horaDeInicio, Long id, Date horaDeFin);

    @Query("select s from SleepTrack s where s.horaDeInicio >= ?1 and s.horaDeFin <= ?2 and s.user.id = ?3")
    List<SleepTrack> findByHoraDeInicioGreaterThanEqualAndHoraDeFinLessThanEqualAndUser_IdEquals(Date horaDeInicio, Date horaDeFin, Long id);

    Optional<SleepTrack> findByDiaEqualsAndUser_IdEquals(Date dia, Long id);

    List<SleepTrack> findByDiaGreaterThanEqualAndDiaLessThanEqualAndUser_IdEquals(Date dia, Date diaFinal, Long id);

    boolean existsByDiaEqualsAndUser_IdEquals(Date dia, Long id);

}