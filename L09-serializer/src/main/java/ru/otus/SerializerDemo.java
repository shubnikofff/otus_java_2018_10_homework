package ru.otus;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SerializerDemo {
	public static void main(String[] args) throws IllegalAccessException {
		Serializer serializer = new Serializer();
		Gson gson = new Gson();

		int[] arrayOfInt = {1, 2, 3};
		int[] arrayOfIntFromJson = gson.fromJson(serializer.toJson(arrayOfInt), int[].class);

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
		Person[] arrayOfPersonFromJson = gson.fromJson(serializer.toJson(arrayOfPerson), Person[].class);

		List<Person> personArrayList = new ArrayList<>(Arrays.asList(arrayOfPerson));
		TypeToken<List<Person>> token = new TypeToken<>() {};
		List<Person> personArrayListFromJson = gson.fromJson(serializer.toJson(personArrayList), token.getType());

		Assert.assertArrayEquals(arrayOfInt, arrayOfIntFromJson);
		Assert.assertArrayEquals(arrayOfPerson, arrayOfPersonFromJson);
		Assert.assertEquals(personArrayList, personArrayListFromJson);

		System.out.println(serializer.toJson(null));
		System.out.println(serializer.toJson(List.of(1, 2, 3, 4)));
		System.out.println(serializer.toJson(arrayOfPerson[0]));
	}
}
