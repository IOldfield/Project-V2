import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class SongsDatabaseConnection {

    private Connection connection = null;

    public SongsDatabaseConnection(String dbFile)
    {
        try           
        {         
            Class.forName("org.sqlite.JDBC");                               
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile); 
            System.out.println("SongsDatabase connection successfully established.");
        } 
        catch (ClassNotFoundException cnfex)    
        {
            System.out.println("Class not found exception: " + cnfex.getMessage());
        }
        catch (SQLException exception)        
        {                        
            System.out.println("SongsDatabase connection error: " + exception.getMessage());
        }

    }
    public PreparedStatement newStatement(String query)
    {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
        }
        catch (SQLException resultsexception) 
        {
            System.out.println("SongsDatabase statement error: " + resultsexception.getMessage());
        }
        return statement;
    }
    public ResultSet runQuery(PreparedStatement statement)
    {               
        try {            
            return statement.executeQuery();                       
        }
        catch (SQLException queryexception) 
        {
            System.out.println("SongsDatabase query error: " + queryexception.getMessage());
            return null;
        }
    }
    public void executeUpdate(PreparedStatement statement)
    {               
        try {            
            statement.executeUpdate();                       
        }
        catch (SQLException queryexception) 
        {
            System.out.println("SongsDatabase update error: " + queryexception.getMessage());
        }
    }
    public void disconnect()
    {
        System.out.println("Disconnecting from SongsDatabase.");
        try {
            if (connection != null) connection.close();                        
        } 
        catch (SQLException finalexception) 
        {
            System.out.println("SongsDatabase disconnection error: " + finalexception.getMessage());
        }        
    }

}