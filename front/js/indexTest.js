$(document).ready(function(){
    requestApi("server/db", "GET", function(response){
        console.log(response);

        fillTemplate("test", {msg: response["hello"]}, "#test-template");
    });
});