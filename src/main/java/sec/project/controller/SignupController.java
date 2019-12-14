package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;

@Controller()
public class SignupController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String loadForm(Model model, Authentication authentication, @RequestParam(defaultValue = "") String search) throws SQLException {
        String username = authentication.getName();

        String databaseAddress = "jdbc:h2:file:./database";

        Connection connection = DriverManager.getConnection(databaseAddress, "sa", "");

        Statement statement = connection.createStatement();
        String sql = "select * from signup where username = '" + username + "' and (lower(name) like '%" + search.toLowerCase() + "%' or lower(address) like '%" + search.toLowerCase() + "%')";

        ResultSet rs = statement.executeQuery(sql);
        List<Signup> signups = new ArrayList<>();
        while (rs.next()) {
            Signup signup = new Signup(rs.getString("name"), rs.getString("address"));
            signups.add(signup);
        }
        
        model.addAttribute("items", signups);
        model.addAttribute("search", search);

        return "form";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String submitForm(Authentication authentication, @RequestParam String name, @RequestParam String address) throws SQLException {
        String username = authentication.getName();

        String databaseAddress = "jdbc:h2:file:./database";

        Connection connection = DriverManager.getConnection(databaseAddress, "sa", "");

        String sql = "INSERT INTO signup (username, name, address) VALUES (?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, address);

        preparedStatement.execute();
        
        return "done";
    }

}
