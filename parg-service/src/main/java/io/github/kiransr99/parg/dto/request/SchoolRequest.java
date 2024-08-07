package io.github.kiransr99.parg.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolRequest {
    private String name;
    private String address;
    private String email;
    private String phone;
}
