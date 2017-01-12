import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;
import javafx.beans.property.StringProperty;
import javafx.beans.property.*;
import javafx.beans.property.SimpleStringProperty;

public class Song
{
    public StringProperty SongName;
    private StringProperty ArtistName;
    private StringProperty ReleaseName;
    private StringProperty Genre;        
    private StringProperty SongLength;
   
    public String getSongName() { return SongName.get(); }
    public void setSongName(String SongName) { this.SongName = new SimpleStringProperty(SongName); }    
    
    public String getSongLength() { return SongLength.get(); }
    public void setSongLength(String SongLength) { this.SongLength = new SimpleStringProperty(SongLength); }

    public String getGenre() { return Genre.get(); }
    public void setGenre(String Genre) { this.Genre = new SimpleStringProperty(Genre); }
 
    public String getArtistName() { return ArtistName.get(); }
    public void setArtistName(String ArtistName) { this.ArtistName = new SimpleStringProperty(ArtistName); }
    
    public String getReleaseName() { return ReleaseName.get(); }
    public void setReleaseName(String ReleaseName) { this.ReleaseName = new SimpleStringProperty(ReleaseName); }
    
    public Song(String SongName, String Genre, String ArtistName, String ReleaseName, String SongLength)
    {
        setSongName(SongName);
        setGenre(Genre);  
        setArtistName(ArtistName);
        setReleaseName(ReleaseName);
        setSongLength(SongLength);
    }
    
    @Override public String toString()
    {
        return (SongName + " " + Genre + " " + ArtistName + " " + ReleaseName + " " + SongLength );
    }

    public static void readAll(List<Song> list)
    {
        list.clear();      
        
        PreparedStatement statement = Application.SongsDatabase.newStatement("select songs.songname, songs.SongLength, artists.ArtistName, releases.ReleaseName, releases.Genre from songs inner join artists on songs.ArtistID = artists.ArtistID inner join releases on songs.ReleaseID = releases.ReleaseID "); 

        if (statement != null)      
        {
            ResultSet results = Application.SongsDatabase.runQuery(statement);       

            if (results != null)      
            {
                try {                              
                    while (results.next()) {                                               
                        list.add( new Song(results.getString("SongName"), 
                                results.getString("Genre"), 
                                results.getString("ArtistName"),
                                results.getString("ReleaseName"),
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
    
    public static void deleteByName(String name){
        try{
            PreparedStatement stat = Application.SongsDatabase.newStatement("DELETE FROM songs WHERE songname=?");
            stat.setString(1, name);
            Application.SongsDatabase.executeUpdate(stat);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}