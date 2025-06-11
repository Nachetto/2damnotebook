package services.impl;

import dao.mongo.DaoPrescribedMedication;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.dto.PatientWithMedicationDTO;
import model.dto.PrescribedMedicationDTO;
import model.error.AppError;
import model.mongo.PrescribedMedication;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class PrescribedMedicationServiceImpl implements services.PrescribedMedicationService {

    private final DaoPrescribedMedication daoPrescribedMedication;

    @Inject
    public PrescribedMedicationServiceImpl(DaoPrescribedMedication daoPrescribedMedication) {
        this.daoPrescribedMedication = daoPrescribedMedication;
    }

    @Override
    public Either<AppError, PatientWithMedicationDTO> getMedicationFromSpecificPatient(ObjectId objectId) {
        Either<AppError, List<PrescribedMedication>> medication = daoPrescribedMedication.getAll(objectId);

        if (medication.isLeft()) {
            return Either.left(medication.getLeft());
        } else {
            List<PrescribedMedication> prescribedMedications = medication.get();
            List<PrescribedMedicationDTO> prescribedMedicationDTOList = new ArrayList<>();
            prescribedMedications.forEach(prescribedMedication -> prescribedMedicationDTOList.add(new PrescribedMedicationDTO(prescribedMedication.getName(), prescribedMedication.getDose())));
            return Either.right(new PatientWithMedicationDTO(objectId, prescribedMedicationDTOList));
        }
    }

    @Override
    public Either<AppError, Integer> save(PrescribedMedication medication) {
        return daoPrescribedMedication.save(medication);
    }

    @Override
    public Either<AppError, Integer> deleteLastMedicationFromLastMedicalRecord(ObjectId patientId) {
        return daoPrescribedMedication.delete(patientId);
    }

}
