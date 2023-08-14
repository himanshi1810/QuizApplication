package Models;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;

public class QuizResultDetails {
    
    private Integer id;
    private QuizResult quizResult;
    private Question question;
    private String userAnswer;

    public static class MetaData
    {
        public static final String TABLE_NAME = "quiz_result_details";
        public static final String ID = "id";
        public static final String QUESTION_ID = "question_id"; 
        public static final String USER_ANSWERS = "userAnswers";
        public static final String QUIZ_RESULT_ID = "quiz_result_id"; 
    }

    public QuizResultDetails(Integer id , QuizResult quizResult,Question question, String userAnswer)
    {
        this.id = id;
        this.quizResult = quizResult;
        this.question = question;
        this.userAnswer= userAnswer;
    }

     public QuizResultDetails(QuizResult quizResult,Question question, String userAnswer)
    {
        this.quizResult = quizResult;
        this.question = question;
        this.userAnswer= userAnswer;
    }

    public QuizResultDetails()
    {

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setQuizResult(QuizResult quizResult) {
        this.quizResult = quizResult;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Integer getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public QuizResult getQuizResult() {
        return quizResult;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

   public static void createTable()
    {
        try {
        String raw = "CREATE TABLE %s (%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, %s NOT NULL, %s INT NOT NULL, %s VARCHAR(200) NOT NULL, FOREIGN KEY (%s) REFERENCES %s (%s), FOREIGN KEY (%s) REFERENCES %s (%s))";

        String query = String.format(raw, MetaData.TABLE_NAME, 
                                        MetaData.ID, 
                                        MetaData.QUIZ_RESULT_ID, 
                                        MetaData.QUESTION_ID, 
                                        MetaData.USER_ANSWERS, 
                                        MetaData.QUIZ_RESULT_ID, 
                                        QuizResult.MetaData.TABLE_NAME, 
                                        QuizResult.MetaData.ID, 
                                        MetaData.QUESTION_ID, 
                                        Question.MetaData.TABLE_NAME,
                                        Question.MetaData.QUESTION_ID);

        System.err.println(query);

        String connectionUrl = "jdbc:sqlite:Quiz.db";
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection(connectionUrl);
        PreparedStatement ps = connection.prepareStatement(query);
        boolean b = ps.execute();

        System.out.println("ResultDetails Table is Created");
        System.out.println(b);
        } 
        catch (Exception error) {
        error.printStackTrace();
        }


    }

    public static boolean saveQuizResultDetails(QuizResult quizResult,Map<Question, String> userAnswers)
    {
        try{
                String raw = "INSERT INTO %s (%s,%s,%s) VALUES  (?,?,?);";

                String query = String.format(raw,MetaData.TABLE_NAME,
                                            MetaData.QUIZ_RESULT_ID,
                                            MetaData.QUESTION_ID,
                                            MetaData.USER_ANSWERS
                                            );

                System.err.println(query);

                String connectionUrl = "jdbc:sqlite:Quiz.db";
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection(connectionUrl);
                PreparedStatement ps =connection.prepareStatement(query);

                Set<Question> questions = userAnswers.keySet() ;
                for(Question question : questions)
              {
                ps.setInt(1, quizResult.getId());
                ps.setInt(2, question.getQuestionId());
                ps.setString(3, userAnswers.get(question));
                ps.addBatch();
              }

            int[] result =  ps.executeBatch();
            if(result.length>0)
            {
                return true;
            }
            }
            catch(Exception error)
            {
                error.printStackTrace();
                return false;
            }
        return false;
    }
}
