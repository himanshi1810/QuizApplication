package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Quiz {
 
private Integer quizId;
 private String title;

 public static class MetaData
 {
     public static final String TABLE_NAME = "quizs"; 
     public static final String QUIZ_ID = "quiz_id"; 
     public static final String TITLE = "title"; 
 }

 //Default Constructor
 public Quiz()
 {

 }
 
 //Constructor
 public Quiz(String title)
 {
    this.title = title;
 }
  
 //Getter Methods
 public Integer getQuizId()
 {
    return quizId;
 }

 public String getTitle()
 {
    return title;
 }

 //Setter Methods
 public void setQuizId(Integer quizId)
 {
    this.quizId = quizId;
 }

 public void setTitle(String title)
 {
    this.title = title;
 }

 @Override
 public String toString()
 {
    return this.title;
 }

 //Other Methods

 public static void createTable() throws ClassNotFoundException
 {
    try{

      String raw = "CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s VARCHAR(50) )";

        String query = String.format(raw, MetaData.TABLE_NAME,MetaData.QUIZ_ID,MetaData.TITLE);

        System.out.println(query);

        String connectionUrl = "jdbc:sqlite:Quiz.db";
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection(connectionUrl);
        PreparedStatement ps =connection.prepareStatement(query);
        boolean b = ps.execute();
        System.out.println("Quiz Table is Created");
        System.out.println(b);
        connection.close();
    }
    catch(Exception error)
    {
        error.printStackTrace();
    }
   }


    public int save()
    {
      try{


            String raw = "Insert into %s (%s) values (?)";
            String query = String.format(raw, MetaData.TABLE_NAME,MetaData.TITLE);
   
           String connectionUrl = "jdbc:sqlite:Quiz.db";
           Class.forName("org.sqlite.JDBC");
           Connection connection = DriverManager.getConnection(connectionUrl);
           PreparedStatement ps =connection.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
           ps.setString(1, this.title);
          int i = ps.executeUpdate();
           ResultSet keys = ps.getGeneratedKeys();

           if(keys.next())
           {
            return keys.getInt(1);
           }
           connection.close();
       }
       catch(Exception error)
       {
           error.printStackTrace();
           return -1;
       }

       return -1;
    }


    public boolean save(List<Question> questions)
    {
      boolean flag = true;
      this.quizId = this.save();

      for(Question q : questions){
         flag = flag && q.save();
         System.out.println(flag);
      }
      
      return flag;

    }

 public static Map<Quiz, List<Question>> getAll()
 {
   Map<Quiz, List<Question>> quizes = new HashMap<>();
   Quiz key = null;
   

   try{

      String query = String.format("SELECT %s.%s,%s,%s,%s,%s,%s,%s,%s,%s FROM %s Join %s on %s.%s = %s.%s", MetaData.TABLE_NAME,MetaData.QUIZ_ID,MetaData.TITLE,Question.MetaData.QUESTION_ID,Question.MetaData.QUESTION,Question.MetaData.OPTION1,Question.MetaData.OPTION2,Question.MetaData.OPTION3,Question.MetaData.OPTION4,Question.MetaData.ANSWER,MetaData.TABLE_NAME,Question.MetaData.TABLE_NAME,Question.MetaData.TABLE_NAME,Question.MetaData.QUIZ_ID,MetaData.TABLE_NAME,MetaData.QUIZ_ID);

      String connectionUrl = "jdbc:sqlite:Quiz.db";
      System.out.println(query);
      

      Class.forName("org.sqlite.JDBC");
      Connection connection = DriverManager.getConnection(connectionUrl);
      PreparedStatement ps =connection.prepareStatement(query);
      
      ResultSet result = ps.executeQuery();

        while(result.next())
        {
            Quiz temp = new Quiz();
            temp.setQuizId(result.getInt(1));
            temp.setTitle(result.getString(2));
            
            Question tempQuestion =  new Question();
            tempQuestion.setQuestionId(result.getInt(3));
            tempQuestion.setQuestion(result.getString(4));
            tempQuestion.setOption1(result.getString(5));
            tempQuestion.setOption2(result.getString(6));
            tempQuestion.setOption3(result.getString(7));
            tempQuestion.setOption4(result.getString(8));
            tempQuestion.setAnswer(result.getString(9));

         if(key != null && key.equals(temp))
         {
            quizes.get(key).add(tempQuestion);
         }
         else
         {
            ArrayList<Question> value = new ArrayList<>();
            value.add(tempQuestion);
            quizes.put(temp,value );
         }

            key =temp;
        }
            connection.close();
         }
      catch(Exception error)
      {
         error.printStackTrace();
       
      }
return quizes;
 }

 public static Map<Quiz, Integer> getAllWithQuestionCount()
 {
   Map<Quiz, Integer> quizes = new HashMap<>();
   Quiz key = null;
   

   try{

      String query = String.format("SELECT %s.%s,%s,COUNT(*) as question_count FROM %s INNER JOIN %s ON %s.%s = %s.%s GROUP by quizs.quiz_id;", MetaData.TABLE_NAME,MetaData.QUIZ_ID,MetaData.TITLE,MetaData.TABLE_NAME,Question.MetaData.TABLE_NAME,MetaData.TABLE_NAME,MetaData.QUIZ_ID,Question.MetaData.TABLE_NAME,Question.MetaData.QUIZ_ID);

      String connectionUrl = "jdbc:sqlite:Quiz.db";
      System.out.println(query);
      

      Class.forName("org.sqlite.JDBC");
      Connection connection = DriverManager.getConnection(connectionUrl);
      PreparedStatement ps =connection.prepareStatement(query);
      
      ResultSet result = ps.executeQuery();

        while(result.next())
        {
            Quiz temp = new Quiz();
            temp.setQuizId(result.getInt(1));
            temp.setTitle(result.getString(2));
            int count = result.getInt(3);
            quizes.put(temp, count);
        }
            connection.close();
         }
      catch(Exception error)
      {
         error.printStackTrace();
       
      }
return quizes;
 }


 //Get Questions using Quiz
public List<Question> getQuestions()
 {
   List<Question> quizes = new ArrayList<>();

   try{

      String query = String.format("SELECT %s,%s,%s,%s,%s,%s,%s FROM %s WHERE %s = ?",Question.MetaData.QUESTION_ID,Question.MetaData.QUESTION,Question.MetaData.OPTION1,Question.MetaData.OPTION2,Question.MetaData.OPTION3,Question.MetaData.OPTION4,Question.MetaData.ANSWER,Question.MetaData.TABLE_NAME,Question.MetaData.QUIZ_ID);

      String connectionUrl = "jdbc:sqlite:Quiz.db";
      System.out.println(query);
      

      Class.forName("org.sqlite.JDBC");
      Connection connection = DriverManager.getConnection(connectionUrl);
      PreparedStatement ps =connection.prepareStatement(query);
      ps.setInt(1, this.quizId);
      ResultSet result = ps.executeQuery();

        while(result.next())
        {
            Question tempQuestion =  new Question();
            tempQuestion.setQuestionId(result.getInt(1));
            tempQuestion.setQuestion(result.getString(2));
            tempQuestion.setOption1(result.getString(3));
            tempQuestion.setOption2(result.getString(4));
            tempQuestion.setOption3(result.getString(5));
            tempQuestion.setOption4(result.getString(6));
            tempQuestion.setAnswer(result.getString(7));

            tempQuestion.setQuiz(this);
            quizes.add(tempQuestion);

        }
            connection.close();
         }
      catch(Exception error)
      {
         error.printStackTrace();
       
      }
return quizes;
 }


 @Override
 public boolean equals(Object obj)
 {
   if(obj == null)
   return false;

   if(!(obj instanceof Quiz))
   return false;
 
   Quiz t = (Quiz)obj;
   if(this.quizId == t.quizId)
   {
      return true;
   }

   return false;
 }

 @Override
 public int hashCode()
 {
   return Objects.hash(quizId,title);
 }
}
