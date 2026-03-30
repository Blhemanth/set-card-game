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