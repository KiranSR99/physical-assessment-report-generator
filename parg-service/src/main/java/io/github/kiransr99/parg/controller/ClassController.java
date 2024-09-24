package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.constant.URL_CONSTANTS;
import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.request.ClassListRequest;
import io.github.kiransr99.parg.dto.request.ClassRequest;
import io.github.kiransr99.parg.dto.response.ClassResponse;
import io.github.kiransr99.parg.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(URL_CONSTANTS.CLASS_URL)
public class ClassController extends BaseController {

    private final ClassService classService;

    @PostMapping(URL_CONSTANTS.SAVE_CLASS)
    public ResponseEntity<GlobalApiResponse<List<ClassResponse>>> saveClass (@Validated @RequestBody ClassRequest request){
        return successResponse(classService.saveClass(request), SYSTEM_MESSAGE.CLASS_SAVED);
    }

    @GetMapping(URL_CONSTANTS.GET_ALL_CLASSES)
    public ResponseEntity<GlobalApiResponse<List<ClassResponse>>> getAllClasses (){
        return successResponse(classService.getAllClasses(), SYSTEM_MESSAGE.CLASS_FETCHED);
    }

    @GetMapping(URL_CONSTANTS.GET_CLASSES_BY_SCHOOL_ID)
    public ResponseEntity<GlobalApiResponse<List<ClassResponse>>> getClassesBySchoolId (@PathVariable Long schoolId){
        return successResponse(classService.getAllClassesBySchoolId(schoolId), SYSTEM_MESSAGE.CLASS_FETCHED);
    }

    @GetMapping(URL_CONSTANTS.GET_CLASSES_BY_EXAM_ID)
    public ResponseEntity<GlobalApiResponse<List<ClassResponse>>> getClassesByExamId (@PathVariable Long examId){
        return successResponse(classService.getAllClassesByExamId(examId), SYSTEM_MESSAGE.CLASS_FETCHED);
    }

    @GetMapping(URL_CONSTANTS.GET_CLASS_BY_ID)
    public ResponseEntity<GlobalApiResponse<ClassResponse>> getClassById (@PathVariable Long classId){
        return successResponse(classService.getClassById(classId), SYSTEM_MESSAGE.CLASS_FETCHED);
    }

    @PutMapping(URL_CONSTANTS.UPDATE_CLASS)
    public ResponseEntity<GlobalApiResponse<ClassResponse>> updateClass (@PathVariable Long classId, @RequestBody ClassListRequest request){
        return successResponse(classService.updateClass(classId, request), SYSTEM_MESSAGE.CLASS_UPDATED);
    }

    @DeleteMapping(URL_CONSTANTS.DELETE_CLASS)
    public ResponseEntity<GlobalApiResponse<String>> deleteClass (@PathVariable Long classId){
        return successResponse(classService.deleteClass(classId), SYSTEM_MESSAGE.CLASS_DELETED);
    }
}
