package DatabaseRelated;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class JsonUtiles {

    public static String readJson(String file) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(file + ".json")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static boolean isEmptyFile(File file) {
        return file.length() == 0;
    }

    public static void createJSON(Sale sale, ArrayList<Product> products) {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONArray arraySales = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        Product[] productsArray = products.toArray(new Product[0]);
        if (isEmptyFile(new File("JSONSales/sales.json"))) {
            File file1 = new File("JSONSales/sales.json");
            File folder = file1.getParentFile();
            if (!folder.exists()) {
                folder.mkdirs();
            } else {
                try {
                    jsonObject.put("operationNumber", sale.getOperationNumber());
                    jsonObject.put("customerName", sale.getCustomerName());
                    jsonObject.put("totalAmmount", sale.getTotalAmmount());
                    jsonObject.put("date", sale.getDate());
                    arraySales.put(productsArray);
                    jsonObject.put("Products", arraySales);
                    jsonArray.put(jsonObject);
                    json.put("sales", jsonArray);
                    FileWriter file = new FileWriter(file1);
                    file.write(json.toString());
                    file.flush();
                    file.close();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            String filePath = "C:\\Users\\Luis Taliercio\\Proyecto-Final-Java\\JSONSales\\sales.json"; //TO-DO change this path
            try {
                jsonObject.put("operationNumber", sale.getOperationNumber());
                jsonObject.put("customerName", sale.getCustomerName());
                jsonObject.put("totalAmmount", sale.getTotalAmmount());
                jsonObject.put("date", sale.getDate());
                arraySales.put(productsArray);
                jsonObject.put("Products", arraySales);
                jsonArray.put(jsonObject);
                json.put("sales", jsonArray);
                FileWriter fw = new FileWriter(filePath,true);
                fw.write(json.toString());
                fw.flush();
                fw.close();
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }


        }

    }
}
