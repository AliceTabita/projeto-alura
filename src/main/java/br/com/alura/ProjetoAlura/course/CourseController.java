package br.com.alura.ProjetoAlura.course;

import br.com.alura.ProjetoAlura.user.Role;
import br.com.alura.ProjetoAlura.user.User;
import br.com.alura.ProjetoAlura.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
public class CourseController {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseController(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/course/new")
    public ResponseEntity createCourse(@Valid @RequestBody NewCourseDTO newCourse) {
        Course course = newCourse.toModel();
        User user= userRepository.findByEmail(newCourse.getInstructorEmail());
        if(user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Instrutor não cadastrado");
        }else if(!user.getRole().equals(Role.INSTRUCTOR)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O usuário não é um instrutor");
        }else if(courseRepository.existsByCode(newCourse.getCode())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe um curso cadastrado com esse código");
        }
        course.setInstructorId(user.getId());
        courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    @PostMapping("/course/{code}/inactive")
    public ResponseEntity createCourse(@PathVariable("code") String courseCode) {
        Course course = courseRepository.findByCode(courseCode);
        if(course == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não existe curso com o código " + courseCode);
        }else if(course.getStatus().equals(Status.INACTIVE)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Este curso já está inativo");
        }
        course.setInactivationDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        course.setStatus(Status.INACTIVE);
        courseRepository.save(course);
        return ResponseEntity.ok().body(course);
    }

}
