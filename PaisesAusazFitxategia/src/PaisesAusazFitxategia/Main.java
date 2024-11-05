package PaisesAusazFitxategia;

import java.io.File;
import java.util.Scanner;

public class Main {

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
			System.out.println("4. 📊 Erregistroak enumeratu");
			System.out.println("5. ➕ Erregistro berri bat gehitu");
			System.out.println("6. ❌ Erregistro bat ezabatu");
			System.out.println("7. 📁 Kopiatu fitxategia");
			System.out.println("8. 📜 Kontatu fitxategiko lerroak");
			System.out.println("9. ✏️ Erregistro bat editatu");
			System.out.println("10. Irten");
			System.out.println("=============================");
			System.out.print("Aukeratu aukera (1-10): ");

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
				FitxategienOperazioak.irakurriRegistroa(fitxategia, 5);
				break;
			case 5:

				break;
			case 6:

				break;
			case 7:

				break;
			case 8:

				break;
			case 9:

				break;
			case 10:

				System.out.println("Irteten...");
				break;
			default:
				System.out.println("Aukera ezegokia. Saiatu berriro.");
			}
		} while (option != 10);
		sc.close();
	}

}
