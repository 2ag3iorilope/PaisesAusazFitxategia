package PaisesAusazFitxategia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		String kodeaBilatu = sc.nextLine().trim();
		boolean found = false;
		long posicion = 0;

		try (RandomAccessFile raf = new RandomAccessFile(fitxategia, "r")) {

			long totalBytes = raf.length();
			System.out.println("Luzera fitxategia: " + totalBytes + " bytes");

			String line;
			while ((line = raf.readLine()) != null) {

				if (line.startsWith("PAISES 1.0")) {
					continue;
				}

				String[] fields = line.split(";");

				if (fields.length >= 6) {
					String codigo = fields[0].trim();
					String estatua = fields[1].trim();
					int biziEsperantza = Integer.parseInt(fields[2].trim());
					String dataSortu = fields[3].trim();
					double poblazioa = Double.parseDouble(fields[4].trim());
					String kapitala = fields[5].trim();

					if (codigo.equals(kodeaBilatu)) {
						System.out.printf("Aurkitutako erregistroa: %s; %s; %d; %s; %.2f; %s%n", codigo, estatua,
								biziEsperantza, dataSortu, poblazioa, kapitala);
						found = true;
						break;
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

	public static void bilatuKapitalarenIzenak(File fitxategia, Scanner sc) {
		System.out.println("Sartu kapitala bilatzeko terminoa:");
		String kapitalaBilatu = sc.nextLine().trim().toLowerCase();
		boolean found = false;

		try (RandomAccessFile raf = new RandomAccessFile(fitxategia, "r")) {

			String line;
			while ((line = raf.readLine()) != null) {

				if (line.startsWith("PAISES 1.0")) {
					continue;
				}

				String[] fields = line.split(";");
				if (fields.length >= 6) {
					String kapitala = fields[5].trim().toLowerCase();

					if (kapitala.contains(kapitalaBilatu)) {
						String codigo = fields[0].trim();
						String estatua = fields[1].trim();
						int biziEsperantza = Integer.parseInt(fields[2].trim());
						String dataSortu = fields[3].trim();
						double poblazioa = Double.parseDouble(fields[4].trim());

						System.out.printf("Aurkitutako erregistroa: %s; %s; %d; %s; %.2f; %s%n", codigo, estatua,
								biziEsperantza, dataSortu, poblazioa, kapitala);
						found = true;
					}
				} else {
					System.out.println("Errorea: erregistroa ez da osatua.");
				}
			}

			if (!found) {
				System.out.println("Ez da aurkitu erregistro bat kapitala izenarekin: " + kapitalaBilatu);
			}
		} catch (IOException e) {
			System.out.println("Errorea fitxategia irakurtzean: " + e.getMessage());
		}
	}

	public static void ezabatuErregistroaLogikoa(File fitxategia, Scanner sc) {
		System.out.println("Sartu ezabatu nahi duzun erregistroaren kodea:");
		String kodeaEzabatu = sc.nextLine().trim(); // Leer el código del registro a eliminar
		boolean found = false;

		try (RandomAccessFile raf = new RandomAccessFile(fitxategia, "rw")) {
			// Saltar el encabezado
			raf.readLine();

			// Buscar el registro a eliminar
			long posicionInicial = raf.getFilePointer();
			String line;

			while ((line = raf.readLine()) != null) {
				String[] fields = line.split(";");
				if (fields.length >= 6) { // Asegurarse de que hay suficientes campos
					String codigo = fields[0].trim();

					// Comprobar si el código coincide con el que se quiere eliminar
					if (codigo.equals(kodeaEzabatu)) {
						// Realizar borrado lógico
						raf.seek(posicionInicial); // Volver al inicio del registro
						String registroBorrado = String.format("%-5s;%s;%s;%s;%s;%s\n", "-" + kodeaEzabatu, fields[1],
								fields[2], fields[3], fields[4], fields[5]);
						raf.writeBytes(registroBorrado);
						found = true;
						System.out.println("Erregistroa logikoki ezabatuta.");
						break;
					}
				}
				posicionInicial = raf.getFilePointer(); // Actualizar la posición para el siguiente registro
			}

			if (!found) {
				System.out.println("Ez da aurkitu erregistroa kodearekin: " + kodeaEzabatu);
			}
		} catch (IOException e) {
			System.out.println("Errorea fitxategia irakurtzean edo idaztean: " + e.getMessage());
		}
	}

	public static void erakutsiEzabatutakoErregistroak(File fitxategia) {
		try (RandomAccessFile raf = new RandomAccessFile(fitxategia, "r")) {
			// Saltar el encabezado
			raf.readLine();

			boolean found = false;
			String line;

			System.out.println("Ezabatutako erregistroak:");

			while ((line = raf.readLine()) != null) {
				String[] fields = line.split(";");
				if (fields.length >= 6) {
					String kodea = fields[0].trim();

					// Comprobar si el código tiene un guion al inicio
					if (kodea.startsWith("-")) {
						String estatua = fields[1].trim();
						int biziEsperantza = Integer.parseInt(fields[2].trim());
						String dataSortu = fields[3].trim();
						double poblazioa = Double.parseDouble(fields[4].trim());
						String kapitala = fields[5].trim();

						System.out.printf(
								"Kodea: %s; Estatua: %s; Bizi Esperantza: %d; Data Sortu: %s; Poblazioa: %.0f; Kapitala: %s%n",
								kodea, estatua, biziEsperantza, dataSortu, poblazioa, kapitala);

						found = true;
					}
				}
			}

			if (!found) {
				System.out.println("Ez dago ezabatutako erregistrorik.");
			}
		} catch (IOException e) {
			System.out.println("Errorea fitxategia irakurtzean: " + e.getMessage());
		}
	}

	private static int lortuHurrengoKodea(File fitxategia) {
		boolean[] kodeakErabilita = new boolean[1000]; // Suponemos un máximo de 1000 códigos
		try (RandomAccessFile raf = new RandomAccessFile(fitxategia, "r")) {
			// Saltar el encabezado
			raf.readLine();

			String line;
			while ((line = raf.readLine()) != null) {
				String[] fields = line.split(";");
				if (fields.length >= 1) {
					String kodeaStr = fields[0].trim();
					if (!kodeaStr.startsWith("-")) { // Solo registrar códigos no eliminados
						int kodea = Integer.parseInt(kodeaStr);
						kodeakErabilita[kodea] = true;
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Errorea kodeak egiaztatzen: " + e.getMessage());
		}

		// Encontrar el primer código libre
		for (int i = 0; i < kodeakErabilita.length; i++) {
			if (!kodeakErabilita[i]) {
				return i;
			}
		}
		return -1; // Si no hay códigos disponibles (lo cual no debería ocurrir)
	}

	public static void gehituErregistroa(File fitxategia, Scanner sc) {
		// Generar código automáticamente
		int kodea = lortuHurrengoKodea(fitxategia);

		// Solicitar el resto de los datos
		System.out.println("Sartu estatua:");
		String estatua = sc.nextLine().trim();
		System.out.println("Sartu bizi esperantza:");
		int biziEsperantza = sc.nextInt();
		sc.nextLine(); // Consumir salto de línea
		System.out.println("Sartu data sortu (yyyy-MM-dd):");
		String dataSortu = sc.nextLine().trim();
		System.out.println("Sartu poblazioa:");
		double poblazioa = sc.nextDouble();
		sc.nextLine(); // Consumir salto de línea
		System.out.println("Sartu kapitala:");
		String kapitala = sc.nextLine().trim();

		// Formatear el nuevo registro
		String record = String.format("%-5d;%-20s;%03d;%-10s;%-15.0f;%-15s\n", kodea, estatua, biziEsperantza,
				dataSortu, poblazioa, kapitala);

		try (RandomAccessFile raf = new RandomAccessFile(fitxategia, "rw")) {
			// Posicionarse al final del archivo y escribir el nuevo registro
			raf.seek(fitxategia.length());
			raf.writeBytes(record);
			System.out.println("Erregistro berria gehitu da fitxategian.");
		} catch (IOException e) {
			System.out.println("Errorea erregistroa gehitzean: " + e.getMessage());
		}
	}
	public static void aldatuErregistroa(File fitxategia, Scanner sc) {
	    // Solicitar el código del registro a modificar
	    System.out.println("Sartu aldatu nahi duzun kodea:");
	    int kodeaAldatu = sc.nextInt();
	    sc.nextLine(); // Consumir salto de línea

	    // Crear una lista temporal para almacenar los registros del archivo
	    List<String> erregistroak = new ArrayList<>();
	    boolean aurkituta = false;

	    try (RandomAccessFile raf = new RandomAccessFile(fitxategia, "r")) {
	        // Leer todos los registros y almacenarlos en la lista temporal
	        String line;
	        while ((line = raf.readLine()) != null) {
	            String[] fields = line.split(";");
	            if (fields.length > 0 && fields[0].trim().equals(String.valueOf(kodeaAldatu)) && !fields[0].trim().startsWith("-")) {
	                // Solicitar los nuevos datos para el registro
	                System.out.println("Sartu estatua berria:");
	                String estatua = sc.nextLine().trim();
	                System.out.println("Sartu bizi esperantza berria:");
	                int biziEsperantza = sc.nextInt();
	                sc.nextLine(); // Consumir salto de línea
	                System.out.println("Sartu data sortu berria (yyyy-MM-dd):");
	                String dataSortu = sc.nextLine().trim();
	                System.out.println("Sartu poblazioa berria:");
	                double poblazioa = sc.nextDouble();
	                sc.nextLine(); // Consumir salto de línea
	                System.out.println("Sartu kapitala berria:");
	                String kapitala = sc.nextLine().trim();

	                // Formatear el registro actualizado
	                line = String.format("%-5d;%-20s;%03d;%-10s;%-15.0f;%-15s", kodeaAldatu, estatua, biziEsperantza, dataSortu, poblazioa, kapitala);
	                aurkituta = true;
	            }
	            // Agregar el registro (modificado o no) a la lista
	            erregistroak.add(line);
	        }
	    } catch (IOException e) {
	        System.out.println("Errorea fitxategia irakurtzean: " + e.getMessage());
	        return;
	    }

	    // Si el código no se encontró, informar al usuario
	    if (!aurkituta) {
	        System.out.println("Errorea: kodea " + kodeaAldatu + " ez da aurkitu fitxategian.");
	        return;
	    }

	    // Sobrescribir el archivo con los registros actualizados
	    try (PrintWriter pw = new PrintWriter(new FileWriter(fitxategia))) {
	        for (String erregistroa : erregistroak) {
	            pw.println(erregistroa);
	        }
	        System.out.println("Erregistroa eguneratu da kodearekin: " + kodeaAldatu);
	    } catch (IOException e) {
	        System.out.println("Errorea fitxategia idaztean: " + e.getMessage());
	    }
	}

}
