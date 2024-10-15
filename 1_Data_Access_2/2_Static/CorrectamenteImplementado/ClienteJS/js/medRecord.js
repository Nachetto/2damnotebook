//GET INFO (patient medRecords)
function getInfo(button) {
    var row = button.parentNode.parentNode;
    var patientId = row.querySelector('td:first-child').innerText.trim();
    var patientName = row.querySelector('td:nth-child(2)').innerText.trim();

    fetch(`http://192.168.1.138:5500/nacho/patients/${patientId}/medRecords`)
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw new Error(err.message || 'Error fetching medRecords'); });
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            var medRecordsTable = document.createElement("table");
            medRecordsTable.id = "medRecordsTable";
            medRecordsTable.setAttribute("data-patient-id", patientId);
            medRecordsTable.className = "table table-bordered table-condensed table-striped";
            medRecordsTable.innerHTML = `<thead><tr><th colspan=4>${patientName}'s MedRecords</th><td colspan=2><button class='btn btn-info btn-sm ms-2' onclick='showAddMedRecordModalW(this)'>Add medRecord</button></td></tr>`;
            medRecordsTable.innerHTML += "<tr><th>ID</th><th>Diagnosis</th><th>Date</th><th>Doctor</th><th>Medications</th><th>Action</th><th>Action</th></tr></thead><tbody></tbody>";

            var medRecordsTableBody = medRecordsTable.querySelector("tbody");

            data.forEach(medRecord => {
                let medRecordRow = createMedRecordRow(medRecord);
                medRecordsTableBody.appendChild(medRecordRow);
            });

            var infoContainer = document.getElementById("infoContainer");
            infoContainer.innerHTML = ""; // Limpiar contenido anterior
            infoContainer.appendChild(medRecordsTable);
        })
        .catch(error => {
            console.error('Error fetching medRecords:', error.message);
            alert(`Error al obtener los registros médicos: ${error.message}`);
        });
}

