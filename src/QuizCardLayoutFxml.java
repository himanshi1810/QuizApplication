import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Listeners.NewScreenListener;
import Models.Quiz;
import Models.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class QuizCardLayoutFxml implements Initializable{

    @FXML
    private Label title;

    @FXML
    private Label noq;

    @FXML
    private JFXButton starButton;

    private Quiz quiz;

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.title.setText(this.quiz.getTitle());
    }

    private NewScreenListener newScreenListener;
    private Student student;
     
    public void setStudent(Student student) {
        this.student = student;
    }

    public void setNewScreenListener(NewScreenListener newScreenListener) {
        this.newScreenListener = newScreenListener;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setNoq(String value)
    {
        this.noq.setText(value);
    }

   
    public void startQuiz(ActionEvent actionEvent)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("QuestionsScreenFxml.fxml"));
        try {
            Node node = fxmlLoader.load();
            QuestionsScreenFxmlController questionsScreenFxmlController = fxmlLoader.getController();
            questionsScreenFxmlController.setStudent(this.student);
            questionsScreenFxmlController.setQuiz(this.quiz);
            questionsScreenFxmlController.setScreenListener(this.newScreenListener);
            this.newScreenListener.ChangeScreen(node);
        } catch (IOException e) {
         
            e.printStackTrace();
        }
        
    }
}