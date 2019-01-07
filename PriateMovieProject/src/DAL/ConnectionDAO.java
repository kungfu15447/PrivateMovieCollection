/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;

/**
 *
 * @author Schweizeren
 */
public class ConnectionDAO
{
    private static final String SERVER_NAME = "10.176.111.31";
    private static final String DATABASE_NAME = "Tunes";
    private static final String USER = "CS2018A_14";
    private static final String PASSWORD = "CS2018A_14";
    SQLServerDataSource ds;
    
    /**
     * The constructor of the ConnectionDAO class. Initialize the information needed to connect to the database
     */
    public ConnectionDAO()
    {
    ds = new SQLServerDataSource();
    ds.setServerName(SERVER_NAME);
    ds.setDatabaseName(DATABASE_NAME);
    ds.setUser(USER);
    ds.setPassword(PASSWORD);
    }
    
    /**
     * Gets connection to the database
     * @return the connection
     * @throws SQLServerException 
     */
    public Connection getConnection() throws SQLServerException {
        Connection con = ds.getConnection();
        return con;
    }
}