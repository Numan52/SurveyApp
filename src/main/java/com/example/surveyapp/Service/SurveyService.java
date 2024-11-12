package com.example.surveyapp.Service;

import com.example.surveyapp.Models.Survey;
import com.example.surveyapp.Models.SurveyAnswerPage;
import com.example.surveyapp.Models.SurveyResult;
import com.example.surveyapp.Models.User;
import com.example.surveyapp.Repositories.SurveyAnswerPageRepository;
import com.example.surveyapp.Repositories.SurveyPageRepository;
import com.example.surveyapp.Repositories.SurveyRepository;
import com.example.surveyapp.Repositories.SurveyResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {

    private SurveyRepository surveyRepository;
    private SurveyPageRepository surveyPageRepository;
    private SurveyResultRepository surveyResultRepository;
    private SurveyAnswerPageRepository surveyAnswerPageRepository;


    public SurveyService(SurveyRepository surveyRepository,
                         SurveyPageRepository surveyPageRepository,
                         SurveyResultRepository surveyResultRepository, SurveyAnswerPageRepository surveyAnswerPageRepository) {
        this.surveyRepository = surveyRepository;
        this.surveyPageRepository = surveyPageRepository;
        this.surveyResultRepository = surveyResultRepository;
        this.surveyAnswerPageRepository = surveyAnswerPageRepository;
    }

    public Survey saveSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public SurveyResult saveSurveyResult(SurveyResult surveyResult) {
        return surveyResultRepository.save(surveyResult);
    }

    public void saveSurveyAnswerPage(SurveyAnswerPage surveyAnswerPage) {
        surveyAnswerPageRepository.save(surveyAnswerPage);
    }

    public List<Survey> getAllUserSurveys(User user) {
        return surveyRepository.findAllByUser(user);
    }

    public Optional<Survey> getSurveyById(Long surveyId) {
        return surveyRepository.findById(surveyId);
    }

}
