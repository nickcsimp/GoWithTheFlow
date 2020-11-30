import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class TestPatients {

    Connection conn;
    Patients patients;
    public TestPatients() throws SQLException {
         patients = new Patients();
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
    public void testAddPatient() throws SQLException {
        patients.addPatient("Billiam", "Broken Leg", 1, 19);
        Statement s = conn.createStatement();
        String sqlStr = "SELECT id FROM patients WHERE name='Billiam';";
        ResultSet rset=s.executeQuery(sqlStr);
        Integer patientId=0;
        while(rset.next()) {
            patientId= rset.getInt("id");
        }
        sqlStr = "SELECT * FROM patients WHERE id="+patientId+";";
        rset=s.executeQuery(sqlStr);
        String patientName = null;
        String patientProblem= null;
        Integer patientCertainty=0;
        Integer patientArrival=0;
        while(rset.next()) {
            patientName = rset.getString("name");
            patientProblem = rset.getString("problem");
            patientCertainty = rset.getInt("certainty");
            patientArrival = rset.getInt("arrival");
        }
        Assert.assertEquals("Billiam", patientName);
        Assert.assertEquals("Broken Leg", patientProblem);
        Assert.assertEquals(java.util.Optional.of(1), java.util.Optional.of(patientCertainty));
        Assert.assertEquals(java.util.Optional.of(19), java.util.Optional.of(patientArrival));
        sqlStr = "DELETE FROM patients WHERE name='Billiam';";
        s.execute(sqlStr);
        rset.close();
        s.close();

    }
    @Test
    public void testRemovePatient() throws SQLException {
        patients.addPatient("Billiam", "Broken Leg", 1, 19);
        Statement s = conn.createStatement();
        String sqlStr = "SELECT id FROM patients WHERE name='Billiam';";
        ResultSet rset=s.executeQuery(sqlStr);
        Integer patientId=0;
        while(rset.next()) {
            patientId= rset.getInt("id");
            patients.removePatient(patientId);
        }
        sqlStr = "SELECT * FROM patients WHERE id="+patientId+";";
        rset=s.executeQuery(sqlStr);
        Assert.assertEquals(false, rset.next());
        rset.close();
        s.close();
    }
}
