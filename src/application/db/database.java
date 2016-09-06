package application.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class database {
	
	//drive name specific for SQLite + register the driver
	private String driverName = "org.sqlite.JDBC";
	private String dbURL = "jdbc:sqlite:database.db";
	private Connection connection = null;
	private Statement stmt = null;
	private HashMap<Integer, String> tuple;
	
	public database() throws ClassNotFoundException{ 	
		Class.forName(driverName);
		try{
			//create a db connection
			connection = DriverManager.getConnection(dbURL);	
			//creates statement
			stmt = connection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS TASK" +
							"(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," 
							+ "ROW INT NOT NULL," +
							"TODO TEXT NOT NULL," +
							"DONE INT NOT NULL)"; //if done = 0, then not done, if 1 then done
			
			//# of seconds driver will wait for Statement to execute
			stmt.setQueryTimeout(30);
			stmt.executeUpdate(sql);
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
	//insert task
	public void update(String text){
		try{
			//increase all indeces as new inserted task will be top of list - hence index 0
			stmt.executeUpdate("UPDATE TASK SET ROW = ROW + 1");
			stmt.executeUpdate("INSERT INTO TASK (ROW, TODO, DONE) VALUES( 0, '" + text + "', 0)" );
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
	//modify DONE value
	public void update(int done, int index){
		try{
			stmt.executeUpdate("UPDATE TASK SET DONE = " + done +  " WHERE ROW = " + index);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	//delete task
	public void delete(int index){
		try{
			stmt.executeUpdate("DELETE FROM TASK WHERE ROW = " + index);
			System.out.println("deleted on db");
			//set indeces to correct value
			stmt.executeUpdate("UPDATE TASK SET ROW = ROW - 1 WHERE ROW > " + index);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//save db
	public void setClose(){
		try{
			stmt.close();
			connection.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
	//curse over the db to get what's there
	public void fetchData(){
		try{
			tuple = new HashMap<Integer, String>();
			ResultSet rs = stmt.executeQuery("SELECT ROW, TODO FROM TASK");
			
			//checks if the table is empty or not (i.e. if empty does nothing)
			if(rs.next()){
				do{
					String todo = rs.getString("TODO");
					int index = rs.getInt("ROW");
					tuple.put(index, todo);
				}while(rs.next());
			}else{
				
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public HashMap<Integer, String> getData(){
		fetchData();
		return tuple;
	}
	
	
	//select rows with done = 1
	//then jab them through
	
	public boolean isDone(String todo){
		try{
			ResultSet rs = stmt.executeQuery("SELECT DONE FROM TASK WHERE TODO LIKE '" + todo + "'");
			if(rs.next()){
				int compare = rs.getInt("DONE");
				System.out.println("task is done: " + (compare == 1));
				return compare==1;
			}else{
				return false;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	
	
}
	
	

 

 
 

