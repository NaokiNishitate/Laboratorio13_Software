package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.domain.SpecialtyTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.mapper.SpecialtyMapper;
import com.tecsup.petclinic.services.SpecialtyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class SpecialtyController {

    private final SpecialtyService specialtyService;
    private final SpecialtyMapper specialtyMapper;

    public SpecialtyController(SpecialtyService specialtyService, SpecialtyMapper specialtyMapper) {
        this.specialtyService = specialtyService;
        this.specialtyMapper = specialtyMapper;
    }

    @GetMapping(value = "/specialties")
    public ResponseEntity<List<SpecialtyTO>> findAll() {
        List<Specialty> specialties = specialtyService.findAll();
        return ResponseEntity.ok(specialtyMapper.toSpecialtyTOList(specialties));
    }

    @GetMapping("/specialties/{id}")
    public ResponseEntity<SpecialtyTO> findById(@PathVariable Integer id) {
        try {
            Specialty specialty = specialtyService.findById(id);
            return ResponseEntity.ok(specialtyMapper.toSpecialtyTO(specialty));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/specialties")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SpecialtyTO> create(@RequestBody SpecialtyTO specialtyTO) {
        Specialty specialty = specialtyMapper.toSpecialty(specialtyTO);
        Specialty savedSpecialty = specialtyService.create(specialty);
        return ResponseEntity.status(HttpStatus.CREATED).body(specialtyMapper.toSpecialtyTO(savedSpecialty));
    }

    @PutMapping("/specialties/{id}")
    public ResponseEntity<SpecialtyTO> update(@RequestBody SpecialtyTO specialtyTO, @PathVariable Integer id) {
        try {
            Specialty specialty = specialtyService.findById(id);
            specialty.setName(specialtyTO.getName());
            Specialty updatedSpecialty = specialtyService.update(specialty);
            return ResponseEntity.ok(specialtyMapper.toSpecialtyTO(updatedSpecialty));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/specialties/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            specialtyService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
