package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.SchoolRequest;
import io.github.kiransr99.parg.dto.request.SchoolUpdateRequest;
import io.github.kiransr99.parg.dto.response.SchoolResponse;
import io.github.kiransr99.parg.dto.response.StudentResponse;

import java.io.IOException;
import java.util.List;

public interface SchoolService {
    SchoolResponse saveSchool(SchoolRequest request) throws IOException;

    List<SchoolResponse> getAllSchools();

    SchoolResponse getSchoolById(Long schoolId);

    SchoolResponse updateSchool(SchoolUpdateRequest updateRequest);

    String deleteSchool(Long id);
}
