package ru.otus;

import com.github.javafaker.Faker;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class SerializerDemo {
	public static void main(String[] args) {
		Serializer serializer = new Serializer();
		Gson gson = new Gson();

		int[] arrayOfInt = {1, 2, 3};
		serializer.toJson(arrayOfInt);

		Person[] personArray = new Person[10];
		Faker faker = new Faker();
		for (int i = 0; i < 10; i++) {
			personArray[i] = new Person(
					faker.funnyName().name(),
					faker.number().numberBetween(16, 75),
					faker.address().fullAddress(),
					faker.bool().bool()
			);
		}
		serializer.toJson(personArray);
		gson.fromJson("", int[].class);

		ArrayList<Person> personArrayList = new ArrayList<>(Arrays.asList(personArray));
		serializer.toJson(personArrayList);
	}
}
