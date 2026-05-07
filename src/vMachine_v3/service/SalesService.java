package vMachine_v3.service;

import vMachine_v3.dto.SalesDto;
import vMachine_v3.repository.Repository;

import java.util.List;

public class SalesService {
    private final Repository repository;

    public SalesService(Repository repository) {
        this.repository = repository;
    }

    public List<SalesDto> getByMember(int id) {
        return repository.findAllSalesByMember(id);
    }

    public List<SalesDto> getSummaryByMenu() {
        return repository.getSummaryByMenu();
    }

    public List<SalesDto> getSummaryByMember() {
        return repository.getSummaryByMember();
    }
}
