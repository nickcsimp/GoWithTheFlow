import java.sql.*;
import java.util.ArrayList;

public class Beds {
    Connection conn;
    public Beds() throws SQLException {
        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
        try {
            // Registers the driver
            Class.forName("org.postgresql.Driver");
        }
        catch (Exception e) {
        }
        conn = DriverManager.getConnection(dbUrl, "postgres", "root");
    }

    public void makeBedTable(){
        try {
            Statement s= conn.createStatement();
            String sqlStr="CREATE TABLE beds(id SERIAL PRIMARY KEY, number integer NOT NULL, wardID integer NOT NULL);";
            s.execute(sqlStr);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addBed(Integer number, String ward){
        //Todo - ensure no doubles of bed
        //Todo - ensure ward exists
        try {
            Statement s = conn.createStatement();
            String sqlStr = "Select id FROM wards WHERE name='"+ward+"';";
            ResultSet rset=s.executeQuery(sqlStr);
            int wardId=0;
            while(rset.next()) {
                wardId = rset.getInt("id");
            }
            sqlStr = "Insert INTO beds(number, wardid) values ("+number+","+wardId+");";
            rset.close();
            try {
                s.execute(sqlStr);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            s.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeBed(Integer bedId){
        Statement s= null;
        //Todo - rset
        try {
            s = conn.createStatement();
            String sqlStr = "DELETE FROM beds WHERE id="+bedId+";";
            try {
                s.execute(sqlStr);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            s.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addBedFeature(String feature, String type){
        try {
            Statement s = conn.createStatement();
            String sqlStr = "ALTER TABLE beds ADD COLUMN '"+feature+"' "+type+";";
            try {
                s.execute(sqlStr);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void editBed(Integer number, String ward, String feature, String change, Connection conn){
        try {
            Statement s = conn.createStatement();
            String sqlStr = "Select Id FROM wards WHERE name='"+ward+"';";
            ResultSet rset=s.executeQuery(sqlStr);
            int wardId=rset.getInt("id");
            sqlStr = "UPDATE beds SET '"+feature+"' = '"+change+"' WHERE number = "+number+" AND ward = "+wardId+";";
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
