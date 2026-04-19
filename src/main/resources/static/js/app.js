// ================= GAME ACTIONS =================

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
        .then(() => showPopup("Game Started!"));
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
        .then(msg => {
            showPopup(msg, msg.includes("Invalid"));
        });
}

// ================= UI HELPERS =================

function renderPlayers(players) {
    const container = document.getElementById("playersList");
    container.innerHTML = "<h3>Players:</h3>";

    Object.keys(players).forEach(p => {
        container.innerHTML += `<div>🧑 ${p}</div>`;
    });
}

function toggleWaiting(data) {
    const waiting = document.getElementById("waitingScreen");

    if (Object.keys(data.players).length < 2) {
        waiting.classList.remove("hidden");
    } else {
        waiting.classList.add("hidden");
    }
}

function controlStartButton(data) {
    const startBtn = document.getElementById("startBtn");

    startBtn.disabled = Object.keys(data.players).length < 2;
}

function showPopup(message, error = false) {
    const popup = document.getElementById("popup");

    popup.innerText = message;
    popup.classList.remove("hidden");

    if (error) popup.classList.add("error");
    else popup.classList.remove("error");

    setTimeout(() => popup.classList.add("hidden"), 2000);
}