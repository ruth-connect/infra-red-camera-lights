package uk.me.ruthmills.infraredcameralights.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import uk.me.ruthmills.infraredcameralights.service.InfraRedCameraLightsService;

@RestController
public class InfraRedCameraLightsController {

	@Autowired
	private InfraRedCameraLightsService infraRedCameraLightsService;

	@PostMapping(value = "/on")
	@ResponseStatus(value = HttpStatus.OK)
	public void on() {
		infraRedCameraLightsService.on();
	}

	@PostMapping(value = "/off")
	@ResponseStatus(value = HttpStatus.OK)
	public void off() {
		infraRedCameraLightsService.off();
	}
}
