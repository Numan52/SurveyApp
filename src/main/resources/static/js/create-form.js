import {requestSurvey} from "./user-forms.js";
import {initializeTextArea} from "./ui.js";


let currentPage = 1
let surveyPageData = []
let surveyName
let pageCount = 1

document.addEventListener("DOMContentLoaded", async function () {
    localStorage.removeItem("surveyData")

    const surveyId = new URLSearchParams(window.location.search).get("surveyId")
    let survey


    if (surveyId) { // load existing survey
        survey = await requestSurvey(surveyId)
        populateForm(survey)
        loadPageData(surveyPageData, 1)

    } else { // create new survey
        createFormTitlePopUp()
        const popup = document.querySelector(".popup")
        const popUpOverlay = document.querySelector(".overlay")
        openPopUp(popup, popUpOverlay)

        const popUpCloseButton = document.querySelector(".popup-close-btn")
        const continueButton = document.querySelector(".popup-continue-btn")
        const titleInputField = document.querySelector(".title-input-field")

        popUpCloseButton.addEventListener("click", () => {
            closePopUp(popup, popUpOverlay)
        })

        continueButton.addEventListener("click", () => {
            closePopUp(popup, popUpOverlay)
            surveyName = titleInputField.value
            console.log(`surveyName: ${surveyName}`)
        })

        addPage(surveyPageData, "1")
        initializeFormPage(true, "")
    }

    const addPageBtn = document.querySelector(".add-content-btn")
    addPageBtn.addEventListener("click", () => {
        savePageData(surveyPageData, currentPage)
        addPage(surveyPageData, ++pageCount)
        currentPage = pageCount
        clearPage()
        initializeFormPage(true, "")
    })

    const addChoiceButton = document.querySelector(".add-choice-btn")
    addChoiceButton.addEventListener("click", () => addAnswerChoiceButton("Your Choice"))

    const saveFormButton = document.querySelector(".save-form-btn")
    saveFormButton.addEventListener("click", saveForm)


    initializeTextArea()

})



function populateForm(survey) {
    surveyPageData = survey.surveyPages.sort((a, b) => a.pageNumber - b.pageNumber)

    survey.surveyPages.forEach(surveyPage => {
        loadPages(surveyPageData, surveyPage.pageNumber)
    })
    pageCount = survey.surveyPages.length
}






function createFormTitlePopUp() {
    const popup = document.createElement("div")
    popup.className = "popup"

    const popUpHeader = document.createElement("div")
    popUpHeader.className = "popup-header"

    const title = document.createElement("div")
    title.className = "popup-title"
    title.textContent = "Give your form a name"
    popUpHeader.appendChild(title)

    const closeButton = document.createElement("button")
    closeButton.textContent = "X"
    closeButton.className = "popup-close-btn"
    popUpHeader.appendChild(closeButton)

    const popUpBody = document.createElement("div")
    popUpBody.className = "popup-body"

    const titleInputField = document.createElement("input")
    titleInputField.className = "title-input-field"
    titleInputField.value = "My new Form"
    popUpBody.appendChild(titleInputField)

    const continueButton = document.createElement("button")
    continueButton.textContent = "Continue"
    continueButton.className = "popup-continue-btn"
    popUpBody.appendChild(continueButton)

    const overlay = document.createElement("div")
    overlay.className = "overlay"


    popup.appendChild(popUpHeader)
    popup.appendChild(popUpBody)

    document.body.appendChild(popup)
    document.body.appendChild(overlay)
}

function openPopUp(popup, overlay) {
    if (popup == null) return
    popup.classList.add("active")
    overlay.classList.add("active")
}

function closePopUp(popup, overlay) {
    if (popup == null) return
    popup.classList.remove("active")
    overlay.classList.remove("active")
}

function initializeFormPage(isDefaultPage, question) {
    const questionContainer = document.querySelector(".question")

    const inputField = document.createElement("textarea")
    inputField.className = "question-input"
    inputField.placeholder = "Enter your Question here"
    inputField.value = question
    if (isDefaultPage) {
        addAnswerChoiceButton("Choice A")
        addAnswerChoiceButton("Choice B")
    }
    questionContainer.appendChild(inputField)
}

