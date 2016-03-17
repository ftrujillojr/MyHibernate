package org.me.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class JsonSQLResultsDeserializer implements JsonDeserializer<JsonSQLResults> {

    @Override
    public JsonSQLResults deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jsonObject = je.getAsJsonObject();

        // =====================================================================
        String sqlQuery = null;
        if (jsonObject.get("sqlQuery") != null) {
            sqlQuery = jsonObject.get("sqlQuery").toString();
        }
        // =====================================================================
        List<String> tableNames = null;
        if (jsonObject.get("tableNames") != null) {
            tableNames = new ArrayList<>();
            JsonArray jaTableNames = jsonObject.get("tableNames").getAsJsonArray();
            for (int ii = 0; ii < jaTableNames.size(); ii++) {
                String tableName = jaTableNames.get(ii).getAsString();
                tableNames.add(tableName);
            }
        }
        // =====================================================================
        Map<String, String> columnNameTypes = null;
        if (jsonObject.get("columnNameTypes") != null) {
            columnNameTypes = new LinkedHashMap<>();
            JsonObject jsonObjectColumnNameTypes = jsonObject.get("columnNameTypes").getAsJsonObject();
            Set<Entry<String, JsonElement>> entrySet = jsonObjectColumnNameTypes.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                String key = entry.getKey();
                String val = jsonObjectColumnNameTypes.get(key).getAsString();
                columnNameTypes.put(key, val);
            }
        }
        // =====================================================================
        Integer numRows = null;
        if (jsonObject.get("numRows") != null) {
            numRows = jsonObject.get("numRows").getAsInt();
        }
// =====================================================================
        List<Map<String, String>> rows = null;
        if (jsonObject.get("rows") != null) {
            rows = new ArrayList<>();
            JsonArray jsonArrayRows = jsonObject.get("rows").getAsJsonArray();
            for (int ii = 0; ii < jsonArrayRows.size(); ii++) {
                Map<String, String> tmpMap = new LinkedHashMap<>();
                Set<Entry<String, JsonElement>> entrySet2 = jsonArrayRows.get(ii).getAsJsonObject().entrySet();
                for (Map.Entry<String, JsonElement> entry : entrySet2) {
                    String key = entry.getKey();
                    String val = jsonArrayRows.get(ii).getAsJsonObject().get(key).getAsString();
                    tmpMap.put(key, val);
                }
                rows.add(tmpMap);
            }
        }
        // Set the values in the new Java object.
        JsonSQLResults jsonSQLResults = new JsonSQLResults();
        jsonSQLResults.setSqlQuery(sqlQuery);
        jsonSQLResults.setColumnNameTypes(columnNameTypes);
        jsonSQLResults.setTableNames(tableNames);
        jsonSQLResults.setNumRows(numRows);
        jsonSQLResults.setRows(rows);
        return jsonSQLResults;
    }
}

