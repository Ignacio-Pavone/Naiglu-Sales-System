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

    public static void createJSON(ArrayList<Sale> saleList) {
        JSONObject json = new JSONObject();
        JSONArray jA = new JSONArray();
        JSONArray jProducts;
        for (int i = 0; i < saleList.size(); i++) {
            JSONObject jO = new JSONObject();
            try {
                Sale aux = saleList.get(i);
                jO.put("operationNumber", aux.getOperationNumber());
                jO.put("customerName", aux.getCustomerName());
                jO.put("totalAmmount", aux.getTotalAmmount());
                jO.put("date", aux.getDate());
                jO.put("dateFormatted", aux.getDateFormatted());
                jA.put(jO);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
            try {
                json.put("sales",jA);
                FileWriter fw = new FileWriter("JsonSales/sales.json");
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

