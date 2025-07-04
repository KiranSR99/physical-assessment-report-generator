package io.github.kiransr99.parg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schoolId;
    @Column(nullable = false)
    private String name;
    private String address;
    @Column(nullable = false, unique = true)
    private String email;
    private String phone;
    private String logo;
    private boolean status = true;

    @OneToMany(mappedBy = "school")
    private List<Exam> exams;

    @OneToMany(mappedBy = "school")
    private List<Class> classes;

    @OneToMany(mappedBy = "school")
    private List<Section> sections;
}
