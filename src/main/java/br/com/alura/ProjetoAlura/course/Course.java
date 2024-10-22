package br.com.alura.ProjetoAlura.course;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String code;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime inactivationDate;
    private Long instructorId;
    private Integer totalRegistrations;

    public Course(String name, String description, String code, Status status) {
        this.name = name;
        this.description = description;
        this.code = code;
        this.status = status;
    }
    @Deprecated
    public Course() {

    }

    public Integer getTotalRegistrations() {
        return totalRegistrations;
    }

    public void setTotalRegistration(Integer totalRegistrations) {
        this.totalRegistrations = totalRegistrations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getInactivationDate() {
        return inactivationDate;
    }

    public void setInactivationDate(LocalDateTime inactivationDate) {
        this.inactivationDate = inactivationDate;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }
}
