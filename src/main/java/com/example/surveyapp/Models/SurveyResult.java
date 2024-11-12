package com.example.surveyapp.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class SurveyResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyResultId;

    //@JsonProperty("surveyId")
    private Long surveyId;

    private String surveyTitle;
    @OneToMany(mappedBy = "surveyResult")
    @JsonIgnoreProperties("surveyResult") // Ignore the survey property in SurveyPage when serializing
    private List<SurveyAnswerPage> surveyAnswerPages;



    public SurveyResult() {
    }

    public SurveyResult(Long surveyId, String surveyTitle, List<SurveyAnswerPage> surveyAnswerPages) {
        this.surveyId = surveyId;
        this.surveyTitle = surveyTitle;
        this.surveyAnswerPages = surveyAnswerPages;
    }


    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public List<SurveyAnswerPage> getSurveyAnswerPages() {
        return surveyAnswerPages;
    }

    public void setSurveyAnswerPages(List<SurveyAnswerPage> surveyAnswerPages) {
        this.surveyAnswerPages = surveyAnswerPages;
    }

    public void setId(Long id) {
        this.surveyResultId = id;
    }

    public Long getId() {
        return surveyResultId;
    }

    @Override
    public String toString() {
        return "SurveyResult{" +
                "surveyId=" + surveyId +
                ", surveyResultId =" + surveyResultId +
                ", surveyTitle='" + surveyTitle + '\'' +
                ", surveyAnswerPages=" + surveyAnswerPages +
                '}';
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }
}
