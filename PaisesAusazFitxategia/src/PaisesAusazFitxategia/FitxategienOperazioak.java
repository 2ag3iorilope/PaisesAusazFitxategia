package PaisesAusazFitxategia;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FitxategienOperazioak {
    static String[] Kodea = { "31", "376", "90", "261", "685", "213", "291", "595", "30", "964" };
    static String[] Estatua = { "Holanda", "Andorra", "Turkia", "Madagascar", "Samoa Occidental", "Argelia", "Eritrea",
            "Paraguay", "Grecia", "Irak" };
    static int[] BiziEsperantza = { 78, 0, 67, 52, 68, 70, 0, 68, 78, 66 };
    static LocalDate[] DataSortu = { LocalDate.of(1581, 7, 26), LocalDate.of(1993, 3, 14), LocalDate.of(1923, 10, 29),
            LocalDate.of(1960, 6, 26), LocalDate.of(1962, 1, 1), LocalDate.of(1962, 7, 5), LocalDate.of(1993, 5, 24),
            LocalDate.of(1825, 8, 25), LocalDate.of(1830, 2, 3), LocalDate.of(1958, 7, 14) };
    static double[] Poblazioa = { 15460000, 64000, 61058000, 13651000, 165000, 27959000, 3400000, 4828000, 10467000,
            20097000 };
    static String[] Kapitala = { "Amsterdam", "Andorra La Vieja", "Ankara", "Antananarivo", "Apia", "Argel", "Asmara",
            "Asuncion", "Atenas", "Bagdad" };
    
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final int RECORD_SIZE = 100; 

    public static void fitxategiaBete(File fitxategia) {
        try (RandomAccessFile raf = new RandomAccessFile(fitxategia, "rw")) {
            // Escribir encabezado
            raf.writeBytes("PAISES 1.0\n");

            for (int i = 0; i < Kodea.length; i++) {
                // Formatear cada registro con un delimitador
                String record = String.format("%-5s;%-20s;%03d;%-10s;%-15.0f;%-15s\n",
                    Kodea[i],
                    Estatua[i],
                    BiziEsperantza[i],
                    DataSortu[i].format(dateFormatter),
                    Poblazioa[i],
                    Kapitala[i]
                );

                // Escribir el registro como bytes para evitar caracteres adicionales
                raf.writeBytes(record);
            }

            System.out.println("Fitxategia datuekin bete da.");
        } catch (IOException e) {
            System.out.println("Errorea fitxategia idaztean: " + e.getMessage());
        }
    }

    public static void irakurriRegistroa(File fitxategia, int index) {
        try (RandomAccessFile raf = new RandomAccessFile(fitxategia, "r")) {
            raf.seek(RECORD_SIZE * (index + 1));  // Ubica el puntero para el registro específico
            
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
    
    public static void fitxategiaHutsikUtzi(File fitxategia, Scanner sc) {
        System.out.println("Hutsik utzi nahi duzu fitxategia? (bai/ez)");
        String confirmation = sc.nextLine();
        
        if (confirmation.equalsIgnoreCase("bai")) {
            try (RandomAccessFile raf = new RandomAccessFile(fitxategia, "rw")) {
                // Sobreescribimos el contenido del archivo
                raf.setLength(0); // Limpiar todo el archivo
                raf.writeBytes("PAISES 1.0\n"); // Escribir solo el encabezado
                
                System.out.println("Fitxategia hutsik utzi da.");
            } catch (IOException e) {
                System.out.println("Errorea fitxategia hutsik uztean: " + e.getMessage());
            }
        } else {
            System.out.println("Ez da fitxategia hutsik utzi.");
        }
    }
    
    
}
