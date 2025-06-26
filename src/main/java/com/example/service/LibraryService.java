package com.example.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Library;
import com.example.entity.Log;
import com.example.entity.User;
import com.example.repository.LibraryRepository;
import com.example.repository.LogRepository;
import com.example.repository.UserRepository;

@Service
public class LibraryService {
	private final LibraryRepository libraryRepository;
	private final LogRepository logRepository;
	private final UserRepository userRepository;

	@Autowired
	public LibraryService(LibraryRepository libraryRepository, LogRepository logRepository,
			UserRepository userRepository) {
		this.libraryRepository = libraryRepository;
		this.logRepository = logRepository;
		this.userRepository = userRepository;
	}

	public List<Library> findAll() {
		return this.libraryRepository.findAll();
	}

	public Optional<Library> findId(Integer id) {
		return this.libraryRepository.findById(id);
	}

	public Optional<User> userId(Integer id) {
		return this.userRepository.findById(id);
	}

	public void borrowBook(Integer id, Integer loginUserId, String returnDueDate) {
		Optional<Library> libraryId = this.findId(id);
		Optional<User> userId = this.userId(loginUserId);

		if (libraryId.isPresent()) {
			Library library = libraryId.get();
			User user = userId.get();
			library.setUser_id(loginUserId);
			this.libraryRepository.save(library);

			Log log = new Log();
			log.setLibrary(library);
			log.setUser(user);
			log.setRentDate(new Date());

			LocalDate localDate = LocalDate.parse(returnDueDate);
			LocalDateTime localDateTime = localDate.atStartOfDay();
			Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
			log.setReturnDueDate(date);

			log.setReturnDate(null);
			this.logRepository.save(log);
		}
	}

	public void returnBook(Integer id, Integer loginUserId) {
		Optional<Library> libraryId = this.findId(id);
		Optional<User> userId = this.userId(loginUserId);

		if (libraryId.isPresent() && userId.isPresent()) {
			Library library = libraryId.get();
			User user = userId.get();

			library.setUser_id(0);
			this.libraryRepository.save(library);

			Optional<Log> logOpt = this.logRepository.findTopByLibraryAndUserAndReturnDateIsNullOrderByRentDateDesc(library, user);
			logOpt.ifPresent(log -> {
				log.setReturnDate(new Date());
				this.logRepository.save(log);
			});
		}
	}

	public List<Log> history(Integer loginUserId) {
		return logRepository.findByUserId(loginUserId);
	}
}
