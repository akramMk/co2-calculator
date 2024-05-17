const loginInputID = "#login_field";
const passwordInputID = "#password_field";
const passwordConfInputID = "#password_confirm_field";

let loginTippy;
let passwordTippy;
let passwordConfTippy;

let loginOK = false;
let passwordOK = false;
let passwordsSame = false;

let loginAlreadyTaken = false;

function submitRegister()
{
    if (!loginOK || !passwordOK || !passwordsSame)
        return;

    console.log("Sending register request.");
    let login = getLoginField();
    let pwd = getPasswordField();
    let pwd_conf = getPasswordConfField();

    POSTApi("users", {
            login: login,
            password: pwd
        }, function(data, textStatus, xhr) {

        console.log(xhr.status);

        if (xhr.status == 500)
        {
            console.log("Server error.");
            return;
        }

        if (xhr.status == 201)
        {
            location.href = "index.html";
            return;
        }

        if (xhr.status == 400)
        {
            console.log("Bad request.");
            return;
        }

        if (xhr.status == 409)
        {
            console.log("Login already taken.");
            loginAlreadyTaken = true;
            onLoginChanged(getLoginField());
            return;
        }

        if (xhr.status == 200)
        {
            console.log("Succesfuly registered.");
            window.location.href = "login.html";
            return;
        }
    });
}

function getLoginField() {
    return $(loginInputID).val();
}

function getPasswordField() {
    return $(passwordInputID).val();
}

function getPasswordConfField() {
    return $(passwordConfInputID).val();
}

/**
 * 
 * @param {string} login 
 */
function onLoginChanged(login)
{
    if (login.length == 0)
    {
        loginTippy.hide();
    }
    else
    {
        const templateFiller = {};

        loginOK = true;
        
        if (login.length < 5 || login.length > 12)
        {
            templateFiller.charsNumber = "not-ok";
            loginOK = false;
        }
        else
            templateFiller.charsNumber = "ok";
        
        if (/[*$#\s]/.test(login))
        {
            templateFiller.charsSpecial = "not-ok";
            loginOK = false;
        }
        else
            templateFiller.charsSpecial = "ok";

        if (loginAlreadyTaken)
            templateFiller.loginTaken = "";
        else
            templateFiller.loginTaken = "hide";

        fillTemplate("registerLoginCheck", templateFiller, function(rendered) {
            const element = document.createElement("div");
            element.innerHTML = rendered;
            loginTippy.setContent(element);
            loginTippy.show();
        });
    }
}

function checkSamePasswords(pwd, pwd_conf) {
    passwordsSame = (pwd == pwd_conf);

    if (pwd.length == 0 && pwd_conf.length == 0)
    {
        passwordConfTippy.hide();
        return;
    }

    const templateFiller = {};
    if (passwordsSame)
        templateFiller.same = "ok";
    else
        templateFiller.same = "not-ok";

    fillTemplate("registerPasswordsSameCheck", templateFiller, function(rendered) {
        const element = document.createElement("div");
        element.innerHTML = rendered;
        passwordConfTippy.setContent(element);
        passwordConfTippy.show();
    });
    
    passwordConfTippy.show();
}

function onPasswordChanged(pwd)
{
    if (pwd.length == 0)
    {
        passwordTippy.hide();
        passwordConfTippy.hide();
    }
    else
    {
        const templateFiller = {};
        
        passwordOK = true;

        if (pwd.length < 8 || pwd.length > 20)
        {
            templateFiller.charsNumber = "not-ok";
            passwordOK = false;
        }
        else
            templateFiller.charsNumber = "ok";
        
        if (/[1-9]/.test(pwd))
            templateFiller.number = "ok";
        else
        {
            templateFiller.number = "not-ok";
            passwordOK = false;
        }
        
        if (/[A-Z]/.test(pwd))
            templateFiller.caps = "ok";
        else
        {
            templateFiller.caps = "not-ok";
            passwordOK = false;
        }

        fillTemplate("registerPasswordCheck", templateFiller, function(rendered) {
            const element = document.createElement("div");
            element.innerHTML = rendered;
            passwordTippy.setContent(element);
            passwordTippy.show();
        });

        checkSamePasswords(pwd, getPasswordConfField());
    }
}

function onPasswordConfChanged(pwd, pwd_conf)
{
    checkSamePasswords(pwd, pwd_conf);
}

$.ready(document)
{
    tippy(loginInputID, {
        content: '',
        placement: 'right',
        hideOnClick: false,
        animation: 'fade',
        trigger: 'manual',
    });

    loginTippy = document.querySelector(loginInputID)._tippy;

    tippy(passwordInputID, {
        content: '',
        placement: 'right',
        hideOnClick: false,
        animation: 'fade',
        trigger: 'manual',
    });

    passwordTippy = document.querySelector(passwordInputID)._tippy;

    tippy(passwordConfInputID, {
        content: '',
        placement: 'right',
        hideOnClick: false,
        animation: 'fade',
        trigger: 'manual',
    });

    passwordConfTippy = document.querySelector(passwordConfInputID)._tippy;

    $("#register_submit").on("click", function(event) {
        event.preventDefault();
        submitRegister();
    });

    $(loginInputID).on("input", function() {
        onLoginChanged(getLoginField());
    });

    $(passwordInputID).on("input", function() {
        onPasswordChanged(getPasswordField());
    });

    $(passwordConfInputID).on("input", function() {
        onPasswordConfChanged(getPasswordField(), getPasswordConfField());
    });

    onLoginChanged(getLoginField());
    onLoginChanged(getPasswordField());
    onLoginChanged(getPasswordField(), getPasswordConfField());
}