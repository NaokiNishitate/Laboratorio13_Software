package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.domain.OwnerTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.mapper.OwnerMapper;
import com.tecsup.petclinic.services.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class OwnerController {

    private final OwnerService ownerService;
    private final OwnerMapper ownerMapper;

    public OwnerController(OwnerService ownerService, OwnerMapper ownerMapper) {
        this.ownerService = ownerService;
        this.ownerMapper = ownerMapper;
    }

    @GetMapping(value = "/owners")
    public ResponseEntity<List<OwnerTO>> findAll() {
        List<Owner> owners = ownerService.findAll();
        return ResponseEntity.ok(ownerMapper.toOwnerTOList(owners));
    }

    @GetMapping("/owners/{id}")
    public ResponseEntity<OwnerTO> findById(@PathVariable Integer id) {
        try {
            Owner owner = ownerService.findById(id);
            return ResponseEntity.ok(ownerMapper.toOwnerTO(owner));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/owners")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OwnerTO> create(@RequestBody OwnerTO ownerTO) {
        Owner owner = ownerMapper.toOwner(ownerTO);
        Owner savedOwner = ownerService.create(owner);
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerMapper.toOwnerTO(savedOwner));
    }

    @PutMapping("/owners/{id}")
    public ResponseEntity<OwnerTO> update(@RequestBody OwnerTO ownerTO, @PathVariable Integer id) {
        try {
            Owner owner = ownerService.findById(id);
            owner.setFirstName(ownerTO.getFirstName());
            owner.setLastName(ownerTO.getLastName());
            owner.setAddress(ownerTO.getAddress());
            owner.setCity(ownerTO.getCity());
            owner.setTelephone(ownerTO.getTelephone());
            Owner updatedOwner = ownerService.update(owner);
            return ResponseEntity.ok(ownerMapper.toOwnerTO(updatedOwner));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/owners/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            ownerService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
