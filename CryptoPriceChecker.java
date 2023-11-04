import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CryptoPriceChecker {
    public static void main(String[] args) {
        try {
            // Define the API URLs for Bitcoin and Ethereum
            String bitcoinUrl = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd";
            String ethereumUrl = "https://api.coingecko.com/api/v3/simple/price?ids=ethereum&vs_currencies=usd";

            // Fetch Bitcoin price
            double bitcoinPrice = fetchCryptoPrice(bitcoinUrl, "bitcoin");
            System.out.println("Bitcoin Price (USD): $" + bitcoinPrice);

            // Fetch Ethereum price
            double ethereumPrice = fetchCryptoPrice(ethereumUrl, "ethereum");
            System.out.println("Ethereum Price (USD): $" + ethereumPrice);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Function to fetch the price of a cryptocurrency
    private static double fetchCryptoPrice(String apiUrl, String cryptoName) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse the JSON response to extract the price
            String responseJson = response.toString();
            int priceIndex = responseJson.indexOf(cryptoName);
            int priceStart = responseJson.indexOf(":", priceIndex) + 1;
            int priceEnd = responseJson.indexOf("}", priceStart);
            String priceString = responseJson.substring(priceStart, priceEnd);

            return Double.parseDouble(priceString);
        } else {
            throw new Exception("Failed to fetch data for " + cryptoName);
        }
    }
}
