package omelcam934.sleepcalmbackend.service;

import omelcam934.sleepcalmbackend.models.SleepTrack;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface SleepTrackService {
    Optional<SleepTrack> findById(long id);
    //Find all seria muy innecesario ya que es por usuarios. Si quiero los de un usuario puedo usar el get de este.
    SleepTrack create(SleepTrack sleepTrack);
    SleepTrack update(SleepTrack sleepTrack);
    void delete(SleepTrack sleepTrack);
    Optional<SleepTrack> findByHoraDeInicioGreaterThanEqualAndUser_IdEqualsAndHoraDeFinLessThanEqual(Date horaDeInicio, Long id, Date horaDeFin);
    List<SleepTrack> findByHoraDeInicioGreaterThanEqualAndHoraDeFinLessThanEqualAndUser_IdEquals(Date horaDeInicio, Date horaDeFin, Long id);
    Optional<SleepTrack> findByDiaEqualsAndUser_IdEquals(Date dia, Long id);

    List<SleepTrack> findByDiaGreaterThanEqualAndDiaLessThanEqualAndUser_IdEquals(Date dia, Date diaFinal, Long id);
    boolean existsByDiaEqualsAndUser_IdEquals(Date dia, Long id);
}
