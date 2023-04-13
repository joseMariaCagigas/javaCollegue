package com.app.usuarios.microservicios.usuarios.controllers;

import com.app.usuarios.microservicios.commons.models.entity.Alumno;
import com.app.usuarios.microservicios.usuarios.services.AlumnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Tag(name = "Alumno API", description = "This APi serve all functionality for management Alumnos")
@RestController
public class AlumnoController {

    @Autowired
    private AlumnoService service;

    @Operation(description = "Return all alumnos bundled into Response", summary ="Return 204 if no data found")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Exito: Listado de alumnos."),
            @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @Operation(description = "Return alumno by nombre or apellido bundled into Response")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Alumno"),
            @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/filtrar/{value}")
    public ResponseEntity<?> verPorNombreOApellido(@PathVariable String value){
        Optional<List<Alumno>> o = service.findByNombreOrApellido(value);
        if(o.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(o.get());
    }


    @Operation(description = "Return alumno by id bundled into Response")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Alumno"),
            @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id){
        Optional<Alumno> o = service.findById(id);
        if(o.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(o.get());
    }

    @Operation(description = "Return alumno updated bundled into Response")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Alumno"),
            @ApiResponse(responseCode = "500", description = "Internal error")})
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Alumno alumno){
        Alumno alumnoDB = service.save(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoDB);
    }

    @Operation(description = "Return alumno updated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Alumno updated"),
            @ApiResponse(responseCode = "500", description = "Internal error")})
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Alumno alumno, @PathVariable Long id){

        Optional<Alumno> o = service.findById(id);
        if(o.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Alumno alumnoDB = o.get().generarAlumno(alumno);
        Alumno alumnoFinal = service.save(alumnoDB);

        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoFinal);

    }

    @Operation(description = "Return code 204")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "Alumno deleted"),
            @ApiResponse(responseCode = "500", description = "Internal error")})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}