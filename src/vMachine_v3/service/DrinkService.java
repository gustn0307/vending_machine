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

    public int sell(int id, int menuId) {
        return repository.sell(id, menuId);
    }

    public DrinkDto getById(int menuId) {
        return repository.findDrinkById(menuId);
    }

    public int insert(DrinkDto drinkDto) {
        return repository.insertMenu(drinkDto);
    }

    public DrinkDto findMenuById(int id) {
        return repository.findDrinkById(id);
    }

    public int update(DrinkDto updateMenu) {
        return repository.updateMenu(updateMenu);
    }

    public int delete(int deleteId) {
        return repository.deleteMenu(deleteId);
    }
}
