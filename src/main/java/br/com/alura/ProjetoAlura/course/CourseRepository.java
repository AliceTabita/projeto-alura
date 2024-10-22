package br.com.alura.ProjetoAlura.course;

import br.com.alura.ProjetoAlura.registration.RegistrationReportItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByCode(String code);
    Course findByCode(String code);
    @Query("SELECT c.name,c.code,u.name,u.email,c.totalRegistrations FROM Course c INNER JOIN User u on u.id = c.instructorId ORDER BY c.totalRegistrations DESC LIMIT 10")
    List<RegistrationReportItem> findTop10ByOrderByValorDesc();
}
