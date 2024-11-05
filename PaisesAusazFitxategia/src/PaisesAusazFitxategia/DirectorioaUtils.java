package PaisesAusazFitxategia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * The Class DirectorioaUtils.
 */
public class DirectorioaUtils {

	/**
	 * Eskatu direktorioa.
	 *
	 * @param sc, gure eskanerra
	 * @return gure direktorioa
	 */
	public static String EskatuDirektorioa(Scanner sc) {
		String helbideOsoaString;
		File dirFile;

		// Eskatu direktorioa direktorio egoki bat sartu harte
		do {
			System.out.println("Sartu fitxategiaren helbide osoa:");
			helbideOsoaString = sc.nextLine();
			dirFile = new File(helbideOsoaString);

			// Konprobatu ea ruta ondo dagoen
			if (!dirFile.isDirectory()) {
				System.out.println("Ez da aurkitu direktorioa: " + helbideOsoaString);
			}
		} while (!dirFile.isDirectory());

		return helbideOsoaString;
	}

	/**
	 * Konprobatu edo sortu fitxategia.
	 *
	 * @param helbideOsoa    , gure fitxategiaren helbide osoa
	 * @param fitxategiIzena , gure fitxategiaren izena
	 * @return gure fitxategia
	 */
	public static File KonprobatuEdoSortuFitxategia(String helbideOsoa, String fitxategiIzena) {
		File dirFile = new File(helbideOsoa);
		File fitxatefiosoaFile = new File(dirFile, fitxategiIzena);

		// Konprobatu fitxategia
		if (fitxatefiosoaFile.exists() && fitxatefiosoaFile.isFile()) {
			System.out.println("Fitxategia existitzen da.");
		} else {
			System.out.println("Fitxategia ez dago, sortzen saiatuko da...");

			// Sortu fitxategia existitzen ez bada
			try {
				if (fitxatefiosoaFile.createNewFile()) {
					System.out.println("Fitxategia sortu da: " + fitxatefiosoaFile.getAbsolutePath());

					try (BufferedWriter writer = new BufferedWriter(new FileWriter(fitxatefiosoaFile))) {
						writer.write("PAISES 1.0");
						writer.newLine();
						System.out.println("Testua idatzi da fitxategian.");
					} catch (IOException e) {
						System.out.println("Errorea fitxategian testua idaztean: " + e.getMessage());
					}
				} else {
					System.out.println("Ezin izan da fitxategia sortu.");
				}
			} catch (IOException e) {
				System.out.println("Errorea fitxategia sortzean: " + e.getMessage());
			}
		}
		return fitxatefiosoaFile;
	}

}
