package fr.batch.voyage;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	private static final String SPRING_CONFIG_FILE = "/spring/applicationContext.xml";

	/** LOGGER **/
	protected static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = null;
		try {
			context = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILE);
			VoyageService vs = (VoyageService) context.getBean("VoyageService");
			vs.execute();
		} catch (Exception e) {
			logger.error("Erreur traitement batch", e);
			return;
		} finally {
			if (context != null) {
				context.close();
			}
		}
	}

}
