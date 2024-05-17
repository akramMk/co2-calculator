/**
 * Url of the api.
 */
const API_URL = "https://192.168.75.108/server/";

/**
 * 
 * @param {string} relativeUrl Path of the resource from the API url. For example, "main" will lead to "api/main".
 * @param {JSON} body
 * @param {function} onResponse Function called when the api response. This function should have a response parameter of type json.
 */
function POSTApi(relativeUrl, body, onResponse) {

  var reqBody = {
    method: 'POST',
    url: API_URL + relativeUrl,
    success: onResponse,
    
    error: function (xhr, status, error) {
      console.error(status + ": " + error);

      onResponse(null, status, xhr);
    }
  }

  if (body != null)
  {
    reqBody.contentType = "application/json; charset=utf-8";
    reqBody.data = JSON.stringify(body);
  }

  var jwt = window.localStorage.getItem("JWT");

  if (jwt != null)
    reqBody.beforeSend = function (xhr) {
      xhr.setRequestHeader ("Authorization", jwt);
    }

  $.ajax(reqBody);
}

/**
 * 
 * @param {string} relativeUrl Path of the resource from the API url. For example, "main" will lead to "api/main".
 * @param {function} onResponse Function called when the api response. This function should have a response parameter of type json.
 */
function GETApi(relativeUrl, onResponse) {

  var body = {
    url: API_URL + relativeUrl,
    dataType: 'json',
    method: 'GET',
    success: onResponse,
    error: function (xhr, status, error) {
      console.error(status + ": " + error);
    }
  };

  var jwt = window.localStorage.getItem("JWT");
  if (jwt != null)
    body.header = { Authorizaton: jwt };

  $.ajax(body);
}

/**
 * Effectue une requête fetch avec les configurations fournies.
 * @param {string} url - L'URL vers laquelle envoyer la requête.
 * @param {object} requestConfig - La configuration de la requête fetch.
 * @returns {Promise<Response>} - La promesse contenant la réponse de la requête fetch.
 */
async function sendFetchRequest(url, requestConfig) {
  try {
    const response = await fetch(API_URL + url, requestConfig);
    if (!response.ok) {
      throw new Error("Request failed with status: " + response.status);
    }
    return response;
  } catch (error) {
    console.error("Fetch request failed:", error);
    throw error;
  }
}

/**
 * Fill an html element, provided with a template and an object to render it with.
 * 
 * @param {string} templateName The name of the template. For example, the template "user" has to be stored in templates/user.mustache.
 * @param {object} templateObject The object to fill the template with.
 * @param {string} target The target to fill with the rendered template. For example, ".user", "#user".
 */
function fillTemplate(templateName, templateObject, target) {
  fetch("js/common/templates/" + templateName + ".mustache")
    .then((response) => response.text())
    .then((template) => {
      const rendered = Mustache.render(template, templateObject);

      $(target).html(rendered);
    });
}

/**
 * Fill an html element, provided with a template and an object to render it with.
 * 
 * @param {string} templateName The name of the template. For example, the template "user" has to be stored in templates/user.mustache.
 * @param {object} templateObject The object to fill the template with.
 * @param {function} callback What to do with the rendered template. The function should take the rendered template as an argument (string).
 */
function fillTemplate(templateName, templateObject, callback) {
  fetch("js/common/templates/" + templateName + ".mustache")
    .then((response) => {
      return response.text();
    })
    .then((template) => {
      const rendered = Mustache.render(template, templateObject);

      callback(rendered);
    });
}

function isConnected()
{
  return window.localStorage.getItem("JWT") != null;
}

/**
 * Affiche ou cache les éléments de l'interface qui nécessitent une connexion. (exemple: le nom de l'utilisateur)
 * @param {boolean} isConnected - Un booléen indiquant si l'utilisateur est connecté ou non.
 */
function displayConnected(connected) {
  if (connected) {
    const elementsRequiringLoggedOut = document.getElementsByClassName("must-be-logged-out");
    for (const element of elementsRequiringLoggedOut) {
      element.style.display = "none";
    }
  }
  else {
    const elementsRequiringLoggedIn = document.getElementsByClassName("must-be-logged-in");
    for (const element of elementsRequiringLoggedIn) {
      element.style.display = "none";
    }
  }
}

function setupNavbar(connected)
{
    $("#logoutLink").click(function (e) {
        e.preventDefault();
        console.log("deco");
        POSTApi("logout", null, function(data, textStatus, xhr) {

          if (xhr.status == 200)
          {
              console.log("Succesfuly logged out.");
              window.localStorage.removeItem("JWT");
              location.href = "index.html";
              return;
          }
        });
    })

    $("#computeLink").click(function (e) {
      e.preventDefault();
      
      if (!isConnected())
      {
        location.href = "calculer.html";
      }

      let userLogin = window.localStorage.getItem("userlogin");

      GETApi("result/" + userLogin, function(data, textStatus, xhr) {
        if (xhr.status == 200)
        {
          location.href = "result.html";
        }
        else
        {
          location.href = "calculer.html";
        }
      });
    });

    if (connected && window.localStorage.getItem("userlogin") != null)
    {
      $("#loggedInUserName").html(window.localStorage.getItem("userlogin"));
    }

}

$.ready(document)
{  
  if (isConnected())
  {
    let now = Date.now();
    let tokenBirth = window.localStorage.getItem("tokenBirth");

    let ellapsed = now - tokenBirth;
    let seconds = Math.floor(ellapsed / (1000));

    console.log("Connected since ", seconds, " seconds.");

    let lifetime = window.localStorage.getItem("tokenLifeTime");

    if (seconds > lifetime)
    {
      console.log("Token expired. Logging out...");
      window.localStorage.clear();
    }
  }

  fetch('navbar.html')
    .then(response => response.text())
    .then(html => document.getElementById('navbar-placeholder').innerHTML = html)
    .then(function() {
      displayConnected(isConnected());
      setupNavbar(isConnected());
    });
}
