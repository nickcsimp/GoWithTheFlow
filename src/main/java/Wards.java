import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Wards {
    Connection conn;
    public Wards() throws SQLException {
        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
        try {
            // Registers the driver
            Class.forName("org.postgresql.Driver");
        }
        catch (Exception e) {
        }
        conn = DriverManager.getConnection(dbUrl, "postgres", "root");
    }
    public void makeWardTable() throws SQLException{
        try {
            Statement s= conn.createStatement();
            String sqlStr="CREATE TABLE wards(id SERIAL PRIMARY KEY, name varchar(128) NOT NULL);";
            s.execute(sqlStr);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addWard(String name){
        //Todo - ensure no doubles of ward
        try {
            Statement s = conn.createStatement();
            String sqlStr = "Insert INTO wards (name) values ('"+name+"');";
            try {
                s.execute(sqlStr);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeWard(Integer id){
        try {
            Statement s = conn.createStatement();
            String sqlStr = "DELETE FROM wards WHERE id="+id+";";
            try {
                s.execute(sqlStr);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}

