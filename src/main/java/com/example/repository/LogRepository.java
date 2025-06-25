package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Library;
import com.example.entity.Log;
import com.example.entity.User;

public interface LogRepository extends JpaRepository<Log, Integer> {
	Optional<Log> findTopByLibraryAndUserAndReturnDateIsNullOrderByRentDateDesc(Library library, User user);
}
