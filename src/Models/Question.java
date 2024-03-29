package Models;

import Models.Quiz;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Question {
    private Quiz quiz;
    private Integer questionId;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;


    public static class MetaData
    {
        public static final String TABLE_NAME = "questions"; 
        public static final String QUESTION_ID = "id";
        public static final String QUESTION = "question";
        public static final String OPTION1 = "option1"; 
        public static final String OPTION2 = "option2"; 
        public static final String OPTION3 = "option3"; 
        public static final String OPTION4 = "option4"; 
        public static final String ANSWER = "answer"; 
        public static final String QUIZ_ID = "quiz_id"; 
      
    }

    //Default Constructor
    public Question()
    {

    }

    //Constructor
    public Question( Quiz quiz,  String question,String option1,String option2, String option3,String option4,String answer)
    {
        this.quiz = quiz;
        this.question =question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
    }
       
    @Override 
    public String toString()
    {
        return this.question;
    }
    
    // Gettter Methods
        public Integer getQuiz()
        {
            return quiz.getQuizId();
        }

        public Integer getQuestionId()
        {
            return questionId;
        }

        public String getQuestion()
        {
            return question;
        }

        public String getOption1()
        {
            return option1;
        }

        public String getOption2()
        {
            return option2;
        }

        public String getOption3()
        {
            return option3;
        }

        public String getOption4()
        {
            return option4;
        }

        public String getAnswer()
        {
            return answer;
        }

        //Setter Methods
        public void setQuiz(Quiz quiz)
        {
            this.quiz= quiz;
        }

        public void setQuestionId(Integer questionId)
        {
            this.questionId= questionId;
        }

        public void setQuestion(String question)
        {
            this.question = question;
        }

        public void setOption1(String option1)
        {
            this.option1 = option1;
        }

        public void setOption2(String option2)
        {
            this.option2 = option2;
        }

        public void setOption3(String option3)
        {
            this.option3 = option3;
        }

        public void setOption4(String option4)
        {
            this.option4 = option4;
        }

        public void setAnswer(String answer)
        {
            this.answer = answer;
        }
       
        public static void createTable() throws ClassNotFoundException
        {
            
            try{
                String raw = "CREATE TABLE %s ( id INTEGER PRIMARY KEY,question VARCHAR(1000), %s VARCHAR(500), %s VARCHAR(500),%s VARCHAR(500),option4 VARCHAR(500),%s VARCHAR(500),%s INTEGER ,FOREIGN KEY (%s) REFERENCES %s(%s) )";

                String query = String.format(raw,MetaData.TABLE_NAME,MetaData.OPTION1,MetaData.OPTION2,MetaData.OPTION3,MetaData.ANSWER, MetaData.QUIZ_ID,MetaData.QUIZ_ID,Quiz.MetaData.TABLE_NAME,Quiz.MetaData.QUIZ_ID);

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

        public boolean save()
        {
            boolean b =false;
            try{

             
                String raw = "INSERT INTO %s (question,%s,%s,%s,%s,%s,%s) VALUES (?, ?, ?, ?, ?, ?, ?)";
                String query = String.format(raw, MetaData.TABLE_NAME, MetaData.OPTION1,MetaData.OPTION2,MetaData.OPTION3,MetaData.OPTION4,MetaData.ANSWER,MetaData.QUIZ_ID);
                System.out.println("Actual Query = " +query);

               String connectionUrl = "jdbc:sqlite:Quiz.db";
               Class.forName("org.sqlite.JDBC");
               Connection connection = DriverManager.getConnection(connectionUrl);

               PreparedStatement ps =connection.prepareStatement(query);
               ps.setString(1, this.question);
               ps.setString(2, this.option1);
               ps.setString(3, this.option2);
               ps.setString(4, this.option3);
               ps.setString(5, this.option4);
               ps.setString(6, this.answer);
               ps.setInt(7,this.quiz.getQuizId() );
               
               int i = ps.executeUpdate();

               System.out.println("Updated Rows :-  " +i);

               connection.close();
               b = true;
           }
           catch(Exception error)
           {
               error.printStackTrace();
               return false;
            
           }
            return b;
    
       
        }

}
