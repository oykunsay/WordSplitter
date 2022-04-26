import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;

public class DatabaseOperations {
	DBConnection conn = new DBConnection();
	Connection con = conn.connDb();
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	

	public void insertWordCount(HashMap<String, Integer> map) throws SQLException {
		Iterator<String> wordIterator = map.keySet().iterator();
		

		while (wordIterator.hasNext()) {
			String key = wordIterator.next();

			statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM words WHERE word='" + key + "'");
			if (rs.next()) {
				int oldCount = rs.getInt(1);
				PreparedStatement ps = con.prepareStatement("UPDATE words SET count= ? WHERE word = ?");
				int newCount =  oldCount +  map.get(key);
				ps.setInt(1, newCount);
				ps.setString(2, key);
				ps.executeUpdate();
			}else{
				preparedStatement = con.prepareStatement("INSERT INTO words" + "(count, word)" + "VALUES (?,?)");
				preparedStatement.setInt(1, map.get(key));
				preparedStatement.setString(2, key);
				preparedStatement.executeUpdate();
			 
		}
	}
	}

	public int wordCount() {
		int rowCount = 0;
		try {
			statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM words");

			while (resultSet.next()) {
				rowCount = resultSet.getInt("count(*)");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rowCount;
	}
}