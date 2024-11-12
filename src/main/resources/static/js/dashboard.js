document.addEventListener("DOMContentLoaded", async function () {
    const newFormButton = document.querySelector(".new-form-btn")
    newFormButton.addEventListener("click", () => navigateToCreateForm())

    const surveys = await loadUserForms()
    displayUserForms(surveys)

})

// function navigateToCreateForm() {
//     window.location.href = "/forms/create"
// }

function navigateToCreateForm(surveyId) {
    if (surveyId !== undefined) {
        window.location.href = `/forms/create?surveyId=${surveyId}`;
    } else {
        window.location.href = "/forms/create";
    }
}


async function loadUserForms() {
    try {
        const response = await fetch("/surveys/get-user-surveys")
        return await response.json()

    } catch (e) {
        console.error(`${e}: ${e.message}`)
    }

}

function displayUserForms(surveys) {
    surveys.forEach(survey => {
        displayUserForm(survey)
    })
}

function displayUserForm(survey) {
    const formsGrid = document.querySelector(".forms-grid")
    const surveyContainer = document.createElement("div")
    surveyContainer.className = "survey-container"
    surveyContainer.addEventListener("click", function () {
        navigateToCreateForm(survey.surveyId)
    })

    const surveyTitle = document.createElement("div")
    surveyTitle.textContent = survey.surveyTitle
    surveyTitle.className = "surveyTitle"

    surveyContainer.appendChild(surveyTitle)
    formsGrid.appendChild(surveyContainer)
}


