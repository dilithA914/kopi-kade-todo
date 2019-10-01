package com.example.demo.services;

import com.example.demo.countrymodels.Country;
import com.example.demo.countrymodels.CountryList;
import com.example.demo.models.CalendarEvent;
import com.example.demo.models.CalendarInitEvent;
import com.example.demo.security.OpenIdConnectFilter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService {

	/**
	 * The Constant APPLICATION_NAME.
	 */
	private static final String APPLICATION_NAME = "Todo App";

	/**
	 * The Constant CALENDAR_ID.
	 */
	private static final String CALENDAR_ID = "primary";

	/**
	 * View all events.
	 */
	@Override
	public List<CalendarEvent> viewAllEvents() throws IOException {
		// Build a new authorized API client service.

		GoogleCredential credential = new GoogleCredential().setAccessToken(OpenIdConnectFilter.getToken());

		Calendar service = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
				.setApplicationName(APPLICATION_NAME).build();

		// List upcoming events - sorted with starting time
		DateTime now = new DateTime(System.currentTimeMillis());
		Events events = service.events().list(CALENDAR_ID).setTimeMin(now).setOrderBy("startTime").setSingleEvents(true)
				.execute();

		List<Event> items = events.getItems();
		List<CalendarEvent> eventList = new ArrayList<>();

		/* map events to simpler models */
		for (Event event : items) {

			DateTime start = event.getStart().getDateTime();
			if (start == null) {
				start = event.getStart().getDate();
			}

			DateTime end = event.getEnd().getDateTime();
			if (end == null) {
				end = event.getEnd().getDate();
			}

			CalendarEvent calEvent = new CalendarEvent(event.getId(), event.getSummary(), event.getDescription(),
					start.toString(), end.toString());

			eventList.add(calEvent);
		}
		return eventList;
	}

	/**
	 * Adds the event.
	 */
	@Override
	public Event addEvent(CalendarInitEvent calInitEvent) throws IOException {

		GoogleCredential credential = new GoogleCredential().setAccessToken(OpenIdConnectFilter.getToken());

		Calendar service = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
				.setApplicationName(APPLICATION_NAME).build();

		Event event = new Event().setSummary(calInitEvent.getSummary()).setDescription(calInitEvent.getDescription());

		String timeZone = "+05:30";
		String strStartDateTime = calInitEvent.getStartDate() + "T" + calInitEvent.getStartTime() + timeZone;

		DateTime startDateTime = new DateTime(strStartDateTime);
		EventDateTime start = new EventDateTime().setDateTime(startDateTime);
		event.setStart(start);

		String strEndDateTime = calInitEvent.getEndDate() + "T" + calInitEvent.getEndTime() + timeZone;

		DateTime endDateTime = new DateTime(strEndDateTime);
		EventDateTime end = new EventDateTime().setDateTime(endDateTime);
		event.setEnd(end);

		EventReminder[] reminderOverrides = new EventReminder[] {
				new EventReminder().setMethod("email").setMinutes(24 * 60),
				new EventReminder().setMethod("popup").setMinutes(10), };
		Event.Reminders reminders = new Event.Reminders().setUseDefault(false)
				.setOverrides(Arrays.asList(reminderOverrides));
		event.setReminders(reminders);

		return service.events().insert(CALENDAR_ID, event).execute();

	}

	/**
	 * View event by id.
	 */
	@Override
	public CalendarEvent viewEventById(String eventId) throws IOException {

		GoogleCredential credential = new GoogleCredential().setAccessToken(OpenIdConnectFilter.getToken());

		Calendar service = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
				.setApplicationName(APPLICATION_NAME).build();

		Event event = service.events().get(CALENDAR_ID, eventId).execute();

		DateTime start = event.getStart().getDateTime();
		if (start == null) {
			start = event.getStart().getDate();
		}

		DateTime end = event.getEnd().getDateTime();
		if (end == null) {
			end = event.getEnd().getDate();
		}

		/* map to a simpler object for response object */
		return new CalendarEvent(event.getId(), event.getSummary(), event.getDescription(), start.toString(),
				end.toString());

	}

	/**
	 * Update event.
	 */
	@Override
	public Event updateEvent(String eventId, CalendarInitEvent calInitEvent) throws IOException {

		GoogleCredential credential = new GoogleCredential().setAccessToken(OpenIdConnectFilter.getToken());

		Calendar service = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
				.setApplicationName(APPLICATION_NAME).build();

		Event event = service.events().get(CALENDAR_ID, eventId).execute();

		event.setSummary(calInitEvent.getSummary()).setDescription(calInitEvent.getDescription());

		String timeZone = "+05:30";
		String strStartDateTime = calInitEvent.getStartDate() + "T" + calInitEvent.getStartTime() + timeZone;

		DateTime startDateTime = new DateTime(strStartDateTime);
		EventDateTime start = new EventDateTime().setDateTime(startDateTime);
		event.setStart(start);

		String strEndDateTime = calInitEvent.getEndDate() + "T" + calInitEvent.getEndTime() + timeZone;

		DateTime endDateTime = new DateTime(strEndDateTime);
		EventDateTime end = new EventDateTime().setDateTime(endDateTime);
		event.setEnd(end);

		EventReminder[] reminderOverrides = new EventReminder[] {
				new EventReminder().setMethod("email").setMinutes(24 * 60),
				new EventReminder().setMethod("popup").setMinutes(10), };
		Event.Reminders reminders = new Event.Reminders().setUseDefault(false)
				.setOverrides(Arrays.asList(reminderOverrides));
		event.setReminders(reminders);

		return service.events().update(CALENDAR_ID, eventId, event).execute();

	}

	/**
	 * Delete event.
	 * 
	 * @return
	 */
	@Override
	public void deleteEvent(String eventId) throws IOException {

		GoogleCredential credential = new GoogleCredential().setAccessToken(OpenIdConnectFilter.getToken());

		Calendar service = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
				.setApplicationName(APPLICATION_NAME).build();

		service.events().delete(CALENDAR_ID, eventId).execute();

	}

	@Override
	public Country[] getCountries() {
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		ResponseEntity<Country[]> responseEntity = restTemplate.getForEntity("https://restcountries.eu/rest/v2/all", Country[].class);
	    return responseEntity.getBody();
	}

}
