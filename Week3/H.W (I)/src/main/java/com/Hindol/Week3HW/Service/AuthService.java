package com.Hindol.Week3HW.Service;

import com.Hindol.Week3HW.DTO.LoginDTO;

public interface AuthService {
    public String generateToken(LoginDTO loginDTO);
}
