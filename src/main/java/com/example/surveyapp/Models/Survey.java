package com.example.surveyapp.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String surveyTitle;
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("survey") // Ignore the survey property in SurveyPage when serializing
    private List<SurveyPage> surveyPages;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Survey() {
    }

    public Survey(String surveyTitle, List<SurveyPage> surveyPages, User user) {
        this.surveyTitle = surveyTitle;
        this.surveyPages = surveyPages;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public List<SurveyPage> getSurveyPages() {
        return surveyPages;
    }

    public void setSurveyPages(List<SurveyPage> surveyPages) {
        this.surveyPages = surveyPages;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }
}
