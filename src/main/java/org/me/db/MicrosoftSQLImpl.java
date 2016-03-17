package org.me.db;

import java.sql.SQLException;

public class MicrosoftSQLImpl extends CommonDBAbstract {

    public MicrosoftSQLImpl(String l_host, String l_instance, String l_port, String l_database, String l_username, String l_password) throws SQLException {
        super(l_host, l_instance, l_port, l_database, l_username, l_password);

        StringBuilder sb_urlConnection = new StringBuilder();
        sb_urlConnection.append("jdbc:sqlserver://").append(l_host).append("\\").append(l_instance).append(":").append(l_port).append(";databaseName=").append(l_database).append(";");
        this.init(sb_urlConnection.toString());
    }

    private void init(String urlConnectionString) {
        this.setJDBCString(urlConnectionString);
        this.setJDBCDriverClass("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }

}
