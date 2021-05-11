

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatlistDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	public ChatModule chat_module;
	
	public ChatlistDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/"+FinalDB.dbProject+"?serverTimezone=Asia/Seoul&useSSL=false&allowPublicKeyRetrieval=true&useSSL=false";
			String dbID = FinalDB.dbID;
			String dbPassword = FinalDB.dbPassword;
			Class.forName("com.mysql.jdbc.Driver");
			conn= DriverManager.getConnection(dbURL, dbID, dbPassword);			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ChatModule> getID(String inputID) {
		
		String SQL = "SELECT * FROM chatlist_tb WHERE order_id=\"" + inputID + "\" OR errand_id=\"" + inputID + "\"";
		ChatModule chat_module;
		ArrayList<ChatModule> ErrandOrderID = new ArrayList<ChatModule>();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
		
			while(rs.next()){
				chat_module = new ChatModule();
				chat_module.setIncreasNum(rs.getInt(1));
				chat_module.setOrderID(rs.getString(2));
				chat_module.setErrandID(rs.getString(3));
				ErrandOrderID.add(chat_module);	
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return ErrandOrderID;
	}
	
}
