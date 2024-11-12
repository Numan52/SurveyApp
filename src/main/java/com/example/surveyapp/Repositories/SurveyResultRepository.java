package com.example.surveyapp.Repositories;

import com.example.surveyapp.Models.SurveyResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyResultRepository extends JpaRepository<SurveyResult, Long> {
}
