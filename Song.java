import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;
import javafx.beans.property.StringProperty;
import javafx.beans.property.*;
import javafx.beans.property.SimpleStringProperty;

public class Song
{
    public IntegerProperty SongID;
    private IntegerProperty ArtistID;
    private IntegerProperty ReleaseID;
    private StringProperty SongName;        
    private StringProperty SongLength;
   
    public String getSongName() { return SongName.get(); }
    public void setSongName(String SongName) { this.SongName = new SimpleStringProperty(SongName); }    
    
    public String getSongLength() { return SongLength.get(); }
    public void setSongLength(String SongLength) { this.SongLength = new SimpleStringProperty(SongLength); }

    public int getSongID() { return SongID.get(); }
    public void setSongID(int SongID) { this.SongID = new SimpleIntegerProperty(SongID); }
 
    public int getArtistID() { return ArtistID.get(); }
    public void setArtistID(int ArtistID) { this.ArtistID = new SimpleIntegerProperty(ArtistID); }
    
    public int getReleaseID() { return ReleaseID.get(); }
    public void setReleaseID(int ReleaseID) { this.ReleaseID = new SimpleIntegerProperty(ReleaseID); }
    
    public Song(int SongID, int ArtistID, int ReleaseID, String SongName, String SongLength)
    {
        setSongID(SongID);  
        setArtistID(ArtistID);
        setReleaseID(ReleaseID);
        setSongName(SongName);
        setSongLength(SongLength);
    }
    
    @Override public String toString()
    {
        return (SongName + " " + SongLength + " "  +SongID + " "  + ArtistID + " "  + ReleaseID );
    }

    public static void readAll(List<Song> list)
    {
        list.clear();      
        
        PreparedStatement statement = Application.SongsDatabase.newStatement("SELECT SongID, ArtistID, ReleaseID, SongName, SongLength FROM Songs"); 

        if (statement != null)      
        {
            ResultSet results = Application.SongsDatabase.runQuery(statement);       

            if (results != null)      
            {
                try {                              
                    while (results.next()) {                                               
                        list.add( new Song(results.getInt("SongID"), 
                                results.getInt("ArtistID"), 
                                results.getInt("ReleaseID"),
                                results.getString("SongName"),
                                results.getString("SongLength")) );
                    }
                }
                catch (SQLException resultsexception)      
                {
                    System.out.println("Database result processing error: " + resultsexception.getMessage());
                }
            }
        }

    }

}