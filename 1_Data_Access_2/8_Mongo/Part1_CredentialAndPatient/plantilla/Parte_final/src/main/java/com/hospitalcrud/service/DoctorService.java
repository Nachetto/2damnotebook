package com.hospitalcrud.service;

import com.hospitalcrud.dao.model.Doctor;
import com.hospitalcrud.dao.repository.DoctorDAO;
import com.hospitalcrud.domain.model.DoctorUI;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DoctorService {
    private static Map<Integer, ObjectId> idMapperList ;
    private final DoctorDAO dao;

    public DoctorService(DoctorDAO dao) {
        idMapperList= new ConcurrentHashMap<>();
        this.dao = dao;
    }

    public List<DoctorUI> getDoctors() {
        List<Doctor> doctors = dao.getAll();
        AtomicInteger i = new AtomicInteger();
        List<DoctorUI> doctorUIList = new ArrayList<>();
        doctors.forEach(p -> {
            int id = i.incrementAndGet();
            idMapperList.put(id, p.getId());
            doctorUIList.add(p.toDoctorUI(id));
        });
        return doctorUIList;
    }

    public ObjectId getDoctorObjectId(int doctorId) {
        System.out.printf(idMapperList.toString());
        ObjectId objectId = idMapperList.get(doctorId);
        if (objectId == null) {
            throw new IllegalArgumentException("Invalid doctor  ID");
        }
        return objectId;
    }

    public Doctor getDoctor(int doctorId) {
        return dao.get(getDoctorObjectId(doctorId));
    }

    public int getDoctorId(ObjectId doctorId) {
        for (Map.Entry<Integer, ObjectId> entry : idMapperList.entrySet()) {
            if (entry.getValue().equals(doctorId)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Doctor ID not found");
    }

    public int saveDoctorObject(ObjectId doctor) {
        idMapperList.put(idMapperList.size() + 1, doctor);
        return idMapperList.size();
    }
}