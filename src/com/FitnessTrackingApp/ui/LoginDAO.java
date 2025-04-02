import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.sql.*;


public class LoginDAO {
    private static Connection connection;

    public LoginDAO() throws Exception {
        Class.forName("org.sqlite.JDBC");

        String dburl = "jdbc:sqlite:resources/FitnessTrackingApp.db";

        connection = DriverManager.getConnection(dburl);

        System.out.println("DB connection successful to: " + dburl);
    }

    public boolean validateEnthusiast(String username, String password) {
        String query = "SELECT * FROM enthusiast WHERE username=? AND password=?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateTrainer(String username, String password) {
        String query = "SELECT * FROM trainers WHERE username=? AND password=?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateAdmins(String username, String password) {
        String query = "SELECT * FROM admins WHERE username=? AND password=?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createEnthusiast(Users enthusiast) {
        String query = "INSERT INTO enthusiast (username, password) values (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, enthusiast.getUsername());
            ps.setString(2, enthusiast.getPassword());
            ps.executeUpdate();
            System.out.println("Enthusiast account created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTrainer(Users trainer) {
        String query = "INSERT INTO trainers (username, password) values (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, trainer.getUsername());
            ps.setString(2, trainer.getPassword());
            ps.executeUpdate();
            System.out.println("Trainer account created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createAdmin(Users admin) {
        String query = "INSERT INTO admins (username, password) values (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, admin.getUsername());
            ps.setString(2, admin.getPassword());
            ps.executeUpdate();
            System.out.println("Admin account created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean duplicateEnthusiast(String username) {
        String query = "SELECT * FROM enthusiast WHERE username=?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean duplicateTrainer(String username) {
        String query = "SELECT * FROM trainers WHERE username=?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean duplicateAdmin(String username) {
        String query = "SELECT * FROM admins WHERE username=?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changePassword(String username, String oldPassword,
                                  String confirmPassword) throws Exception {
        String[] tables = {"enthusiast", "trainers", "admins"};
        boolean isValid = false;

        for (String table : tables) {
            String validateQuery = "SELECT * FROM " + table + " WHERE username=? AND password=?";
            try (PreparedStatement validateStmt = connection.prepareStatement(validateQuery)) {
                validateStmt.setString(1, username);
                validateStmt.setString(2, oldPassword);
                ResultSet rs = validateStmt.executeQuery();
                if (rs.next()) {
                    isValid = true;
                    break;
                }
                if (!isValid) {
                    System.out.println("Current password is incorrect");
                    return false;
                }
            }
        }
        for (String table : tables) {
            String updateQuery = "UPDATE " + table + " SET password=? WHERE username=?";
            try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                updateStmt.setString(1, confirmPassword);
                updateStmt.setString(2, username);
                updateStmt.executeUpdate();
                System.out.println("Password updated");
            } catch (SQLException e) {
                System.out.println("Failed to update password: " + e.getMessage());
            }
        }
        return true;
    }

    public void updateEnthusiastProfile(String name, String birthday, String gender, double height, double weight,
                                        String fitnessGoals, String username) {
        String query = "UPDATE enthusiast SET name=?, birthday=?, gender=?, height=?, weight=?, fitnessgoals=? where username=?";
        try (PreparedStatement Stmt = connection.prepareStatement(query)) {
            Stmt.setString(1, name);
            Stmt.setString(2, birthday);
            Stmt.setString(3, gender);
            Stmt.setDouble(4, height);
            Stmt.setDouble(5, weight);
            Stmt.setString(6, fitnessGoals);
            Stmt.setString(7, ThisUser.getInstance().getCurrentUsername());
            Stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void retrieveEnthusiastProfile(TextField nameField, TextField birthdayField, ComboBox<String> genderBox,
                                          TextField heightField, TextField weightField, ComboBox<String> fitnessGoalsBox, String username) {
        String query = "SELECT * FROM Enthusiast WHERE username=?";
        try (PreparedStatement Stmt = connection.prepareStatement(query)) {
            Stmt.setString(1, ThisUser.getInstance().getCurrentUsername());
            ResultSet rs = Stmt.executeQuery();
            if (rs.next()) {
                nameField.setText(rs.getString("fullname"));
                birthdayField.setText(rs.getString("birthday"));
                genderBox.setValue(rs.getString("gender"));
                heightField.setText(rs.getString("height"));
                weightField.setText(rs.getString("weight"));
                fitnessGoalsBox.setValue(rs.getString("fitnessgoals"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean forgetPassword(String username, String birthday, String password) {
        String[] tables = {"enthusiast", "trainers", "admins"};
        boolean isUpdated = false;
        for (String table : tables) {
            String query = "UPDATE " + table + " SET password=? WHERE username=? AND birthday=?";
            try (PreparedStatement Stmt = connection.prepareStatement(query)) {
                Stmt.setString(1, password);
                Stmt.setString(2, username);
                Stmt.setString(3, birthday);
                int rowsUpdated = Stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    isUpdated = true;
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isUpdated;
    }
}