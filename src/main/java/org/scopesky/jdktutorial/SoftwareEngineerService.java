package org.scopesky.jdktutorial;

import org.scopesky.jdktutorial.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareEngineerService {

    private final SoftwareEngineerRepository softwareEngineerRepository;

    public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository) {
        this.softwareEngineerRepository = softwareEngineerRepository;
    }
    public List<SoftwareEngineer> getAllSoftwareEngineers(){
        return softwareEngineerRepository.findAll();
    }
    //we first find the software engineer by id and then return it
    public SoftwareEngineer getSoftwareEngineerById(Integer id) {
        return softwareEngineerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Software engineer with id " + id + " not found"
                ));
    }
    public void saveSoftwareEngineer(SoftwareEngineer softwareEngineer){
        softwareEngineerRepository.save(softwareEngineer);
    }
    //we first find the software engineer by id and then delete it
    public void deleteSoftwareEngineer(Integer id) {
        SoftwareEngineer softwareEngineer = softwareEngineerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Software engineer with id " + id + " not found"
                ));
        softwareEngineerRepository.delete(softwareEngineer);
    }
}
