package vMachine_v3.repository;

import vMachine_v3.dto.DrinkDto;
import vMachine_v3.dto.MemberDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private final Connection conn;

    public Repository(Connection conn) {
        this.conn = conn;
    }

    public boolean insert(MemberDto memberDto) {
        PreparedStatement psmt = null;

        try {
            String sql = "INSERT INTO member (user_id, password, name, tel, card_num) values (?, ?, ?, ?, ?)";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, memberDto.getUserId());
            psmt.setString(2, memberDto.getPassword());
            psmt.setString(3, memberDto.getName());
            psmt.setString(4, memberDto.getTel());
            psmt.setString(5, memberDto.getCardNum());

            psmt.executeUpdate();
            psmt.close();
        } catch (Exception e) {
            System.out.println("insert() 오류: " + e.getMessage());
        }
        return true;
    }

    public List<MemberDto> findAll() {
        List<MemberDto> memberDtoList = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM member WHERE is_admin = 0";
            psmt = conn.prepareStatement(sql);

            rs = psmt.executeQuery();

            while (rs.next()) {
                MemberDto memberDto = new MemberDto(
                        rs.getInt("id"),
                        rs.getString("user_id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("tel"),
                        rs.getInt("balance"),
                        rs.getString("card_num")
                );
                memberDtoList.add(memberDto);
            }
            psmt.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("findAll() 오류: " + e.getMessage());
        }

        return memberDtoList;
    }

    public MemberDto findMember(String userId, String password) {
        MemberDto memberDto = new MemberDto();
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM member WHERE user_id = ? AND password = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, userId);
            psmt.setString(2, password);

            rs = psmt.executeQuery();

            while (rs.next()) {
                memberDto.setId(rs.getInt("id"));
                memberDto.setUserId(rs.getString("user_id"));
                memberDto.setPassword(rs.getString("password"));
                memberDto.setName(rs.getString("name"));
                memberDto.setTel(rs.getString("tel"));
                memberDto.setBalance(rs.getInt("balance"));
                memberDto.setCardNum(rs.getString("card_num"));
                memberDto.setAdmin(rs.getBoolean("is_admin"));
            }
        } catch (Exception e) {
            System.out.println("findMember() 오류: " + e.getMessage());
        }
        return memberDto;
    }

    public List<DrinkDto> getAllDrink() {
        List<DrinkDto> drinkDtoList = new ArrayList<>();

        return drinkDtoList;
    }
}
