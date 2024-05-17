function fetch_logout() {
    const headers = new Headers();
    headers.append("Content-Type", "application/json");
    headers.append("Accept", "application/json");
    headers.append("Authorization", userAuth);
    const requestConfig = {
        method: "POST",
        headers: headers,
        mode: "cors", 
        body: JSON.stringify({login: user.login})
    };
    return fetch("users/logout", requestConfig)
}

function logout() {
    fetch_logout().then(result => {
        if (result.ok && result.status == 204) {
            displayConnected(false);
            isConnected = false;
            console.log("Déco réussie !")
            updateLoginLogoutButton();
            //window.location.href = 'home.html';
            user = {};
            userAuth = "";
        } else {
            console.log("Déco ratée !")
        }
    });
}
