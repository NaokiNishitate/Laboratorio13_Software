package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.domain.VetTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.mapper.VetMapper;
import com.tecsup.petclinic.services.VetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class VetController {

    private final VetService vetService;
    private final VetMapper vetMapper;

    public VetController(VetService vetService, VetMapper vetMapper) {
        this.vetService = vetService;
        this.vetMapper = vetMapper;
    }

    @GetMapping(value = "/vets")
    public ResponseEntity<List<VetTO>> findAll() {
        List<Vet> vets = vetService.findAll();
        return ResponseEntity.ok(vetMapper.toVetTOList(vets));
    }

    @GetMapping("/vets/{id}")
    public ResponseEntity<VetTO> findById(@PathVariable Integer id) {
        try {
            Vet vet = vetService.findById(id);
            return ResponseEntity.ok(vetMapper.toVetTO(vet));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/vets")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VetTO> create(@RequestBody VetTO vetTO) {
        Vet vet = vetMapper.toVet(vetTO);
        Vet savedVet = vetService.create(vet);
        return ResponseEntity.status(HttpStatus.CREATED).body(vetMapper.toVetTO(savedVet));
    }

    @PutMapping("/vets/{id}")
    public ResponseEntity<VetTO> update(@RequestBody VetTO vetTO, @PathVariable Integer id) {
        try {
            Vet vet = vetService.findById(id);
            vet.setFirstName(vetTO.getFirstName());
            vet.setLastName(vetTO.getLastName());
            Vet updatedVet = vetService.update(vet);
            return ResponseEntity.ok(vetMapper.toVetTO(updatedVet));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/vets/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            vetService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
