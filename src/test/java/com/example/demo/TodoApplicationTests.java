package com.example.demo;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


// TODO: Auto-generated Javadoc
/**
 * The Class TodoApplicationTests.        // google authentication is not tested
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TodoApplicationTests {
	
	/** The random server port. */
	@LocalServerPort
	int randomServerPort;

	/**
	 * Test add task success.
	 *
	 * @throws URISyntaxException the URI syntax exception
	 */
	@Test
	public void testAddTaskSuccess() throws URISyntaxException{
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + randomServerPort + "/todo/addTask";
		URI uri = new URI(baseUrl);
		ObjectId testId = ObjectId.get();
		Todo todo = new Todo(testId, "testTask", "textDescription", LocalDate.parse("2019-09-14"), LocalTime.parse("11:11:11"));
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			HttpEntity<Todo> request = new HttpEntity<>(todo , headers);
			ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
			
			Assertions.assertEquals(201, result.getStatusCodeValue());
			Assertions.assertEquals(true, result.getBody().contains("testTask"));
		}
		catch (HttpClientErrorException ex) {
		}
		
	}
	
	/**
	 * Test add task bad request.
	 *
	 * @throws URISyntaxException the URI syntax exception
	 */
	@Test
	public void testAddTaskBadRequest() throws URISyntaxException{
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + randomServerPort + "/todo/addTask";
		URI uri = new URI(baseUrl);
		ObjectId testId = ObjectId.get();
		Todo todo = new Todo(testId, "testTask", "textDescription", LocalDate.parse("2019-09-14"), LocalTime.parse("11:11:11"));   
		
		//HttpHeaders headers = new HttpHeaders();
		
		try {
			HttpEntity<Todo> request = new HttpEntity<>(todo); 			//request without headers
			restTemplate.postForEntity(uri, request, String.class);
			
			Assertions.fail();
		}
		catch (HttpClientErrorException ex) {
			Assertions.assertEquals(400, ex.getRawStatusCode());
			Assertions.assertEquals(true, ex.getResponseBodyAsString().contains("Bad Request")  || ex.getResponseBodyAsString().contains("Bad Missing request header") );
		}
		
	}
	
	
	/**
	 * Test view all tasks success.
	 *
	 * @throws URISyntaxException the URI syntax exception
	 */
	@Test
	public void testViewAllTasksSuccess() throws URISyntaxException{
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + randomServerPort + "/todo/viewAllTasks";
		URI uri = new URI(baseUrl);
		
		//HttpHeaders headers = new HttpHeaders();
		
		try {
			//HttpEntity<Todo> request = new HttpEntity<>(null,headers); 			
			ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
			
			String testId = "5d77202f1ad58f1bc85229e3";
			Assertions.assertEquals(200, result.getStatusCodeValue());
		    Assertions.assertEquals(true, result.getBody().contains(testId));
		}
		catch (HttpClientErrorException ex) {			
		}		
	}
	
	/**
	 * Test view task by id success.
	 *
	 * @throws URISyntaxException the URI syntax exception
	 */
	@Test
	public void testViewTaskByIdSuccess() throws URISyntaxException{
		RestTemplate restTemplate = new RestTemplate();
		
		String testId = "5d77202f1ad58f1bc85229e3";
		final String baseUrl = "http://localhost:" + randomServerPort + "/todo/viewTaskById/"+testId;
		URI uri = new URI(baseUrl);
		
		//HttpHeaders headers = new HttpHeaders();
		
		try {
			//HttpEntity<Todo> request = new HttpEntity<>(null,headers); 			
			ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
			
			Assertions.assertEquals(200, result.getStatusCodeValue());
		    Assertions.assertEquals(true, result.getBody().contains(testId));
		}
		catch (HttpClientErrorException ex) {			
		}		
	}
	

	/*
	 * put and delete mapping to be completed
	 
	@Test
	public void testUpdateTaskSuccess() throws URISyntaxException{
		RestTemplate restTemplate = new RestTemplate();
		
		String testId = "5d77202f1ad58f1bc85229e3";
		final String baseUrl = "http://localhost:" + randomServerPort + "/todo/updateTask/"+testId;
		URI uri = new URI(baseUrl);
		ObjectId testObjectId= new ObjectId(testId);
		Todo todo = new Todo(testObjectId, "testTask", "textDescription", LocalDate.parse("2019-09-14"), LocalTime.parse("11:11:11"));   
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			HttpEntity<Todo> request = new HttpEntity<>(todo,headers); 			
			restTemplate.put(uri, request);;
			
			Assertions.assertEquals(200, result.getStatusCodeValue());
		    Assertions.assertEquals(true, result.getBody().contains(testId));
		}
		catch (HttpClientErrorException ex) {			
		}		
	}
	
	
	 *
	 */

}
