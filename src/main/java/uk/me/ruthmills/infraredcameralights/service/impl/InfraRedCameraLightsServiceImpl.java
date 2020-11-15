package uk.me.ruthmills.infraredcameralights.service.impl;

import static com.pi4j.io.gpio.PinState.LOW;
import static com.pi4j.io.gpio.RaspiPin.GPIO_00;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

import uk.me.ruthmills.infraredcameralights.service.InfraRedCameraLightsService;

@Service
public class InfraRedCameraLightsServiceImpl implements InfraRedCameraLightsService {

	@Value("${endpoint}")
	private String endpoint;

	private volatile GpioController gpio;
	private volatile GpioPinDigitalOutput redLed;

	@PostConstruct
	public void initialise() {
		gpio = GpioFactory.getInstance();

		redLed = gpio.provisionDigitalOutputPin(GPIO_00, "Red LED", LOW);
		redLed.setShutdownOptions(true, LOW);
	}

	@Override
	public void tick() {
		redLed.high();
		try {
			Thread.sleep(250);
		} catch (InterruptedException ex) {
		}
		redLed.low();
	}
}
