$('#addPatientForm').on('submit', function (e) {
    e.preventDefault();
    const patientData = {
        patientId: $('#patientId').val().trim(),
        patientName: $('#patientName').val().trim(),
        age: $('#age').val(),
        gender: $('#gender').val(),
        address: $('#address').val().trim(),
        contactNumber: $('#contactNumber').val().trim(),
        email: $('#email').val().trim(),
        admissionDate: $('#admissionDate').val(),
        dischargeDate: $('#dischargeDate').val(),
        doctorAssigned: $('#doctorAssigned').val().trim(),
        roomNumber: $('#roomNumber').val().trim(),
        insuranceProvider: $('#insuranceProvider').val().trim(),
        policyNumber: $('#policyNumber').val().trim(),
        emergencyContact: $('#emergencyContact').val().trim()
    };

    $.ajax({
        url: 'PatientDataServlet',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(patientData),
        success: function (response) {
            alert(response.message || 'Patient data added successfully!');
            $('#addPatientForm')[0].reset();
        },
        error: function () {
            alert('Failed to add patient data!');
        }
    });
});

$('#searchPatientBtn').on('click', function () {
    const searchPatientId = $('#searchPatientId').val().trim();
    if (!searchPatientId) {
        alert('Please enter a Patient ID!');
        return;
    }

    $.ajax({
        url: 'PatientDataServlet',
        type: 'GET',
        data: { searchPatientId },
        success: function (response) {
            $('#searchResponse').html(JSON.stringify(response, null, 2));
        },
        error: function () {
            $('#searchResponse').html('Failed to fetch patient data.');
        }
    });
});
