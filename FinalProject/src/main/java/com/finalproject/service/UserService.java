package com.finalproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.finalproject.dto.UserDTO;
import com.finalproject.mapper.UserMapper;

@Service
public class UserService {
    private UserMapper mapper;

    public UserService(UserMapper mapper) {
        this.mapper = mapper;
    }

    public UserDTO login(String id, String passwd) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("pass", passwd);
        return mapper.login(map);
    }

    public int insertUser(UserDTO userDTO) {
        return mapper.insertUser(userDTO);
    }

    public int updateUser(UserDTO userDTO) {
        return mapper.updateUser(userDTO);
    }

    public int deleteUser(String userId) {
        return mapper.deleteUser(userId);
    }

    public int updateUserRank(String userId, int rankNo) {
        return mapper.updateUserRank(userId, rankNo);
    }

    public List<UserDTO> getAllUsers() {
        return mapper.getAllUsers();
    }
}
