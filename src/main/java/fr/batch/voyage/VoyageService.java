package fr.batch.voyage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VoyageService {

	@Value("${file.in}")
	private String fileIn;

	@Value("${file.out}")
	private String fileOut;

	protected static Logger logger = Logger.getLogger(VoyageService.class);

	public void execute() {
		logger.info("En cours de DEV...");
		logger.info("file.in = " + fileIn);
		logger.info("file.out = " + fileOut);
	}

}
