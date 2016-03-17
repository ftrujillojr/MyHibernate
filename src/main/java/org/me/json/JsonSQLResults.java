package org.me.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.me.exceptions.JsonUtilsException;


public class JsonSQLResults {

    private String contextPath;
    private String requestURI;
    private String requestURL;
    private String method;
    private String queryString;
    private String authType;
    private String sqlQuery;
    private Integer httpStatusCode;
    private String httpStatusDescription;
    
    private List<String> tableNames;
    private Map<String, String> columnNameTypes;
    private Integer numRows;
    private List<Map<String, String>> rows;
    
    private String errorMessage;
    private String jsonResponse;
    
    public JsonSQLResults() {
        
    }
    
    public String toJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(this) + "\n";
        return (json);
    }
    
    public String toJsonPretty() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(this) + "\n";
        return (json);
    }

    public static JsonSQLResults fromJson(String jsonResults) throws JsonUtilsException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(
                JsonSQLResults.class,
                new JsonSQLResultsDeserializer());

        Gson gson = gsonBuilder.create();
        Type collectionType = new TypeToken<JsonSQLResults>() {
        }.getType();

        JsonSQLResults jsonSQLResults = null;

        try {
            jsonSQLResults = gson.fromJson(jsonResults, collectionType);
        } catch (JsonParseException ex) {
            String msg = ex.getMessage();
            msg += "\n" + jsonResults + "\n";
            throw new JsonUtilsException(msg);
        }
        return jsonSQLResults;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<String> rowsListCSV = this.rowsToListCSVStrings();

        Iterator<String> itr2 = rowsListCSV.iterator();
        while (itr2.hasNext()) {
            String row = itr2.next();
            sb.append(row).append("\n");
        }

        if (this.errorMessage != null && this.errorMessage.isEmpty() == false) {
            sb.append("errorMessage: ").append(this.errorMessage).append("\n");
        }

        return (sb.toString());
    }

    public String getHttpStatusDescription() {
        return httpStatusDescription;
    }

    public void setHttpStatusDescription(String httpStatusDescription) {
        this.httpStatusDescription = httpStatusDescription;
    }
    
    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }
    
    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getJsonResponse() {
        return jsonResponse;
    }

    public void setJsonResponse(String jsonResponse) {
        this.jsonResponse = jsonResponse;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public List<String> getTableNames() {
        return tableNames;
    }

    public void setTableNames(List<String> tableNames) {
        this.tableNames = tableNames;
    }

    public Map<String, String> getColumnNameTypes() {
        return columnNameTypes;
    }

    public void setColumnNameTypes(Map<String, String> columnNameTypes) {
        this.columnNameTypes = columnNameTypes;
    }

    public Integer getNumRows() {
        return numRows;
    }

    public void setNumRows(Integer numRows) {
        this.numRows = numRows;
    }

    public List<Map<String, String>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, String>> rows) {
        this.rows = rows;
    }

    public List<String> rowsToListCSVStrings() {
        List<String> outputList = new ArrayList<>();

        if (this.rows == null) {
            return outputList;
        }

        StringBuilder sb = new StringBuilder();
        Iterator<Map<String, String>> itrList = this.rows.listIterator();
        boolean firstTime = true;
        Set<String> prevColumnNames = new TreeSet<>();

        // EACH line
        while (itrList.hasNext()) {
            Map<String, String> map = itrList.next();

            Set<String> columnNames = map.keySet();
            if (columnNames.containsAll(prevColumnNames) == false) {
                firstTime = true;
            }

            if (firstTime) {
                prevColumnNames.clear();
                Iterator<String> setItr = columnNames.iterator();
                while (setItr.hasNext()) {
                    String columnName = setItr.next();
                    prevColumnNames.add(columnName);
                    columnName = columnName.replaceAll("[\\n]+", ";");
                    sb.append(columnName).append(",");
                }
                sb.replace(sb.length() - 1, sb.length(), "");
                outputList.add(sb.toString());
                sb.setLength(0);
                firstTime = false;
            }

            Iterator<String> itrMap = map.keySet().iterator();

            // CONTAINS a hash map of data
            while (itrMap.hasNext()) {
                String key = itrMap.next();
                String val = map.get(key);
                sb.append(val).append(",");
            }
            if (sb.length() > 2) {
                sb.replace(sb.length() - 1, sb.length(), "");
                outputList.add(sb.toString());
                sb.setLength(0);
            }
        }

        return outputList;
    }

}
