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
        String contenido = "";
        try {
            contenido = new String(Files.readAllBytes(Paths.get(file + ".json")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contenido;
    }

    public static boolean isEmptyFile(File file) {
        return file.length() == 0;
    }

    public static void createJSON(Sale sale, ArrayList<Product> products) {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONArray arraySales = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        Product[] arregloProductos = products.toArray(new Product[0]);
        if (isEmptyFile(new File("JSONSales/sales.json"))) {
            File archivo = new File("JSONSales/sales.json");
            File carpeta = archivo.getParentFile();
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            } else {
                try {
                    jsonObject.put("operationNumber", sale.getOperationNumber());
                    jsonObject.put("customerName", sale.getCustomerName());
                    jsonObject.put("totalAmmount", sale.getTotalAmmount());
                    jsonObject.put("date", sale.getDate());
                    arraySales.put(arregloProductos);
                    jsonObject.put("Products", arraySales);
                    jsonArray.put(jsonObject);
                    json.put("sales", jsonArray);
                    FileWriter file = new FileWriter(archivo);
                    file.write(json.toString());
                    file.flush();
                    file.close();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            String filePath = "C:\\Users\\Luis Taliercio\\Proyecto-Final-Java\\JSONSales\\sales.json";
            try {
                jsonObject.put("operationNumber", sale.getOperationNumber());
                jsonObject.put("customerName", sale.getCustomerName());
                jsonObject.put("totalAmmount", sale.getTotalAmmount());
                jsonObject.put("date", sale.getDate());
                arraySales.put(arregloProductos);
                jsonObject.put("Products", arraySales);
                jsonArray.put(jsonObject);
                json.put("sales", jsonArray);
                FileWriter fw = new FileWriter(filePath,true);
                fw.write(json.toString());
                fw.flush();
                fw.close();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
}
