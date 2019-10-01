package com.example.demo.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.countrymodels.Country;
import com.example.demo.models.CalendarEvent;
import com.example.demo.models.CalendarInitEvent;
import com.example.demo.services.CalendarServiceImpl;

@RestController
@RequestMapping("/todo")
public class TodoController {

	@Autowired
	CalendarServiceImpl calendarService;

	@GetMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}

	@PostMapping("/events")
	public ResponseEntity<Object> addEvent(@Valid @NotNull @RequestBody CalendarInitEvent calEvent) throws IOException {
		return new ResponseEntity<>(calendarService.addEvent(calEvent), HttpStatus.CREATED);
	}

	@GetMapping("/events")
	public ResponseEntity<Object> viewAllEvents() throws IOException {
		List<CalendarEvent> eventList = calendarService.viewAllEvents();

		if (eventList.isEmpty()) {
			return new ResponseEntity<>("No Upcoming Events", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(eventList, HttpStatus.OK);
	}

	@GetMapping("/events/{id}")
	public ResponseEntity<Object> viewTaskById(@PathVariable("id") String id) throws IOException {
		return new ResponseEntity<>(calendarService.viewEventById(id), HttpStatus.OK);
	}

	@DeleteMapping("/events/{id}")
	public ResponseEntity<Object> deleteTask(@PathVariable("id") String id) throws IOException {
		calendarService.deleteEvent(id);
		return new ResponseEntity<>("Event deleted successfully", HttpStatus.OK);
	}

	@PutMapping("/events/{id}")
	public ResponseEntity<Object> updateTask(@PathVariable("id") String id,
			@Valid @NotNull @RequestBody CalendarInitEvent calEvent) throws IOException {
		return new ResponseEntity<>(calendarService.updateEvent(id, calEvent), HttpStatus.OK);
	}

	//@GetMapping(value = "/country", produces = { "application/json; charset=UTF-8" })
	@GetMapping("/countries")
	public ResponseEntity<Object> countryData() {
		return new ResponseEntity<>(calendarService.getCountries(), HttpStatus.OK);
	}

	@GetMapping("/logout")
	public RedirectView exit(HttpServletRequest request, HttpServletResponse response) {
		new SecurityContextLogoutHandler().logout(request, null, null);
		return new RedirectView("http://localhost:8081");
	}

}
