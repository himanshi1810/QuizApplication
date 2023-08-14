import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Listeners.NewScreenListener;
import Models.Student;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class StudentMainScreen implements Initializable
{
    @FXML
    private JFXButton backButton;

    @FXML
    private StackPane stackPanel;

    private Student student;

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
       
    }
    public void setStudent(Student student) {
        this.student = student;
         addQuizListScreen();
    }

    private void addScreenToStackPane(Node node)
    {
        this.stackPanel.getChildren().add(node);
    }

    private void addQuizListScreen()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("QuizListFxml.fxml"));

        try{
            Node node =fxmlLoader.load();
            QuizListFxml quizListFxml = fxmlLoader.getController();
            quizListFxml.setStudent(this.student);
            quizListFxml.setScreenListener(new NewScreenListener() {

                @Override
                public void handle(Event arg0) {
                    
                }

                @Override
                public void ChangeScreen(Node node) {
                   addScreenToStackPane(node);
                }

                @Override
                public void removeTopScreen() {
                   stackPanel.getChildren().remove(stackPanel.getChildren().size() - 1);
                } 
                
            });
            quizListFxml.setCards();
            stackPanel.getChildren().add(node);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
   

    }
     public void back(ActionEvent actionEvent)
     {
        ObservableList<Node> nodes = this.stackPanel.getChildren();
        if(nodes.size() == 1)
        {
            return;
        }
        this.stackPanel.getChildren().remove(nodes.size() - 1);
     }
    
}
