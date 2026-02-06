package dao;

import database.DBConnectionMgr;
import dto.UserDto;
import java.sql.*;

public class UserDao {
    private DBConnectionMgr pool;

    public UserDao() {
        pool = DBConnectionMgr.getInstance();
    }

    // 1. 회원가입 (비밀번호 암호화는 나중에 이 안에서 처리)
    public boolean insertUser(UserDto user) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            String sql = "INSERT INTO users (email, password, fuel_type, efficiency, current_mileage, profile_img) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword()); // 나중에 암호화 로직 적용 지점
            pstmt.setString(3, user.getFuelType());
            pstmt.setDouble(4, user.getEfficiency());
            pstmt.setInt(5, user.getCurrentMileage());
            pstmt.setString(6, user.getProfileImg());
            
            if (pstmt.executeUpdate() == 1) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }

 // 이메일 중복 체크 (가입 전 필수 확인 로직)
    public boolean checkEmail(String email) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;
        
        String sql = "SELECT email FROM users WHERE email = ?";
        
        try {
            con = pool.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                exists = true; // 이미 이메일이 존재함
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return exists;
    }
}