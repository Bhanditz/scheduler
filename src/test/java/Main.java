import java.io.IOException;
import java.sql.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * Created by sara.mustafa on 4/12/17.
 */
public class Main {

    /**
     * Read resume of a candidate and write it into a file
     *

     */
    public static void readBlob(int id) {
        // update sql
        String selectSQL = "SELECT * FROM eol.content_partner WHERE id=?";
        ResultSet rs = null;

        try (Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/eol?rewriteBatchedStatements=true","root","root");
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            // set parameter;
            pstmt.setInt(1,id);
            System.out.println("call  " );
            rs = pstmt.executeQuery();


            while (rs.next()) {
                InputStream input = rs.getBinaryStream("logo");
                // write binary stream into file
                File file = new File("imagepost50."+rs.getString("logo_type"));
                FileOutputStream output = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    output.write(buffer);
                }
                output.close();
                System.out.println("Writing to file " + file.getAbsolutePath());
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //
        readBlob(50);
    }
}