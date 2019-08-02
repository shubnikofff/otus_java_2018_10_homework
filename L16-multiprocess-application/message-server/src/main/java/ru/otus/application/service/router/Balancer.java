package ru.otus.application.service.router;

import ru.otus.application.Application;

class Balancer {
	private String lastAddressee = Application.SECOND_DB_SERVER_ID;

	String getAddressee() {
		final String addressee = lastAddressee.equals(Application.FIRST_DB_SERVER_ID) ? Application.SECOND_DB_SERVER_ID : Application.FIRST_DB_SERVER_ID;
		lastAddressee = addressee;
		return addressee;
	}
}
