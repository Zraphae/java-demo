package cn.enn.test.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Random;

@Slf4j
public class TestJDBC {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";

    private static final String USER = "root";
    private static final String PASS = "415426880";

    public static void main(String[] args) {
        Connection conn = null;
        try{
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql = "INSERT INTO transactions (col1,col2,col3,col4,col5) VALUES(?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            Random random = new Random();
            for(int i = 0; i < 1000000; i++) {
                int nextInt = random.nextInt(10);
                ps.setString(1, "col1" + i + nextInt);
                ps.setString(2, "col2" + i + nextInt);
                ps.setString(3, "col3" + i + nextInt);
                ps.setString(4, "col4" + i + nextInt);
                ps.setString(5, "col5" + i + nextInt);

                ps.executeUpdate();
            }

            ps.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }


    private static void query(){
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM test_date";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while(rs.next()){
                log.info("--1111-->{}", rs.getTimestamp("date_type"));
                log.info("-2222--->{}", rs.getDate("date_type"));
                log.info("-3333--->{}", rs.getDate("timestamp_type"));
                log.info("-4444--->{}", rs.getTimestamp("timestamp_type"));
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
    }

}
