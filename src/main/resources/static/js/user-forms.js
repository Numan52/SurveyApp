async function requestSurvey(surveyId) {
    const response = await fetch(`/surveys/${surveyId}`)
    return await response.json()
}


export {requestSurvey}