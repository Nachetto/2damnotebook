package org.example.ui;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.common.Constantes;
import org.example.domain.Credential;
import org.example.domain.Patient;
import org.example.domain.PrescribedMedication;
import org.example.service.MedicationService;
import org.example.service.PatientService;
import org.example.service.RecordService;
import org.example.domain.Record;

import java.util.*;

public class Main {
    private final PatientService patientService;

    private final RecordService recordService;
    private final MedicationService medicationService;

    @Inject
    public Main(PatientService patientService, RecordService recordService, MedicationService medicationService) {
        this.patientService = patientService;
        this.recordService = recordService;
        this.medicationService = medicationService;
    }

    public void run() {
        //Show all usernames an passwords
        patientService.getAll().get().stream().filter(p -> p.getCredential() != null).forEach(p -> System.out.println(p.getCredential()));
        Scanner sc = new Scanner(System.in);
        try {
            //Login pidiendo usuario y contraseña y si no es correcto que vuelva a pedirlo, llamar al servicio doLogin(username, password) para comprobarlo
            System.out.println("Introduce el usuario: ");
            String usuario = sc.nextLine();
            System.out.println("Introduce la contraseña: ");
            String contrasena = sc.nextLine();
            while (!patientService.checkLogin(new Credential(usuario, contrasena))) {
                System.out.println("Usuario o contraseña incorrectos, vuelva a introducirlos.");
                System.out.println("Introduce el usuario: ");
                usuario = sc.nextLine();
                System.out.println("Introduce la contraseña: ");
                contrasena = sc.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Error while doing login, exiting...");
            return;
        }


        int option = 0;
        while (option != 5) {
            try {
                System.out.println(Constantes.MENU);
                System.out.println(Constantes.QUIERE_VER_DEL_1_AL_14_15_PARA_SALIR);
                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1:
                        ejercicio1();
                        break;
                    case 2:
                        ejercicio2();
                        break;
                    case 3:
                        ejercicio3(sc);
                        break;
                    case 4:
                        ejercicio4();
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
        System.out.println(Constantes.GOODBYE);

    }

    // Show all patients
    private void ejercicio1() {
        Either<String, List<Patient>> patients = patientService.getAll();
        if (patients.isLeft()) {
            System.out.println(patients.getLeft());
        } else {
            System.out.println(patients.get());
        }
    }

    //Show medical records by patient
    private void ejercicio2() {
        System.out.println("Enter the Patient's ID: ");
        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println(patientService.getRecords(id));
    }

    //Append a new medical record with two medications: Make sure that the patient and the doctor exist
    private void ejercicio3(Scanner sc) {
        System.out.println("Enter the Patient's ID: ");
        int patientID = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the Doctor's ID: ");
        int doctorID = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the diagnosis: ");
        String diagnosis = sc.nextLine();
        System.out.println("Enter the first medication id: ");
        int medicationID1 = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the first medication name: ");
        String medicationName1 = sc.nextLine();
        System.out.println("Enter the first medication dosage: ");
        String medicationDosage1 = sc.nextLine();
        System.out.println("Enter the second medication id: ");
        int medicationID2 = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the second medication name: ");
        String medicationName2 = sc.nextLine();
        System.out.println("Enter the second medication dosage: ");
        String medicationDosage2 = sc.nextLine();
        //public PrescribedMedication(int medicationID, String name, String dosage, int recordID)
        if (medicationService.save(new PrescribedMedication(medicationID1, medicationName1, medicationDosage1, 0)) == -1 || medicationService.save(new PrescribedMedication(medicationID2, medicationName2, medicationDosage2, 0)) == -1) {
            System.out.println("Error while saving the medications");
        } else {
            //public Record(int recordID, int patientID, String diagnosis, int doctorID)
            if (recordService.save(new Record(0, patientID, diagnosis, doctorID)) == -1) {
                System.out.println("Error while saving the record");
            } else {
                System.out.println("Record and Medications saved correctly");
            }
        }
    }


    //Delete a patient: If it has any medications associated with one of their medical records, ask
    //the user, and if so, delete everything related to the patient before deleting the patient.
    private void ejercicio4() {
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
                //delete the patient medical records
                if (recordService.deleteByPatient(id) == -1 || medicationService.deleteByPatient(id) == -1 || patientService.delete(id) == -1) {
                    System.out.println("Error while deleting the records, medications or patient");
                } else {
                    System.out.println("Patient deleted correctly");
                }
            }
        }
    }
}

