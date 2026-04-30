package vMachine_v3.service;

import vMachine_v3.dto.MemberDto;
import vMachine_v3.repository.Repository;

import java.util.List;

public class MemberService {
    private final Repository repository;

    public MemberService(Repository repository) {
        this.repository = repository;
    }

    public boolean register(MemberDto memberDto) {
        return repository.insert(memberDto);
    }

    public List<MemberDto> getAll() {
        return repository.findAll();
    }

    public MemberDto login(String userId, String password) {
        return repository.findMemberByUserId(userId, password);
    }

    public int update(MemberDto memberDto) {
        return repository.update(memberDto);
    }

    public MemberDto findById(int id) {
        return repository.findMemberById(id);
    }

    public int delete(int deleteId) {
        return repository.deleteMember(deleteId);
    }
}
