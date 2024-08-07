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
    private String name;
    private String address;
    private String email;
    private String phone;

    public SchoolResponse(School savedSchool) {
        this.name = savedSchool.getName();
        this.address = savedSchool.getAddress();
        this.email = savedSchool.getEmail();
        this.phone = savedSchool.getPhone();
    }
}
