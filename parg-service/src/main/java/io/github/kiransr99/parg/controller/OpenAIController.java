package io.github.kiransr99.parg.controller;


import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parg/api/v1/openai")
public class OpenAIController extends BaseController {

    private final OpenAIService openAIService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateSummary(@RequestBody Map<String, String> request) {
        String input = request.get("input");
        String summary = openAIService.generateSummary(input);
        return ResponseEntity.ok(summary);
    }

}
