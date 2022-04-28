import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DatabaseOperations {
	static DBConnection conn = new DBConnection();
	static Connection con = conn.connDb();
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
				int newCount = oldCount + map.get(key);
				ps.setInt(1, newCount);
				ps.setString(2, key);
				ps.executeUpdate();
			} else {
				preparedStatement = con.prepareStatement("INSERT INTO words" + "(count, word)" + "VALUES (?,?)");
				preparedStatement.setInt(1, map.get(key));
				preparedStatement.setString(2, key);
				preparedStatement.executeUpdate();

			}
		}
	}

	public ArrayList<Integer> getWordCount(ArrayList<Integer> arrayList) throws SQLException {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM words");

		while (rs.next()) {
			int wordCount = rs.getInt(1);
			arrayList.add(wordCount);
		}
		return arrayList;
	}
}