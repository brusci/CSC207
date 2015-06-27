We are developing an Android application that helps hospitals with their triage management.

When a patient enters the emergency room, information is recorded about the patient, such as: name, birthdate, health card number, symptoms, and vital signs.
This application prioritizes patients with high urgency for triage care, as defined by the hospital policy.
The application allows the nurse to update the status of a patient and access their previous medical history.
The application allows the nurse to check if a patient's condition is improving or worsening, and if the patient have been seen by a doctor.
All information about a patient can be saved in a database which is then loaded when the application starts.
We will store the patient's information in an SQLite database.
The Records class will create and maintain the SQLite database.
The Patient and Records classes will be used by the MainActivity class.
The Patient class has several methods to make the design modular.
The SQLite database can then be exported to a hospital database if required.

![](https://github.com/brusci/CSC207-Labs/androidApp/starView.jpg)