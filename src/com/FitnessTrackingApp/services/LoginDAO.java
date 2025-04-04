package com.FitnessTrackingApp.services;

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

    public boolean changePassword(String username, String oldPassword, String confirmPassword) throws Exception {
	String[] tables = {"enthusiast", "trainers", "admins"};
	boolean isValid = false;
	String userTable = null;

	// Validate old password and find the correct table
	for (String table : tables) {
	    String validateQuery = "SELECT * FROM " + table + " WHERE username=? AND password=?";
	    try (PreparedStatement validateStmt = connection.prepareStatement(validateQuery)) {
		validateStmt.setString(1, username);
		validateStmt.setString(2, oldPassword);
		ResultSet rs = validateStmt.executeQuery();
		if (rs.next()) {
		    isValid = true;
		    userTable = table;
		    break; // Stop checking once the user is found
		}
	    }
	}

	if (!isValid) {
	    System.out.println("Current password is incorrect");
	    return false;
	}

	// Validate new password before updating
	if (!isValidPassword(confirmPassword)) {
	    System.out.println("Password must be 8-16 characters long, contain at least 1 uppercase and 1 lowercase letter.");
	    return false;
	}

	// Update password in the correct table
	String updateQuery = "UPDATE " + userTable + " SET password=? WHERE username=?";
	try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
	    updateStmt.setString(1, confirmPassword);
	    updateStmt.setString(2, username);
	    int rowsAffected = updateStmt.executeUpdate();
	    if (rowsAffected > 0) {
		System.out.println("Password updated successfully");
		return true;
	    } else {
		System.out.println("Password update failed");
		return false;
	    }
	} catch (SQLException e) {
	    System.out.println("Failed to update password: " + e.getMessage());
	    return false;
	}
    }

    // Helper method to validate password requirements
    private boolean isValidPassword(String password) {
	return password.length() >= 8 && password.length() <= 16 &&
		password.matches(".*[A-Z].*") && // At least one uppercase letter
		password.matches(".*[a-z].*");  // At least one lowercase letter
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

    public void updateTrainerProfile(String name, String birthday, String gender, double height, double weight,
	    String fitnessGoals, String username) {
	String query = "UPDATE Trainers SET fullname=?, birthday=?, gender=?, height=?, weight=?, fitnessgoals=? where username=?";
	try (PreparedStatement Stmt = connection.prepareStatement(query)) {
	    Stmt.setString(1, name);
	    Stmt.setString(2, birthday);
	    Stmt.setString(3, gender);
	    Stmt.setDouble(4, height);
	    Stmt.setDouble(5, weight);
	    Stmt.setString(6, fitnessGoals);
	    Stmt.setString(7, username);
	    Stmt.executeUpdate();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void retrieveTrainerProfile(TextField nameField, TextField birthdayField, ComboBox<String> genderBox,
	    TextField heightField, TextField weightField, ComboBox<String> fitnessGoalsBox, String username) {
	String query = "SELECT * FROM Trainers WHERE username=?";
	try (PreparedStatement Stmt = connection.prepareStatement(query)) {
	    Stmt.setString(1, username);
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

    public void insertWorkoutPlan(String goal, TextField[] exercises, TextField[] reps, TextField[] sets, String username, String password) {
	String tableName = goal.replace(" ", "");
	int trainerID = getTrainerID(username, password);

	if (trainerID == -1) {
	    System.out.println("Invalid trainer credentials.");
	    return;
	}

	String query = "INSERT INTO " + tableName + " (TrainerID, Exercise, Reps, Sets) VALUES (?, ?, ?, ?)";
	try (PreparedStatement Stmt = connection.prepareStatement(query)) {
	    for (int i = 0; i < exercises.length; i++) {
		if (!exercises[i].getText().isEmpty()) {
		    Stmt.setInt(1, trainerID);
		    Stmt.setString(2, exercises[i].getText());
		    Stmt.setInt(3, Integer.parseInt(reps[i].getText()));
		    Stmt.setInt(4, Integer.parseInt(sets[i].getText()));
		    Stmt.executeUpdate();
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void updateWorkoutPlan(String goal, TextField[] exercises, TextField[] reps, TextField[] sets, String username, String password) {
	String tableName = goal.replace(" ", "");
	int trainerID = getTrainerID(username, password);

	if (trainerID == -1) {
	    System.out.println("Invalid trainer credentials.");
	    return;
	}

	String query = "UPDATE " + tableName + " SET Reps=?, Sets=? WHERE Exercise=? AND TrainerID=?";
	try (PreparedStatement Stmt = connection.prepareStatement(query)) {
	    for (int i = 0; i < exercises.length; i++) {
		if (!exercises[i].getText().isEmpty()) {
		    Stmt.setInt(1, Integer.parseInt(reps[i].getText()));
		    Stmt.setInt(2, Integer.parseInt(sets[i].getText()));
		    Stmt.setString(3, exercises[i].getText());
		    Stmt.setInt(4, trainerID);
		    Stmt.executeUpdate();
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void loadWorkoutPlan(String goal, TextField[] exercises, TextField[] reps, TextField[] sets, String username, String password) {
	String tableName = goal.replace(" ", "");
	int trainerID = getTrainerID(username, password);

	if (trainerID == -1) {
	    System.out.println("Invalid trainer credentials.");
	    return;
	}

	String query = "SELECT Exercise, Reps, Sets FROM " + tableName + " WHERE TrainerID=?";
	try (PreparedStatement Stmt = connection.prepareStatement(query)) {
	    Stmt.setInt(1, trainerID);
	    ResultSet rs = Stmt.executeQuery();
	    int index = 0;
	    while (rs.next() && index < exercises.length) {
		exercises[index].setText(rs.getString("Exercise"));
		reps[index].setText(String.valueOf(rs.getInt("Reps")));
		sets[index].setText(String.valueOf(rs.getInt("Sets")));
		index++;
	    }
	    while (index < exercises.length) {
		exercises[index].clear();
		reps[index].clear();
		sets[index].clear();
		index++;
	    }
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    private int getTrainerID(String username, String password) {
	String query = "SELECT trainerID FROM trainers WHERE username = ? AND password = ?";
	try (PreparedStatement Stmt = connection.prepareStatement(query)) {
	    Stmt.setString(1, username);
	    Stmt.setString(2, password);
	    ResultSet rs = Stmt.executeQuery();
	    if (rs.next()) {
		return rs.getInt("trainerID");
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return -1;
    }
}