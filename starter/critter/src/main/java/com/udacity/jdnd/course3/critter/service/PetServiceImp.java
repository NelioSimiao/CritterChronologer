package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PetServiceImp implements  PetService{

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Pet savePet(Pet pet) {
        Pet savedPet = petRepository.save(pet);
        Customer customer = savedPet.getOwner();
        if (customer != null){
            customer.getPets().add(savedPet);
            customerRepository.save(customer);
        }
        return savedPet;
    }

    @Override
    public Pet getPetById(long petId) {
        return petRepository.findById(petId).orElseThrow(()-> new NoSuchElementException( "pet not be found"));
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public List<Pet> getPetsByOwnerId(long ownerId) {
        return petRepository.getPetsByOwnerId(ownerId);
    }
}
