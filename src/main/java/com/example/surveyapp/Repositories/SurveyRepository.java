package com.example.surveyapp.Repositories;

import com.example.surveyapp.Models.Survey;
import com.example.surveyapp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findAllByUser(User user);

}
