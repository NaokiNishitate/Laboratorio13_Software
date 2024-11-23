package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.exception.PetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
@Slf4j
public class PetServiceTest {

    @Autowired
    private PetService petService;

    /**
     *
     */
    @Test
    public void testFindPetById() {

        Integer ID = 1;
        String NAME = "Leo";
        Pet pet = null;

        try {
            pet = this.petService.findById(ID);
        } catch (PetNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("" + pet);
        assertEquals(NAME, pet.getName());

    }

    /**
     *
     */
    @Test
    public void testFindPetByName() {

        String FIND_NAME = "Leo";
        int SIZE_EXPECTED = 1;

        List<Pet> pets = this.petService.findByName(FIND_NAME);

        assertEquals(SIZE_EXPECTED, pets.size());
    }

    /**
     *
     */
    @Test
    public void testFindPetByTypeId() {

        int TYPE_ID = 5;
        int SIZE_EXPECTED = 2;

        List<Pet> pets = this.petService.findByTypeId(TYPE_ID);

        assertEquals(SIZE_EXPECTED, pets.size());
    }

    /**
     *
     */
    @Test
    public void testFindPetByOwnerId() {

        int OWNER_ID = 10;
        int SIZE_EXPECTED = 2;

        List<Pet> pets = this.petService.findByOwnerId(OWNER_ID);

        assertEquals(SIZE_EXPECTED, pets.size());

    }
}
