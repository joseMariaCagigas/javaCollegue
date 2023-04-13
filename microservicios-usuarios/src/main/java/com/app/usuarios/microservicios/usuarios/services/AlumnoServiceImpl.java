package com.app.usuarios.microservicios.usuarios.services;

import com.app.usuarios.microservicios.commons.models.entity.Alumno;
import com.app.usuarios.microservicios.usuarios.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoServiceImpl implements AlumnoService{

    @Autowired
    AlumnoRepository alumnoRepository;


    @Override
    @Transactional(readOnly = true)
    public Iterable<Alumno> findAll() {
        return alumnoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Alumno> findById(Long id) {
        return alumnoRepository.findById(id);
    }

    @Override
    @Transactional
    public Alumno save(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        alumnoRepository.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<Alumno>> findByNombreOrApellido(String value) {
        return alumnoRepository.findByNombreOrApellido(value);
    }
}