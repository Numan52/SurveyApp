import {requestSurvey} from "./user-forms.js";


document.addEventListener("DOMContentLoaded", async function () {
    const surveyId = new URLSearchParams(window.location.search).get("surveyId")
    const survey = await requestSurvey(surveyId)
    console.log(survey)
    displayForm(survey)
})


function displayForm(survey) {
    const surveyTitle = document.querySelector(".survey-title")
    surveyTitle.textContent = survey.surveyTitle
    const formContainer = document.querySelector(".form-container")

    const submitButton = document.querySelector(".submit-btn")
    submitButton.addEventListener("click", () => collectResponses(survey))

    survey.surveyPages.forEach(surveyPage => {
        const pageContainer = document.createElement("div")
        pageContainer.className = "page-container"

        const questionField = document.createElement("textarea")
        questionField.value = surveyPage.question
        questionField.className = "question-field"


        const answersContainer = document.createElement("div")
        answersContainer.className = "answers-container"

        let activeAnswerButton = null

        surveyPage.answers.forEach(answer => {
            const answerButton = document.createElement("button")
            answerButton.textContent = answer
            answerButton.className = "answer-btn"

            answerButton.addEventListener("click", function() {
                console.log(activeAnswerButton)
                if (activeAnswerButton) {
                    activeAnswerButton.classList.remove("active")
                }
                answerButton.classList.add("active")
                activeAnswerButton = answerButton
                console.log(`AFTER: ${activeAnswerButton}`)
            })
            answersContainer.appendChild(answerButton)
        })

        pageContainer.appendChild(questionField)
        pageContainer.appendChild(answersContainer)
        formContainer.appendChild(pageContainer)
    })

    initializeTextArea()
}


function initializeTextArea() {
    // const textArea = document.querySelector(".question-input");
    const textArea = document.getElementsByTagName("textarea");

    for (let i = 0; i < textArea.length; i++) {
        textArea[i].setAttribute("style", "height:" + (40) + "px;overflow-y:hidden;");
        textArea[i].readOnly = true
    }

}

async function collectResponses(survey) {
    const surveyResults = JSON.parse(JSON.stringify(survey))
    const activeButtons = document.querySelectorAll(".answer-btn.active")
    for (let i = 0; i < surveyResults.surveyPages.length; i++) {
        surveyResults.surveyPages[i].answers = [activeButtons[i].textContent]
    }
    surveyResults["surveyAnswerPages"] = surveyResults["surveyPages"]
    delete surveyResults["surveyPages"]
    console.log(surveyResults)

    await postSurveyResults(surveyResults)
}

async function postSurveyResults(surveyResults) {
    try {
        let response = await fetch("/surveys/post-survey-answers", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(surveyResults)
        })

       // window.location.href = "/thanks-for-participating"
    } catch (e) {
        console.error(`Error while posting survey Results: ${e}: ${e.message}`)
    }

}
