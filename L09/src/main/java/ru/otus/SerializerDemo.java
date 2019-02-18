package ru.otus;

import com.github.javafaker.Faker;
import com.google.gson.Gson;

import javax.json.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SerializerDemo {
	public static void main(String[] args) {
		Serializer serializer = new Serializer();
		Gson gson = new Gson();

		int[] arrayOfInt = {1, 2, 3};
		System.out.println(gson.toJson(arrayOfInt));
//		System.out.println(serializer.toJson(arrayOfInt));

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
		System.out.println(gson.toJson(personArray));

//		serializer.toJson(personArray);
//		gson.fromJson("", int[].class);

		ArrayList<Person> personArrayList = new ArrayList<>(Arrays.asList(personArray));
		System.out.println(gson.toJson(personArrayList));
//		serializer.toJson(personArrayList);

		System.out.println("\n\n\n");
//		JsonObject jsonObject = Json.createObjectBuilder()
//				.add("firstName", "Duke")
//				.add("age", 28)
//				.add("streetAddress", "100 Internet Dr")
//				.add("phoneNumbers", Json.createArrayBuilder()
//						.add(Json.createObjectBuilder()
//								.add("type", "home")
//								.add("number", "222-222-2222")))
//				.build();
		JsonStructure jsonObject = Json.createArrayBuilder().add(1).add("qwerty").build();
		System.out.println(jsonObject);
	}
}
