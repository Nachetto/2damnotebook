package com.hospitalcrud.dao.repository.xmlFiles;

import com.hospitalcrud.common.config.Configuration;
import com.hospitalcrud.dao.model.MedRecord;
import com.hospitalcrud.dao.model.xml.MedRecordsXML;
import com.hospitalcrud.dao.repository.MedRecordDAO;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;


@Repository
@Log4j2
@Profile("xml")
public class MedRecordRepository implements MedRecordDAO {
    private final Configuration config;
    public MedRecordRepository() {
        this.config = Configuration.getInstance();
    }

    @Override
    public List<MedRecord> getAll() {
        return readXML();
    }

    @Override
    public List<MedRecord> get(int patientId) {
        return readXML().stream()
                .filter(m -> m.getIdPatient() == patientId)
                .toList();
    }

    @Override
    public int save(MedRecord m) {
        List<MedRecord> medRecords = readXML();
        int id = Integer.parseInt(config.getNextIdMedRecord());
        m.setId(id);
        medRecords.add(m);//lo añades al final con el ultimo id
        writeXML(medRecords);
        if (medRecords.stream().anyMatch(medRecord -> medRecord.getId() == id)) {
            config.setNextIdMedRecord(String.valueOf(id + 1));
        }else {
            return 0;
        }
        return 1;
    }

    @Override
    public void update(MedRecord m) {
        try {
            List<MedRecord> updatedMedRecords = readXML().stream()
                    .map(medRecord -> {
                        if (medRecord.getId() == m.getId()) {
                            return m;//si es el mismo, guardo el nuevo
                        }
                        return medRecord;
                    })
                    .toList();
            writeXML(updatedMedRecords);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error updating the medical record: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        if (!confirmation) {
            return false;
        }
        List<MedRecord> medRecords = readXML();
        medRecords.removeIf(m -> m.getId() == id);
        return writeXML(medRecords);
    }


    private List<MedRecord> readXML() {
        File file = new File(config.getPathMedicalRecords());
        if (!file.exists()) {
            throw new InternalServerErrorException("Error leyendo el archivo: " + file.getName());
        }
        try {
            // El contexto en el que le pasas el root element
            JAXBContext context = JAXBContext.newInstance(MedRecordsXML.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // Desde la raíz, te metes en el elemento que quieres
            return ((MedRecordsXML) unmarshaller.unmarshal(file)).getMedicalRecords();
        } catch (JAXBException e) {
            throw new InternalServerErrorException("Error while reading the xml, see more details:\n" +
                    e.getMessage());
        }
    }


    private boolean writeXML(List<MedRecord> records) {
        try {
            File file = new File(config.getPathMedicalRecords());
            JAXBContext jaxbContext = JAXBContext.newInstance(MedRecordsXML.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //Pasas el objeto que quieres escribir a root element
            MedRecordsXML medRecordsXML = new MedRecordsXML(records);
            marshaller.marshal(medRecordsXML, file);
            return true;
        } catch (JAXBException e) {
            throw new InternalServerErrorException("Error writing XML"+ e.getMessage());
        }
    }


}
