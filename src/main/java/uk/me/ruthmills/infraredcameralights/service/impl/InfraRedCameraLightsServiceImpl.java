package uk.me.ruthmills.infraredcameralights.service.impl;

import static com.pi4j.io.gpio.PinState.HIGH;
import static com.pi4j.io.gpio.RaspiPin.GPIO_08;
import static com.pi4j.io.gpio.RaspiPin.GPIO_09;

import javax.annotation.PostConstruct;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

import uk.me.ruthmills.infraredcameralights.service.InfraRedCameraLightsService;

@Service
public class InfraRedCameraLightsServiceImpl implements InfraRedCameraLightsService {

	@Value("${endpoint}")
	private String endpoint;

	private volatile GpioController gpio;
	private volatile GpioPinDigitalOutput infraRedLeds;
	private volatile GpioPinDigitalOutput redLed;

	private RestTemplate restTemplate;
	private final Logger logger = LoggerFactory.getLogger(InfraRedCameraLightsServiceImpl.class);

	@PostConstruct
	public void initialise() {
		gpio = GpioFactory.getInstance();

		infraRedLeds = gpio.provisionDigitalOutputPin(GPIO_08, "Red LED", HIGH);
		infraRedLeds.setShutdownOptions(true, HIGH);

		redLed = gpio.provisionDigitalOutputPin(GPIO_09, "Amber LED", HIGH);
		redLed.setShutdownOptions(true, HIGH);

		restTemplate = new RestTemplate(getClientHttpRequestFactory());

		logger.info("About to send POST to " + endpoint);
		try {
			restTemplate.postForEntity(endpoint, new HttpEntity<String>(""), String.class);
		} catch (Exception ex) {
			logger.error("Failed sending initialise request", ex);
		}
	}

	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		int timeout = 9000;
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).setConnectionRequestTimeout(timeout)
				.setSocketTimeout(timeout).build();
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		return new HttpComponentsClientHttpRequestFactory(client);
	}

	@Override
	public void on() {
		infraRedLeds.low();
	}

	@Override
	public void off() {
		infraRedLeds.high();
	}

	@Override
	public void tick() {
		infraRedLeds.low();
		try {
			Thread.sleep(250);
		} catch (InterruptedException ex) {
		}
		infraRedLeds.high();
	}
}
