
import java.io.BufferedReader;
        import java.io.FileReader;
        import java.io.IOException;
        import java.util.HashMap;
        import java.util.Map;
        import java.util.Set;

public class PriceCalculator {
    public static void main(String[] args) {
        String priceFile = "price_file.csv"; // Replace with the name of your CSV file
        String[] productsToBuy = {"product1", "product2", "product3"}; // Replace with the list of products to buy

        Map<Integer, Shop> shopMap = readPriceFile(priceFile);

        int bestShopId = findBestShop(shopMap, productsToBuy);
        double minTotalPrice = calculateTotalPrice(shopMap.get(bestShopId), productsToBuy);

        System.out.println("The best shop to go to is Shop ID: " + bestShopId);
        System.out.println("The total price will be: " + minTotalPrice);
    }

    private static Map<Integer, Shop> readPriceFile(String priceFile) {
        Map<Integer, Shop> shopMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(priceFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int shopId = Integer.parseInt(parts[0]);
                double price = Double.parseDouble(parts[1]);
                String[] products = new String[parts.length - 2];
                System.arraycopy(parts, 2, products, 0, parts.length - 2);
                Shop shop = new Shop(price, products);
                shopMap.put(shopId, shop);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return shopMap;
    }

    private static int findBestShop(Map<Integer, Shop> shopMap, String[] productsToBuy) {
        int bestShopId = -1;
        double minTotalPrice = Double.MAX_VALUE;

        for (Map.Entry<Integer, Shop> entry : shopMap.entrySet()) {
            Shop shop = entry.getValue();
            double totalPrice = calculateTotalPrice(shop, productsToBuy);
            if (totalPrice < minTotalPrice) {
                minTotalPrice = totalPrice;
                bestShopId = entry.getKey();
            }
        }

        return bestShopId;
    }

    private static double calculateTotalPrice(Shop shop, String[] productsToBuy) {
        Set<String> productsInShop = shop.getProducts();
        double totalPrice = shop.getPrice();

        for (String product : productsToBuy) {
            if (!productsInShop.contains(product)) {
                // If the product is not available in the shop, add its price to the total
                totalPrice += shop.getPrice();
            }
        }

        return totalPrice;
    }
}

