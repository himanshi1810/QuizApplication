package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

public class QuizResult {
   private Integer id;
   private Quiz quiz;
   private Student student;
   private Integer rightAnswers ;
   private Timestamp timestamp; 

      public static class MetaData
    {
        public static final String TABLE_NAME = "quiz_results"; 
        public static final String STUDENT_ID = "student_id";
        public static final String ID = "quiz_result_id";
        public static final String QUIZ_ID = "quiz_id"; 
        public static final String RIGHT_ANSWERS = "rightAnswers";
        public static final String TIMESTAMP = "date_time";
      
    }

   //Intialize block (Instance Block)
   {
     timestamp = new Timestamp(new Date(0).getTime());
   }

   public QuizResult()
   {

   }

   public QuizResult(Integer id,Quiz quiz, Student student , Integer rightAnswers)
   {
    this.id = id;
    this.quiz = quiz;
    this.student = student;
    this.rightAnswers = rightAnswers;
   }

   public QuizResult(Quiz quiz, Student student , Integer rightAnswers)
   {
    this.quiz = quiz;
    this.student = student;
    this.rightAnswers = rightAnswers;
   }

   public void setId(Integer id) {
        this.id = id;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setRightAnswers(Integer rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

   public Integer getId()
    {
        return id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public Student getStudent() {
        return student;
    }

    public Integer getRightAnswers() {
        return rightAnswers;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public static void createTable()
    {
         try{
                String raw = "    CREATE TABLE %s(\r\n" + 
                        "  %s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" + 
                        "  %s INT NOT NULL,\r\n" + 
                        "  %s INT NOT NULL,\r\n" +
                        " %s INT NOT NULL ,\r\n" + 
                        "  %s TIMESTAMP NOT NULL,\r\n" + 
                        "  FOREIGN KEY (%s) REFERENCES %s(%s),\r\n" + 
                        "  FOREIGN KEY (%s) REFERENCES %s(%s) );";

                String query = String.format(raw,MetaData.TABLE_NAME,
                                            MetaData.ID,
                                            MetaData.QUIZ_ID,
                                            MetaData.STUDENT_ID,
                                            MetaData.RIGHT_ANSWERS,
                                            MetaData.TIMESTAMP,
                                            MetaData.QUIZ_ID,
                                            Quiz.MetaData.TABLE_NAME,
                                            Quiz.MetaData.QUIZ_ID,
                                            MetaData.STUDENT_ID,
                                            Student.MetaData.TABLE_NAME,
                                            Student.MetaData.ID);

                System.err.println(query);

                String connectionUrl = "jdbc:sqlite:Quiz.db";
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection(connectionUrl);
                PreparedStatement ps =connection.prepareStatement(query);
                boolean b = ps.execute();
                System.out.println("Question Table is Created");
                System.out.println(b);
            }
            catch(Exception error)
            {
                error.printStackTrace();
            }

     
    }

    public boolean save(Map<Question, String> userAnswers)
    {
        try{
                String raw = "INSERT INTO %s (%s,%s,%s,%s) VALUES (?,?,?,CURRENT_TIMESTAMP);";

                String query = String.format(raw,MetaData.TABLE_NAME,
                                            MetaData.STUDENT_ID,
                                            MetaData.QUIZ_ID,
                                            MetaData.RIGHT_ANSWERS,
                                            MetaData.TIMESTAMP);

                System.err.println(query);

                String connectionUrl = "jdbc:sqlite:Quiz.db";
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection(connectionUrl);
                PreparedStatement ps =connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, this.getStudent().getId());
                ps.setInt(2, this.getQuiz().getQuizId());
                ps.setInt(3, this.getRightAnswers());
               
                int result = ps.executeUpdate();
                if(result > 0)
                {
                   ResultSet keys = ps.getGeneratedKeys();
                   if(keys.next())
                   {
                    this.setId(keys.getInt(1));
                    // Now save Details
                    System.out.println(this);
                    return saveQuizResultDetails(userAnswers);
                   }
                }
            }
            catch(Exception error)
            {
                error.printStackTrace();
                return false;
            }

            return false;
    }

    private boolean saveQuizResultDetails(Map<Question, String> userAnswers)
    {
        return QuizResultDetails.saveQuizResultDetails(this , userAnswers);
    }
}



