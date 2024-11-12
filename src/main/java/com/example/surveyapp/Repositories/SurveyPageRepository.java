package com.example.surveyapp.Repositories;

import com.example.surveyapp.Models.Survey;
import com.example.surveyapp.Models.SurveyPage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyPageRepository extends JpaRepository<SurveyPage, Long> {

}
