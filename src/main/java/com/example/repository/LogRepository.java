package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Log;

public interface LogRepository extends JpaRepository<Log, Integer> {
//	Optional<Log> findTopByLibrary_idAndUser_idOrderByRent_dateDesc(Integer library_id, Integer user_id);
}
