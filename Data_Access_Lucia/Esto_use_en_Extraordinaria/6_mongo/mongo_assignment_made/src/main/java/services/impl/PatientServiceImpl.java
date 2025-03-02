package services.impl;

import common.Constants;
import dao.hibernate.DaoCredential;
import dao.mongo.DaoPatient;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.error.AppError;
import model.hibernate.CredentialEntity;
import model.mongo.Patient;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class PatientServiceImpl implements services.PatientService {

    private final DaoPatient daoPatient;
    private final DaoCredential daoCredential;

    @Inject
    public PatientServiceImpl(DaoPatient daoPatient, DaoCredential daoCredential) {
        this.daoPatient = daoPatient;
        this.daoCredential = daoCredential;
    }

    @Override
    public Either<AppError, List<Patient>> getAllPatients() {
        return daoPatient.getAll();
    }

    @Override
    public Either<AppError, HashMap<Integer, ObjectId>> getAllPatientsIDs() {
        Either<AppError, List<Patient>> allPatients = daoPatient.getAll();

        if (allPatients.isRight()) {
            List<Patient> patients = allPatients.get();
            HashMap<Integer, ObjectId> patientIdsMap = new HashMap<>();

            IntStream.range(0, patients.size())
                    .forEach(i -> patientIdsMap.put(i + 1, patients.get(i).getPatientId()));

            return Either.right(patientIdsMap);
        } else {
            return Either.left(allPatients.getLeft());
        }
    }

    @Override
    public Either<AppError, Patient> getPatientById(ObjectId id) {
        return daoPatient.get(id);
    }

    @Override
    public Either<AppError, Integer> updatePatient(Patient patient) {
        Either<AppError, Integer> result;

        if (patient.getName() == null || patient.getName().isEmpty()) {
            result = Either.left(new AppError(Constants.INVALID_NAME_INPUT_ERROR));
        } else if (patient.getBirthDate() == null || patient.getBirthDate().isAfter(LocalDate.now())) {
            result = Either.left(new AppError(Constants.INVALID_DATE_INPUT_ERROR));
        } else if (patient.getPhone() == null || patient.getPhone().length() != 9 || !patient.getPhone().matches("\\d+")) {
            result = Either.left(new AppError(Constants.INVALID_PHONE_INPUT_ERROR));
        } else {
            result = daoPatient.update(patient);
        }
        return result;
    }

    //TODO: get DaoCredential from hibernate here and save the credential in the database while saving the patient in mongo

    @Override
    public Either<AppError, Integer> savePatient(Patient patient, CredentialEntity credentialEntity) {
        Either<AppError, Integer> result;
        if (patient.getName() == null || patient.getName().isEmpty()) {
            result = Either.left(new AppError(Constants.INVALID_NAME_INPUT_ERROR));
        } else if (patient.getBirthDate() == null || patient.getBirthDate().isAfter(LocalDate.now())) {
            result = Either.left(new AppError(Constants.INVALID_DATE_INPUT_ERROR));
        } else if (patient.getPhone() == null || patient.getPhone().length() != 9 || !patient.getPhone().matches("\\d+")) {
            result = Either.left(new AppError(Constants.INVALID_PHONE_INPUT_ERROR));
        } else {

            //save the credential in the database
            daoCredential.save(credentialEntity);
            result = daoPatient.save(patient);
        }
        return result;
    }

    @Override
    public Either<AppError, Integer> deletePatient(ObjectId patientId, Boolean confirmation) {
        return daoPatient.delete(patientId, confirmation);
    }
}
