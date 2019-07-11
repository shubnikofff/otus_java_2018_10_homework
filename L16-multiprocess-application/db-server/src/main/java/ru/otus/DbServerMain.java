package ru.otus;

import java.io.FileWriter;

public class DbServerMain {
	public static void main(String[] args) {
		try {
			FileWriter fw = new FileWriter("/home/alexey/db-server.log");
			fw.write("Hello from DB Server");
			fw.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
