package com.leveluplove.leveluplove.repository;

import com.leveluplove.leveluplove.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> { }
