package com.example.surveyapp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class SurveyAnswerPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int pageNumber;
    private String question;
    @ElementCollection
    @CollectionTable(name = "survey_answer_page_answers", joinColumns = @JoinColumn(name = "survey_answer_page_id"))
    @Column(name = "answer")
    private List<String> answers;
    @ManyToOne()
    @JoinColumn(name = "survey_result_id")
    @JsonIgnore
    private SurveyResult surveyResult;



    public SurveyAnswerPage() {
    }

    public SurveyAnswerPage(int pageNumber, String question, List<String> answers) {
        this.pageNumber = pageNumber;
        this.question = question;
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public SurveyResult getSurveyResult() {
        return surveyResult;
    }

    public void setSurveyResult(SurveyResult surveyResult) {
        this.surveyResult = surveyResult;
    }
}
