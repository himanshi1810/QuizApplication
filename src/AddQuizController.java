

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.management.Notification;

import com.jfoenix.controls.JFXTreeView;

import Models.Question;
import Models.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.Alert.AlertType;

public class AddQuizController implements Initializable {

    @FXML
    private JFXTreeView treeView;

    @FXML
    private TextField quizTitle;

    @FXML
    private TextArea question;

    @FXML
    private TextField option1;

    @FXML
    private TextField option2;

    @FXML
    private TextField option3;

    @FXML
    private TextField option4;

    @FXML
    private RadioButton option2Radio;

    @FXML
    private RadioButton option3Radio;

    @FXML
    private RadioButton option4Radio;

    @FXML
    private RadioButton option1Radio;

    @FXML
    private Button addNextQuestion;

    @FXML
    private Button submitQuiz;

    @FXML
    private Button setQuizTitleButton;
   
    private ToggleGroup radioGroup;

    //My Variables
    public Quiz quiz = null;
    public ArrayList<Question> questions = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        radioButtonSetup();
        renderTreeView();
    }

    private void renderTreeView()
    {
       Map<Quiz, List<Question>> data = Quiz.getAll();
       Set<Quiz> quizzes = data.keySet();

       TreeItem root = new TreeItem("Quizzes");
       for(Quiz q : quizzes)
       {
        TreeItem quizTreeItem = new TreeItem(q);

        List<Question> questions = data.get(q);
        for(Question question : questions)
        {
            TreeItem questionTreeItem = new TreeItem(question);
            questionTreeItem.getChildren().add(new TreeItem("A : " + question.getOption1()));
            questionTreeItem.getChildren().add(new TreeItem("B : " +question.getOption2()));
            questionTreeItem.getChildren().add(new TreeItem("C : " +question.getOption3()));
            questionTreeItem.getChildren().add(new TreeItem("D : " +question.getOption4()));
            questionTreeItem.getChildren().add(new TreeItem("Ans : " +question.getAnswer()));
            quizTreeItem.getChildren().add(questionTreeItem);
        }
        
        root.getChildren().add(quizTreeItem);
       }

    //    quizTreeItem.setExpanded(true);
       root.setExpanded(true);
       this.treeView.setRoot(root);
    }

    private void radioButtonSetup()
    {
        radioGroup = new ToggleGroup();
        option1Radio.setToggleGroup(radioGroup);
        option2Radio.setToggleGroup(radioGroup);
        option3Radio.setToggleGroup(radioGroup);
        option4Radio.setToggleGroup(radioGroup);
    }

    @FXML
    private void setQuizTitle(ActionEvent e)
    {
        System.out.println("Set Quiz Title");
        String title = quizTitle.getText();


        if(title.trim().isEmpty()){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Quiz Title");
            alert.setHeaderText("Enter valid Quiz title");
            alert.setHeight(200);
            alert.setWidth(200);
            alert.show();
            System.out.println("Enter Valid Quiz Title");
        }
        else{

            quizTitle.setEditable(false);
            System.out.println("Save Title.......");
            this.quiz = new Quiz(title);
        }
    }

    private boolean validateFields()
    {

        if(quiz == null)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Quiz");
            alert.setHeaderText("Please Enter quiz Title");
            alert.setHeight(200);
            alert.setWidth(200);
            alert.show(); 
            return false;  
        }

        String question = this.question.getText();
        String op1 = this.option1.getText();
        String op2 = this.option2.getText();
        String op3 = this.option3.getText();
        String op4 = this.option4.getText();
        Toggle selectedRadio = radioGroup.getSelectedToggle();
        System.out.println(selectedRadio);

        if(question.trim().isEmpty() || op1.trim().isEmpty() || op2.trim().isEmpty() || op3.trim().isEmpty()||op4.trim().isEmpty() )
        {
            // String message = "Select An Answer";
        Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Question");
            alert.setHeaderText("All Fields are Required");
            alert.setHeight(200);
            alert.setWidth(200);
            alert.setContentText("Question,Option1,Option2,Option3,Option4");
            alert.show(); 
            return false;  
        }

        else
        {
            if(selectedRadio == null)
            {
                // System.out.println("Hello Error occur");
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Question");
                alert.setHeaderText("Please Select The Answer");
                alert.setHeight(100);
                 alert.setWidth(200);
                alert.show();
                return false;
                
            }

            else
            {
               return true;
            }
        }

    }

    public void addNextQuestion(ActionEvent e)
    {
       addQuestions();
    }

    public boolean addQuestions()
    {
        boolean valid = validateFields();
       Question question = new Question();
        if(valid)
        {
            //save
            
            question.setOption1(option1.getText().trim());
            question.setOption2(option2.getText().trim());
            question.setOption3(option3.getText().trim());
            question.setOption4(option4.getText().trim());


            Toggle selected = radioGroup.getSelectedToggle();

            String ans = null;

            if(selected == option1Radio)
            {
                ans = option1.getText().trim();
            }

            else if(selected == option2Radio)
            {
                ans = option2.getText().trim();
            }

            else if(selected == option3Radio)
            {
                ans = option3.getText().trim();
            }

            else if(selected == option4Radio)
            {
                ans = option4.getText().trim();
            }

           question.setAnswer(ans);
           question.setQuestion(this.question.getText().trim());
            
            this.question.clear();
            option1.clear();
            option2.clear();
            option3.clear();
            option4.clear();
            

            

            questions.add( question);

            question.setQuiz(quiz);

            System.out.println("Save Question----");
            System.out.println(questions);
            System.out.println(quiz);


            
        }

        return valid;
    }
    public void submitQuiz(ActionEvent e)
    {
        boolean flag =addQuestions();
        if(flag)
        {
            flag = quiz.save(questions);
            if(flag){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("QUIZ SUCCESSFULLY SAVED");
                alert.setHeight(200);
                 alert.setWidth(200);
                alert.show();
            }
            else
            {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Fail");
                alert.setHeaderText("CANNOT SAVE QUIZ TRY AGAIN");
                alert.setHeight(200);
                 alert.setWidth(200);
                alert.show();
            }
            quizTitle.clear();
            quizTitle.setEditable(true);
        }
       
        
        
    }
    
}