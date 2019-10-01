package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

public class CalendarInitEvent {

	@Getter
	@Setter
	private String summary;
	@Getter
	@Setter
	private String description;
	@Getter
	@Setter
	private String startDate;
	@Getter
	@Setter
	private String endDate;
	@Getter
	@Setter
	private String startTime;
	@Getter
	@Setter
	private String endTime;

	public CalendarInitEvent(String summary, String description, String startDate, String endDate, String startTime,
			String endTime) {

		this.summary = summary;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;

	}

}
