package com.example.demo.countrymodels;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {

	String name;
	List<String> topLevelDomain;
	String alpha2Code;
	String alpha3Code;
	List<String> callingCodes;
	String capital;
	List<String> altSpellings;
	String region;
	String subregion;
	int population;
	List<Integer> latlng;
	String demonym;
	double area;
	double gini;
	List<String> timezones;
	List<String> borders;
	String nativeName;
	String numericCode;
	List<Currency> currencies;
	List<Language> languages;
	Translation translations;
	String flag;
	List<RegionalBlocs> regionalBlocs;
	String cioc;

}