// DELETE MEDRECORD
function deleteApp(button) {
    var row = button.parentNode.parentNode;
    var appId = row.querySelector('td:first-child').innerText.trim();

    fetch(`http://192.168.1.138:5500/nacho/medRecords/${appId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ confirmation: true })
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw new Error(err.message || 'Error deleting medRecord'); });
            }
            console.log('medRecord deleted successfully');
            row.parentNode.removeChild(row); // Elimina la fila si se elimina correctamente del servidor
        })
        .catch(error => {
            console.error('Error deleting medRecord:', error.message);
            alert(`Error al eliminar el registro médico: ${error.message}`);
        });
}

// UPDATE MEDRECORD
function updateMedRecord(event) {
    event.preventDefault();

    var medRecordId = document.getElementById("updateMedRecordId").value;
    var medRecordDesc = document.getElementById("updateMedRecordDesc").value;
    var medRecordDate = document.getElementById("updateMedRecordDate").value;
    var medRecordIdPatient = document.getElementById("updateMedRecordIdPatient").value;
    var medRecordIdDoctor = document.getElementById("updateMedRecordIdDoctor").value;
    var selectedMeds = getSelectedMeds("updateMedRecordMeds");

    var medRecord = {
        id: medRecordId,
        description: medRecordDesc,
        date: medRecordDate,
        idPatient: medRecordIdPatient,
        idDoctor: medRecordIdDoctor,
        medications: selectedMeds
    };

    fetch('http://192.168.1.138:5500/nacho/medRecords', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(medRecord)
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw new Error(err.message || 'Failed to update medRecord'); });
            }
        })
        .then(data => {
            console.log('medRecord updated successfully');
            $('#updateMedRecordModal').modal('hide');
            modifyMedRecordHTMLtable(medRecord);
        })
        .catch(error => {
            console.error('Error updating medRecord:', error.message);
            alert(`Error al actualizar el registro médico: ${error.message}`);
        });
}

// ADD MEDRECORD
function addMedRecord(event) {
    event.preventDefault();

    var medRecordDesc = document.getElementById("addMedRecordDesc").value;
    var medRecordDate = document.getElementById("addMedRecordDate").value;
    var parsedDate = new Date(medRecordDate);
    var formattedDate = parsedDate.toISOString().split('T')[0];
    var iddoctor = document.getElementById('addMedRecordDoctor').value;
    var selectedMeds = getSelectedMeds("addMedRecordMeds");
    var table = document.getElementById("medRecordsTable");
    var patientId = table.getAttribute("data-patient-id");

    var medRecord = {
        id: 0,
        description: medRecordDesc,
        date: formattedDate,
        idPatient: patientId,
        idDoctor: iddoctor,
        medications: selectedMeds
    };

    fetch("http://192.168.1.138:5500/nacho/medRecords", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(medRecord)
    })
        .then(response => {
            if (!response.ok) {
                console.error('Error adding medRecord:', response.status, response.body);
                return response.json().then(err => { throw new Error(err.message || 'Error adding medRecord'); });
            }
            return response.json();
        })
        .then(data => {
            console.log('medRecord added successfully, index:', data);
            document.getElementById("addMedRecordForm").reset();
            $('#addMedRecordModal').modal('hide');

            var tableBody = document.querySelector("#medRecordsTable tbody");
            medRecord.id = data;
            let row = createMedRecordRow(medRecord);
            tableBody.appendChild(row);
        })
        .catch(error => {
            console.error('Error adding medRecord:', error.message);
            alert(`Error al añadir el registro médico: ${error.message}`);
        });
}

//Function to modify the medRecord in the HTML table
function modifyMedRecordHTMLtable(data) {
    var rows = document.querySelectorAll("#medRecordsTable tbody tr");
    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        if (row.cells[0].textContent == data.id) {
            row.cells[1].textContent = data.description;
            row.cells[2].textContent = data.date;
            row.cells[3].textContent = data.idDoctor;
            row.cells[4].textContent = getMedicationsString(data);
            break;
        }
    }
}

//Function to add row to the medRecord table
function createMedRecordRow(medRecord) {
    var medRecordRow = document.createElement("tr");
    medRecordRow.innerHTML = "<td>" + medRecord.id + "</td>" +
        "<td>" + medRecord.description + "</td>" +
        "<td>" + medRecord.date + "</td>" +
        "<td>" + medRecord.idDoctor + "</td>" +
        "<td>" + getMedicationsString(medRecord) + "</td>" +
        "<td><button class='btn btn-danger btn-sm' onclick='deleteApp(this)'>Delete</button></td>" +
        "<td><button class='btn btn-primary btn-sm ms-2' onclick='showUpdateMedRecordModalW(this)'>Update</button></td>";
    return medRecordRow;
}

//Function to get the selected medications from the HTML select control
function getSelectedMeds(medsSelect) {
    medsSelectControl = document.getElementById(medsSelect);
    var selectedOptions = [];
    for (var i = 0; i < medsSelectControl.options.length; i++) {
        var option = medsSelectControl.options[i];
        if (option.selected) {
            selectedOptions.push(option.textContent);
        }
    }
    return selectedOptions;
}

function getMedicationsString(medRecord) {
    var medicationsString = "";
    if (medRecord.medications !== null) {
        for (var i = 0; i < medRecord.medications.length; i++) {
            medicationsString += medRecord.medications[i];
            if (i < medRecord.medications.length - 1) {
                medicationsString += ", ";
            }
        }
    }
    return medicationsString;
}

//Function to show the modal window for updating medRecord
function showUpdateMedRecordModalW(button) {
    var row = button.parentNode.parentNode;
    var cells = row.getElementsByTagName("td");

    var appId = cells[0].innerText;
    var appDesc = cells[1].innerText;
    var appDate = cells[2].innerText;
    var appIdDoctor = cells[3].innerText;
    var table = document.getElementById("medRecordsTable");
    var appIdPatient = table.getAttribute("data-patient-id");

    document.getElementById("updateMedRecordId").value = appId;
    document.getElementById("updateMedRecordDesc").value = appDesc;
    document.getElementById("updateMedRecordDate").value = appDate;
    document.getElementById("updateMedRecordIdPatient").value = appIdPatient;

    fillDoctorCombo('updateMedRecordIdDoctor', function () {
        document.getElementById("updateMedRecordIdDoctor").value = appIdDoctor;
    });

    fillMedicationsCombo('updateMedRecordMeds');

    var updateappForm = document.getElementById("updateMedRecordForm");
    updateappForm.addEventListener("submit", updateMedRecord);

    var updateMedRecordModal = new bootstrap.Modal(document.getElementById('updateMedRecordModal'));
    updateMedRecordModal.show();
}

//Function to show the modal window for adding medRecord
function showAddMedRecordModalW(event) {
    var addMedRecordModal = new bootstrap.Modal(document.getElementById('addMedRecordModal'));
    fillDoctorCombo('addMedRecordDoctor', function () { });
    fillMedicationsCombo('addMedRecordMeds');

    var addappForm = document.getElementById("addMedRecordForm");
    addappForm.addEventListener("submit", addMedRecord);
    addMedRecordModal.show();
}

//fillDoctorCombo: callback is used to ensure the combo is loaded before setting values
function fillDoctorCombo(combo, callback) {
    fetch('http://192.168.1.138:5500/nacho/doctors')
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw new Error(err.message || 'Error fetching doctors'); });
            }
            return response.json();
        })
        .then(data => {
            let doctorSelect = document.getElementById(combo);
            doctorSelect.innerHTML = '';

            data.forEach(doctor => {
                let option = document.createElement('option');
                option.value = doctor.id;
                option.textContent = doctor.name;
                doctorSelect.appendChild(option);
            });
            callback();
        })
        .catch(error => {
            console.error('Error when fetching doctors:', error.message);
            alert(`Error al obtener los doctores: ${error.message}`);
        });
}

function fillMedicationsCombo(combo) {
    let medsSelect = document.getElementById(combo);
    medsSelect.innerHTML = '';

    let medications = ["Ibuprofen", "Tylenol", "Penicilina", "Insulin", "Folic acid"];
    medications.forEach(medication => {
        let option = document.createElement('option');
        option.textContent = medication;
        medsSelect.appendChild(option);
    });
}
