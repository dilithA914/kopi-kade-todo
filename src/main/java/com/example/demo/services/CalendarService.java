package com.example.demo.services;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.countrymodels.Country;
import com.example.demo.countrymodels.CountryList;
import com.example.demo.models.CalendarEvent;
import com.example.demo.models.CalendarInitEvent;
import com.google.api.services.calendar.model.Event;

public interface CalendarService {

	/**
	 * View all events upcoming.
	 *
	 * @return the response entity
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	List<CalendarEvent> viewAllEvents() throws IOException;

	/**
	 * Adds the event.
	 *
	 * @param calInitEvent - response body object
	 * @return the response entity - created event from calendar
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	Event addEvent(CalendarInitEvent calInitEvent) throws IOException;

	/**
	 * View event by id.
	 *
	 * @param eventId
	 * @return the response entity - includes the requested event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	CalendarEvent viewEventById(String eventId) throws IOException;

	/**
	 * Update event.
	 *
	 * @param eventId
	 * @param calInitEvent - new event content
	 * @return the response entity - updated event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	Event updateEvent(String eventId, CalendarInitEvent calInitEvent) throws IOException;

	/**
	 * Delete event.
	 *
	 * @param eventId
	 * @return the response entity - failed(403) or successfull(201)
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void deleteEvent(String eventId) throws IOException;
	
	Country[] getCountries();
}
