package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private Integer age;
    private String gender;
    public StudentResponse(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.dateOfBirth = student.getDateOfBirth();
        this.age = student.getDateOfBirth().until(LocalDate.now()).getYears();
        this.gender = student.getGender();
    }
}
