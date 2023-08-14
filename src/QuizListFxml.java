import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.jfoenix.controls.JFXButton;

import Listeners.NewScreenListener;
import Models.Quiz;
import Models.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;


public class QuizListFxml implements Initializable
{
    @FXML 
    private FlowPane quizListContainer;

    Map<Quiz, Integer> quizzes = null;

    private NewScreenListener screenListener;
    private Set<Quiz> keys;

    private Student student;

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setScreenListener(NewScreenListener screenListener)
    {
        this.screenListener = screenListener;
       
    }

    public void setCards()
    {
        for(Quiz quiz : keys)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("QuizCardLayoutFxml.fxml"));

        try{
            // System.out.println("Hello");
            Node node =fxmlLoader.load();
            QuizCardLayoutFxml quizCardLayoutFxml = fxmlLoader.getController();
            quizCardLayoutFxml.setQuiz(quiz);
            quizCardLayoutFxml.setStudent(this.student);
            quizCardLayoutFxml.setNoq(quizzes.get(quiz) + "");
            quizCardLayoutFxml.setNewScreenListener(this.screenListener);
            quizListContainer.getChildren().add(node);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        quizzes =  Quiz.getAllWithQuestionCount();
         keys = quizzes.keySet();

        
        
    }
    
}