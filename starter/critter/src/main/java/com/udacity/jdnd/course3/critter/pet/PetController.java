package com.udacity.jdnd.course3.critter.pet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerService;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        // throw new UnsupportedOperationException();
        Pet pet = petService.mapperDTOToEntity(petDTO);
        pet = petService.save(pet);
        return petService.mapperEntityToDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        // throw new UnsupportedOperationException();
        Pet pet = petService.findById(petId);
        return petService.mapperEntityToDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        // throw new UnsupportedOperationException();
        List<Pet> lstPet = petService.findAll();
        List<PetDTO> lstPetDTO = new ArrayList<>();
        for (Pet pet : lstPet) {
            lstPetDTO.add(petService.mapperEntityToDTO(pet));
        }
        return lstPetDTO;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        // throw new UnsupportedOperationException();
        Customer customer = customerService.findById(ownerId);
        Set<Pet> pets = customer.getPets();
        List<PetDTO> lstPetDTO = new ArrayList<>();
        for (Pet pet : pets) {
            lstPetDTO.add(petService.mapperEntityToDTO(pet));
        }
        return lstPetDTO;
    }
}
