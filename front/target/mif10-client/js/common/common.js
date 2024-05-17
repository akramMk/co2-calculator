/**
 * Url of the api.
 */
const API_URL = "https://192.168.75.108/";

/**
 * 
 * @param {string} relativeUrl Path of the resource from the API url. For example, "main" will lead to "api/main".
 * @param {string} ajaxMethod "GET", "POST", ...
 * @param {function} onResponse Function called when the api response. This function should have a response parameter of type json.
 */
function requestApi(relativeUrl, ajaxMethod, onResponse) {
    $.ajax({
        url: API_URL + relativeUrl,
        dataType: 'json',
        method: ajaxMethod,
        success: onResponse,
        error: function (xhr, status, error) {
            console.error(status + ": " + error);
        }
    });
}

/**
 * Fill an html element, provided with a template and an object to render it with.
 * 
 * @param {string} templateName The name of the template. For example, the template "user" has to be stored in templates/user.mustache.
 * @param {object} templateObject The object to fill the template with.
 * @param {string} target The target to fill with the rendered template. For example, ".user", "#user".
 */
function fillTemplate(templateName, templateObject, target){
    fetch("js/common/templates/" + templateName + ".mustache")
        .then((response) => response.text())
        .then((template) => {
            const rendered = Mustache.render(template, templateObject);

            $(target).html(rendered);
        });
}