import java.net.URL;
import java.util.ResourceBundle;


import Models.Question;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class QuizResultSingleQuestion implements Initializable {

    @FXML
    private Label question;

    @FXML
    private Label option1;

    @FXML
    private Label option2;

    @FXML
    private Label option3;

    @FXML
    private Label option4;

    
    private Question questionObject;
    private String userAnswer;

    public void setValues(Question questionObject, String userAnswer)
    {
        this.questionObject = questionObject;
        if(userAnswer == null)
            userAnswer = "";
        else
            this.userAnswer = userAnswer;
        this.userAnswer = userAnswer;

        setText();
    }

    private void setText()
    {
        this.question.setText(this.questionObject.getQuestion());
        this.option1.setText(this.questionObject.getOption1());
        this.option2.setText(this.questionObject.getOption2());
        this.option3.setText(this.questionObject.getOption3());
        this.option4.setText(this.questionObject.getOption4());

        if(option1.getText().trim().equalsIgnoreCase(this.questionObject.getAnswer()))
        {
            option1.setTextFill(Color.web("#3DBE29"));
        }
        else if(option2.getText().trim().equalsIgnoreCase(this.questionObject.getAnswer()))
        {
            option2.setTextFill(Color.web("#3DBE29"));
        }
        else if(option3.getText().trim().equalsIgnoreCase(this.questionObject.getAnswer()))
        {
            option3.setTextFill(Color.web("#3DBE29"));
        }
        else if(option4.getText().trim().equalsIgnoreCase(this.questionObject.getAnswer()))
        {
            option4.setTextFill(Color.web("#3DBE29"));
        }

         if(!userAnswer.trim().equalsIgnoreCase(this.questionObject.getAnswer().trim()))
        {
            if(option1.getText().trim().equalsIgnoreCase(this.userAnswer))
        {
            option1.setTextFill(Color.web("#DE4839"));
        }
        else if(option2.getText().trim().equalsIgnoreCase(this.userAnswer))
        {
            option2.setTextFill(Color.web("#DE4839"));
        }
        else if(option3.getText().trim().equalsIgnoreCase(this.userAnswer))
        {
            option3.setTextFill(Color.web("#DE4839"));
        }
        else if(option4.getText().trim().equalsIgnoreCase(this.userAnswer))
        {
            option4.setTextFill(Color.web("#DE4839"));
        }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
    
    
}
