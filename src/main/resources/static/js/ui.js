function initializeTextArea() {
    // const textArea = document.querySelector(".question-input");
    const textArea = document.getElementsByTagName("textarea");
    console.log(textArea)

    for (let i = 0; i < textArea.length; i++) {
        textArea[i].setAttribute("style", "height:" + (40) + "px;overflow-y:hidden;");
        textArea[i].maxLength = 150
        textArea[i].addEventListener("input", OnInput, false);
    }

    function OnInput() {
        this.style.height = 'auto';
        this.style.height = (this.scrollHeight - 15) + "px";
    }
}

export {initializeTextArea}