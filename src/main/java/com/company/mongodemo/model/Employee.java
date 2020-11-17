package com.company.mongodemo.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "employee")
public class Employee {

	public static final String SEQUENCE_NAME = "users_sequence";
	@Id
	private long id;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String firstName;
	
	private String lastName;
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String emailId;
	
	
	private Float salary;
	
}
