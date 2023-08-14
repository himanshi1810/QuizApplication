import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import Listeners.NewScreenListener;
import Models.Question;
import Models.Quiz;
import Models.QuizResult;
import Models.Student;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;



public class QuestionsScreenFxmlController implements Initializable {

    private class QuestionObservable {
        Property<String> question = new SimpleStringProperty();
        Property<String> option1 = new SimpleStringProperty();
        Property<String> option2 = new SimpleStringProperty();
        Property<String> option3 = new SimpleStringProperty();
        Property<String> option4 = new SimpleStringProperty();
        Property<String> answer = new SimpleStringProperty();

        public void setQuestion(Question question) {
            this.question.setValue(question.getQuestion());
            this.option1.setValue(question.getOption1());
            this.option2.setValue(question.getOption2());
            this.option3.setValue(question.getOption3());
            this.option4.setValue(question.getOption4());
            this.answer.setValue(question.getAnswer());
        }

    }

    @FXML
    private FlowPane progresspane;

    @FXML
    public Label title;

    @FXML
    public Label time;

     @FXML
    public Label question;

    @FXML
    private JFXButton nextButton;

    @FXML
    private JFXButton submitButton;

     @FXML
    public JFXRadioButton option1;

    @FXML
    public JFXRadioButton option2;

    @FXML
    public JFXRadioButton option3;

    @FXML
    public JFXRadioButton option4;

    @FXML
    public ToggleGroup options;

    //Listeners
    private NewScreenListener screenListener;




    //Non Fxml Fields
    private Quiz quiz;
    private List<Question> questionList;
    private Question currentQuestion;
    int currentIndex = 0;
    private QuestionObservable questionObservable;
    private Map<Question, String> studentAnswers = new HashMap<>();
    private Integer numberOfRightAnswers = 0;
    private Student student;

    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    //timer Fields
     private long min, sec, hr, totalSec = 0;
     private Timer timer;

    //Methods and Constructors
    public void setQuiz(Quiz quiz) 
    {
        this.quiz = quiz;
        this.title.setText(this.quiz.getTitle());
        this.getData();
    }

        private  String format(long value)
            {
                if(value < 10)
                {
                    return 0+""+value;
                }
                return value+"";
            }
    
        public void convertTime()
        {

           min = TimeUnit.SECONDS.toMinutes(totalSec);
           sec = totalSec - (min * 60);
           hr = TimeUnit.MINUTES.toHours(min);
           min = min - (hr * 60);

           time.setText(format(hr) + ":" + format(min) + ":" + format(sec));

           totalSec --;
        }

