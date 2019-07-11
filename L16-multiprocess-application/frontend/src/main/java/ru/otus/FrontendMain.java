package ru.otus;

import java.io.FileWriter;

public class FrontendMain {
	public static void main(String[] args) {
		while (true) {
			try {
				FileWriter fw = new FileWriter("/home/alexey/frontend.log");
				fw.write("Hello from Frontend");
				fw.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
