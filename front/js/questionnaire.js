let questionIDs;
let questionsCount;

let steps = new Map(); // <int, object> (question id, question + answers + constraints)
let userAnswers = new Map(); // <int, bool> (answer id, true: checked)

let indicesStack = [];

$.ready(document)
{
    GETApi("questions", fillIDs);
}

function getCurrentQuestionIndex()
{
    if (indicesStack.length == 0)
    {
        console.error("Current question index is not set. The function was probably called before downloading a question.");
        return null;
    }

    return indicesStack[indicesStack.length - 1];
}

function getCurrentQuestionID()
{
    return questionIDs[getCurrentQuestionIndex()];
}

function fillIDs(data)
{
    questionIDs = data;
    questionsCount = questionIDs.length;

    startQuiz();
}

function startQuiz()
{
    $("#buttonPrev").click(onPrevClick);
    $("#buttonNext").click(onNextClick);

    if (questionsCount > 0)
    {
        indicesStack.push(0);
        downloadAndDisplay(getCurrentQuestionID());
    }
    else
    {
        console.error("No question ID retrieved !");
    }
}

function downloadQuestion(questionID, then)
{
    if (!steps.has(questionID))
    {
        GETApi("questions/" + questionID.toString(), function(data) {
            steps.set(questionID, data);
            
            if (then != null)
                then();
        });
    }
    else if(then != null)
    {
        then();
    }

}

function display(questionID)
{
    let questionData = steps.get(questionID);
    let templateObject = {
        questionLabel: questionData.question.description,
        answers: []
    };

    for (let i = 0; i < questionData.answers.length; i++)
    {
        let answerId = questionData.answers[i].id;
        let answerChecked = false;
        if (userAnswers.has(answerId))
        {
            answerChecked = userAnswers.get(answerId);
        }

        templateObject.answers.push({
            description: questionData.answers[i].description,
            id: questionData.answers[i].id,
            checked: answerChecked
        });
    }

    fillTemplate("question", templateObject, function(rendered) {
        $(".question").html(rendered);
    });
}

function downloadAndDisplay(questionID)
{
    downloadQuestion(questionID, function () {
        display(questionID);
    });
}

function onPrevClick(e)
{
    e.preventDefault();

    if (indicesStack.length <= 1)
        return;

    discardAnswers();
    indicesStack.pop();
    display(getCurrentQuestionID());
}

function getNextQuestionIndex(then, current)
{
    if (current == null)
        current = getCurrentQuestionIndex();

    let next = current + 1;
    if (next >= questionIDs.length) // We just ran out of questions to check. No problem, the quiz is just over.
    {
        then(false, -1);
        return;
    }

    downloadQuestion(questionIDs[next], function () {
        console.log("constraint: ", steps.get(questionIDs[next]).constraint)

        console.log("Checking for index ", next);

        // Check if no constraint
        if (steps.get(questionIDs[next]).constraint == null)
        {
            then(true, next);
            return;
        }

        let ansId = steps.get(questionIDs[next]).constraint.answerId;

        if (!userAnswers.has(ansId))
        {
            console.error("Checking a question where the constraint question id has not been explored yet. It could be true, or false, but it should be asked to user. Apparently, it was not.\nHere is the question data: ", steps.get(questionIDs[next]));
            then(false, -1);
            return;
        }

        if (userAnswers.get(ansId) == true)
        {
            then(true, next);
            return;
        }
        else
        {
            getNextQuestionIndex(then, next);
            return;
        }
    });
}

function onNextClick(e)
{
    e.preventDefault();

    if (!checkAnswers())
        return;

    saveAnswers();

    console.log("Clicked on next.");
    
    getNextQuestionIndex(function (found, index) {
        if (found)
        {
            indicesStack.push(index);
            downloadAndDisplay(getCurrentQuestionID());
        }
        else
        {
            console.log("Couldn't reach any question ! Maybe the test is finished ?");
            window.location.href = "result.html";
        }
    });
    
}

function checkAnswers()
{
    return true;
}

function saveAnswers()
{
    let inputs = $(".user-answer");

    let answersTrue = [];

    for (let i = 0; i < inputs.length; i++)
    {
        const input = inputs[i];
        let answerId = parseInt(input.id);
        let checked = input.checked;

        if (checked)
        {
            answersTrue.push(answerId);
        }

        userAnswers.set(answerId, checked);
    }

    console.log(userAnswers);

    if(!isConnected())
    {
        return;
    }

    // If connected, we send data to server
    let userlogin = window.localStorage.getItem("userlogin");

    let body = {
        login: userlogin,
        idAnswers: answersTrue
    };

    console.log("Request body: ", body);
    console.log("QUESTION ID : ", getCurrentQuestionID());
    console.log("URL : ", "questions/" + (getCurrentQuestionID()) + "/userAnwsers");
    console.log(API_URL + "questions/" + (getCurrentQuestionID()) + "/userAnwsers");

    POSTApi("questions/" + (getCurrentQuestionID()) + "/userAnswers", body, function (data, textStatus, xhr) {
        console.log(xhr.status);
    });


}

function discardAnswers()
{
    let inputs = $(".user-answer");

    for (let i = 0; i < inputs.length; i++)
    {
        const input = inputs[i];
        let answerId = parseInt(input.id);

        userAnswers.delete(answerId);
    }

    console.log(userAnswers);
}
