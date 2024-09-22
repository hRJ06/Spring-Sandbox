package com.Hindol.Week3HW.Service;

import com.Hindol.Week3HW.Entity.UserEntity;

public interface SessionService {
    public void generateNewSession(UserEntity userEntity, String token);
    public void validateSession(String token);
}
