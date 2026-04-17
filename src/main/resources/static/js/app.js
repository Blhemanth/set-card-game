function createGame() {
    fetch('/api/game/create')
        .then(res => res.json())
        .then(data => {
            alert("Session ID: " + data.sessionId);
            localStorage.setItem("sessionId", data.sessionId);
            window.location.href = "game.html";
        });
}

function joinGame() {
    const sessionId = document.getElementById("sessionId").value;

    fetch(`/api/game/join?sessionId=${sessionId}`)
        .then(res => res.text())
        .then(playerId => {
            localStorage.setItem("sessionId", sessionId);
            localStorage.setItem("playerId", playerId);
            window.location.href = "game.html";
        });
}

function startGame() {
    const sessionId = localStorage.getItem("sessionId");

    fetch(`/api/game/start?sessionId=${sessionId}`)
        .then(() => alert("Game Started"));
}

function passCard(value) {
    const sessionId = localStorage.getItem("sessionId");
    const playerId = localStorage.getItem("playerId");

    fetch(`/api/game/pass?sessionId=${sessionId}&playerId=${playerId}&cardValue=${value}`);
}

function callSet() {
    const sessionId = localStorage.getItem("sessionId");
    const playerId = localStorage.getItem("playerId");

    fetch(`/api/game/set?sessionId=${sessionId}&playerId=${playerId}`)
        .then(res => res.text())
        .then(msg => alert(msg));
}
function showWaiting() {
    document.getElementById("waitingScreen").classList.remove("hidden");
}

function hideWaiting() {
    document.getElementById("waitingScreen").classList.add("hidden");
}
function showPopup(message, isError = false) {
    const popup = document.getElementById("popup");
    popup.innerText = message;
    popup.classList.remove("hidden");

    if (isError) {
        popup.classList.add("error");
    } else {
        popup.classList.remove("error");
    }

    setTimeout(() => {
        popup.classList.add("hidden");
    }, 2000);
}
function checkPlayersAndToggleWaiting(data) {
    const waitingScreen = document.getElementById("waitingScreen");

    if (!data.players || Object.keys(data.players).length < 2) {
        waitingScreen.classList.remove("hidden");
    } else {
        waitingScreen.classList.add("hidden");
    }
}