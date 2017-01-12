import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;

public class SceneController 
{    

    private static Stage stage;     

    @FXML   private Label title;
    @FXML   private TableView<Song> centralDB;
    @FXML   private Button searchButton;
    @FXML   private TextField textField;
    @FXML   private Button addButton;
    @FXML   private Button refineButton;
    @FXML   private Button sortButton;

    public SceneController()         
    {
        System.out.println("Initialising controllers...");

        if (stage != null)
        {
            System.out.println("Error, duplicate controller - terminating application!");
            System.exit(-1);
        }        

    } 
    
    public void initialize () throws SQLException        
    {            
        System.out.println("Asserting controls...");
        try
        {
            assert title != null : "Can't find title";
            assert centralDB != null : "Can't find CentralDB.";
            assert addButton != null : "Can't find add button.";
            assert refineButton != null : "Can't find refine button.";
            assert sortButton != null : "Can't find sort button.";
            assert searchButton != null : "Can't find search button.";
            assert textField != null : "Can't find text field.";

        }
        catch (AssertionError ae)
        {
            System.out.println("FXML assertion failure: " + ae.getMessage());
            Application.terminate();
        }

        System.out.println("Populating scene with items from the database..."); 
        @SuppressWarnings("unchecked")

        ObservableList<Song> songList = FXCollections.observableArrayList();  // Tables require a special type of list.
        Song.readAll(songList);             // Hand over control to the fruit model to populate this list.

        /* The first column is for the Fruit 'type' values */
        TableColumn<Song, String> SongNameColumn = new TableColumn<>("Song Name");
        SongNameColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("SongName"));
        SongNameColumn.setMinWidth(600);
        centralDB.getColumns().add(SongNameColumn);

        TableColumn<Song, String> GenreColumn = new TableColumn<>("Genre");
        GenreColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("Genre"));
        GenreColumn.setMinWidth(150);
        centralDB.getColumns().add(GenreColumn);

        TableColumn<Song, String> ArtistNameColumn = new TableColumn<>("Artist Name");
        ArtistNameColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("ArtistName"));
        ArtistNameColumn.setMinWidth(200);
        centralDB.getColumns().add(ArtistNameColumn);

        TableColumn<Song, String> ReleaseNameColumn = new TableColumn<>("Release Name");
        ReleaseNameColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("ReleaseName"));
        ReleaseNameColumn.setMinWidth(400);
        centralDB.getColumns().add(ReleaseNameColumn);

        TableColumn<Song, String> SongLengthColumn = new TableColumn<>("Song Length");
        SongLengthColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("SongLength"));
        SongLengthColumn.setMinWidth(150);
        centralDB.getColumns().add(SongLengthColumn); 

        centralDB.setItems(songList);
        
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

    @FXML   void refresh() throws SQLException{
        initialize();
    }
    
    @FXML   void addClicked()
    {
        System.out.println("Add was clicked!");        
    }

    @FXML   void refineClicked()
    {
        System.out.println("Refine was clicked!");        
    }

    @FXML   void sortClicked()
    {
        System.out.println("Sort was clicked!");        
    }

    @FXML   void searchClicked()
    {
        System.out.println("Search was clicked!"); 
        
        
    }

    @FXML   void tableViewClicked()
    {
        Song selectedItem = (Song) centralDB.getSelectionModel().getSelectedItem();

        if (selectedItem == null)
        {
            System.out.println("Nothing selected");
        }
        else
        {
            System.out.println(selectedItem +"(ID: " + selectedItem.getSongName() + ") is selected.");

            try
            {
                FXMLLoader loader = new FXMLLoader(Application.class.getResource("Scene2.fxml"));

                Stage stage = new Stage();
                stage.setTitle(selectedItem.getSongName());
                stage.setScene(new Scene(loader.load()));
                stage.show();
                
                SceneController2 controller2 = loader.getController();
                controller2.prepareStageEvents(stage, this, selectedItem.getSongName());
                
                controller2.setTempName(selectedItem.getSongName());

            }
            catch(Exception E)
            {
                System.out.println(E.getMessage());
            }
        }
    }
}

