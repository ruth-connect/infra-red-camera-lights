package uk.me.ruthmills.infraredcameralights.polling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import uk.me.ruthmills.infraredcameralights.service.InfraRedCameraLightsService;

@Component
public class InfraRedCameraLightsPoller {

	private final Logger logger = LoggerFactory.getLogger(InfraRedCameraLightsPoller.class);

	@Autowired
	private InfraRedCameraLightsService infraRedCameraLightsService;

	@Scheduled(cron = "*/4 * * * * *")
	public void tick() {
		try {
			infraRedCameraLightsService.tick();
		} catch (Exception ex) {
			logger.error("Exception in poller thread", ex);
		}
	}
}
