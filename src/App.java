import Models.Question;
import Models.Quiz;
import Models.QuizResult;
import Models.QuizResultDetails;
import Models.Student;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class App extends Application {
        
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        createTables();
        Parent root = FXMLLoader.load(getClass().getResource("loginFxml.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("adminHome.fxml"));
    
        Stage stage = new Stage();
        stage.setTitle("Quiz Application");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        // stage.setMaximized(true);
        stage.show();

        
    }

    public void createTables() throws ClassNotFoundException
    {
        Quiz.createTable();
        Question.createTable();
        Student.createTable();
        QuizResult.createTable();
        QuizResultDetails.createTable();
    }
}

