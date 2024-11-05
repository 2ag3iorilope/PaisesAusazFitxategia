package PaisesAusazFitxategia;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// TODO: Auto-generated Javadoc
/**
 * The Class FitxategienOperazioak.
 */
public class FitxategienOperazioak {

	/** Kodea. */
	static String[] Kodea = { "31", "376", "90", "261", "685", "213", "291", "595", "30", "964" };

	/** Estatua. */
	static String[] Estatua = { "Holanda", "Andorra", "Turkia", "Madagascar", "Samoa Occidental", "Argelia", "Eritrea",
			"Paraguay", "Grecia", "Irak" };

	/** Bizi esperantza. */
	static int[] BiziEsperantza = { 78, 0, 67, 52, 68, 70, 0, 68, 78, 66 };

	/** Data sortu. */
	static LocalDate[] DataSortu = { LocalDate.of(1581, 7, 26), LocalDate.of(1993, 3, 14), LocalDate.of(1923, 10, 29),
			LocalDate.of(1960, 6, 26), LocalDate.of(1962, 1, 1), LocalDate.of(1962, 7, 5), LocalDate.of(1993, 5, 24),
			LocalDate.of(1825, 8, 25), LocalDate.of(1830, 2, 3), LocalDate.of(1958, 7, 14) };

	/** Poblazioa. */
	static double[] Poblazioa = { 15460000, 64000, 61058000, 13651000, 165000, 27959000, 3400000, 4828000, 10467000,
			20097000 };

	/** Kapitala. */
	static String[] Kapitala = { "Amsterdam", "Andorra La Vieja", "Ankara", "Antananarivo", "Apia", "Argel", "Asmara",
			"Asuncion", "Atenas", "Bagdad" };

	/** Gure dataren Formatua. */
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/** Constant RECORD_SIZE. */
	private static final int RECORD_SIZE = 100;

	/**
	 * Fitxategia bete.
	 *
	 * @param fitxategia, gure fitxategia
	 */
	public static void fitxategiaBete(File fitxategia) {
		try (RandomAccessFile raf = new RandomAccessFile(fitxategia, "rw")) {

			raf.writeBytes("PAISES 1.0\n");

			for (int i = 0; i < Kodea.length; i++) {

				// Foramtua eman
				String record = String.format("%-5s;%-20s;%03d;%-10s;%-15.0f;%-15s\n", Kodea[i], Estatua[i],
						BiziEsperantza[i], DataSortu[i].format(dateFormatter), Poblazioa[i], Kapitala[i]);

				raf.writeBytes(record);
			}

			System.out.println("Fitxategia datuekin bete da.");
		} catch (IOException e) {
			System.out.println("Errorea fitxategia idaztean: " + e.getMessage());
		}
	}

	/**
	 * Irakurri registroa.
	 *
	 * @param fitxategia, gure fitxategia
	 * @param index
	 */
	public static void irakurriRegistroa(File fitxategia, int index) {
		try (RandomAccessFile raf = new RandomAccessFile(fitxategia, "r")) {
			raf.seek(RECORD_SIZE * (index + 1));

			String kodea = raf.readUTF().trim();
			String estatua = raf.readUTF().trim();
			int biziEsperantza = raf.readInt();
			LocalDate dataSortu = LocalDate.parse(raf.readUTF().trim(), dateFormatter);
			double poblazioa = raf.readDouble();
			String kapitala = raf.readUTF().trim();

			System.out.println("Kodea: " + kodea);
			System.out.println("Estatua: " + estatua);
			System.out.println("Bizi Esperantza: " + biziEsperantza);
			System.out.println("Data Sortu: " + dataSortu);
			System.out.println("Poblazioa: " + poblazioa);
			System.out.println("Kapitala: " + kapitala);
		} catch (IOException e) {
			System.out.println("Errorea fitxategia irakurtzean: " + e.getMessage());
		}
	}

	/**
	 * Fitxategia hutsik utzi.
	 *
	 * @param fitxategia, gure fitxategia
	 * @param sc,         gure eskanerra
	 */
	public static void fitxategiaHutsikUtzi(File fitxategia, Scanner sc) {
		System.out.println("Hutsik utzi nahi duzu fitxategia? (bai/ez)");
		String confirmation = sc.nextLine();

		if (confirmation.equalsIgnoreCase("bai")) {
			try (RandomAccessFile raf = new RandomAccessFile(fitxategia, "rw")) {

				raf.setLength(0);
				raf.writeBytes("PAISES 1.0\n");

				System.out.println("Fitxategia hutsik utzi da.");
			} catch (IOException e) {
				System.out.println("Errorea fitxategia hutsik uztean: " + e.getMessage());
			}
		} else {
			System.out.println("Ez da fitxategia hutsik utzi.");
		}
	}
	
	 public static void bilatuErregistroBat(File fitxategia, Scanner sc) {
	        System.out.println("Sartu kodea erregistroa bat bilatzeko:");
	        String kodeaBilatu = sc.nextLine().trim(); // Leer el código a buscar
	        boolean found = false;
	        long posicion = 0; // Comenzar desde el principio del archivo

	        try (RandomAccessFile raf = new RandomAccessFile(fitxategia, "r")) {
	            // Verificar la longitud del archivo
	            long totalBytes = raf.length();
	            System.out.println("Luzera fitxategia: " + totalBytes + " bytes");

	            // Leer línea por línea
	            String line;
	            while ((line = raf.readLine()) != null) {
	                // Ignorar la primera línea de encabezado
	                if (line.startsWith("PAISES 1.0")) {
	                    continue;
	                }

	                // Separar la línea en campos usando ";" como delimitador
	                String[] fields = line.split(";");

	                if (fields.length >= 6) { // Asegurarse de que hay suficientes campos
	                    String codigo = fields[0].trim(); // El primer campo es el código
	                    String estatua = fields[1].trim(); // El segundo campo es el estado
	                    int biziEsperantza = Integer.parseInt(fields[2].trim()); // El tercer campo es la esperanza de vida
	                    String dataSortu = fields[3].trim(); // El cuarto campo es la fecha de creación
	                    double poblazioa = Double.parseDouble(fields[4].trim()); // El quinto campo es la población
	                    String kapitala = fields[5].trim(); // El sexto campo es la capital

	                    // Comprobar si el código coincide con el que se busca
	                    if (codigo.equals(kodeaBilatu)) {
	                        System.out.printf("Aurkitutako erregistroa: %s; %s; %d; %s; %.2f; %s%n",
	                                codigo, estatua, biziEsperantza, dataSortu, poblazioa, kapitala);
	                        found = true;
	                        break; // Termina la búsqueda si se encuentra
	                    }
	                } else {
	                    System.out.println("Errorea: erregistroa ez da osatua.");
	                }
	            }

	            if (!found) {
	                System.out.println("Ez da aurkitu erregistro bat kodearekin: " + kodeaBilatu);
	            }
	        } catch (IOException e) {
	            System.out.println("Errorea fitxategia irakurtzean: " + e.getMessage());
	        }
	    }

}
