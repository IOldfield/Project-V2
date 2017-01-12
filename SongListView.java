import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;
import javafx.beans.property.StringProperty;
import javafx.beans.property.*;
import javafx.beans.property.SimpleStringProperty;

public class SongListView
{
    public String SName;
    public String SLength;
    public String RName;
    public String RLength;
    public String RNumberOfSongs;
    public String RDate;
    public String RFName;
    public String GName;
    public String LName;
    public String LStartYear;
    public String AName;
    public String ANumberOfMembers;
    public String AStartYear;

    public SongListView(String SName, String SLength, String RName, String RLength, String RNumberOfSongs, String RDate, String RFName, String GName, String LName, String LStartYear, String AName, String ANumberOfMembers, String AStartYear)
    {
        this.SName = SName;
        this.SLength = SLength;
        this.RName = RName;
        this.RLength = RLength;
        this.RNumberOfSongs = RNumberOfSongs;
        this.RDate = RDate;
        this.RFName = RFName;
        this.GName = GName;
        this.LName = LName;
        this.LStartYear = LStartYear;
        this.AName = AName;
        this.ANumberOfMembers = ANumberOfMembers;
        this.AStartYear = AStartYear;
    }

    @Override public String toString()
    {
        return (SName + "\n" + SLength + "\n" + RName + "\n" + RLength + "\n" + RNumberOfSongs + "\n" + RDate + "\n" + RFName + "\n" + GName + "\n" + LName + "\n" + LStartYear + "\n" + AName + "\n" + ANumberOfMembers + "\n" + AStartYear);
    }

    public static String readAllSLV(String songName)
    {

        String outputString = "";

        System.out.println("Attempting to get details for " + songName);

        PreparedStatement statement = Application.SongsDatabase.newStatement("select songs.songname, songs.SongLength, artists.ArtistName, artists.NumberOfMembers, artists.StartYear, releases.ReleaseName, releases.ReleaseDate, releases.ReleaseLength, releases.NumberOfSongs, releases.genre, recordlabels.LabelName, recordlabels.StartYear, releaseformat.ReleaseFormatName from songs inner join artists on songs.ArtistID = artists.ArtistID inner join releases on songs.ReleaseID = releases.ReleaseID inner join RecordLabels on artists.LabelID = RecordLabels.LabelID inner join releaseformat on releases.ReleaseFormatID = releaseformat.ReleaseFormatID where songs.songname = ?"); 
        try{
            if (statement != null)      
            {
                statement.setString(1, songName);
                ResultSet results = Application.SongsDatabase.runQuery(statement);       

                if (results != null)        
                {
                    while (results.next()) {                                               
                        SongListView slv = new SongListView(
                                results.getString("SongName"), 
                                results.getString("SongLength"),
                                results.getString("ReleaseName"), 
                                results.getString("ReleaseLength"), 
                                results.getString("NumberOfSongs"), 
                                results.getString("ReleaseDate"), 
                                results.getString("ReleaseFormatName"), 
                                results.getString("Genre"), 
                                results.getString("LabelName"), 
                                results.getString("StartYear"), 
                                results.getString("ArtistName"), 
                                results.getString("NumberOfMembers"), 
                                results.getString("StartYear"));

                        outputString = slv.toString();

                    }
                }
            }
        }
        catch (SQLException resultsexception)       
        {
            System.out.println("Database result processing error: " + resultsexception.getMessage());
        }

        System.out.println("Details for song:\n" + outputString);

        return outputString;

    }

}

