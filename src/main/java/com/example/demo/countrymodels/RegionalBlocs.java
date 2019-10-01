package com.example.demo.countrymodels;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionalBlocs {

	String acronym;
	String name;
	List<String> otherAcronyms;
	List<String> otherNames;

}
