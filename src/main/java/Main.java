import java.sql.SQLException;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) throws SQLException {
        Beds beds = new Beds();
        beds.addBed(10, "Oncology");

    }
}
