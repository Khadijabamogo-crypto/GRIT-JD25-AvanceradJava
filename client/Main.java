package se.gritacademy;

import java.util.Scanner;

public class Main{

    private static Scanner scanner = new Scanner(System.in);
    private static AdClient client = new AdClient();

    public static void main(String[] args) {

        while (true) {
            System.out.println("""
                    1. Lista annonser
                    2. Visa annons
                    3. Skapa annons
                    4. Ändra pris
                    5. Radera annons
                    0. Avsluta
                    """);

            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> client.listAds();
                case 2 -> client.showAd();
                case 3 -> client.createAd();
                case 4 -> client.updatePrice();
                case 5 -> client.deleteAd();
                case 0 -> {
                    System.out.println("Hej då!");
                    System.exit(0);

                }
                default -> System.out.println("Ogiltigt val, försök igen.");
            }

        }
    }

    public static Scanner getScanner(){
        return scanner;
    }

}
