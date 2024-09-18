package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.constant.URL_CONSTANTS;
import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.request.SchoolRequest;
import io.github.kiransr99.parg.dto.request.SchoolUpdateRequest;
import io.github.kiransr99.parg.dto.response.SchoolResponse;
import io.github.kiransr99.parg.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(URL_CONSTANTS.SCHOOL_URL)
public class SchoolController extends BaseController {
    private final SchoolService schoolService;

    @PostMapping(URL_CONSTANTS.SAVE_SCHOOL)
    public ResponseEntity<GlobalApiResponse<SchoolResponse>> saveSchool (@Validated @RequestBody SchoolRequest request){
        return successResponse(schoolService.saveSchool(request), SYSTEM_MESSAGE.SCHOOL_SAVED);
    }
    @GetMapping(URL_CONSTANTS.GET_ALL_SCHOOLS)
    public ResponseEntity<GlobalApiResponse<List<SchoolResponse>>> getAllSchools (){
        return successResponse(schoolService.getAllSchools(), SYSTEM_MESSAGE.SCHOOL_FETCHED);
    }
    @GetMapping(URL_CONSTANTS.GET_SCHOOL_BY_ID)
    public ResponseEntity<GlobalApiResponse<SchoolResponse>> getSchoolById (@PathVariable Long schoolId){
        return successResponse(schoolService.getSchoolById(schoolId), SYSTEM_MESSAGE.SCHOOL_FETCHED);
    }
    @PutMapping(URL_CONSTANTS.UPDATE_SCHOOL)
    public ResponseEntity<GlobalApiResponse<SchoolResponse>> updateSchool (@RequestBody SchoolUpdateRequest schoolUpdateRequest){
        return successResponse(schoolService.updateSchool(schoolUpdateRequest), SYSTEM_MESSAGE.SCHOOL_UPDATED);
    }

    @PutMapping(URL_CONSTANTS.DELETE_SCHOOL)
    public ResponseEntity<GlobalApiResponse<String>> deleteSchool (@PathVariable Long schoolId){
        return successResponse(schoolService.deleteSchool(schoolId), SYSTEM_MESSAGE.SCHOOL_DELETED);
    }
}
