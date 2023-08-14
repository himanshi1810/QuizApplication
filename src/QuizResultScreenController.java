import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import Models.Question;
import Models.Quiz;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;

public class QuizResultScreenController implements Initializable {

    @FXML
    private PieChart attemptedChart;

    @FXML
    private PieChart scoreChart;

    @FXML
    private VBox questionsContainer;

    //Non fxml Variables
    private Map<Question, String> userAnswers;
    private Integer numberOfRightAnswers = 0;
    private Quiz quiz;
    private List<Question> questionList;
    private Integer notAttempted =0;
    private Integer attempted = 0;

 
    public void setValues(Map<Question , String> userAnswers,Integer numberOfRightAnswers,Quiz quiz, List<Question> questionList)
    {
        this.userAnswers = userAnswers;
        this.numberOfRightAnswers = numberOfRightAnswers;
        this.quiz = quiz;
        this.questionList = questionList;

        this.attempted = this.userAnswers.keySet().size();
        this.notAttempted = this.questionList.size() - attempted;
        setValuesToChart();
        renderQuestions();
    }

    private void renderQuestions()
    {
         for(int i = 0 ; i<this.questionList.size() ; i++)
       {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("QuizResultSingleQuestionFxml.fxml"));
        try {
            Node node = fxmlLoader.load();
            QuizResultSingleQuestion quizResultSingleQuestion = fxmlLoader.getController();
            quizResultSingleQuestion.setValues(this.questionList.get(i), this.userAnswers.get(this.questionList.get(i)));
            questionsContainer.getChildren().add(node);
            questionsContainer.requestLayout();
        } catch (IOException e) {
         
            e.printStackTrace();
        }
       }
    }
    
    private void setValuesToChart()
    {
       ObservableList<PieChart.Data> attemptedData = this.attemptedChart.getData();
       attemptedData.add(new PieChart.Data(String.format("Attempted (%d)",this.attempted),this.attempted));
       attemptedData.add(new PieChart.Data(String.format("Not Attempted (%d)",this.notAttempted),this.notAttempted));

        ObservableList<PieChart.Data> scoreChartData = this.scoreChart.getData();
        scoreChartData.add(new PieChart.Data(
            String.format("Right Answers (%d)",this.numberOfRightAnswers),this.numberOfRightAnswers));
        scoreChartData.add(new PieChart.Data(
            String.format("Wrong Answers (%d)",this.attempted - this.numberOfRightAnswers),this.attempted - this.numberOfRightAnswers));


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      
    }
    
}
