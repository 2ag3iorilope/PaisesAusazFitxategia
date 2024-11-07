package PaisesAusazFitxategia;

import java.io.File;
import java.util.Scanner;

/**
 * The Class Main.
 */
public class Main {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		// Fitxategiaren izena eskatu
		System.out.printf("Sartu lan egingo duzun fitxategiaren izena:");
		String fitxategiIzenaString = sc.nextLine() + ".txt";

		String helbideOsoaString = DirectorioaUtils.EskatuDirektorioa(sc);

		File fitxategia = DirectorioaUtils.KonprobatuEdoSortuFitxategia(helbideOsoaString, fitxategiIzenaString);

		int option;

		do {
			System.out.println("\n=============================");
			System.out.println("           MENUA            ");
			System.out.println("=============================");
			System.out.println("1. 🗂️ Fitxategia bete datuekin");
			System.out.println("2. 🗑️ Fitxategia hutsik utzi");
			System.out.println("3. 🔍 Bilatu eta erakutsi erregistroa");
			System.out.println("4. 📊 Kapitala Bilatu");
			System.out.println("5. ➕ Erregistro bat ezabatu");
			System.out.println("6. ❌ Ikusi Ezabatutako Erregistroak");
			System.out.println("7. 📁 Gehitu Erregistro bat");
			System.out.println("8. 📜 Aldatu erregistro bat");
			System.out.println("9.  Irten");
			System.out.println("=============================");
			System.out.print("Aukeratu aukera (1-9): ");

			option = sc.nextInt();
			sc.nextLine();

			switch (option) {
			case 1:

				FitxategienOperazioak.fitxategiaBete(fitxategia);
				break;
			case 2:
				FitxategienOperazioak.fitxategiaHutsikUtzi(fitxategia, sc);
				break;
			case 3:
				FitxategienOperazioak.bilatuErregistroBat(fitxategia, sc);
				break;
			case 4:
				FitxategienOperazioak.bilatuKapitalarenIzenak(fitxategia, sc);
				break;
			case 5:
				FitxategienOperazioak.ezabatuErregistroaLogikoa(fitxategia, sc);
				break;
			case 6:
				FitxategienOperazioak.erakutsiEzabatutakoErregistroak(fitxategia);
				break;
			case 7:
				FitxategienOperazioak.gehituErregistroa(fitxategia, sc);
				break;
			case 8:
				FitxategienOperazioak.aldatuErregistroa(fitxategia, sc);
				break;
			case 9:
				System.out.println("Irteten...");
				break;

			default:
				System.out.println("Aukera ezegokia. Saiatu berriro.");
			}
		} while (option != 9);
		sc.close();
	}

}
