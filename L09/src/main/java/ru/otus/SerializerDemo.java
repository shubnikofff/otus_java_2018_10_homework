package ru.otus;

import com.github.javafaker.Faker;
import com.google.gson.Gson;

import javax.json.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SerializerDemo {
	public static void main(String[] args) throws IllegalAccessException {
		Serializer serializer = new Serializer();
//		Gson gson = new Gson();
//
		int[] arrayOfInt = {1, 2, 3};
		boolean[] arrayOfBool = {true, false, true};
//		System.out.println(gson.toJson(arrayOfInt));
//


		Person[] arrayOfPerson = new Person[10];
		Faker faker = new Faker();
		for (int i = 0; i < 10; i++) {
			arrayOfPerson[i] = new Person(
					faker.funnyName().name(),
					faker.number().numberBetween(16, 75),
					faker.address().fullAddress(),
					faker.bool().bool()
			);
		}

		ArrayList<Person> personArrayList = new ArrayList<>(Arrays.asList(arrayOfPerson));

		System.out.println(serializer.toJson(arrayOfInt));
		System.out.println(serializer.toJson(arrayOfBool));
		System.out.println(serializer.toJson(arrayOfPerson));
		System.out.println(serializer.toJson(personArrayList));

//		System.out.println(Json.createObjectBuilder(stringObjectHashMap).build());
	}
}
