package com.example.surveyapp.Models;

import java.util.ArrayList;
import java.util.List;

public class SurveyDto {

    private Long surveyId;
    private String surveyTitle;

    private List<SurveyPage> surveyPages;


    public SurveyDto() {
    }

    public SurveyDto(Long surveyId, String surveyTitle, List<SurveyPage> surveyPages) {
        this.surveyId = surveyId;
        this.surveyTitle = surveyTitle;
        this.surveyPages = surveyPages;
    }

    public static SurveyDto toSurveyDto(Survey survey) {
        String surveyTitle = survey.getSurveyTitle();
        List<SurveyPage> surveyPages = survey.getSurveyPages();
        Long surveyId = survey.getId();

        return new SurveyDto(surveyId, surveyTitle, surveyPages);
    }

    public static List<SurveyDto> toSurveyDtoList(List<Survey> surveysList) {
        List<SurveyDto> surveyDtoList = new ArrayList<>();
        for (Survey survey : surveysList) {
            surveyDtoList.add(toSurveyDto(survey));
        }
        return surveyDtoList;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public List<SurveyPage> getSurveyPages() {
        return surveyPages;
    }

    public void setSurveyPages(List<SurveyPage> surveyPages) {
        this.surveyPages = surveyPages;
    }
}
