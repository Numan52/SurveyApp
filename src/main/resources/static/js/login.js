document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("loginForm");
    form.addEventListener("submit", function (event) {
        event.preventDefault()
        const formData = new FormData(form);
        console.log(formData)
        const authenticationRequest = {
            username: formData.get("email"),
            password: formData.get("password")
        };

        fetch("/authenticate", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(authenticationRequest)
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(errorMessage => {
                        console.log(`Error: ${errorMessage}`);
                    });
                } else {
                    return response.json().then((data) => {
                        console.log(`token: ${data.jwt}`)
                        document.cookie = "jwt=" + data.jwt
                        window.location.href = "/"
                    });
                }
            })
            .catch(error => {
                console.log(`Error: ${error.message}`);
            })
    });
});



