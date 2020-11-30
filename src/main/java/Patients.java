import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;

public class Patients {
    Connection conn;
    public Patients() throws SQLException {
        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
        try {
            // Registers the driver
            Class.forName("org.postgresql.Driver");
        }
        catch (Exception e) {
        }
        conn = DriverManager.getConnection(dbUrl, "postgres", "root");
    }

    public void makePatientTable(){
        try {
            Statement s= conn.createStatement();
            //TODO Make arrival a time
            String sqlStr="CREATE TABLE patients(id SERIAL PRIMARY KEY, name varchar(128) NOT NULL, problem varchar(128), certainty integer, arrival integer);";
            s.execute(sqlStr);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addPatient(String name, String problem, int certainty, Integer time){
        Statement s= null;
        try {
            s = conn.createStatement();
            String sqlStr = "Insert INTO patients (name, problem, certainty, arrival ) values ('"+name+"','"+problem+"',"+certainty+","+time+");";
            try {
                s.execute(sqlStr);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removePatient(Integer id){
        try {
            Statement s = conn.createStatement();
            String sqlStr = "DELETE FROM patients WHERE id="+id+";";
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

