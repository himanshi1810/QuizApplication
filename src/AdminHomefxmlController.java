

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class AdminHomefxmlController implements Initializable {
    @FXML
    private TabPane adminTabPane;

    @FXML
    private Tab addQuizTab;

    @FXML
    private Tab addStudentTab;

    @FXML
    private Tab logOutTab;


   


    @Override
    public void initialize(URL location, ResourceBundle resources) {
 
        //throw new UnsupportedOperationException("Unimplemented method initialize");
        
        try {
           Parent node = FXMLLoader.load(getClass().getResource("AddQuiz.fxml"));
            addQuizTab.setContent(node);

            Parent studentNode = FXMLLoader.load(getClass().getResource("adminStudentTab.fxml"));
            addStudentTab.setContent(studentNode);

      
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        
    }

    
}
