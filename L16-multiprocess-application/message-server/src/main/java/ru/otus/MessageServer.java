package ru.otus;

import ru.otus.application.service.ProcessRunner;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MessageServer {
	public static void main(String[] args) {
//		ProcessRunner processRunner = new ProcessRunner();
//		try {
//			processRunner.start("java -jar L16-multiprocess-application/frontend/target/frontend-1.0-SNAPSHOT.jar");
//			System.out.println(processRunner.getOutput());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		new MessageServer().start();
	}

	private void start() {
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		ProcessRunner processRunner = new ProcessRunner();
		startDbServer(executorService, processRunner);
		executorService.shutdown();
		System.out.println(processRunner.getOutput());
	}

	private void startDbServer(ScheduledExecutorService executorService, ProcessRunner processRunner) {
		executorService.schedule(() -> {
			try {
				processRunner.start("java -jar L16-multiprocess-application/frontend/target/frontend-1.0-SNAPSHOT.jar");
				System.out.println(processRunner.getOutput());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}, 5, TimeUnit.SECONDS);
	}
}