function addAnswerChoiceButton(answer) {
    const answerChoices = document.querySelector(".answer-choices")
    const answerButton = document.createElement("button")
    const answerButtonText = document.createElement("span")
    answerButtonText.className = "answer-btn-text"
    answerButtonText.contentEditable = "true"
    answerButton.addEventListener("keydown", function (event) {
        if (event.key === " ") {
            if (event.code !== 'Space') {
                return
            }
            event.preventDefault()
            document.execCommand("insertText", false, ' ')

        }
    });
    answerButton.addEventListener("click", function (event) {
        console.log(event.keyCode)
        console.log("clicked")
        const selection = window.getSelection();
        const range = document.createRange();
        range.selectNodeContents(answerButtonText);
        selection.removeAllRanges();
        selection.addRange(range);

    })

    answerButton.className = "answer-btn"
    answerButtonText.textContent = answer

    const removeChoiceIcon = document.createElement("img")
    removeChoiceIcon.src = "/remove-button.svg"
    removeChoiceIcon.className = "remove-choice-icon"
    removeChoiceIcon.addEventListener("click", function () {
        removeChoiceIcon.parentElement.remove()
    })

    answerButton.appendChild(answerButtonText)
    answerButton.appendChild(removeChoiceIcon)
    answerChoices.appendChild(answerButton)

}


function loadPages(surveyPageData, pageNumber) {
    const formPageButtons = document.querySelector(".form-page-buttons")
    console.log(surveyPageData)
    const formPageButton = document.createElement("button")
    formPageButton.className = "form-page-button"
    formPageButton.addEventListener("click", function () {
        console.log(`pageNumber: ${currentPage}`)
        console.log(`formPageButton.textContent: ${formPageButton.textContent}`)
        currentPage = formPageButton.textContent
        console.log(`pageNumber-after: ${currentPage}`)
        loadPageData(surveyPageData, currentPage)
        savePageData(surveyPageData, pageNumber)
    })

    const number = document.createElement("span")
    number.textContent = pageNumber


    formPageButton.appendChild(number)
    formPageButtons.appendChild(formPageButton)
}


function addPage(surveyPageData, pageNumber) {
    const formPageButtons = document.querySelector(".form-page-buttons")
    console.log(surveyPageData)
    const formPageButton = document.createElement("button")
    formPageButton.className = "form-page-button"
    formPageButton.addEventListener("click", function () {
        console.log(`pageNumber: ${currentPage}`)
        console.log(`formPageButton.textContent: ${formPageButton.textContent}`)
        savePageData(surveyPageData, currentPage)
        currentPage = formPageButton.textContent
        console.log(`pageNumber-after: ${currentPage}`)
        loadPageData(surveyPageData, currentPage)
    })

    const number = document.createElement("span")
    number.textContent = pageNumber


    formPageButton.appendChild(number)
    formPageButtons.appendChild(formPageButton)
}

function clearPage() {
    const answerChoices = document.querySelector(".answer-choices")
    answerChoices.innerHTML = ""

    const question = document.querySelector(".question")
    question.innerHTML = ""

}

function savePageData(surveyPageData, pageNumber) {
    const question = document.querySelector(".question-input")
    const answerButtons = document.querySelectorAll(".answer-btn-text")
    let answerTexts = []
    answerButtons.forEach(answerButton => answerTexts.push(answerButton.textContent))

    const index = surveyPageData.findIndex(page => page.pageNumber == pageNumber)
    if (index !== -1) {
        surveyPageData.splice(index, 1)
    }


    surveyPageData.push({
        pageNumber: pageNumber,
        question: question.value,
        answers: answerTexts
    })
    console.log(`savePageData: ${surveyPageData}`)
    localStorage.setItem("surveyData", JSON.stringify(surveyPageData))

}

function loadPageData(surveyPageData, pageNumber) {
    let surveyPage = surveyPageData.find(page => page.pageNumber == pageNumber)
    clearPage()
    initializeFormPage(false, surveyPage.question)
    surveyPage.answers.forEach(answer => addAnswerChoiceButton(answer))

}

async function saveForm() {
    savePageData(surveyPageData, currentPage)
    // try {
        const surveyPages = JSON.parse(localStorage.getItem("surveyData"))
        console.log(surveyName)
        const survey = {
                surveyTitle: surveyName,
                surveyPages: surveyPages
        }
        console.log(survey)
        let response = await fetch("/surveys/create-survey", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(survey)
        })
    window.location.href = "/"


    //     if (response.ok) {
    //         console.log("survey saved successfully")
    //     } else {
    //         throw new Error(`Failed to save survey: ${response.status} ${response.statusText}`)
    //     }
    // } catch (error) {
    //     console.error(error)
    // }

    }

