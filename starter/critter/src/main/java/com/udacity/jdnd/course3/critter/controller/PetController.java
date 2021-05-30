package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petService.savePet(toPet(petDTO));
        return toPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return toPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<Pet> allPets = petService.getAllPets();
        return toPetDTOs(allPets);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> allPets = petService.getPetsByOwnerId(ownerId);
        return toPetDTOs(allPets);
    }


    private Pet toPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        if (petDTO.getOwnerId() != 0) {
            Customer customer= customerService.getCustomerById(petDTO.getOwnerId());
            pet.setOwner(customer);
        }
        return pet;
    }

    private PetDTO toPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getOwner() != null) {
            petDTO.setOwnerId(pet.getOwner().getId());
        }
        return petDTO;
    }


    private List<PetDTO> toPetDTOs(List<Pet> allPets) {
        List<PetDTO> petDTOS = new ArrayList<PetDTO>();
        for (Pet p : allPets) {
            PetDTO petDTO = toPetDTO(p);
            petDTOS.add(petDTO);
        }
        return petDTOS;
    }


}
