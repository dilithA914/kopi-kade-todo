package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

public class CalendarEvent {

	@Getter
	@Setter
	private String id;
	@Getter
	@Setter
	private String summary;
	@Getter
	@Setter
	private String description;
	@Getter
	@Setter
	private String startDateTime;
	@Getter
	@Setter
	private String endDateTime;

	public CalendarEvent(String id, String summary, String description, String string, String string2) {
		this.id = id;
		this.summary = summary;
		this.description = description;
		this.startDateTime = string;
		this.endDateTime = string2;

	}

}
