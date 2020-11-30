import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class TestBeds {

    Connection conn;
    Beds beds;
    public TestBeds() throws SQLException {
        beds = new Beds();
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
    public void testAddBed() throws SQLException {
        Wards wards=new Wards();
        wards.addWard("Billaim");
        beds.addBed(1,"Billiam");
        Statement s = conn.createStatement();
        String sqlStr = "SELECT id FROM wards WHERE name='Billiam';";
        ResultSet rset=s.executeQuery(sqlStr);
        Integer wardId=0;
        while(rset.next()) {
            wardId= rset.getInt("id");
        }

        sqlStr = "SELECT id FROM beds WHERE number=1 AND wardid="+wardId+";";
        rset=s.executeQuery(sqlStr);
        Integer bedId=0;
        while(rset.next()) {
            bedId= rset.getInt("id");
        }

        sqlStr = "SELECT * FROM beds WHERE id="+bedId+";";
        rset=s.executeQuery(sqlStr);
        Integer bedNumber = 0;
        Integer actualWardId=0;
        while(rset.next()) {
            bedNumber = rset.getInt("number");
            actualWardId=rset.getInt("wardid");
        }
        Assert.assertEquals(wardId, actualWardId);
        Assert.assertEquals(java.util.Optional.of(1), java.util.Optional.of(bedNumber));
        sqlStr = "DELETE FROM beds WHERE id="+bedId+";";
        s.execute(sqlStr);
        rset.close();
        s.close();
    }
    @Test
    public void testRemoveBed() throws SQLException {
        Wards wards = new Wards();
        wards.addWard("Billaim");
        beds.addBed(1, "Billiam");
        Statement s = conn.createStatement();
        String sqlStr = "SELECT id FROM wards WHERE name='Billiam';";
        ResultSet rset = s.executeQuery(sqlStr);
        Integer wardId = 0;
        while (rset.next()) {
            wardId = rset.getInt("id");
        }

        sqlStr = "SELECT id FROM beds WHERE number=1 AND wardid=" + wardId + ";";
        rset = s.executeQuery(sqlStr);
        Integer bedId = 0;
        while (rset.next()) {
            bedId = rset.getInt("id");
            beds.removeBed(bedId);
        }

        sqlStr = "SELECT * FROM beds WHERE id=" + bedId + ";";
        rset = s.executeQuery(sqlStr);
        Assert.assertEquals(false, rset.next());
        s.execute(sqlStr);
        rset.close();
        s.close();
    }
}