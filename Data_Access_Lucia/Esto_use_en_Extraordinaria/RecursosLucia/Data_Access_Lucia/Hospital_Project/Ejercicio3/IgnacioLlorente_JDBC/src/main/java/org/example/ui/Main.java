package org.example.ui;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.common.Constantes;
import org.example.domain.Credential;
import org.example.domain.Patient;
import org.example.domain.PrescribedMedication;
import org.example.domain.Record;
import org.example.service.*;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final RecordService recordService;
    private final MedicationService medicationService;
    private final DoctorService doctorService;

    @Inject
    public Main(PatientService patientService, AppointmentService appointmentService, RecordService recordService, MedicationService medicationService, DoctorService doctorService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.recordService = recordService;
        this.medicationService = medicationService;
        this.doctorService = doctorService;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        String username = "";
        try {
            //Login pidiendo username y contraseña y si no es correcto que vuelva a pedirlo, llamar al servicio doLogin(username, password) para comprobarlo
            System.out.println("Enter Username: ");
            username = sc.nextLine();
            System.out.println("Enter Password: ");
            String contrasena = sc.nextLine();
            while (!patientService.checkLogin(new Credential(username, contrasena))) {
                System.out.println("Username or password not valid, try again.");
                System.out.println("Enter Username: ");
                username = sc.nextLine();
                System.out.println("Enter Password: ");
                contrasena = sc.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Error while doing login, exiting...");
            return;
        }
        int option = 0;
        if (username.equals("root")) {
            adminMenu(sc, option);
        } else {
            Either<String, Boolean> isPatient = patientService.isPatient(username);
            if (isPatient.isRight() && (isPatient.get().equals(false)))
                doctorMenu(sc, option, username);
            if (isPatient.isLeft())
                System.out.println(isPatient.getLeft());
            else
                System.out.println("Patient UI not implemented yet");
        }

        System.out.println(Constantes.GOODBYE);
    }

    private void adminMenu(Scanner sc, int option) {
        while (option != 16) {
            try {
                System.out.println(Constantes.MENU_ADMIN);
                System.out.println(Constantes.CHOOSE_ECERCISE_NUMBER_ADMIN);
                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1:
                        exercise1();
                        break;
                    case 2:
                        exercise2();
                        break;
                    case 3:
                        exercise3(sc);
                        break;
                    case 4:
                        exercise4();
                        break;
                    case 5:
                        saveToXML();
                        break;
                    case 6:
                        exercise6(sc);
                        break;
                    case 7:
                        exercise7();
                        break;
                    case 8:
                        exercise8(sc);
                        break;
                    case 9:
                        exercise9(sc);
                        break;
                    case 10:
                        exercise10();
                        break;
                    case 11:
                        exercise11(sc);
                        break;
                    case 12:
                        exercise12(sc);
                        break;
                    case 13:
                        exercise13(sc);
                        break;
                    case 14:
                        exercise14();
                        break;
                    case 15:
                        exercise15();
                        break;
                    default:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("You have entered a wrong input, showing the menu again.");
                sc.next();
                option = 0;
            } catch (Exception e) {
                System.out.println(Constantes.UNEXPECTED_ERROR_SHOWINH_MENU_AGAIN);
                option = 0;
            }
        }
    }

    private void doctorMenu(Scanner sc, int option, String username) {
        while (option != 6) {
            try {
                System.out.println(Constantes.MENU_DOCTOR);
                System.out.println(Constantes.CHOOSE_ECERCISE_NUMBER_DOCTOR);
                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1:
                        exerciseDoctor1(username);
                        break;
                    case 2:
                        exerciseDoctor2(sc, username);
                        break;
                    case 3:
                        exerciseDoctor3(sc);
                        break;
                    case 4:
                        exerciseDoctor4(sc);
                        break;
                    case 5:
                        exerciseDoctor5(sc);
                        break;
                    default:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("You have entered a wrong input, showing the menu again.");
                sc.next();
                option = 0;
            } catch (Exception e) {
                System.out.println(Constantes.UNEXPECTED_ERROR_SHOWINH_MENU_AGAIN);
                option = 0;
            }
        }
    }


    // Show all patients
    private void exercise1() {
        Either<String, List<Patient>> patients = patientService.getAll();
        if (patients.isLeft()) {
            System.out.println(patients.getLeft());
        } else {
            System.out.println(patients.get());
        }
    }

    //Show medical records by patient
    private void exercise2() {
        System.out.println("Enter the Patient's ID: ");
        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println(patientService.getRecords(id));
    }

    //Append a new medical record with two medications: Make sure that the patient and the doctor exist
    private void exercise3(Scanner sc) {
        Record record = null;

        //check for patientID and doctorID existence
        boolean bothExist = false;
        while (!bothExist) {
            record = requestRecord(sc);
            if (patientService.get(record.getPatientID()).isLeft() ||
                    doctorService.get(record.getDoctorID()).isLeft()) {
                System.out.println("The patient or the doctor doesn't exist, please enter valid ID's");
            } else {
                bothExist = true;
            }
        }

        System.out.println("\n***************************************\nEntering the medications for the record:");
        PrescribedMedication medication1 = requestMedication(sc, 1);
        PrescribedMedication medication2 = requestMedication(sc, 2);

        int result = recordService.save(record, medication1, medication2);

        if (result == -1) {
            System.out.println("Error while saving the record");
        } else if (result == -2) {
            System.out.println("Error while saving the medications");

        } else {
            System.out.println("Record and Medications saved correctly");
        }
    }

    //Delete a patient: If it has any medications associated with one of their medical records, ask
    //the user, and if so, delete everything related to the patient before deleting the patient.
    private void exercise4() {
        System.out.println("Enter the Patient's ID: ");
        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt();
        sc.nextLine();

        //check if the patient has any medications associated with one of their medical records
        if (recordService.hasMedications(id, medicationService)) {
            System.out.println("The patient has medications associated with " +
                    "one of their medical records, do you want to delete everything related " +
                    "to the patient before deleting the patient? (Y/N)");
            String answer = sc.nextLine();
            if (!answer.equalsIgnoreCase("Y")) {
                System.out.println("The patient wasn't deleted, exiting...");
            } else {
                //delete the patient, medications and records
                if (medicationService.deleteByPatient(id) == -1
                        || recordService.deleteByPatient(id) == -1
                        || patientService.delete(id, true) == -1) {
                    System.out.println("Error while deleting the records, medications or patient");
                } else {
                    System.out.println("Patient deleted correctly");
                }
            }
        } else {
            //delete the patient
            if (patientService.delete(id, false ) == -1) {
                System.out.println("Error while deleting the patient");
            } else {
                System.out.println("Patient deleted correctly");
            }
        }
    }

    //Get information about the medications of a given patient
    private void exercise6(Scanner sc) {
        System.out.println("Enter the Patient's ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println(recordService.medicationsFromAPatientXML(id));
    }

    //Get the patients that are medicated with Amoxicilina
    private void exercise7() {
        System.out.println("Patients medicated with Amoxicilina: ");
        System.out.println(medicationService.getPatientsMedicatedWith("Amoxicilina"));
    }

    //Append a new medical order to a given patient in the xml, the patientID diagnosis and doctor name will be asked
    private void exercise8(Scanner sc) {
        System.out.println("Enter the Patient's ID: ");
        int patientID = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the diagnosis: ");
        String diagnosis = sc.nextLine();
        System.out.println("Enter the Doctor's name: ");
        String doctorName = sc.nextLine();
        if (recordService.appendRecordXML(patientID, diagnosis, doctorName) == -1) {
            System.out.println("Error while saving the record");
        } else {
            System.out.println("Record and Medication saved correctly");
        }
    }

    //delete a patient from the xml
    private void exercise9(Scanner sc) {
        System.out.println("Enter the Patient's ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (recordService.deletePatientXML(id) == -1) {
            System.out.println("Error while deleting the patient");
        } else {
            System.out.println("Patient deleted correctly");
        }
    }

    //Show the information of all patients, including the total amount paid
    private void exercise10() {
        try {
            System.out.println("Showing all patients and the total amount paid: ");
            Either<String, List<Patient>> patients = patientService.getAllPatientsWithTotalAmmountPaid();
            if (patients.isLeft()) {
                System.out.println(patients.getLeft());
                return;
            }
            System.out.println(patients.get());
        } catch (Exception e) {
            System.out.println("Error while showing the patients");
        }
    }

    //Show medical records by patient and, when clicking on one, show all the prescribed medication
    private void exercise11(Scanner sc) {
        System.out.println("Enter the Patient's ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        Either<String, List<Record>> records = recordService.getRecords(id);
        if (records.isLeft()) {
            System.out.println(records.getLeft());
        } else {
            if (records.get().isEmpty()) {
                System.out.println("No records found for the patient");
            } else {
                System.out.println("Records found for the patient: " + records.get() + "\nSelect a record id to see the prescribed medications:");
                int recordID = sc.nextInt();
                sc.nextLine();
                System.out.println("Medications found for the record: " + recordService.medicationsFromARecordId(recordID));
                System.out.println("\nEnd of medications for the record, press enter when you are ready to continue");
                sc.nextLine();
            }
        }
    }

    //Append a new medical record with two medicines: Make sure that the patient and the medication exist
    private void exercise12(Scanner sc) {
        Record record = null;

        //check for patientID and doctorID existence
        boolean bothExist = false;
        while (!bothExist) {
            record = requestRecord(sc);
            if (patientService.get(record.getPatientID()).isLeft() ||
                    doctorService.get(record.getDoctorID()).isLeft()) {
                System.out.println("The patient or the doctor doesn't exist, please enter valid ID's");
            } else {
                bothExist = true;
            }
        }

        System.out.println("\n***************************************\nEntering the medications for the record:");
        PrescribedMedication medication1 = requestMedication(sc, 1);
        PrescribedMedication medication2 = requestMedication(sc, 2);

        int result = recordService.save(record, medication1, medication2);

        if (result == -1) {
            System.out.println("Error while saving the record");
        } else if (result == -2) {
            System.out.println("Error while saving the medications");
        } else {
            System.out.println("Record and Medications saved correctly");
        }
    }

    //Delete a patient: If it has any medication, ask the user first, and if so, delete the patient with all their data
    private void exercise13(Scanner sc) {
        System.out.println("Enter the Patient's ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        //check if the patient has any medications
        if (recordService.hasMedications(id, medicationService)) {
            System.out.println("The patient has medications, do you want to delete the patient with all their data? (Y/N)");
            String answer = sc.nextLine();
            if (!answer.equalsIgnoreCase("Y")) {
                System.out.println("The patient wasn't deleted, exiting...");
            } else {
                //delete the patient, medications and records
                if (patientService.delete(id, true) == -1) {
                    System.out.println("Error while deleting the records, medications or patient");
                } else {
                    System.out.println("Patient deleted correctly");
                }
            }
        } else {
            //delete the patient
            if (patientService.delete(id, false) == -1) {
                System.out.println("Error while deleting the patient");
            } else {
                System.out.println("Patient deleted correctly");
            }
        }
    }

    //Find the patient with the most medical records
    private void exercise14() {
        Either<String, Patient> patient = recordService.getPatientWithMostRecords();
        if (patient.isLeft()) {
            System.out.println(patient.getLeft());
        } else {
            System.out.println(patient.get());
        }
    }

    //Find the date with more patients
    private void exercise15() {
        System.out.println("The date with more patients is: ");
        Either<String, LocalDateTime> date = appointmentService.getDateWithMorePatients();
        if (date.isLeft()) {
            System.out.println(date.getLeft());
        } else {
            System.out.println(date.get().toLocalDate());
        }
    }


    private Record requestRecord(Scanner sc) {
        System.out.println("Enter the Patient's ID: ");
        int patientID = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the Doctor's ID: ");
        int doctorID = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the diagnosis: ");
        String diagnosis = sc.nextLine();
        return new Record(recordService.getNewRecordID(), patientID, diagnosis, doctorID);
    }

    private Record requestRecord(Scanner sc, int doctorID) {
        System.out.println("Enter the Patient's ID: ");
        int patientID = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the diagnosis: ");
        String diagnosis = sc.nextLine();
        return new Record(recordService.getNewRecordID(), patientID, diagnosis, doctorID);
    }

    private PrescribedMedication requestMedication(Scanner sc, int number) {
        int medicationID1 = medicationService.getNewMedicationID();
        System.out.println("Enter the nº" + number + " medication name: ");
        String medicationName1 = sc.nextLine();
        System.out.println("Enter the nº" + number + " medication dosage: ");
        String medicationDosage1 = sc.nextLine();

        return new PrescribedMedication(medicationID1, medicationName1, medicationDosage1, 0);
    }

    private void saveToXML() {
        System.out.println("Saving to XML");
        if (recordService.saveToXML(recordService.getAll().get(), medicationService.getAll().get(), patientService.getAll().get(), doctorService.getAll().get())
                == -1) {
            System.out.println("Error while saving to XML");
        } else {
            System.out.println("Saved to XML correctly");
        }
    }


    //Show all medical records asignated to this doctor
    private void exerciseDoctor1(String username) {
        recordService.getRecordsFromDoctorUsername(username);
        Either<String, List<Record>> records = recordService.getRecordsFromDoctorUsername(username);
        if (records.isLeft()) {
            System.out.println(records.getLeft());
        } else {
            System.out.println(records.get());
        }
    }

    //Add a new medical record asignated to this doctor
    private void exerciseDoctor2(Scanner sc, String username) {
        int doctorID = doctorService.getDoctorIDFromUsername(username);
        if (doctorID == -1) {
            System.out.println("Error while getting the doctorID");
            return;
        }
        Record record = null;
        //check for patientID and doctorID existence
        boolean bothExist = false;
        while (!bothExist) {
            record = requestRecord(sc, doctorID);
            if (patientService.get(record.getPatientID()).isLeft()
            || doctorService.get(record.getDoctorID()).isLeft()) {
                System.out.println("The patient or doctor doesn't exist, please enter valid ID's" +doctorID);
            } else {
                bothExist = true;
            }
        }

        if (recordService.save(record) == -1) {
            System.out.println("Error while saving the record");
        }  else {
            System.out.println("Record and Medications saved correctly");
        }
    }

    //Modify a medical record asignated to this doctor
    private void exerciseDoctor3(Scanner sc) {
        System.out.println("Enter the Record's ID: ");
        int recordID = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the new diagnosis: ");
        String diagnosis = sc.nextLine();
        Record record = new Record(recordID, 0, diagnosis, 0);
        if (recordService.modify(record) == -1) {
            System.out.println("Error while modifying the record");
        } else {
            System.out.println("Record modified correctly");
        }
    }

    //Add a new medication assigned to this doctor
    private void exerciseDoctor4(Scanner sc) {
        System.out.println("Enter the Record's ID: ");
        int recordID = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the medication name: ");
        String medicationName1 = sc.nextLine();
        System.out.println("Enter the medication dosage: ");
        String medicationDosage1 = sc.nextLine();

        PrescribedMedication medication = new PrescribedMedication(medicationName1, medicationDosage1, recordID);
        int result = medicationService.save(medication);



        if (result == -1) {
            System.out.println("Error while saving the record");
        } else if (result == -2) {
            System.out.println("Error while saving the medications");
        } else {
            System.out.println("Record and Medications saved correctly");
        }
    }

    //Modify a medication assigned to this doctor
    private void exerciseDoctor5(Scanner sc) {
        System.out.println("Enter the Medication's ID: ");
        int medicationID = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the new medication name: ");
        String medicationName = sc.nextLine();
        System.out.println("Enter the new medication dosage: ");
        String medicationDosage = sc.nextLine();
        PrescribedMedication medication = new PrescribedMedication(medicationID, medicationName, medicationDosage, 0);
        if (medicationService.modify(medication) == -1) {
            System.out.println("Error while modifying the medication");
        } else {
            System.out.println("Medication modified correctly");
        }
    }
}