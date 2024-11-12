package com.example.surveyapp.Controllers;

import com.example.surveyapp.Models.*;
import com.example.surveyapp.Service.SurveyService;
import com.example.surveyapp.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class SurveyController {
    private SurveyService surveyService;
    private UserService userService;

    public SurveyController(SurveyService surveyService, UserService userService) {
        this.surveyService = surveyService;
        this.userService = userService;
    }

    @PostMapping("/surveys/create-survey")
    public ResponseEntity<?> createSurvey(@RequestBody Survey survey, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        System.out.println(username);
        User user = userService.findUserByUsername(username);
        survey.setUser(user);
        for (SurveyPage surveyPage : survey.getSurveyPages()) {
            surveyPage.setSurvey(survey);
        }

        surveyService.saveSurvey(survey);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/surveys/get-user-surveys")
    public ResponseEntity<?> getUserSurveys(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        User user = userService.findUserByUsername(username);
        List<Survey> surveysList = surveyService.getAllUserSurveys(user);
        System.out.println("surveysList: " + surveysList);

        List<SurveyDto> surveyDtoList = SurveyDto.toSurveyDtoList(surveysList);
        for (SurveyDto surveyDto : surveyDtoList) {
            System.out.println(surveyDto.getSurveyPages());
        }


        return ResponseEntity.ok().body(surveyDtoList);
    }

    @GetMapping("/surveys/{surveyId}")
    public ResponseEntity<?> getSurvey(@PathVariable("surveyId") Long surveyId) {
        Optional<Survey> surveyOptional = surveyService.getSurveyById(surveyId);
        try {
            Survey survey = surveyOptional.orElseThrow();
            SurveyDto surveyDto = SurveyDto.toSurveyDto(survey);
            return ResponseEntity.ok(surveyDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Survey does not exist");
        }
    }

    @PostMapping("/surveys/post-survey-answers")
    public ResponseEntity<?> postSurveyAnswers(@RequestBody SurveyResult surveyResult) {
        System.out.println(surveyResult.toString());

        surveyService.saveSurveyResult(surveyResult);
        for (SurveyAnswerPage surveyAnswerPage : surveyResult.getSurveyAnswerPages()) {
            surveyAnswerPage.setSurveyResult(surveyResult);
            surveyService.saveSurveyAnswerPage(surveyAnswerPage);
        }



        return ResponseEntity.ok().build();
    }
}
