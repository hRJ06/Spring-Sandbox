package com.Hindol.User_Service.Repository;

import com.Hindol.User_Service.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
