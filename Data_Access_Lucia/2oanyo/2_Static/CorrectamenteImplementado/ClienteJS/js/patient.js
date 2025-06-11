//GET ALL
function getAllpatients() {
    fetch("http://192.168.1.138:5500/nacho/patients")
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw new Error(err.message || 'Error fetching patients'); });
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            var tableBody = document.querySelector("#patientTable tbody");
            tableBody.innerHTML = "";

            data.forEach(patient => {
                let row = createPatientRow_LongVersion(patient);
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error fetching data:', error.message);
            alert(`Error al obtener los pacientes: ${error.message}`);
        });
}

// DELETE PATIENT
function deletePatient(button) {
    var row = button.parentNode.parentNode;
    var patientId = row.querySelector('td:first-child').innerText.trim();
    sendDeleteRequest(true);

    function sendDeleteRequest(confirmation) {
        fetch(`http://192.168.1.138:5500/nacho/patients/${patientId}?confirm=${confirmation}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.status === 204) {
                    console.log('Patient deleted successfully');
                    row.parentNode.removeChild(row);
                    document.getElementById("infoContainer").innerHTML = "";
                    return;
                }

                if (!response.ok) {
                    if (response.status === 409) {
                        return response.text().then(eMessage => {
                            let resp = confirm(eMessage);
                            if (resp) {
                                sendDeleteRequest(true);
                            } else {
                                throw new Error('User cancelled operation');
                            }
                        });
                    } else {
                        return response.json().then(err => { throw new Error(err.message || 'Error deleting patient'); });
                    }
                }
            })
            .catch(error => {
                console.error('Error deleting patient:', error.message);
                alert(`Error al eliminar el paciente: ${error.message}`);
            });
    }
}

// ADD PATIENT
function addPatient(event) {
    event.preventDefault();

    let patientName = document.getElementById("addpatientName").value;
    let patientDOB = document.getElementById("addpatientDOB").value;
    let patientPhone = document.getElementById("addpatientPhone").value;
    let patientUname = document.getElementById("addpatientUserName").value;
    let patientPwd = document.getElementById("addpatientPassword").value;

    let patient = {
        id: 0,
        name: patientName,
        birthDate: patientDOB,
        phone: patientPhone,
        paid: 0,
        userName: patientUname,
        password: patientPwd
    };

    fetch("http://192.168.1.138:5500/nacho/patients", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(patient)
    })
        .then(response => {
            if (!response.ok) {
                if (response.status === 409) {
                    return response.text().then(eMessage => {
                        alert(eMessage);
                    });
                } else {
                    return response.json().then(err => { throw new Error(err.message || 'Error adding patient'); });
                }
            }
            return response.json();
        })
        .then(data => {
            console.log('Patient added successfully, index:', data);
            document.getElementById("addpatientForm").reset();
            $('#addpatientModal').modal('hide');

            var tableBody = document.querySelector("#patientTable tbody");
            patient.id = data;
            let row = createPatientRow_LongVersion(patient);
            tableBody.appendChild(row);
        })
        .catch(error => {
            console.error('Error adding patient:', error.message);
            alert(`Error al añadir el paciente: ${error.message}`);
        });
}

// UPDATE PATIENT
function updatePatient(event) {
    event.preventDefault();

    let patientId = document.getElementById("updatepatientId").value;
    let patientName = document.getElementById("updatepatientName").value;
    let patientDOB = document.getElementById("updatepatientDOB").value;
    let patientPhone = document.getElementById("updatepatientPhone").value;

    var patient = {
        id: patientId,
        name: patientName,
        birthDate: patientDOB,
        phone: patientPhone
    };

    fetch('http://192.168.1.138:5500/nacho/patients', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(patient)
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw new Error(err.message || 'Error updating patient'); });
            }
        })
        .then(data => {
            $('#updatepatientModal').modal('hide');
            modifyPatientHTMLtable(patient);
        })
        .catch(error => {
            console.error('Error updating patient:', error.message);
            alert(`Error al actualizar el paciente: ${error.message}`);
        });
}

// Function to modify HTML patient table
function modifyPatientHTMLtable(data) {
    var rows = document.querySelectorAll("#patientTable tbody tr");
    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        if (row.cells[0].textContent == data.id) {
            row.cells[1].textContent = data.name;
            row.cells[2].textContent = data.birthDate;
            row.cells[3].textContent = data.phone;
            break;
        }
    }
}

// Function to show the modal window for updating
function showUpdatePatientModalW(button) {
    let row = button.parentNode.parentNode;
    let cells = row.getElementsByTagName("td");

    let patientId = cells[0].innerText;
    let patientName = cells[1].innerText;
    let patientDOB = cells[2].innerText;
    let patientPhone = cells[3].innerText;

    document.getElementById("updatepatientId").value = patientId;
    document.getElementById("updatepatientName").value = patientName;
    document.getElementById("updatepatientDOB").value = patientDOB;
    document.getElementById("updatepatientPhone").value = patientPhone;

    var updatepatientForm = document.getElementById("updatepatientForm");
    updatepatientForm.addEventListener("submit", updatePatient);

    var updatepatientModal = new bootstrap.Modal(document.getElementById('updatepatientModal'));
    updatepatientModal.show();
}

// Function to add row to the table
function createPatientRow(patient) {
    var row = document.createElement("tr");
    row.innerHTML = "<td>" + patient.id + "</td>" +
        "<td>" + patient.name + "</td>" +
        "<td>" + patient.phone + "</td>" +
        "<td><button class='btn btn-info btn-sm'>Get Info</button></td>" +
        "<td><button class='btn btn-danger btn-sm'>Delete</button></td>" +
        "<td><button class='btn btn-primary btn-sm ms-2'>Update</button></td>";

    row.querySelector(".btn-info").addEventListener("click", getInfo);
    row.querySelector(".btn-danger").addEventListener("click", deletePatient);
    row.querySelector(".btn-primary").addEventListener("click", showUpdatePatientModalW);

    return row;
}

// Function to add row to the table (detailed version)
function createPatientRow_LongVersion(patient) {
    let row = document.createElement("tr");
    let idCell = document.createElement("td");
    let nameCell = document.createElement("td");
    let dobCell = document.createElement("td");
    let phoneCell = document.createElement("td");
    let paidCell = document.createElement("td");
    let getInfoButtonCell = document.createElement("td");
    let deleteButtonCell = document.createElement("td");
    let updateButtonCell = document.createElement("td");

    let idText = document.createTextNode(patient.id);
    let nameText = document.createTextNode(patient.name);
    let dobText = document.createTextNode(patient.birthDate);
    let phoneText = document.createTextNode(patient.phone);
    let paidText = document.createTextNode(patient.paid);

    idCell.appendChild(idText);
    nameCell.appendChild(nameText);
    dobCell.appendChild(dobText);
    phoneCell.appendChild(phoneText);
    paidCell.appendChild(paidText);

    var getInfoButton = document.createElement("button");
    getInfoButton.className = "btn btn-info btn-sm";
    getInfoButton.textContent = "Get Info";

    var deleteButton = document.createElement("button");
    deleteButton.className = "btn btn-danger btn-sm";
    deleteButton.textContent = "Delete";

    var updateButton = document.createElement("button");
    updateButton.className = "btn btn-primary btn-sm ms-2";
    updateButton.textContent = "Update";

    getInfoButton.addEventListener("click", function () {
        getInfo(this);
    });

    deleteButton.addEventListener("click", function () {
        deletePatient(this);
    });

    updateButton.addEventListener("click", function () {
        showUpdatePatientModalW(this);
    });

    getInfoButtonCell.appendChild(getInfoButton);
    deleteButtonCell.appendChild(deleteButton);
    updateButtonCell.appendChild(updateButton);

    row.appendChild(idCell);
    row.appendChild(nameCell);
    row.appendChild(dobCell);
    row.appendChild(phoneCell);
    row.appendChild(paidCell);
    row.appendChild(getInfoButtonCell);
    row.appendChild(deleteButtonCell);
    row.appendChild(updateButtonCell);

    return row;
}

// Call the function when the DOM is ready
document.addEventListener("DOMContentLoaded", function () {
    getAllpatients();

    var addButton = document.getElementById("addButton");
    addButton.addEventListener("click", function () {
        var addpatientForm = document.getElementById("addpatientForm");
        addpatientForm.addEventListener("submit", addPatient);

        var addpatientModal = new bootstrap.Modal(document.getElementById('addpatientModal'));
        addpatientModal.show();
    });
});
