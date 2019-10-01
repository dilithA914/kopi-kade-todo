package com.example.demo;

import java.time.LocalDate;
import java.time.LocalTime;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.example.demo.models.Todo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WebTest {
	
	/*
	 *  	junit + webflux unit tests  : get/post
	 */
	
	@Autowired
	private WebTestClient webClient;
	
	@Test
	public void testViewTaskById() {		
		
		String testId = "5d77202f1ad58f1bc85229e3";		
		
		this.webClient.get().uri("/todo/viewTask/5d77202f1ad58f1bc85229e3")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.json(String.format("{\"id\":\"%s\"}",testId));
	}
	
	@Test
	public void testViewAllTodo() {
		this.webClient.get().uri("/todo/viewAllTasks")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$[0].id").isEqualTo("5d77202f1ad58f1bc85229e3")
			.jsonPath("$[1].id").isEqualTo("5d77204a1ad58f1bc85229e4");
	}
	
	@Test
	public void testCreateTask() throws JSONException {
		ObjectId id = ObjectId.get();
		String text = "testString";
		String task = "testTask";
		LocalDate date = LocalDate.parse("2019-09-14");
		LocalTime time = LocalTime.parse("11:11:11");
		Todo todo = new Todo(id,task,text,date,time);
		
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("task", "testttt");
		jsonBody.put("text", "textttt");
		jsonBody.put("date", "2019-09-14");
		jsonBody.put("time", "11:11:11");
		
		String abc = "{ \"date\": \"2019-09-10\", \"task\": \"testttttttttttttttttt\", \"text\": \"string\", \"time\": \"08:08:08\"}";
		
		this.webClient.post().uri("/todo/addTask")
			.accept(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromObject(abc))
			.exchange()
			.expectStatus().isOk();
			
	}

}
