package com.udacity.jdnd.course3.critter.pet;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerService;
import com.udacity.jdnd.course3.critter.utils.IBaseAction;

@Service
public class PetService implements IBaseAction<Pet> {

    @Autowired
    private PetRepository petRepository;

    private final CustomerService customerService;

    public PetService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    @Override
    public Pet findById(long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet have " + id + "not found, please type other id!"));
    }

    @Override
    @Transactional
    public Pet save(Pet pet) {
        Pet entity = petRepository.save(pet);
        Customer customer = entity.getCustomer();
        Set<Pet> pets = customer.getPets();
        if (pets != null && !pets.contains(entity)) {
            pets.add(entity);
            customer.setPets(pets);
        }
        return entity;
    }

    public PetDTO mapperEntityToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }

    public Pet mapperDTOToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setCustomer(customerService.findById(petDTO.getOwnerId()));
        return pet;
    }

}
