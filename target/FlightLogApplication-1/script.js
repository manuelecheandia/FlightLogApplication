var ws; 

function connectPilot() {

    var pilotName = document.getElementById("pilotName").value; 
    if (pilotName.trim() === "") {
        displayErrorMessage("Please enter your name.");
        return;
    }
    
    var host = document.location.host; 
    var pathname = document.location.pathname; 
    
    ws = new WebSocket("ws://" + host + pathname + "flightlog/" + pilotName);
    
        console.log(ws);

    ws.onopen = function() {
        toggleLogFields(true); 
        displayErrorMessage("Connected as " + pilotName);
        console.log("OnOpen", pilotName);
    };

    ws.onerror = function(event) {
        displayErrorMessage("WebSocket error!", event);
        toggleLogFields(false); 
        console.log("onerror");
    };

ws.onmessage = function(event) {
    var message = JSON.parse(event.data);
    if (message) {
        addLogToTable(message);
        console.log("addLogtoTable");
    } else {
        displayErrorMessage(message.info);
        console.log("addLogtoTable Error");
    }
};


    ws.onclose = function(event) {
         console.log("WebSocket closed with code:", event.code, "reason:", event.reason);
        displayErrorMessage("Disconnected from the server");
        toggleLogFields(false); 
    };
}

function logFlight() {
    if (!ws || ws.readyState !== WebSocket.OPEN) {
        displayErrorMessage("You are not connected. Please log in.");
        return;
    }

    var date = document.getElementById("date").value;
    var pilot = document.getElementById("pilotName").value; 
    console.log("Pilot name:", pilot); 
    var aircraft = document.getElementById("aircraft").value;
    var hours = document.getElementById("hours").value;
    var notes = document.getElementById("notes").value;

    if (!date || aircraft.trim() === "" || hours.trim() === "") {
        displayErrorMessage("Please fill out all required fields.");
        return;
    }

    var json = JSON.stringify({
        "date": date,
        "pilot": pilot, 
        "aircraft": aircraft,
        "hours": parseFloat(hours),
        "notes": notes
    });
    ws.send(json);
}
function disconnectPilot(){
    if(ws){
        ws.close();
    }
    displayErrorMessage("Disconnected from the server.");
}


function displayErrorMessage(message){
    var errorMessageElement = document.getElementById("error-message");
    errorMessageElement.textContent = message;
}

function toggleLogFields(enable) {
    document.getElementById("date").disabled = !enable;
    document.getElementById("aircraft").disabled = !enable;
    document.getElementById("hours").disabled = !enable;
    document.getElementById("notes").disabled = !enable;
    document.getElementById("logFlight").disabled = !enable;
    document.getElementById("disconnectPilot").disabled = !enable;
}


function addLogToTable(logEntry) {
    var table = document.getElementById('logsTable').getElementsByTagName('tbody')[0];
    var newRow = table.insertRow(table.rows.length);

    var cell1 = newRow.insertCell(0);
    var cell2 = newRow.insertCell(1);
    var cell3 = newRow.insertCell(2);
    var cell4 = newRow.insertCell(3);
    var cell5 = newRow.insertCell(4);

    cell1.textContent = logEntry.date;
    cell2.textContent = logEntry.pilotName; 
    cell3.textContent = logEntry.aircraft;
    cell4.textContent = logEntry.hours;
    cell5.textContent = logEntry.notes;
}
