package com.example.surveyapp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class SurveyPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int pageNumber;
    private String question;
    @ElementCollection
    @CollectionTable(name = "survey_page_answers", joinColumns = @JoinColumn(name = "survey_page_id"))
    @Column(name = "answer")
    private List<String> answers;
    @ManyToOne()
    @JoinColumn(name = "survey_id")
    @JsonIgnore
    private Survey survey;



    public SurveyPage() {
    }

    public SurveyPage(int pageNumber, String question, List<String> answers) {
        this.pageNumber = pageNumber;
        this.question = question;
        this.answers = answers;
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

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public Long getId() {
        return id;
    }


}
