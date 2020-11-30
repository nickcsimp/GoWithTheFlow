import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class TestWards {

    Connection conn;
    Wards wards;
    public TestWards() throws SQLException {
        wards = new Wards();
    }
    @Before
    public void setUp() throws SQLException {
        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
        try {
            // Registers the driver
            Class.forName("org.postgresql.Driver");
        }
        catch (Exception e) {
        }
        conn = DriverManager.getConnection(dbUrl, "postgres", "root");
    }

    @Test
    public void testAddWard() throws SQLException {
        wards.addWard("Billiam");
        Statement s = conn.createStatement();
        String sqlStr = "SELECT id FROM wards WHERE name='Billiam';";
        ResultSet rset=s.executeQuery(sqlStr);
        Integer wardId=0;
        while(rset.next()) {
            wardId= rset.getInt("id");
        }
        sqlStr = "SELECT * FROM wards WHERE id="+wardId+";";
        rset=s.executeQuery(sqlStr);
        String wardName = null;
        while(rset.next()) {
            wardName = rset.getString("name");
        }
        Assert.assertEquals("Billiam", wardName);
        sqlStr = "DELETE FROM wards WHERE name='Billiam';";
        s.execute(sqlStr);
        rset.close();
        s.close();

    }
    @Test
    public void testRemoveWard() throws SQLException {
        wards.addWard("Billiam");
        Statement s = conn.createStatement();
        String sqlStr = "SELECT id FROM wards WHERE name='Billiam';";
        ResultSet rset=s.executeQuery(sqlStr);
        Integer wardId=0;
        while(rset.next()) {
            wardId= rset.getInt("id");
            wards.removeWard(wardId);
        }
        sqlStr = "SELECT * FROM patients WHERE id="+wardId+";";
        rset=s.executeQuery(sqlStr);
        Assert.assertEquals(false, rset.next());
        rset.close();
        s.close();
    }
}
