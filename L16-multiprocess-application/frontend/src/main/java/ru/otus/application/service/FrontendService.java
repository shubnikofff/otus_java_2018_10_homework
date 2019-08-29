package ru.otus.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FrontendService {
	@Value("${id}")
	private String id;

	public String getId() {
		return id;
	}
}
