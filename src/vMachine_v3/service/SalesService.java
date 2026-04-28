package vMachine_v3.service;

import vMachine_v3.repository.Repository;

public class SalesService {
    private final Repository repository;

    public SalesService(Repository repository) {
        this.repository = repository;
    }
}
