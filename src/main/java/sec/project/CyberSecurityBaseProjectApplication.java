package sec.project;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import org.h2.tools.RunScript;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CyberSecurityBaseProjectApplication {

    public static void main(String[] args) throws Throwable {
        // Open connection to a database -- do not alter this code
        String databaseAddress = "jdbc:h2:file:./database";

        Connection connection = DriverManager.getConnection(databaseAddress, "sa", "");

        try {
            // If database has not yet been created, insert content
            RunScript.execute(connection, new FileReader("sql/database-schema.sql"));
            RunScript.execute(connection, new FileReader("sql/database-import.sql"));
        } catch (Throwable t) {
            System.err.println(t.getMessage());
        }

        connection.close();

        SpringApplication.run(CyberSecurityBaseProjectApplication.class);
    }
}
