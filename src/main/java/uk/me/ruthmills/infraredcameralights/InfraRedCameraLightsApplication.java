package uk.me.ruthmills.infraredcameralights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InfraRedCameraLightsApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfraRedCameraLightsApplication.class, args);
	}
}
