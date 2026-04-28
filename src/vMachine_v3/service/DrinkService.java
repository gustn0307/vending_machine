package vMachine_v3.service;

import vMachine_v3.dto.DrinkDto;
import vMachine_v3.repository.Repository;

import java.util.List;

public class DrinkService {
    private final Repository repository;

    public DrinkService(Repository repository) {
        this.repository = repository;
    }

    public List<DrinkDto> getAll() {
        return repository.getAllDrink();
    }
}
