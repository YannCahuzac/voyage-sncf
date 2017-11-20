package fr.batch.voyage;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VoyageService {

	@Value("${file.in}")
	private String fileIn;

	@Value("${file.out}")
	private String fileOut;

	private final String newLineFile = "\r\n";
	private final String regexNb = "[0-9]+";

	protected static Logger logger = Logger.getLogger(VoyageService.class);

	/**
	 * Lecture et écriture dans les fichiers en entrée et sortie
	 */
	public void execute() {
		BufferedReader br = null;
		FileOutputStream fos = null;
		try {
			logger.info("file.in = " + fileIn);
			logger.info("file.out = " + fileOut);

			fos = new FileOutputStream(fileOut);
			br = new BufferedReader(new FileReader(fileIn));
			String line;
			while ((line = br.readLine()) != null) {
				logger.info(line);

				String opti = convertLine(line);

				fos.write(opti.getBytes());
				fos.write(newLineFile.getBytes());
			}

		} catch (Exception e) {
			logger.error("Une erreur est survenue: " + e.getMessage());
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				logger.error("Une erreur est survenue lors de la fermeture des streams in/out: " + e.getMessage());
			}
		}

	}

	/**
	 * Concersion de la ligne en entrée
	 */
	private String convertLine(String inLine) {

		String ret = "";

		if (!inLine.matches(regexNb)) {
			ret = "Format incorrect pour la ligne --> " + inLine;
		} else {
			List<Integer> packages = new ArrayList<Integer>();
			// On convertit tous les Strings en Integer
			for (int i = 0; i < inLine.length(); i++) {
				char nbC = inLine.charAt(i);
				if (String.valueOf(nbC) != null && !String.valueOf(nbC).equals("0")) {
					packages.add(new Integer(String.valueOf(nbC)));
				}
			}
			// On trie notre liste pour être sûr de prendre le premier plus grand par la suite:
			Collections.sort(packages, new IntegerCompare());
			ret = optimisation(packages);
		}

		return ret;
	}

	/**
	 * BOUCLE sur: 1) Prendre le plus grand élément X1 de la liste 2) Prendre le deuxième plus grand élément X2 de la liste de telle sorte que X1+X2<11 3) ...
	 */
	private String optimisation(List<Integer> packagesSorted) {

		String ret = "";
		StringBuffer sb = new StringBuffer("");

		Integer somme = 0;

		while (packagesSorted.size() > 0) {
			somme = 0;
			while (somme < 10 && packagesSorted.size() > 0) {
				// Le plus grand élément actuel additionné à un autre élément de la liste en cours, ne doit pas dépasser 10:
				Integer plusGrand = prendreLePlusGrandPossible(packagesSorted, 10 - somme);
				if (plusGrand != null) {
					somme += plusGrand;
					if (somme <= 10) {
						sb.append(plusGrand.toString());
					}
				} else {
					// Il n'existe plus de plus grand possible dans la liste packagesSorted, alors on kill
					somme = 10;
				}
			}
			if (packagesSorted.size() > 0) {
				sb.append(" / ");
			}
		}

		return sb.toString();
	}

	/**
	 * 
	 * @param packagesSorted
	 *            notre liste contenant les integer triés restant à "packager"
	 * @param tailleMax
	 *            la taille que ne doit pas dépasser le plus grand élément de la liste
	 */
	private Integer prendreLePlusGrandPossible(List<Integer> packagesSorted, int tailleMax) {
		Integer ret = null;
		boolean find = false;
		if (packagesSorted != null && packagesSorted.size() > 0) {
			Iterator<Integer> itInt = packagesSorted.iterator();
			while (itInt.hasNext() && !find) {
				Integer i = itInt.next();
				if (i != null && i <= tailleMax) {
					ret = i;
					find = true;
				}
			}
			if (find) {
				// On enlève l'élément qui n'a plus lieu d'être
				packagesSorted.remove(ret);
			}
		}
		return ret;
	}

}
