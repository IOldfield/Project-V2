import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;

public class SceneController2 
{    

    private static Stage stage;     
    
    @FXML   private Button deleteButton;
    @FXML   private ListView list;

    public SceneController2()         
    {
        System.out.println("Initialising controllers...");

        if (stage != null)
        {
            System.out.println("Error, duplicate controller - terminating application!");
            System.exit(-1);
        }        

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

        System.out.println("Populating scene with items from the database...");     

        @SuppressWarnings("unchecked")
        List<Song> targetList = list.getItems();  
        Song.readAll(targetList);
    }

    public void prepareStageEvents(Stage stage)
    {
        System.out.println("Preparing stage events...");

        this.stage = stage;

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() 
            {
                public void handle(WindowEvent we) {
                    System.out.println("Exit button was clicked!");
                    Application.terminate();
                }
            });
    }    
    
    @FXML   void deleteClicked()
    {
        System.out.println("Delete was clicked");
    }
    
    @FXML   void listViewClicked()
    {
        Song selectedItem = (Song) list.getSelectionModel().getSelectedItem();
        
        if (selectedItem == null)
        {
            System.out.println("Nothing selected");
        }
        else
        {
            System.out.println(selectedItem +"(ID: " + selectedItem.getSongID() + ") is selected.");
            
            try
            {
                FXMLLoader loader = new FXMLLoader(Application.class.getResource("Scene3.fxml"));
                
                Stage stage = new Stage();
                stage.setTitle(selectedItem.getSongName());
                stage.setScene(new Scene(loader.load()));
                stage.show();
            }
            catch(Exception E)
            {
                System.out.println(E.getMessage());
            }
        }
    }
}