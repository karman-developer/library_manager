package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Library;
import com.example.entity.Log;
import com.example.service.LibraryService;
import com.example.service.LoginUser;

@Controller
@RequestMapping("library")
public class LibraryController {
	private final LibraryService libraryService;

	@Autowired
	public LibraryController(LibraryService libraryService) {
		this.libraryService = libraryService;
	}

	@GetMapping
	public String index(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		List<Library> libraries = this.libraryService.findAll();
		model.addAttribute("libraries", libraries);
		model.addAttribute("loginUserId", loginUser.getId());
		return "library/index";
	}

	@GetMapping("/borrow")
	public String borrowingForm(@RequestParam Integer id, Model model) {
		Optional<Library> library = this.libraryService.findId(id);
		model.addAttribute("library", library.get());
		return "library/borrowingForm";
	}

	@PostMapping("/borrow")
	public String borrow(@RequestParam Integer id,
			@RequestParam("return_due_date") String returnDueDate,
			@AuthenticationPrincipal LoginUser loginUser) {
		Integer loginUserId = loginUser.getId();
		this.libraryService.borrowBook(id, loginUserId, returnDueDate);
		return "redirect:/library";
	}

	@PostMapping("/return")
	public String returnBook(@RequestParam Integer id, @AuthenticationPrincipal LoginUser loginUser) {
		Integer loginUserId = loginUser.getId();
		this.libraryService.returnBook(id, loginUserId);
		return "redirect:/library";
	}
	
	@GetMapping("/history")
	public String history(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		Integer loginUserId = loginUser.getId();
		List<Log> history = libraryService.history(loginUserId);
		model.addAttribute("history", history);
		return "library/borrowHistory";
	}
}
