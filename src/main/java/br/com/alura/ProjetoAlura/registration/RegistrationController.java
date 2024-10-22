package br.com.alura.ProjetoAlura.registration;

import br.com.alura.ProjetoAlura.course.Course;
import br.com.alura.ProjetoAlura.course.CourseRepository;
import br.com.alura.ProjetoAlura.course.Status;
import br.com.alura.ProjetoAlura.user.User;
import br.com.alura.ProjetoAlura.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RegistrationController {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final RegistrationRepository registrationRepository;

    public RegistrationController(CourseRepository courseRepository, UserRepository userRepository, RegistrationRepository registrationRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.registrationRepository = registrationRepository;
    }
    @PostMapping("/registration/new")
    public ResponseEntity createCourse(@Valid @RequestBody NewRegistrationDTO newRegistration) {
        Course course =  courseRepository.findByCode(newRegistration.getCourseCode());
        User user = userRepository.findByEmail(newRegistration.getStudentEmail());
        if(course == null || course.getStatus().equals(Status.INACTIVE)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Este curso não está disponível para matricula");
        }else if(user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado");
        }else if(registrationRepository.existsByCourseIdAndUserId(course.getId(),user.getId())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Este aluno já está matriculado nesse curso");
        }
        Registration registration = new Registration();
        registration.setCourseId(course.getId());
        registration.setUserId(user.getId());
        registrationRepository.save(registration);
        course.setTotalRegistration(course.getTotalRegistrations()+1);
        courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/registration/report")
    public ResponseEntity<List<RegistrationReportItem>> report() {
        List<RegistrationReportItem> items = new ArrayList<>();
        items = courseRepository.findTop10ByOrderByValorDesc();
        return ResponseEntity.ok(items);
    }

}
