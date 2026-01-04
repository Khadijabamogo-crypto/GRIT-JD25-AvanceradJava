package se.gritacademy;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;




public class AdClient {

    private static final String BASE_URL = "http://localhost:8080/ads";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    private static final Scanner scanner = new  Scanner (System.in);
    private final ObjectMapper mapper = new ObjectMapper();


    public static void main(String[] args) {
        AdClient client = new AdClient();

        while (true) {
            System.out.println("""
                    1.Lista annonser
                    2. Visa annons
                    3. Skapa annons
                    4. Ändra pris
                    5. Radera annons
                    0. Avsluta
                    """);
            System.out.println("Choose: ");
            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
                case 1-> client.listAds();
                case 2-> client.showAd();
                case 3-> client.createAd();
                case 4 -> client.updatePrice();
                case 5 ->  client.deleteAd();
                case 0 ->{
                    System.out.println("hej då");
                   return;
                }
                default -> System.out.println("ogiltig svar");
            }
        }
    }



    // Get /ads
    public void listAds(){
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .GET()
                    .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() == 200){
            List<Dijas> ads = mapper.readValue(
                    response.body(),
                    mapper.getTypeFactory().constructCollectionType(List.class, Dijas.class)
            );
            if(ads.isEmpty()){
                System.out.println("Inga annonser hittades");
            }else {
                ads.forEach(ad -> System.out.println("ID: "+ad.getId()+ "|"+ ad.getTitle()));
            }
        }else {
            System.out.println("Misslyckades att hämta annonser. status:"+response.statusCode());
        }

        }catch(Exception e){
            e.printStackTrace();
            }


        }



    // GET/ads/{id} show one ad
    public void showAd(){
        try{
            System.out.println("Enter ad ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim()); // Safe input

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(AdClient.BASE_URL.trim() + "/" + id))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: "+ response.statusCode());


            if (response.statusCode() == 200) {
                Dijas ad = mapper.readValue(response.body(), Dijas.class);
                System.out.println(ad);
            } else {
                System.out.println("Ad not found.");
            }

    }catch (Exception e){
            e.printStackTrace();
        }
    }


    //POST /ads // Create new ad
    public void createAd() {
        try {

            System.out.println("Title: ");
            String title = scanner.nextLine().trim();

            System.out.println("Seller: ");
            String seller = scanner.nextLine().trim();

            System.out.println("Description: ");
            String description = scanner.nextLine().trim();

            System.out.println("Price: ");
            int price = Integer.parseInt(scanner.nextLine().trim()); //safer than nextInt


            //create ad object and convert to json
            Dijas ad = new Dijas(0, title, seller, description, price);
            String json = mapper.writeValueAsString(ad);

            // send post request to server
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status: " + response.statusCode());
            System.out.println(response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



            // Bygger JSON med Jackson
            /*Dijas ad = new Dijas(0,title,seller,description,price);
            ObjectsMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(ad);

             */


    //PUT/ads/{id}/price // UPPDATE PRICE
    public void updatePrice(){
        try{
            System.out.println(" Ad ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.println("New price: ");
            int price = Integer.parseInt(scanner.nextLine().trim());


            // prepare json with only the price
            String json = "{\"price\":" +price+ "}";


            // send put request to uptade price
           HttpRequest putRequest = HttpRequest.newBuilder()
                   .uri(URI.create(BASE_URL.trim() + "/" +id+ "/price"))
                   .header("Content-Type","application/json")
                   .PUT(HttpRequest.BodyPublishers.ofString(json))
                   .build();

           HttpResponse<String> putResponse =
                   httpClient.send(putRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + putResponse.statusCode());
            System.out.println(putResponse.body());

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //DELETE /ads/{id}
    public void deleteAd(){
        try{
            System.out.println("Enter ad ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL.trim() + "/" + id))
                    .DELETE()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: "+ response.statusCode());

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
