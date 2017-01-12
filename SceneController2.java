import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.event.EventHandler;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javafx.stage.WindowEvent;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import java.lang.String;

public class SceneController2 
{    

    private Stage stage;     
    private SceneController parentScene;

    @FXML   private Button deleteButton;
    @FXML   private ListView<String> list;
    
    public String tempName;

    public SceneController2()         
    {
        System.out.println("Initialising controllers...");

    } 

    public void setTempName(String n){
        tempName = n;
    }
    
    public void initialize ()          
    {            
        System.out.println("Asserting controls...");
        try
        {
            assert list != null : "Can't find list";
            assert deleteButton != null : "Can't find delete button";

        }
        catch (AssertionError ae)
        {
            System.out.println("FXML assertion failure: " + ae.getMessage());
            Application.terminate();
        }   

    }

    public void prepareStageEvents(Stage stage, SceneController parent, String songTitle)
    {
       
        System.out.println("Preparing stage events...");

        this.stage = stage;
        this.parentScene = parent;
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() 
            {
                public void handle(WindowEvent we) {
                    System.out.println("Exit button was clicked!");
                    stage.close();
                }
            });

        System.out.println("Populating scene with items from the database...");     

        String songDetails = SongListView.readAllSLV(songTitle);

        for (String info : songDetails.split("\n")) list.getItems().add(info);
    }    

    @FXML   void deleteClicked() throws SQLException
    {
       
       Song.deleteByName(tempName);
       parentScene.refresh();
       stage.close();
       
    }
    

    @FXML   void listViewClicked()
    {
    }

}