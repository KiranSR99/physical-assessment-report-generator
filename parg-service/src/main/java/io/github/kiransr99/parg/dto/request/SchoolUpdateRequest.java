package io.github.kiransr99.parg.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SchoolUpdateRequest {
    private Long schoolId;
    private String name;
    private String address;
    private String email;
    private String phone;
    private MultipartFile logo;
}
