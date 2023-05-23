package omelcam934.sleepcalmbackend.controller;


import omelcam934.sleepcalmbackend.dto.SleepTrackDto;
import omelcam934.sleepcalmbackend.dto.UserDto;
import omelcam934.sleepcalmbackend.models.SleepTrack;
import omelcam934.sleepcalmbackend.models.User;
import omelcam934.sleepcalmbackend.service.SleepTrackService;
import omelcam934.sleepcalmbackend.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/sleeptrack")
public class SleepTrackController {

    private final SleepTrackService sleepTrackService;
    private final UserService userService;

    public SleepTrackController(SleepTrackService sleepTrackService, UserService userService) {
        this.sleepTrackService = sleepTrackService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getWeek(Authentication authentication){
        User user = getUser(authentication);
        // get today and clear time of day
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        // get start of this week in milliseconds
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date lunes = cal.getTime();
        cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        Date domingo = cal.getTime();

        return ResponseEntity.ok(
                sleepTrackService.findByDiaGreaterThanEqualAndDiaLessThanEqualAndUser_IdEquals
                (lunes,domingo, user.getId())
                .stream().map(
                        sleepTrack -> new SleepTrackDto(sleepTrack.getHoraDeInicio(),sleepTrack.getHoraDeFin(),sleepTrack.getDia())
                        )
        );
    }

    @PostMapping
    public ResponseEntity<?> addTrack(Authentication authentication, @RequestBody SleepTrackDto sleepTrackDto){
        User user = getUser(authentication);

        //Si se intenta añadir un track el mismo dia que ya hay uno actualizo el que tenemos
        if(sleepTrackService.existsByDiaEqualsAndUser_IdEquals(getDia(sleepTrackDto.getHoraDeInicio()),user.getId())) {
            return updateTrack(authentication, sleepTrackDto);
        }
        SleepTrack sleepTrack = new SleepTrack();
        sleepTrack.setHoraDeInicio(sleepTrackDto.getHoraDeInicio());
        sleepTrack.setHoraDeFin(sleepTrack.getHoraDeFin());
        sleepTrack.setUser(user);
        SleepTrack newSleepTrack = sleepTrackService.create(sleepTrack);
        return ResponseEntity.ok(new SleepTrackDto(newSleepTrack.getHoraDeInicio(),newSleepTrack.getHoraDeFin(),newSleepTrack.getDia()));
    }

    @PutMapping
    public ResponseEntity<?> updateTrack(Authentication authentication, @RequestBody SleepTrackDto sleepTrackDto){
        User user = getUser(authentication);
        Optional<SleepTrack> sleepTrackOptional = sleepTrackService.findByDiaEqualsAndUser_IdEquals
                (sleepTrackDto.getDia() != null ? sleepTrackDto.getDia() : getDia(sleepTrackDto.getHoraDeInicio()),user.getId());
        if(sleepTrackOptional.isEmpty())
            return ResponseEntity.notFound().build();
        SleepTrack sleepTrack = sleepTrackOptional.get();
        sleepTrack.setHoraDeInicio(sleepTrackDto.getHoraDeInicio());
        sleepTrack.setHoraDeFin(sleepTrackDto.getHoraDeFin());
        SleepTrack newSleepTrack = sleepTrackService.update(sleepTrack);
        return ResponseEntity.ok(new SleepTrackDto(newSleepTrack.getHoraDeInicio(),newSleepTrack.getHoraDeFin(),newSleepTrack.getDia()));
    }

    @GetMapping("/week")
    public ResponseEntity<?> getWeek(Authentication authentication, @RequestParam(name = "diaSemana") @DateTimeFormat(pattern = "yyyy-MM-dd_HH:mm:ss") Date diaSemana){
        User user = getUser(authentication);
        Semana semana = getSemana(diaSemana);
        return ResponseEntity.ok(
                sleepTrackService
                        .findByDiaGreaterThanEqualAndDiaLessThanEqualAndUser_IdEquals
                        (semana.lunes(),semana.domingo(), user.getId())
                        .stream().map(
                                sleepTrack -> new SleepTrackDto(sleepTrack.getHoraDeInicio(),sleepTrack.getHoraDeFin(),sleepTrack.getDia())
                        )
        );
    }

    private User getUser(Authentication authentication){
        return userService.findByUsernameLikeIgnoreCaseOrEmailLikeIgnoreCase(authentication.getName(), authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("El token contenia un usuario que no existe, hiuston tenemos un problema"));
    }

    private Date getDia(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        if(hora > 5)
            calendar.add(Calendar.DAY_OF_WEEK,1);

        calendar.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar.getTime();
    }

    private Semana getSemana(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date lunes = cal.getTime();
        cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        Date domingo = cal.getTime();

        return new Semana(lunes,domingo);
    }

    //Pair es protegido asi que creo un record para almacenar ambos dias de la semana
    private static record Semana(Date lunes, Date domingo){}

}
