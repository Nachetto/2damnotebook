package com.example.reyortiz_ejercicio3.domain;

import com.example.reyortiz_ejercicio3.data.EmpleadoRepository;
import com.example.reyortiz_ejercicio3.data.MovilRepository;
import com.example.reyortiz_ejercicio3.data.modelo.Empleado;
import com.example.reyortiz_ejercicio3.data.modelo.Movil;
import com.example.reyortiz_ejercicio3.domain.errores.DeleteException;
import com.example.reyortiz_ejercicio3.domain.errores.UpdateException;
import com.example.reyortiz_ejercicio3.domain.modelo.MovilResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovilService {
    private final MovilRepository repository;
    private final EmpleadoRepository empleadoRepository;

    public List<MovilResponse> getMoviles(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Empleado empleado = empleadoRepository.findByNombre(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Movil> moviles;
        List<MovilResponse> movilResponses = new ArrayList<>();
        if (empleado.getRol().equals("ADMIN")){
            moviles = repository.findAll();
        } else {
            moviles =  repository.findAllByEmpleado(empleado.getId());
        }
        for (Movil movil : moviles){
            Empleado owner = empleadoRepository.findById(movil.getEmpleado()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            movilResponses.add(new MovilResponse(movil.getId(), movil.getCapacidad(), movil.getModelo(), movil.getYear(), owner.getNombre()));
        }
        return movilResponses;
    }

    public Movil updateMovil(Movil movil){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Empleado empleado = empleadoRepository.findByNombre(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (empleado.getId() == movil.getEmpleado()){
            return repository.save(movil);
        } else {
            throw new UpdateException("Este no es tu móvil");
        }
    }

    public void deleteMovil(int id){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Empleado empleado = empleadoRepository.findByNombre(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Movil movil = repository.findById(id).orElseThrow(() -> new DeleteException("Movil not found"));

        if (empleado.getId() == movil.getEmpleado()){
            repository.deleteById(id);
        } else {
            throw new DeleteException("Este no es tu móvil");
        }

    }
}
