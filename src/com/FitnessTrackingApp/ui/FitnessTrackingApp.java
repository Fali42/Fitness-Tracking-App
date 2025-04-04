package com.FitnessTrackingApp.ui;
import com.FitnessTrackingApp.services.LoginDAO;
import com.FitnessTrackingApp.services.Users;
import com.FitnessTrackingApp.services.ThisUser;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class FitnessTrackingApp extends Application {

    private Stage primaryStage;
    private int logoClickCount = 0;
    ThisUser current = new ThisUser();

    @Override
    public void start(Stage primaryStage) {
	this.primaryStage = primaryStage;
	primaryStage.setTitle("Fitness Tracking App");

	showHomePage();
    }

    private void showHomePage() {
	Label logo = new Label("Fitness Tracking App");
	logo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

	TextField usernameField = new TextField();
	usernameField.setPromptText("Username");

	PasswordField passwordField = new PasswordField();
	passwordField.setPromptText("Password");

	Button loginEnthusiastBtn = new Button("Login as Enthusiast");
	loginEnthusiastBtn.setOnAction(e -> {
	    try {
		String username = usernameField.getText();
		String password = passwordField.getText();

		LoginDAO loginDAO = new LoginDAO();
		boolean isValid = loginDAO.validateEnthusiast(username, password);
		if (isValid) {
		    current.setCurrentUsername(username);
		    current.setCurrentPassword(password);

		    Alert alert = new Alert(Alert.AlertType.INFORMATION);
		    alert.setTitle("Successful Login");

		    alert.setHeaderText(null);
		    alert.setContentText("Welcome " + username);
		    alert.showAndWait();
		    showEnthusiastPage();
		} else {
		    Alert alert = new Alert(Alert.AlertType.ERROR);
		    alert.setTitle("Error");
		    alert.setHeaderText(null);
		    alert.setContentText("Invalid username or password");
		    alert.showAndWait();
		}
	    } catch (Exception ex) {
		System.out.println(ex.getMessage());
	    }
	});


	Button loginTrainerBtn = new Button("Login as Trainer");
	loginTrainerBtn.setOnAction(e -> {
	    try {
		String username = usernameField.getText();
		String password = passwordField.getText();
		LoginDAO loginDAO = new LoginDAO();
		boolean isValid = loginDAO.validateTrainer(username, password);
		if (isValid) {

		    current.setCurrentUsername(username);
		    current.setCurrentPassword(password);

		    Alert alert = new Alert(Alert.AlertType.INFORMATION);
		    alert.setTitle("Successful Login");
		    alert.setHeaderText(null);
		    alert.setContentText("Welcome " + username);
		    alert.showAndWait();
		    showTrainerPage();
		} else {
		    Alert alert = new Alert(Alert.AlertType.ERROR);
		    alert.setTitle("Error");
		    alert.setHeaderText(null);
		    alert.setContentText("Invalid username or password");
		    alert.showAndWait();
		}
	    } catch (Exception ex) {
		System.out.println(ex.getMessage());
	    }
	});

	Button createAccountBtn = new Button("Create Account");
	createAccountBtn.setOnAction(e -> {
	    String newUsername = usernameField.getText();
	    String newPassword = passwordField.getText();

	    if(newUsername == null || newPassword == null){
		System.out.println("Username or Password cannot be empty");
		return;
	    }
	    ThisUser.getInstance().setCurrentUsername(newUsername);
	    ThisUser.getInstance().setCurrentPassword(newPassword);
	    showCreateAccountPage(); });

	Hyperlink forgotPasswordLink = new Hyperlink("Forgot Password?");
	forgotPasswordLink.setOnAction(e -> showForgotPasswordPage());

	Button adminLoginBtn = new Button("Admin Login");
	adminLoginBtn.setVisible(false);
	adminLoginBtn.setOnAction(e -> {
	    try {
		String username = usernameField.getText();
		String password = passwordField.getText();
		LoginDAO loginDAO = new LoginDAO();
		boolean isValid = loginDAO.validateAdmins(username, password);
		if (isValid) {
		    Alert alert = new Alert(Alert.AlertType.INFORMATION);
		    alert.setTitle("Successful Login");
		    alert.setHeaderText(null);
		    alert.setContentText("Welcome " + username);
		    alert.showAndWait();
		    showAdminPage(username);
		} else {
		    Alert alert = new Alert(Alert.AlertType.ERROR);
		    alert.setTitle("Error");
		    alert.setHeaderText(null);
		    alert.setContentText("Invalid username or password");
		    alert.showAndWait();
		}
	    } catch (Exception ex) {
		System.out.println(ex.getMessage());
	    }
	});


	VBox layout = new VBox(10);
	layout.setAlignment(Pos.CENTER);
	layout.setPadding(new Insets(20));

	HBox loginButtons = new HBox(10, loginEnthusiastBtn, loginTrainerBtn);
	loginButtons.setAlignment(Pos.CENTER);

	layout.getChildren().addAll(
		logo, usernameField, passwordField, loginButtons,
		createAccountBtn, forgotPasswordLink, adminLoginBtn
		);


	logo.setOnMouseClicked(event -> {
	    logoClickCount++;
	    if (logoClickCount == 3) {
		adminLoginBtn.setVisible(true);
		logoClickCount = 0;
	    }
	});


	Scene scene = new Scene(layout, 600, 800);
	primaryStage.setScene(scene);
	primaryStage.show();
    }

    // METHODS FOR EACH PAGE
    private void showEnthusiastPage() {
	Label titleLabel = new Label("Hello, " + current.getCurrentUsername());
	titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

	HBox titleBox = new HBox(titleLabel);
	titleBox.setAlignment(Pos.CENTER);
	titleBox.setPadding(new Insets(10));

	Button workoutHistoryBtn = new Button("Workout History & Progress");
	workoutHistoryBtn.setPrefSize(200, 100);
	workoutHistoryBtn.setOnAction(e -> showWorkoutHistoryPage());

	Button workoutPlansBtn = new Button("Workout Plans");
	workoutPlansBtn.setPrefSize(200, 100);
	workoutPlansBtn.setOnAction(e -> showWorkoutPlansPage1());

	Button subscriptionsBtn = new Button("Subscriptions");
	subscriptionsBtn.setPrefSize(200, 100);
	subscriptionsBtn.setOnAction(e -> {
            try {
                showSubscriptionsPage();
            } catch (Exception ex) {
                Logger.getLogger(FitnessTrackingApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

	Button profileManagementBtn = new Button("Profile Management");
	profileManagementBtn.setPrefSize(200, 100);
	profileManagementBtn.setOnAction(e -> showProfileManagementUserPage());

	GridPane gridPane = new GridPane();
	gridPane.setHgap(20);
	gridPane.setVgap(20);
	gridPane.setAlignment(Pos.CENTER);

	gridPane.add(workoutHistoryBtn, 0, 0);
	gridPane.add(workoutPlansBtn, 1, 0);
	gridPane.add(subscriptionsBtn, 0, 1);
	gridPane.add(profileManagementBtn, 1, 1);

	StackPane backButton = createBackButton();
	backButton.setOnMouseClicked(e -> showHomePage());

	VBox enthusiastLayout = new VBox(20, backButton, titleBox, gridPane);
	enthusiastLayout.setAlignment(Pos.TOP_CENTER);
	enthusiastLayout.setPadding(new Insets(20));

	Scene enthusiastScene = new Scene(enthusiastLayout, 600, 800);
	primaryStage.setScene(enthusiastScene);
    }

    private void showTrainerPage() {
	Label titleLabel = new Label("Hello, " + current.getCurrentUsername());
	titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

	HBox titleBox = new HBox(titleLabel);
	titleBox.setAlignment(Pos.CENTER);
	titleBox.setPadding(new Insets(10));

	Button createWorkoutPlanBtn = new Button("Create Workout Plan");
	createWorkoutPlanBtn.setPrefSize(200, 100);
	createWorkoutPlanBtn.setOnAction(e -> showCreateWorkoutPlanPage());

	Button updateWorkoutPlanBtn = new Button("Update Workout Plan");
	updateWorkoutPlanBtn.setPrefSize(200, 100);
	updateWorkoutPlanBtn.setOnAction(e -> showUpdateWorkoutPlanPage());

	Button userProgressBtn = new Button("User Progress");
	userProgressBtn.setPrefSize(200, 100);
	userProgressBtn.setOnAction(e -> showUserProgressPage());

	Button profileManagementBtn = new Button("Profile Management");
	profileManagementBtn.setPrefSize(200, 100);
	profileManagementBtn.setOnAction(e -> showProfileManagementTrainerPage());

	GridPane gridPane = new GridPane();
	gridPane.setHgap(20);
	gridPane.setVgap(20);
	gridPane.setAlignment(Pos.CENTER);
	gridPane.add(createWorkoutPlanBtn, 0, 0);
	gridPane.add(updateWorkoutPlanBtn, 1, 0);
	gridPane.add(userProgressBtn, 0, 1);
	gridPane.add(profileManagementBtn, 1, 1);

	StackPane backButton = createBackButton();
	backButton.setOnMouseClicked(e -> showHomePage());

	VBox trainerLayout = new VBox(20, backButton, titleBox, gridPane);
	trainerLayout.setAlignment(Pos.TOP_CENTER);
	trainerLayout.setPadding(new Insets(20));

	Scene trainerScene = new Scene(trainerLayout, 600, 800);
	primaryStage.setScene(trainerScene);
    }
    
    private void showAdminPage(String username) {

        Label greetingLabel = new Label("Welcome Admin, " + username + "!");
        greetingLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button manageUsersBtn = new Button("Manage Users");
        manageUsersBtn.setOnAction(e -> showManageUsersPage(username));

        Button managePlansBtn = new Button("Manage Workout Plans & Subscriptions");
        managePlansBtn.setOnAction(e -> showManagePlansAndSubscriptionsPage(username));

        Button performanceBtn = new Button("User Progress & Trainer Performance");
        performanceBtn.setOnAction(e -> showUserProgressAndTrainerPerformancePage(username));

        Button reportsBtn = new Button("Generate Reports");
        reportsBtn.setOnAction(e -> showGenerateReportsPage(username));

        HBox row1 = new HBox(10, manageUsersBtn, managePlansBtn);
        HBox row2 = new HBox(10, performanceBtn, reportsBtn);

        row1.setAlignment(Pos.CENTER);
        row2.setAlignment(Pos.CENTER);

        StackPane backButton = createBackButton();
        backButton.setOnMouseClicked(e -> showHomePage());

        VBox layout = new VBox(20, backButton, greetingLabel, row1, row2);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene adminScene = new Scene(layout, 600, 800);
        primaryStage.setScene(adminScene);
    }
    
    private void showUserProgressAndTrainerPerformancePage(String username) {
        Label greeting = new Label("Welcome Admin, " + username + "!");
        greeting.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label title = new Label("User Progress & Trainer Performance");
        title.setStyle("-fx-font-size: 16px;");

        Button userProgressBtn = new Button("User Progress");
        Button trainerPerformanceBtn = new Button("Trainer Performance");

        StackPane backButton = createBackButton();
        backButton.setOnMouseClicked(e -> showAdminPage(username));;

        VBox layout = new VBox(20, backButton, greeting, title, userProgressBtn, trainerPerformanceBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene UserProgressAndTrainerPerformanceScene = new Scene(layout, 600, 800);
        primaryStage.setScene(UserProgressAndTrainerPerformanceScene);
    }
    
    private void showManagePlansAndSubscriptionsPage(String username) {
        Label greeting = new Label("Welcome Admin, " + username + "!");
        greeting.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label title = new Label("Manage Workout Plan & Subscriptions");
        title.setStyle("-fx-font-size: 16px;");

        Button managePlansBtn = new Button("Manage Workout Plans");
        Button subscriptionsBtn = new Button("Subscriptions");

        StackPane backButton = createBackButton();
        backButton.setOnMouseClicked(e -> showAdminPage(username));

        VBox layout = new VBox(20, backButton, greeting, title, managePlansBtn, subscriptionsBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene ManagePlansAndSubscriptionsScene = new Scene(layout, 600, 800);
        primaryStage.setScene(ManagePlansAndSubscriptionsScene);
    }
    
    private void showManageUsersPage(String username) {
        Label greeting = new Label("Welcome Admin, " + username + "!");
        greeting.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label title = new Label("Manage Users");
        title.setStyle("-fx-font-size: 16px;");

        Button fitnessTrainersBtn = new Button("Fitness Trainers");
        Button fitnessEnthusiastsBtn = new Button("Fitness Enthusiasts");
        Button logoutBtn = new Button("Log Out");
        logoutBtn.setOnAction(e -> showHomePage());

        StackPane backButton = createBackButton();
        backButton.setOnMouseClicked(e -> showAdminPage(username));

        VBox layout = new VBox(20, backButton, greeting, title, fitnessTrainersBtn, fitnessEnthusiastsBtn, logoutBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene ManageUsersScene = new Scene(layout, 600, 800);
        primaryStage.setScene(ManageUsersScene);
    }

    private void showGenerateReportsPage(String username) {
        Label greeting = new Label("Welcome Admin, " + username + "!");
        greeting.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label title = new Label("Generate Reports");
        title.setStyle("-fx-font-size: 16px;");

        HBox enthusiastsRow = new HBox(10, new Label("Enthusiasts enrolled"), new Button("Generate"));
        enthusiastsRow.setAlignment(Pos.CENTER);

        HBox trainersRow = new HBox(10, new Label("Trainers enrolled"), new Button("Generate"));
        trainersRow.setAlignment(Pos.CENTER);

        StackPane backButton = createBackButton();
        backButton.setOnMouseClicked(e -> showAdminPage(username));

        VBox layout = new VBox(20, backButton, greeting, title, enthusiastsRow, trainersRow);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene GenerateReportsScene = new Scene(layout, 600, 800);
        primaryStage.setScene(GenerateReportsScene);
    }

    private void showCreateAccountPage() {
        GridPane createAccountLayout = new GridPane();
        createAccountLayout.setAlignment(Pos.CENTER);
        createAccountLayout.setHgap(20);
        createAccountLayout.setVgap(20);

        StackPane backButton = createBackButton();
        backButton.setOnMouseClicked(e -> showHomePage());
        createAccountLayout.add(backButton, 0, 0);

        Button createEnthusiastBtn = new Button("Fitness Enthusiast");
        Button createTrainerBtn = new Button("Fitness Trainer");
        Button createAdminBtn = new Button("Fitness Admin");
        Label logo = new Label("Fitness Tracking App");
        logo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        createAccountLayout.add(logo, 1, 0, 2,1);
        Label description = new Label("Would you like to create an Account as a ");
        Label description2 = new Label("Fitness Trainer or a Fitness Enthusiast?");
        logo.setWrapText(true);
        description.setWrapText(true);
        description.setPrefWidth(400);
        createAccountLayout.add(description2, 0, 2, 3,1);
        createAccountLayout.add(description, 0, 1, 3, 1);
        Label aLabel = new Label("Are you an Admin?");

        HBox loginButtons = new HBox(10, createEnthusiastBtn, createTrainerBtn);
        createAccountLayout.add(loginButtons, 0, 3, 3, 1);

        VBox adminLoginLayout = new VBox(10, aLabel, createAdminBtn);
        createAccountLayout.add(adminLoginLayout, 2, 4, 3, 1);

        adminLoginLayout.setVisible(false);

        logo.setOnMouseClicked(event -> {
            logoClickCount++;
            if (logoClickCount == 3) {
                adminLoginLayout.setVisible(true);
                logoClickCount = 0;
            }
        });

        createEnthusiastBtn.setOnAction(e -> {
            try{
                LoginDAO loginDAO = new LoginDAO();
                String username = ThisUser.getInstance().getCurrentUsername();
                String password = ThisUser.getInstance().getCurrentPassword();

                if(username == null || username.isEmpty() || password == null || password.isEmpty()) {
                    System.out.println("username or password cannot be empty");
                }

                boolean userExist = loginDAO.duplicateEnthusiast(username);

                if(!userExist){
                    Users newEnthusiast = new Users(username, password);
                    loginDAO.createEnthusiast(newEnthusiast);
                    System.out.println("Successful");
                    showEnthusiastPage();
                } else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Username already exists");
                    alert.showAndWait();
                }
            } catch (Exception ex){
                System.out.println("Enthusiast account cannot be created: " + ex.getMessage());
            }
        });

        createTrainerBtn.setOnAction(e -> {
            try{
                LoginDAO loginDAO = new LoginDAO();
                String username = ThisUser.getInstance().getCurrentUsername();
                String password = ThisUser.getInstance().getCurrentPassword();

                if(username == null || username.isEmpty() || password == null || password.isEmpty()) {
                    System.out.println("username or password cannot be empty");
                }

                boolean userExist = loginDAO.duplicateTrainer(username);

                if(!userExist){
                    Users newTrainer = new Users(username, password);
                    loginDAO.createTrainer(newTrainer);
                    System.out.println("Successful");
                    showTrainerPage();
                } else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Username already exists");
                    alert.showAndWait();
                }
            } catch (Exception ex){
                System.out.println("Trainer account cannot be created: " + ex.getMessage());
            }
        });

        createAdminBtn.setOnAction(e -> {
            try{
                LoginDAO loginDAO = new LoginDAO();
                String username = ThisUser.getInstance().getCurrentUsername();
                String password = ThisUser.getInstance().getCurrentPassword();

                if(username == null || username.isEmpty() || password == null || password.isEmpty()) {
                    System.out.println("username or password cannot be empty");
                }

                boolean userExist = loginDAO.duplicateAdmin(username);

                if(!userExist){
                    Users newAdmin = new Users(username, password);
                    loginDAO.createAdmin(newAdmin);
                    System.out.println("Successful");
                    showAdminPage(username);
                } else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Username already exists");
                    alert.showAndWait();
                }
            } catch (Exception ex){
                System.out.println("Admin account cannot be created: " + ex.getMessage());
            }
        });

        Scene createAccountScene = new Scene(createAccountLayout, 600, 800);
        primaryStage.setScene(createAccountScene);
    }

    private void showForgotPasswordPage() {
        GridPane forgotPasswordLayout = new GridPane();
        forgotPasswordLayout.setAlignment(Pos.CENTER);
        forgotPasswordLayout.setHgap(10);
        forgotPasswordLayout.setVgap(10);

        Label title = new Label("Forgot Password Page");

        Label usernameLabel = new Label("Username: ");
        TextField usernameField = new TextField();
        Label birthdayLabel = new Label("Birthday: ");
        TextField birthdayField = new TextField();
        Label passwordLabel = new Label("New Password: ");
        PasswordField passwordField = new PasswordField();
        Button resetPasswordBtn = new Button("Reset Password");

        StackPane backButton = createBackButton();
        backButton.setOnMouseClicked(e -> showHomePage());

        resetPasswordBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String birthday = birthdayField.getText();
            String newPassword = passwordField.getText();

            if(username.isEmpty() || birthday.isEmpty() || newPassword == null || newPassword.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("All fields cannot be empty");
                alert.showAndWait();
            }
            try {
                LoginDAO loginDAO = new LoginDAO();
                boolean successful = loginDAO.forgetPassword(username, birthday, newPassword);
                if(successful) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Password changed successfully");
                    alert.showAndWait();
                    showHomePage();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Password change failed");
                    alert.showAndWait();
                }
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        });

        forgotPasswordLayout.add(title, 1 ,1, 2, 1);
        forgotPasswordLayout.add(usernameLabel, 0 ,2);
        forgotPasswordLayout.add(usernameField, 1 ,2);
        forgotPasswordLayout.add(birthdayLabel, 0 ,3);
        forgotPasswordLayout.add(birthdayField, 1 ,3);
        forgotPasswordLayout.add(passwordLabel, 0 ,4);
        forgotPasswordLayout.add(passwordField, 1 ,4);
        forgotPasswordLayout.add(resetPasswordBtn, 1 ,5, 2, 1);
        forgotPasswordLayout.add(backButton, 0, 0);


        Scene forgotPasswordScene = new Scene(forgotPasswordLayout, 600, 800);
        primaryStage.setScene(forgotPasswordScene);
    }


    private void showPasswordResetPage() {
	VBox passwordResetLayout = createPage("Password Reset Page");
	Label logo = new Label("Fitness Tracking App");
	logo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

	Label oldPWLabel = new Label("Password");
	PasswordField oldPasswordField = new PasswordField();
	Label newPWLabel = new Label("New Password");
	PasswordField newPasswordField = new PasswordField();
	Label confirmPWLabel = new Label("Confirm Password");
	PasswordField confirmPasswordField = new PasswordField();
	Button updatePWBtn = new Button("Update Password");

	updatePWBtn.setOnAction(e -> {
	    String username = ThisUser.getInstance().getCurrentUsername();
	    String oldPassword = oldPasswordField.getText();
	    String newPassword = newPasswordField.getText();
	    String confirmPassword = confirmPasswordField.getText();
	    if (oldPassword == null || newPassword == null || confirmPassword == null) {
		System.out.print("Password cannot be empty");
		return;
	    }
	    if (oldPassword.equals(confirmPassword)) {
		System.out.print("New password cannot be the old password");
		return;
	    }
	    if (!newPassword.equals(confirmPassword)) {
		System.out.print("New password and confirm password do not match");
		return;
	    }

	    try {
		LoginDAO loginDAO = new LoginDAO();
		boolean successful = loginDAO.changePassword(username, oldPassword, confirmPassword);
		if (successful) {
		    Alert alert = new Alert(Alert.AlertType.INFORMATION);
		    alert.setTitle("Success");
		    alert.setHeaderText(null);
		    alert.setContentText("Password changed successfully");
		    alert.showAndWait();
		} else {
		    Alert alert = new Alert(Alert.AlertType.ERROR);
		    alert.setTitle("Error");
		    alert.setHeaderText(null);
		    alert.setContentText("Password change failed");
		    alert.showAndWait();
		}
	    } catch (Exception ex) {
		System.out.println("Enthusiast account cannot be created: " + ex.getMessage());
	    }
	});

	passwordResetLayout.setAlignment(Pos.CENTER);
	passwordResetLayout.setPadding(new Insets(20));

	HBox oldPasswordLine = new HBox(10, oldPWLabel, oldPasswordField);
	oldPasswordLine.setAlignment(Pos.CENTER);
	HBox newPasswordLine = new HBox(10, newPWLabel, newPasswordField);
	newPasswordLine.setAlignment(Pos.CENTER);
	HBox confirmPasswordLine = new HBox(10, confirmPWLabel, confirmPasswordField);
	confirmPasswordLine.setAlignment(Pos.CENTER);

	passwordResetLayout.getChildren().addAll(
		logo, oldPasswordLine, newPasswordLine, confirmPasswordLine
		);

	Scene passwordResetScene = new Scene(passwordResetLayout, 600, 800);
	primaryStage.setScene(passwordResetScene);
    }

    // FITNESS ENTHUSIAST PAGES 
    private void showWorkoutHistoryPage() {
	Label title = new Label("Workout History & Progress");
	title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

	StackPane backButton = createBackButton();
	backButton.setOnMouseClicked(e -> showEnthusiastPage());

	GridPane progressGrid = new GridPane();
	progressGrid.setHgap(10);
	progressGrid.setVgap(10);
	progressGrid.setAlignment(Pos.CENTER);

	Label subscribersLabel = new Label("Trainers");
	Label typeLabel = new Label("Type of Workout");
	Label timesCompleted = new Label("Times completed");

	progressGrid.add(subscribersLabel, 0, 0);
	progressGrid.add(typeLabel, 1, 0);
	progressGrid.add(timesCompleted, 2, 0);

	for (int i = 1; i < 5; i++) {
	    progressGrid.add(new Label("Username"), 0, i);
	    progressGrid.add(new Label("Placeholder"), 1, i);
	    progressGrid.add(new Label("Placeholder"), 2, i);
	}

	VBox workoutHistoryLayout = new VBox(10, backButton, title, progressGrid);
	workoutHistoryLayout.setAlignment(Pos.CENTER);
	workoutHistoryLayout.setPadding(new Insets(20));

	Scene workoutHistoryScene = new Scene(workoutHistoryLayout, 600, 800);
	primaryStage.setScene(workoutHistoryScene);
    }



    private ObservableList<String> subscribedTrainers = FXCollections.observableArrayList();
    private Map<String, Integer> workoutCompletionCounts = new HashMap<>();

    private void showWorkoutPlansPage1() {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));

        StackPane backButton = createBackButton();
        backButton.setOnMouseClicked(e -> showEnthusiastPage());

        Label titleLabel = new Label("Workout Plans");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        HBox headerBox = new HBox(10, backButton, titleLabel);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        GridPane contentGrid = new GridPane();
        contentGrid.setHgap(20);
        contentGrid.setVgap(10);
        contentGrid.setPadding(new Insets(20));

        Label trainersLabel = new Label("Subscribed Trainers");
        trainersLabel.setStyle("-fx-font-weight: bold;");
        VBox trainersColumn = new VBox(10, trainersLabel);

        Label workoutTypeLabel = new Label("Select Workout Type");
        workoutTypeLabel.setStyle("-fx-font-weight: bold;");

        ComboBox<String> workoutTypeDropdown = new ComboBox<>();
        workoutTypeDropdown.getItems().addAll("Weight Loss", "Balanced", "Strength Training");

        VBox workoutTypeColumn = new VBox(10, workoutTypeLabel, workoutTypeDropdown);

        contentGrid.add(trainersColumn, 0, 0);
        contentGrid.add(workoutTypeColumn, 1, 0);

        VBox plansListContainer = new VBox(10);
        ScrollPane scrollPane = new ScrollPane(plansListContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);

        contentGrid.add(scrollPane, 0, 1, 2, 1);

        for (String trainer : subscribedTrainers) {
            trainersColumn.getChildren().add(new Label(trainer));
        }

        workoutTypeDropdown.setOnAction(e -> {
            plansListContainer.getChildren().clear();
            String selectedType = workoutTypeDropdown.getValue();

            if (selectedType != null) {
                for (String trainer : subscribedTrainers) {
                    HBox planEntry = new HBox(20);
                    planEntry.setAlignment(Pos.CENTER_LEFT);

                    Label trainerLabel = new Label(trainer);

                    String workoutKey = ThisUser.getInstance().getCurrentUsername() + "_" + 
                                        trainer + "_" + selectedType;
                    int count = workoutCompletionCounts.getOrDefault(workoutKey, 0);
                    Label countLabel = new Label("Completed: " + count + " times");

                    Button selectButton = new Button("Select Plan");
                    selectButton.setOnAction(event -> showWorkoutPlanPage2(trainer, selectedType));

                    planEntry.getChildren().addAll(trainerLabel, selectButton, countLabel);
                    plansListContainer.getChildren().add(planEntry);
                }
            }
        });

        mainLayout.getChildren().addAll(headerBox, contentGrid);

        Scene scene = new Scene(mainLayout, 600, 800);
        primaryStage.setScene(scene);
    }



    private void showWorkoutPlanPage2(String trainer, String workoutType) {
	Label title = new Label("Workout Plan");
	title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

	StackPane backButton = createBackButton();
	backButton.setOnMouseClicked(e -> showEnthusiastPage());

	GridPane workoutGrid = new GridPane();
	workoutGrid.setHgap(10);
	workoutGrid.setVgap(10);
	workoutGrid.setAlignment(Pos.CENTER);

	workoutGrid.add(new Label("Complete?"), 0, 0);
	workoutGrid.add(new Label("Exercise"), 1, 0);
	workoutGrid.add(new Label("No. of Reps"), 2, 0);
	workoutGrid.add(new Label("No. of Sets"), 3, 0);

	for (int i = 1; i <= 5; i++) {
	    CheckBox completeCheckBox = new CheckBox();
	    workoutGrid.add(completeCheckBox, 0, i);

	    TextField exerciseField = new TextField();
	    exerciseField.setEditable(false); 
	    workoutGrid.add(exerciseField, 1, i);

	    TextField repsField = new TextField();
	    repsField.setEditable(false); 
	    workoutGrid.add(repsField, 2, i);

	    TextField setsField = new TextField();
	    setsField.setEditable(false);
	    workoutGrid.add(setsField, 3, i);
	}

	Button completeWorkoutButton = new Button("Complete Workout");

	VBox workoutPlanLayout = new VBox(10, backButton, title, workoutGrid, completeWorkoutButton);
	workoutPlanLayout.setAlignment(Pos.CENTER);
	workoutPlanLayout.setPadding(new Insets(20));

	Scene workoutPlanScene = new Scene(workoutPlanLayout, 600, 800);
	primaryStage.setScene(workoutPlanScene);
    }

    private void showSubscriptionsPage() throws Exception {
	VBox mainLayout = new VBox(10);
	mainLayout.setPadding(new Insets(20));

	StackPane backButton = createBackButton();
	backButton.setOnMouseClicked(e -> showEnthusiastPage());

	Label titleLabel = new Label("Manage Subscriptions");
	titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

	HBox headerBox = new HBox(10, backButton, titleLabel);
	headerBox.setAlignment(Pos.CENTER_LEFT);

	GridPane contentGrid = new GridPane();
	contentGrid.setHgap(20);
	contentGrid.setVgap(10);
	contentGrid.setPadding(new Insets(20));

	
	Label trainerLabel = new Label("Trainer");
	trainerLabel.setStyle("-fx-font-weight: bold;");

	Label subscribeLabel = new Label("Subscribe");
	subscribeLabel.setStyle("-fx-font-weight: bold;");

	Label unsubscribeLabel = new Label("Unsubscribe");
	unsubscribeLabel.setStyle("-fx-font-weight: bold;");

	contentGrid.add(trainerLabel, 0, 0);
	contentGrid.add(subscribeLabel, 1, 0);
	contentGrid.add(unsubscribeLabel, 2, 0);

	
	List<String> allTrainers = getAvailableTrainers();  

	int rowIndex = 1;
	for(String trainer : allTrainers) {
	    Label trainerName = new Label(trainer);

	    Button subscribeBtn = new Button("Subscribe");
	    subscribeBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
	    subscribeBtn.setOnAction(e -> {
		if(!subscribedTrainers.contains(trainer)) {
		    subscribedTrainers.add(trainer);
		    updateSubscriptionDisplay(contentGrid, allTrainers);
		}
	    });

	    Button unsubscribeBtn = new Button("Unsubscribe");
	    unsubscribeBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
	    unsubscribeBtn.setOnAction(e -> {
		subscribedTrainers.remove(trainer);
		updateSubscriptionDisplay(contentGrid, allTrainers);
	    });

	    if(subscribedTrainers.contains(trainer)) {
		subscribeBtn.setDisable(true);
		unsubscribeBtn.setDisable(false);
	    } else {
		subscribeBtn.setDisable(false);
		unsubscribeBtn.setDisable(true);
	    }

	    contentGrid.add(trainerName, 0, rowIndex);
	    contentGrid.add(subscribeBtn, 1, rowIndex);
	    contentGrid.add(unsubscribeBtn, 2, rowIndex);

	    rowIndex++;
	}

	mainLayout.getChildren().addAll(headerBox, contentGrid);

	Scene scene = new Scene(mainLayout, 600, 800);
	primaryStage.setScene(scene);
    }

    private void updateSubscriptionDisplay(GridPane contentGrid, List<String> allTrainers) {
	contentGrid.getChildren().clear();

	contentGrid.add(new Label("Trainer"), 0, 0);
	contentGrid.add(new Label("Subscribe"), 1, 0);
	contentGrid.add(new Label("Unsubscribe"), 2, 0);

	int rowIndex = 1;
	for(String trainer : allTrainers) {
	    Label trainerName = new Label(trainer);

	    Button subscribeBtn = new Button("Subscribe");
	    subscribeBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
	    subscribeBtn.setOnAction(e -> {
		subscribedTrainers.add(trainer);
		updateSubscriptionDisplay(contentGrid, allTrainers);
	    });

	    Button unsubscribeBtn = new Button("Unsubscribe");
	    unsubscribeBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
	    unsubscribeBtn.setOnAction(e -> {
		subscribedTrainers.remove(trainer);
		updateSubscriptionDisplay(contentGrid, allTrainers);
	    });

	    if(subscribedTrainers.contains(trainer)) {
		subscribeBtn.setDisable(true);
		unsubscribeBtn.setDisable(false);
	    } else {
		subscribeBtn.setDisable(false);
		unsubscribeBtn.setDisable(true);
	    }

	    contentGrid.add(trainerName, 0, rowIndex);
	    contentGrid.add(subscribeBtn, 1, rowIndex);
	    contentGrid.add(unsubscribeBtn, 2, rowIndex);

	    rowIndex++;
	}
    }

   
    
    private List<String> getAvailableTrainers() throws Exception {
        LoginDAO loginDAO = new LoginDAO();
        return loginDAO.getAllTrainerUsernames();
    }
    
    


    private void showProfileManagementUserPage() {
	VBox ProfileManagementUserLayout = createPage("Profile Management");
	Label logo = new Label("Profile Management");
	logo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

	StackPane backButton = createBackButton();
        backButton.setOnMouseClicked(e -> showEnthusiastPage());

	Label nameLabel = new Label("Full Name");
	TextField nameField = new TextField();
	Label birthdayLabel = new Label("Birthday");
	TextField birthdayField = new TextField();
	Label genderLabel = new Label("Gender");
	ComboBox<String> genderBox = new ComboBox<>();
	genderBox.getItems().addAll("Female", "Male", "Others");
	Label heightLabel = new Label("Height (cm)");
	TextField heightField = new TextField();
	Label weightLabel = new Label("Weight (lb)");
	TextField weightField = new TextField();
	Label fitnessGoalsLabel = new Label("Fitness Goals");
	ComboBox<String> fitnessGoalsBox = new ComboBox<>();
	fitnessGoalsBox.getItems().addAll("Weight Loss", "Strength Training", "Balanced");
	Button updateBtn = new Button("Update");
	Button logoutBtn = new Button("Logout");

	
	ProfileManagementUserLayout.setAlignment(Pos.CENTER);
	ProfileManagementUserLayout.setPadding(new Insets(20));

	HBox nameLine = new HBox(10, nameLabel, nameField);
	nameLine.setAlignment(Pos.CENTER);
	HBox birthdayLine = new HBox(10, birthdayLabel, birthdayField);
	birthdayLine.setAlignment(Pos.CENTER);
	HBox genderLine = new HBox(10, genderLabel, genderBox);
	genderLine.setAlignment(Pos.CENTER);
	HBox heightLine = new HBox(10, heightLabel, heightField);
	heightLine.setAlignment(Pos.CENTER);
	HBox weightLine = new HBox(10, weightLabel, weightField);
	weightLine.setAlignment(Pos.CENTER);
	HBox fitnessGoalsLine = new HBox(10, fitnessGoalsLabel, fitnessGoalsBox);
	fitnessGoalsLine.setAlignment(Pos.CENTER);
	HBox updateBtnLine = new HBox(10, updateBtn);
	updateBtnLine.setAlignment(Pos.CENTER);
	HBox logoutBtnLine = new HBox(10, logoutBtn);
	logoutBtnLine.setAlignment(Pos.CENTER);

	ProfileManagementUserLayout.getChildren().addAll(
		nameLine, birthdayLine, genderLine, heightLine, weightLine, fitnessGoalsLine,
		updateBtnLine, logoutBtnLine
		);

	try {
	    LoginDAO loginDAO = new LoginDAO();
	    String username = ThisUser.getInstance().getCurrentUsername();
	    loginDAO.retrieveEnthusiastProfile(nameField, birthdayField, genderBox, heightField, weightField,
		    fitnessGoalsBox, username);

	} catch (Exception e){
	    e.printStackTrace();
	}

	updateBtn.setOnAction(e -> {
	    try{
		LoginDAO loginDAO = new LoginDAO();
		loginDAO.updateEnthusiastProfile(nameField.getText(), birthdayField.getText(),
			genderBox.getSelectionModel().getSelectedItem(), Double.parseDouble(heightField.getText()),
			Double.parseDouble(weightField.getText()), fitnessGoalsBox.getSelectionModel().getSelectedItem(),
			ThisUser.getInstance().getCurrentUsername());
	    } catch (Exception ex){
		ex.printStackTrace();
	    }
	});

	logoutBtn.setOnAction(e -> {
	    try{
		ThisUser.getInstance().clearCurrentUser();
		showHomePage();
	    } catch (Exception ex){
		ex.printStackTrace();
	    }
	});

	Scene ProfileManagementUserScene = new Scene(ProfileManagementUserLayout, 600, 800);
	primaryStage.setScene(ProfileManagementUserScene);
    }

    // FITNESS TRAINER PAGES
    private void showCreateWorkoutPlanPage() {
	Label title = new Label("Create Workout Plan");
	title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

	StackPane backButton = createBackButton();
	backButton.setOnMouseClicked(e -> showTrainerPage());

	Label fitnessGoalLabel = new Label("Fitness Goal");
	ComboBox<String> fitnessGoal = new ComboBox<>();
	fitnessGoal.getItems().addAll("Weight Loss", "Strength Training", "Balanced");

	GridPane workoutGrid = new GridPane();
	workoutGrid.setHgap(10);
	workoutGrid.setVgap(10);
	workoutGrid.setAlignment(Pos.CENTER);

	workoutGrid.add(new Label("Exercise"), 0, 0);
	workoutGrid.add(new Label("No. of Reps"), 1, 0);
	workoutGrid.add(new Label("No. of Sets"), 2, 0);

	TextField[] exerciseFields = new TextField[5];
	TextField[] repFields = new TextField[5];
	TextField[] setFields = new TextField[5];

	for (int i = 0; i < 5; i++) {
	    exerciseFields[i] = new TextField();
	    repFields[i] = new TextField();
	    setFields[i] = new TextField();
	    workoutGrid.add(exerciseFields[i], 0, i + 1);
	    workoutGrid.add(repFields[i], 1, i + 1);
	    workoutGrid.add(setFields[i], 2, i + 1);
	}

	Button createPlanButton = new Button("Create Plan");
	createPlanButton.setOnAction(e -> {
	    String goal = fitnessGoal.getValue();

	    try {
		if (goal != null) {
		    LoginDAO loginDAO = new LoginDAO();
		    loginDAO.insertWorkoutPlan(goal, exerciseFields, repFields, setFields, current.getCurrentUsername(), current.getCurrentPassword());
		}
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	});

	VBox layout = new VBox(10, backButton, title, fitnessGoalLabel, fitnessGoal, workoutGrid, createPlanButton);
	layout.setAlignment(Pos.CENTER);
	layout.setPadding(new Insets(20));

	primaryStage.setScene(new Scene(layout, 600, 800));
    }

    private void showUpdateWorkoutPlanPage() {
	Label title = new Label("Update Workout Plan");
	title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

	StackPane backButton = createBackButton();
	backButton.setOnMouseClicked(e -> showTrainerPage());

	Label fitnessGoalLabel = new Label("Fitness Goal");
	ComboBox<String> fitnessGoal = new ComboBox<>();
	fitnessGoal.getItems().addAll("Weight Loss", "Strength Training", "Balanced");

	GridPane workoutGrid = new GridPane();
	workoutGrid.setHgap(10);
	workoutGrid.setVgap(10);
	workoutGrid.setAlignment(Pos.CENTER);

	workoutGrid.add(new Label("Exercise"), 0, 0);
	workoutGrid.add(new Label("No. of Reps"), 1, 0);
	workoutGrid.add(new Label("No. of Sets"), 2, 0);

	TextField[] exerciseFields = new TextField[5];
	TextField[] repFields = new TextField[5];
	TextField[] setFields = new TextField[5];

	for (int i = 0; i < 5; i++) {
	    exerciseFields[i] = new TextField();
	    repFields[i] = new TextField();
	    setFields[i] = new TextField();
	    workoutGrid.add(exerciseFields[i], 0, i + 1);
	    workoutGrid.add(repFields[i], 1, i + 1);
	    workoutGrid.add(setFields[i], 2, i + 1);
	}

	Button updatePlanButton = new Button("Update Plan");

	fitnessGoal.setOnAction(e -> {
	    String goal = fitnessGoal.getValue();
	    try {
		if (goal != null) {
		    LoginDAO loginDAO = new LoginDAO();
		    loginDAO.loadWorkoutPlan(goal, exerciseFields, repFields, setFields, current.getCurrentUsername(), current.getCurrentPassword());
		}
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }

	});

	updatePlanButton.setOnAction(e -> {
	    String goal = fitnessGoal.getValue();

	    try {
		if (goal != null) {
		    LoginDAO loginDAO = new LoginDAO();
		    loginDAO.updateWorkoutPlan(goal, exerciseFields, repFields, setFields, current.getCurrentUsername(), current.getCurrentPassword());
		}
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	});

	VBox layout = new VBox(10, backButton, title, fitnessGoalLabel, fitnessGoal, workoutGrid, updatePlanButton);
	layout.setAlignment(Pos.CENTER);
	layout.setPadding(new Insets(20));

	primaryStage.setScene(new Scene(layout, 600, 800));
    }

    private void showUserProgressPage() {
	Label title = new Label("User Progress");
	title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

	StackPane backButton = createBackButton();
	backButton.setOnMouseClicked(e -> showTrainerPage());

	GridPane progressGrid = new GridPane();
	progressGrid.setHgap(10);
	progressGrid.setVgap(10);
	progressGrid.setAlignment(Pos.CENTER);

	Label subscribersLabel = new Label("Subscribers");
	Label typeLabel = new Label("Type of Workout");
	Label timesCompleted = new Label("No. of times completed");

	progressGrid.add(subscribersLabel, 0, 0);
	progressGrid.add(typeLabel, 1, 0);
	progressGrid.add(timesCompleted, 2, 0);

	for (int i = 1; i < 5; i++) {
	    progressGrid.add(new Label("Username"), 0, i);
	    progressGrid.add(new Label("Placeholder"), 1, i);
	    progressGrid.add(new Label("Placeholder"), 2, i);
	}

	VBox userProgressLayout = new VBox(10, backButton, title, progressGrid);
	userProgressLayout.setAlignment(Pos.CENTER);
	userProgressLayout.setPadding(new Insets(20));

	Scene userProgressScene = new Scene(userProgressLayout, 600, 800);
	primaryStage.setScene(userProgressScene);
    }

    private void showProfileManagementTrainerPage() {
	Label title = new Label("Profile Management");
	title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

	StackPane backButton = createBackButton();
	backButton.setOnMouseClicked(e -> showTrainerPage());

	Label nameLabel = new Label("Full Name");
	TextField nameField = new TextField();
	Label birthdayLabel = new Label("Birthday");
	TextField birthdayField = new TextField();
	Label genderLabel = new Label("Gender");
	ComboBox<String> genderBox = new ComboBox<>();
	genderBox.getItems().addAll("Female", "Male");
	Label heightLabel = new Label("Height (cm)");
	TextField heightField = new TextField();
	Label weightLabel = new Label("Weight (lb)");
	TextField weightField = new TextField();
	Label fitnessGoalsLabel = new Label("Fitness Goals");
	ComboBox<String> fitnessGoalsBox = new ComboBox<>();
	fitnessGoalsBox.getItems().addAll("Weight Loss", "Strength Training", "Balanced");
	Label passwordLabel = new Label("New Password");
	PasswordField passwordField = new PasswordField();
	Label confirmPasswordLabel = new Label("Confirm New Password");
	PasswordField confirmPasswordField = new PasswordField();
	Button updateButton = new Button("Update");
	Button logoutButton = new Button("Log Out");
	Label errorLabel = new Label();
	errorLabel.setStyle("-fx-text-fill: red;");

	HBox nameLine = new HBox(10, nameLabel, nameField);
	nameLine.setAlignment(Pos.CENTER);
	HBox birthdayLine = new HBox(10, birthdayLabel, birthdayField);
	birthdayLine.setAlignment(Pos.CENTER);
	HBox genderLine = new HBox(10, genderLabel, genderBox);
	genderLine.setAlignment(Pos.CENTER);
	HBox heightLine = new HBox(10, heightLabel, heightField);
	heightLine.setAlignment(Pos.CENTER);
	HBox weightLine = new HBox(10, weightLabel, weightField);
	weightLine.setAlignment(Pos.CENTER);
	HBox fitnessGoalsLine = new HBox(10, fitnessGoalsLabel, fitnessGoalsBox);
	fitnessGoalsLine.setAlignment(Pos.CENTER);
	HBox updateBtnLine = new HBox(10, updateButton);
	updateBtnLine.setAlignment(Pos.CENTER);
	HBox logoutBtnLine = new HBox(10, logoutButton);
	logoutBtnLine.setAlignment(Pos.CENTER);

	try {
	    LoginDAO loginDAO = new LoginDAO();
	    String username = current.getCurrentUsername();
	    loginDAO.retrieveTrainerProfile(nameField, birthdayField, genderBox, heightField, weightField,
		    fitnessGoalsBox, username);



	} catch (Exception e){
	    e.printStackTrace();
	}

	updateButton.setOnAction(e -> {
	    try{
		LoginDAO loginDAO = new LoginDAO();
		loginDAO.updateTrainerProfile(nameField.getText(), birthdayField.getText(),
			genderBox.getSelectionModel().getSelectedItem(), Double.parseDouble(heightField.getText()),
			Double.parseDouble(weightField.getText()), fitnessGoalsBox.getSelectionModel().getSelectedItem(),
			current.getCurrentUsername());

		String username = current.getCurrentUsername();
		String oldPassword = current.getCurrentPassword();
		String password = passwordField.getText();
		String confirmPassword = confirmPasswordField.getText();


		if (password.isEmpty() || confirmPassword.isEmpty()) {
		    errorLabel.setText("Fields cannot be empty.");
		} 
		else if (!password.equals(confirmPassword)) {
		    errorLabel.setText("Passwords do not match.");
		} 
		else {

		    System.out.println(username);
		    System.out.println(oldPassword);
		    System.out.println(confirmPassword);

		    loginDAO.changePassword(username, oldPassword, confirmPassword);

		    errorLabel.setText("Password changed");
		    errorLabel.setStyle("-fx-text-fill: green;");  

		}


	    } catch (Exception ex){
		ex.printStackTrace();
	    }
	});

	logoutButton.setOnAction(e -> {
	    try{
		ThisUser.getInstance().clearCurrentUser();
		showHomePage();
	    } catch (Exception ex){
		ex.printStackTrace();
	    }
	});

	VBox profileManagementLayout = new VBox(10, backButton, title, nameLine, birthdayLine, genderLine, heightLine, weightLine, fitnessGoalsLine, passwordLabel, passwordField, 
		confirmPasswordLabel, confirmPasswordField, 
		errorLabel, updateButton, logoutButton);
	profileManagementLayout.setAlignment(Pos.CENTER);
	profileManagementLayout.setPadding(new Insets(20));

	Scene profileManagementScene = new Scene(profileManagementLayout, 600, 800);
	primaryStage.setScene(profileManagementScene);
    }

    private Scene showAccountTypePage(){
	Button createEnthusiastBtn = new Button("Fitness Enthusiast");
	Button createTrainerBtn = new Button("Fitness Trainer");
	Button createAdminBtn = new Button("Fitness Admin");
	Label logo = new Label("Fitness Tracking App");
	logo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
	Label description = new Label("Would you like to create an Account as a Fitness Trainer or a Fitness Enthusiast?");
	Label aLabel = new Label("Are you an Admin?");

	HBox loginButtons = new HBox(10, createEnthusiastBtn, createTrainerBtn);
	loginButtons.setAlignment(Pos.CENTER);

	VBox adminLoginLayout = new VBox(10, aLabel, createAdminBtn);
	adminLoginLayout.setAlignment(Pos.CENTER);

	VBox layout = new VBox(20);
	layout.setAlignment(Pos.CENTER);
	layout.getChildren().addAll(logo, description, loginButtons, adminLoginLayout);

	return null;
    }

    private VBox createPage(String pageTitle) {
        StackPane backButton = createBackButton();
        backButton.setOnMouseClicked(e -> showHomePage());

        Label pageLabel = new Label(pageTitle);
        pageLabel.setStyle("-fx-font-size: 20px;");

        VBox pageLayout = new VBox(10, backButton, pageLabel);
        pageLayout.setAlignment(Pos.CENTER);
        pageLayout.setPadding(new Insets(20));

        return pageLayout;
    }

    // Method to create the back button with a triangle icon
    private StackPane createBackButton() {
	Polygon triangle = new Polygon();
	triangle.getPoints().addAll(
		0.0, 20.0,
		20.0, 0.0,
		20.0, 40.0
		);
	triangle.setFill(Color.BLUE);

	StackPane backButton = new StackPane(triangle);
	backButton.setPadding(new Insets(10));
	backButton.setAlignment(Pos.TOP_LEFT); 

	return backButton;
    }

    public static void main(String[] args) {
	launch(args);
    }
}