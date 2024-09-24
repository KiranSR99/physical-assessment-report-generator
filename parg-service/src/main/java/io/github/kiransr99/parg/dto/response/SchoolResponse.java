package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.School;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolResponse {
    private Long id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String logo;

    public SchoolResponse(School savedSchool) {
        this.id = savedSchool.getSchoolId();
        this.name = savedSchool.getName();
        this.address = savedSchool.getAddress();
        this.email = savedSchool.getEmail();
        this.phone = savedSchool.getPhone();
        this.logo = "http://localhost:8080/" + savedSchool.getLogo();
    }
}
