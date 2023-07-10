package dev.dashaun.service.jsensor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;
import oshi.software.os.OperatingSystem;

import java.util.ArrayList;
import java.util.List;

@EnableMethodSecurity
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

@RestController
class SensorController {

	private static final Logger logger = LoggerFactory.getLogger(SensorController.class);

	static List<String> oshi = new ArrayList<>();

	@GetMapping("/")
	public String index() {
		SystemInfo si = new SystemInfo();

		HardwareAbstractionLayer hal = si.getHardware();
		OperatingSystem os = si.getOperatingSystem();

		logger.info("Checking Processor...");
		printProcessor(hal.getProcessor());

		logger.info("Checking Sensors...");
		printSensors(hal.getSensors());

		StringBuilder output = new StringBuilder();
		for (String line : oshi) {
			output.append(line);
			if (line != null && !line.endsWith("\n")) {
				output.append('\n');
			}
		}
		logger.info("Printing Operating System and Hardware Info:{}{}", '\n', output);
		return output.toString();
	}

	private static void printProcessor(CentralProcessor processor) {
		oshi.add(processor.toString());
	}

	private static void printSensors(Sensors sensors) {
		oshi.add("Sensors: " + sensors.toString());
	}

}
