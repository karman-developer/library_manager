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
import com.example.repository.LibraryRepository;
import com.example.repository.LogRepository;

@Service
public class LibraryService {
	private final LibraryRepository libraryRepository;
	private final LogRepository logRepository;

	@Autowired
	public LibraryService(LibraryRepository libraryRepository, LogRepository logRepository) {
		this.libraryRepository = libraryRepository;
		this.logRepository = logRepository;
	}

	public List<Library> findAll() {
		return this.libraryRepository.findAll();
	}

	public Optional<Library> findId(Integer id) {
		return this.libraryRepository.findById(id);
	}

	public void borrowBook(Integer id, Integer loginUserId, String returnDueDate) {
		Optional<Library> libraryId = this.findId(id);
		if (libraryId.isPresent()) {
			Library library = libraryId.get();
			library.setUser_id(loginUserId);
			this.libraryRepository.save(library);

			Log log = new Log();
			log.setLibrary_id(id);
			log.setUser_id(loginUserId);
			log.setRent_date(new Date());

			LocalDate localDate = LocalDate.parse(returnDueDate);
			LocalDateTime localDateTime = localDate.atStartOfDay();
			Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
			log.setReturn_due_date(date);

			log.setReturn_date(null);
			this.logRepository.save(log);
		}
	}

//	public void returnBook(Integer id, Integer loginUserId) {
//		Optional<Library> libraryId = this.findId(id);
//		if (libraryId.isPresent()) {
//			Library library = libraryId.get();
//			library.setUser_id(0);
//			this.libraryRepository.save(library);
//
//			Optional<Log> logOpt = this.logRepository.findTopByLibrary_idAndUser_idOrderByRent_dateDesc(id,
//					loginUserId);
//			logOpt.ifPresent(log -> {
//				log.setReturn_date(new Date());
//				this.logRepository.save(log);
//			});
//		}
//	}
}
