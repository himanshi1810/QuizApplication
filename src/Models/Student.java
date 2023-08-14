package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Exceptions.LoginException;

public class Student 
{
    public Integer id;
    public String firstName;
    public String lastName;
    public String mobile;
    public Character gender;
    public String email;
    public String password;

   
    public Student()
    {

    }

    public class MetaData{
        public static final String TABLE_NAME = "studets"; 
        public static final String ID = "id";
        public static final String FIRST_NAME = "firstName"; 
        public static final String LAST_NAME = "lastNmae"; 
        public static final String MOBILE = "mobile"; 
        public static final String GENDER = "gender"; 
        public static final String EMAIL = "email"; 
        public static final String PASSWORD = "password"; 
      
    }

    public Student(String email,String password)
    {
        this.email = email;
        this.password = password;
    }

    public Student(String firstName, String lastName, String mobile, Character gender, String email, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile =mobile;
        this.gender = gender;
        this.email = email;
        this.password =password;
    }

    public Student(Integer id,String firstName, String lastName, String mobile, Character gender, String email, String password)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile =mobile;
        this.gender = gender;
        this.email = email;
        this.password =password;
    }

    // Gettter Methods
    public Integer getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getMobile()
    {
        return mobile;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public Character getGender()
    {
        return gender;
    }

    //Setter Methods

    public void setId(Integer id)
    {
        this.id= id;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName =lastName;
    }

    public void setMobile(String mobile)
    {
        this.mobile= mobile;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setGender(Character gender)
    {
        this.gender = gender;
    }

    @Override
    public String toString()
    {
       return "Student(" + "id=" + id + ", FirstName" + firstName + ", LastName" +lastName + ", Mobile Number" +mobile + ", EMail" +email + ",Password " +password +   ")";
    }
   
    public static void createTable() throws ClassNotFoundException
        {
            
            try{
                String raw = "CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY,%s VARCHAR(20), %s VARCHAR(20), %s VARCHAR(20),%s VARCHAR(20),%s VARCHAR(20),%s char )";

                String query = String.format(raw,MetaData.TABLE_NAME,MetaData.ID,MetaData.FIRST_NAME,MetaData.LAST_NAME,MetaData.MOBILE, MetaData.EMAIL,MetaData.PASSWORD,MetaData.GENDER);

                System.out.println(query);
                

                String connectionUrl = "jdbc:sqlite:Quiz.db";
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection(connectionUrl);
                PreparedStatement ps =connection.prepareStatement(query);
                boolean b = ps.execute();
                System.out.println("Student Table is Created");
                System.out.println(b);
            }
            catch(Exception error)
            {
                error.printStackTrace();
            }
        }

    public Student save()
        {
            
            try{

             
                String raw = "INSERT INTO %s (%s,%s,%s,%s,%s,%s) VALUES (?, ?, ?, ?, ?, ?)";
                String query = String.format(raw, MetaData.TABLE_NAME, MetaData.FIRST_NAME,MetaData.LAST_NAME,MetaData.MOBILE,MetaData.EMAIL,MetaData.PASSWORD,MetaData.GENDER);
                System.out.println("Actual Query = " +query);

               String connectionUrl = "jdbc:sqlite:Quiz.db";
               Class.forName("org.sqlite.JDBC");
               Connection connection = DriverManager.getConnection(connectionUrl);

               PreparedStatement ps =connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
               ps.setString(1, this.firstName);
               ps.setString(2, this.lastName);
               ps.setString(3, this.mobile);
               ps.setString(4, this.email);
               ps.setString(5, this.password);
               ps.setString(6, String.valueOf(this.gender));
               
               
               int i = ps.executeUpdate();
                ResultSet keys = ps.getGeneratedKeys();
                
                if(keys.next()){
                   this.id = keys.getInt(1);
                }
               System.out.println("Updated Rows :-  " +i);

               connection.close();
              return this;
           }
           catch(Exception error)
           {
               error.printStackTrace();
              
           }
            return null;
    
       
        }


    public static ArrayList<Student> getAll(){
    ArrayList<Student> students = new ArrayList<>();
    try{          
        String query = String.format("SELECT %s, %s, %s, %s, %s, %s, %s  FROM %s ", MetaData.ID,MetaData.FIRST_NAME,MetaData.LAST_NAME,MetaData.MOBILE,MetaData.EMAIL,MetaData.PASSWORD,MetaData.GENDER,MetaData.TABLE_NAME);
        System.out.println("Actual Query = " +query);

       String connectionUrl = "jdbc:sqlite:Quiz.db";
       Class.forName("org.sqlite.JDBC");
       Connection connection = DriverManager.getConnection(connectionUrl);

       PreparedStatement ps =connection.prepareStatement(query);
       
       ResultSet result = ps.executeQuery();
        
        
        while(result.next()){
            
            Student s = new Student();
            s.setId(result.getInt(1));
            s.setFirstName(result.getString(2));
            s.setLastName(result.getString(3));
            s.setMobile(result.getString(4));
            s.setEmail(result.getString(5));
            s.setPassword(result.getString(6));
            s.setGender(result.getString(7).charAt(0));
            students.add(s);

        }
      

       connection.close();
    
   }
   catch(Exception error)
   {
       error.printStackTrace();
      
   }

   return students;
}


    public boolean isExists()
        {
            
            try{

             
             System.out.println("Hello I am in isExist");   
                String query = String.format("SELECT * FROM %s WHERE %s = ?;", MetaData.TABLE_NAME,MetaData.EMAIL);
                System.out.println("Actual Query = " +query);

               String connectionUrl = "jdbc:sqlite:Quiz.db";
               Class.forName("org.sqlite.JDBC");
               Connection connection = DriverManager.getConnection(connectionUrl);

               PreparedStatement ps =connection.prepareStatement(query);
               ps.setString(1, this.email);
               
               
               
               ResultSet result = ps.executeQuery();
                
                
                if(result.next()){
                   return true;

                }
              

               connection.close();
            
           }
           catch(Exception error)
           {
               error.printStackTrace();
              
           }
          return false;
    
       
     

        }

    public void login() throws SQLException, ClassNotFoundException , LoginException
    {
        

             
           
               String query = String.format("SELECT %s,%s,%s,%s,%s FROM %s WHERE %s = ? AND %s = ?;", MetaData.ID,MetaData.FIRST_NAME,MetaData.LAST_NAME,MetaData.MOBILE,MetaData.GENDER,MetaData.TABLE_NAME,MetaData.EMAIL,MetaData.PASSWORD);
               System.out.println("Actual Query = " +query);

              String connectionUrl = "jdbc:sqlite:Quiz.db";
              Class.forName("org.sqlite.JDBC");
              Connection connection = DriverManager.getConnection(connectionUrl);

              PreparedStatement ps =connection.prepareStatement(query);
              ps.setString(1, this.email);
              ps.setString(2, this.password);
              
              
              
              ResultSet result = ps.executeQuery();
               
               
               if(result.next())
               {
                 
                this.setId(result.getInt(1));
                this.setFirstName(result.getString(2)); 
                this.setLastName(result.getString(3));
                this.setMobile(result.getString(4)); 
                this.setGender(result.getString(5).charAt(0));
               }

               else
               {
               throw new LoginException("Login Failed");
               }
             

              connection.close();
           
          
          
   
      
    
    }
}
