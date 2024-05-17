const passwordID = "#password";
var passwordTippy = null;

function connect() {
    const headers = new Headers();
    headers.append("Content-Type", "application/json");

    const body = {
        login: document.getElementById("login").value,
        password: document.getElementById("password").value
    };

    console.log("In connect: username = " + body.login);

    POSTApi("login", body, function(data, textStatus, xhr) {
        console.log(xhr.status);
        // Récupérer le nom d'utilisateur après la connexion réussie
        if (xhr.status === 200) {
            var jwt = xhr.getResponseHeader("Authorization");
            window.localStorage.setItem("JWT", jwt); 
            window.localStorage.setItem("userlogin", body.login); 
            window.localStorage.setItem("tokenBirth", Date.now());
            window.localStorage.setItem("tokenLifeTime", 30 * 60);
            location.href = "index.html";
        }
        else if (xhr.status == 401 || xhr.status == 404) {
            console.log("Bad login.");
            passwordTippy.show();
        }
    });
}

$.ready(document)
{
    tippy(passwordID, {
        content: '',
        placement: 'right',
        hideOnClick: false,
        animation: 'fade',
        trigger: 'manual',
    });
    
    passwordTippy = document.querySelector(passwordID)._tippy;

    passwordTippy.setContent('Login ou mot de passe incorrect.');
    passwordTippy.hide();

    document.getElementById("login-form").addEventListener("submit", function(event) {
        event.preventDefault(); // Empêche que si on clique plusieurs fois ça "rame"
        connect(); // Appeler la fonction connect une fois le formulaire soumis
    });
}
