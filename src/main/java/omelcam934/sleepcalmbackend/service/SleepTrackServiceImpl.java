package omelcam934.sleepcalmbackend.service;

import omelcam934.sleepcalmbackend.models.SleepTrack;
import omelcam934.sleepcalmbackend.repository.SleepTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SleepTrackServiceImpl implements SleepTrackService{

    private final SleepTrackRepository sleepTrackRepository;

    public SleepTrackServiceImpl(@Autowired SleepTrackRepository sleepTrackRepository) {
        this.sleepTrackRepository = sleepTrackRepository;
    }

    @Override
    public Optional<SleepTrack> findById(long id) {
        return sleepTrackRepository.findById(id);
    }

    /**
     * Crea un registro de sueño de usuario y lo parsea al dia correcto
     * @param sleepTrack registro a añadir
     * @return el registro ya en bbdd
     */
    @Override
    public SleepTrack create(SleepTrack sleepTrack) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sleepTrack.getHoraDeInicio());
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        if(hora > 5)
            calendar.add(Calendar.DAY_OF_WEEK,1);

        calendar.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        sleepTrack.setDia(calendar.getTime());
        return sleepTrackRepository.save(sleepTrack);
    }

    /**
     * Actualiza un registro de sueño de usuario
     * @param sleepTrack registro a actualizar
     * @return el registro actualizado
     */
    @Override
    public SleepTrack update(SleepTrack sleepTrack) {
        return sleepTrackRepository.save(sleepTrack);
    }

    @Override
    public void delete(SleepTrack sleepTrack) {
        sleepTrackRepository.delete(sleepTrack);
    }

    @Override
    public Optional<SleepTrack> findByHoraDeInicioGreaterThanEqualAndUser_IdEqualsAndHoraDeFinLessThanEqual(Date horaDeInicio, Long id, Date horaDeFin) {
        return sleepTrackRepository.findByHoraDeInicioGreaterThanEqualAndUser_IdEqualsAndHoraDeFinLessThanEqual(horaDeInicio,id,horaDeFin);
    }

    @Override
    public List<SleepTrack> findByHoraDeInicioGreaterThanEqualAndHoraDeFinLessThanEqualAndUser_IdEquals(Date horaDeInicio, Date horaDeFin, Long id) {
        return sleepTrackRepository.findByHoraDeInicioGreaterThanEqualAndHoraDeFinLessThanEqualAndUser_IdEquals(horaDeInicio, horaDeFin, id);
    }

    @Override
    public Optional<SleepTrack> findByDiaEqualsAndUser_IdEquals(Date dia, Long id) {
        return sleepTrackRepository.findByDiaEqualsAndUser_IdEquals(dia, id);
    }

    @Override
    public List<SleepTrack> findByDiaGreaterThanEqualAndDiaLessThanEqualAndUser_IdEquals(Date dia, Date diaFinal, Long id) {
        return sleepTrackRepository.findByDiaGreaterThanEqualAndDiaLessThanEqualAndUser_IdEquals(dia, diaFinal, id);
    }

    @Override
    public boolean existsByDiaEqualsAndUser_IdEquals(Date dia, Long id){
        return sleepTrackRepository.existsByDiaEqualsAndUser_IdEquals(dia, id);
    }
}