        private void setTimer()
        {
            totalSec = this.questionList.size() * 2;
            this.timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run()
                {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run()
                        {
                        System.out.println("After 1 sec....");
                            convertTime();
                            if(totalSec<=0)
                            {
                                timer.cancel();
                                time.setText("00:00:00");
                                //Saving data to database
                                submit(null);

                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Time Up");
                                alert.setHeaderText("Your Time is over Thank You");
                                alert.setHeight(100);
                                alert.setWidth(200);
                                alert.show();
                            

                            }
                        }
                    });
                }
            };
           
            timer.schedule(timerTask, 0 ,1000);
        }

    private void getData(){
        if(quiz  != null)
        {
            this.questionList = quiz.getQuestions();
            Collections.shuffle(this.questionList);
            renderProgress();
            setNextQuestion();
            setTimer();
            
        }
    }


    private void renderProgress()
    {
        for(int i = 0 ; i<this.questionList.size() ; i++)
       {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProgressCircleFxml.fxml"));
        try {
            Node node = fxmlLoader.load();
            ProgressCircleController progressCircleController = fxmlLoader.getController();
            progressCircleController.setNumber(i+1);
            progressCircleController.setDefaultColor();
            progresspane.getChildren().add(node);
        } catch (IOException e) {
       
            e.printStackTrace();
        }
       }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       this.shawNextQuestionButton();
       this.hideSubmitQuizButton();

       this.questionObservable = new QuestionObservable();

       bindFields();

       
       this.option1.setSelected(true);

       
    }

    private void bindFields()
    {
        this.question.textProperty().bind(this.questionObservable.question);
        this.option1.textProperty().bind(this.questionObservable.option1);
        this.option2.textProperty().bind(this.questionObservable.option2);
        this.option3.textProperty().bind(this.questionObservable.option3);
        this.option4.textProperty().bind(this.questionObservable.option4);
    } 

    
    public void nextQuestion(ActionEvent actionEvent)
    {

        boolean isRight = false;  

        //Checking Answer
        ToggleGroup options = new ToggleGroup();
        option1.setToggleGroup(options);
        option2.setToggleGroup(options);
        option3.setToggleGroup(options);
        option4.setToggleGroup(options);
        JFXRadioButton selectedButton = (JFXRadioButton)options.getSelectedToggle();
        String userAnswer = selectedButton.getText();
        String rightAnswer = this.currentQuestion.getAnswer();
        if(userAnswer.trim().equalsIgnoreCase(rightAnswer.trim()))
        {
            isRight = true;
            this.numberOfRightAnswers++ ;
        }

        //Saving Answer to hashmap
        studentAnswers.put(this.currentQuestion, userAnswer);

        Node circleNode = this.progresspane.getChildren().get(currentIndex - 1);  
        ProgressCircleController controller = (ProgressCircleController)circleNode.getUserData(); 

        if(isRight)
        {
           
            controller.setRightAnsweredColor();

        }
        else
        {
            controller.setWrongAnsweredColor();
        }
        this.setNextQuestion();
    }

    private void setNextQuestion()
    {
        if(!(currentIndex >= questionList.size()))
        {
            //Changing the color
            Node circleNode = this.progresspane.getChildren().get(currentIndex);  
            ProgressCircleController controller = (ProgressCircleController)circleNode.getUserData(); 
            controller.setCurrentQuestionColor();

            this.currentQuestion = this.questionList.get(currentIndex);
            List<String> options = new ArrayList<>();
            options.add(this.currentQuestion.getOption1());
            options.add(this.currentQuestion.getOption2());
            options.add(this.currentQuestion.getOption3());
            options.add(this.currentQuestion.getOption4());
            Collections.shuffle(options);

            this.currentQuestion.setOption1(options.get(0));
            this.currentQuestion.setOption2(options.get(1));
            this.currentQuestion.setOption3(options.get(2));
            this.currentQuestion.setOption4(options.get(3));

            // this.question.setText(this.currentQuestion.getQuestion());
            // this.option1.setText(options.get(0));
            // this.option2.setText(options.get(1));
            // this.option3.setText(options.get(2));
            // this.option4.setText(options.get(3));

            this.questionObservable.setQuestion(this.currentQuestion);
            currentIndex ++;  
        }
        else{
            hideNextQuestionButton();
            shawSubmitQuizButton();
        }
        
    }

    private void shawNextQuestionButton()
    {
        this.nextButton.setVisible(true);
    }

    private void shawSubmitQuizButton()
    {
        this.submitButton.setVisible(true);
    }

    private void hideNextQuestionButton()
    {
        this.nextButton.setVisible(false);
    }

    private void hideSubmitQuizButton()
    {
        this.submitButton.setVisible(false);
    }

    public void submit(ActionEvent actionEvent)
    {
        System.out.println(this.studentAnswers);

        QuizResult quizResult = new QuizResult(this.quiz, student,numberOfRightAnswers);
        boolean result = quizResult.save(this.studentAnswers);
        if(result)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("You Successfully Attempted Quiz");
            alert.setHeight(100);
            alert.setWidth(200);
            alert.show();
            timer.cancel();
            openResultScreen();
        }
        else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something Went Wrong");
            alert.setHeight(100);
            alert.setWidth(200);
            alert.show();
        }
    }

    private void openResultScreen()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("QuizResultScreenFxml.fxml"));
        try {
            Node node = fxmlLoader.load();
            QuizResultScreenController quizResultScreenController = fxmlLoader.getController();
            quizResultScreenController.setValues(this.studentAnswers, numberOfRightAnswers, quiz, questionList);
            this.screenListener.removeTopScreen();
            this.screenListener.ChangeScreen(node);
        } catch (IOException e) {
         
            e.printStackTrace();
        }
        

    }
}

