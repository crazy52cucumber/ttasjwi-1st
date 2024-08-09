package dbutil;


import java.sql.*;
import java.util.logging.Logger;

public class MariaConnection {
  private static final Logger logger = Logger.getLogger(MariaConnection.class.getName());
  private static MariaConnection instance;
  private Connection con;
  private String url = "jdbc:mariadb://10.41.2.94:3306/tspoon";
  private String username = "ttasjwi";
  private String password = "ttasjwi";

  private MariaConnection() {
    try {
      Class.forName("org.mariadb.jdbc.Driver");
      con = DriverManager.getConnection(url, username, password);
      con.setAutoCommit(false);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Connection getConnection() {
    return con;
  }

  public static MariaConnection getInstance() {
    try {
      if (instance == null || instance.getConnection().isClosed()) instance = new MariaConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return instance;
  }

  public static void closeAll(PreparedStatement pstmt, ResultSet rs) {
    try {
      if (pstmt != null) {
        pstmt.close();
      }
      if (rs != null) {
        rs.close();
      }
    } catch (SQLException e) {
      logger.warning("Error closing resources" + e.getMessage());
    }
  }
}
