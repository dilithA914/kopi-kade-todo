package com.example.demo.countrymodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Language {

	String iso639_1;
	String iso639_2;
	String name;
	String nativeName;

}
