package dev.dashaun.service.jsensor;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.PlatformEnum;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * A demonstration of access to many of OSHI's capabilities
 */
public class SystemInfoTest {

	// NOSONAR squid:S5786

	private static final Logger logger = LoggerFactory.getLogger(SystemInfoTest.class);

	static List<String> oshi = new ArrayList<>();

	/**
	 * Test that this platform is implemented..
	 */
	@Test
	void testPlatformEnum() {
		assertThat("Unsupported OS", SystemInfo.getCurrentPlatform(), is(not(PlatformEnum.UNKNOWN)));
		// Exercise the main method
		main(null);
	}

	/**
	 * The main method, demonstrating use of classes.
	 * @param args the arguments (unused)
	 */
	public static void main(String[] args) {

		logger.info("Initializing System...");
		SystemInfo si = new SystemInfo();

		HardwareAbstractionLayer hal = si.getHardware();

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
	}

	private static void printProcessor(CentralProcessor processor) {
		oshi.add(processor.toString());
	}

	private static void printSensors(Sensors sensors) {
		oshi.add("Sensors: " + sensors.toString());
	}

}