package edu.ncku.storage;

import io.github.cdimascio.dotenv.Dotenv;
// import io.github.cdimascio.dotenv.DotenvException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("unused")
public class SqlClient {
	static final Dotenv dotenv = Dotenv.configure().load();
	static final String DB_URL = dotenv.get("DB_URL");
	static final String USER = dotenv.get("USER");
	static final String PASS = dotenv.get("PASS");

	private static final String GATHER = "SELECT * FROM Test";
	private Connection conn;
	private PreparedStatement pstmt;


	public SqlClient() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.conn = DriverManager.getConnection(DB_URL, USER, PASS);

			System.out.println("Connection established successfully");
			
			// pstmt = conn.prepareStatement(GATHER);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver loading failed");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}	


// String sql = "SELECT * FROM Test";
// ResultSet rs = stmt.executeQuery(sql);

// while(rs.next())
// {
// 	System.out.println("id: " + rs.getString("id"));
// 	System.out.println("name: " + rs.getString("name"));
// }

// rs.close();stmt.close();conn.close()
// ;
