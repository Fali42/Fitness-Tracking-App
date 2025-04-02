package com.FitnessTrackingApp.ui;



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
                showAdminPage();
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

        // Layout
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

        // Sets the scene size
        Scene scene = new Scene(layout, 600, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // METHODS FOR EACH PAGE (still incomplete) 
    private void showEnthusiastPage() {
	VBox enthusiastLayout = createPage("Welcome, Enthusiast!");
	Scene enthusiastScene = new Scene(enthusiastLayout, 600, 800);
	primaryStage.setScene(enthusiastScene);
    }

    private void showTrainerPage() {
	Label titleLabel = new Label("Hello, \"Username\"");
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

    private void showAdminPage() {
	VBox trainerLayout = createPage("Welcome, Admin!");
	Scene trainerScene = new Scene(trainerLayout, 600, 800);
	primaryStage.setScene(trainerScene);
    }

    private void showCreateAccountPage() {
        VBox createAccountLayout = createPage("Welcome, Create Account!");

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

        createAccountLayout.setAlignment(Pos.CENTER);
        createAccountLayout.getChildren().addAll(logo, description, loginButtons, adminLoginLayout);

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
                    showAdminPage();
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
        VBox forgotPasswordLayout = createPage("Forgot Password Page");
        Label logo = new Label("Fitness Tracking App");
        logo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label usernameLabel = new Label("Username: ");
        TextField usernameField = new TextField();
        Label birthdayLabel = new Label("Birthday: ");
        TextField birthdayField = new TextField();
        Label passwordLabel = new Label("New Password: ");
        PasswordField passwordField = new PasswordField();
        Button resetPasswordBtn = new Button("Reset Password");

        resetPasswordBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String birthday = birthdayField.getText();
            String newPassword = passwordField.getText();
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

        forgotPasswordLayout.setAlignment(Pos.CENTER);
        forgotPasswordLayout.setPadding(new Insets(20));

        HBox usernameLine = new HBox(10, usernameLabel, usernameField);
        usernameLine.setAlignment(Pos.CENTER);
        HBox birthdayLine = new HBox(10, birthdayLabel, birthdayField);
        birthdayLine.setAlignment(Pos.CENTER);
        HBox newPasswordLine = new HBox(10, passwordLabel, passwordField);
        newPasswordLine.setAlignment(Pos.CENTER);
        HBox resetPasswordBtnLine = new HBox(10, resetPasswordBtn);
        resetPasswordBtnLine.setAlignment(Pos.CENTER);

        forgotPasswordLayout.getChildren().addAll(
                logo, usernameLine, birthdayLine, newPasswordLine, resetPasswordBtn
        );

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
	VBox workoutHistoryLayout = createPage("Workout History & Progress");
	Scene workoutHistoryScene = new Scene(workoutHistoryLayout, 600, 800);
	primaryStage.setScene(workoutHistoryScene);
    }

    private void showWorkoutPlansPage1() {
	VBox workoutPlansLayout1 = createPage("Workout Plans");
	Scene workoutPlansScene1 = new Scene(workoutPlansLayout1, 600, 800);
	primaryStage.setScene(workoutPlansScene1);
    }

    private void showWorkoutPlansPage2() {
	VBox workoutPlansLayout2 = createPage("Workout Plans");
	Scene workoutPlansScene2 = new Scene(workoutPlansLayout2, 600, 800);
	primaryStage.setScene(workoutPlansScene2);
    }

    private void showSubscriptionsPage() {
	VBox SubscriptionsLayout = createPage("Subscriptions");
	Scene SubscriptionsScene = new Scene(SubscriptionsLayout, 600, 800);
	primaryStage.setScene(SubscriptionsScene);
    }

    private void showProfileManagementUserPage() {
        VBox ProfileManagementUserLayout = createPage("Profile Management");
        Label logo = new Label("Fitness Tracking App");
        logo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

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

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

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

        layout.getChildren().addAll(
                logo, nameLine, birthdayLine, genderLine, heightLine, weightLine, fitnessGoalsLine,
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

	// Adding headers
	workoutGrid.add(new Label("Exercise"), 0, 0);
	workoutGrid.add(new Label("No. of Reps"), 1, 0);
	workoutGrid.add(new Label("No. of Sets"), 2, 0);

	for (int i = 1; i <= 5; i++) {
	    workoutGrid.add(new TextField(), 0, i);
	    workoutGrid.add(new TextField(), 1, i);
	    workoutGrid.add(new TextField(), 2, i);
	}

	Button createPlanButton = new Button("Create Plan");

	VBox createWorkoutLayout = new VBox(10, backButton, title, fitnessGoalLabel, fitnessGoal, workoutGrid, createPlanButton);
	createWorkoutLayout.setAlignment(Pos.CENTER);
	createWorkoutLayout.setPadding(new Insets(20));

	Scene createWorkoutScene = new Scene(createWorkoutLayout, 600, 800);
	primaryStage.setScene(createWorkoutScene);
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

	// Adding headers
	workoutGrid.add(new Label("Exercise"), 0, 0);
	workoutGrid.add(new Label("No. of Reps"), 1, 0);
	workoutGrid.add(new Label("No. of Sets"), 2, 0);

	for (int i = 1; i <= 5; i++) {
	    workoutGrid.add(new TextField(), 0, i);
	    workoutGrid.add(new TextField(), 1, i);
	    workoutGrid.add(new TextField(), 2, i);
	}

	Button updatePlanButton = new Button("Update Plan");

	VBox updateWorkoutLayout = new VBox(10, backButton, title, fitnessGoalLabel, fitnessGoal, workoutGrid, updatePlanButton);
	updateWorkoutLayout.setAlignment(Pos.CENTER);
	updateWorkoutLayout.setPadding(new Insets(20));

	Scene updateWorkoutScene = new Scene(updateWorkoutLayout, 600, 800);
	primaryStage.setScene(updateWorkoutScene);
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

	Label passwordLabel = new Label("New Password");
	PasswordField passwordField = new PasswordField();

	Label confirmPasswordLabel = new Label("Confirm New Password");
	PasswordField confirmPasswordField = new PasswordField();

	Label errorLabel = new Label();
	errorLabel.setStyle("-fx-text-fill: red;");

	Button updateButton = new Button("Update");
	updateButton.setOnAction(e -> {
	    String password = passwordField.getText();
	    String confirmPassword = confirmPasswordField.getText();

	    if (password.isEmpty() || confirmPassword.isEmpty()) {
		errorLabel.setText("Fields cannot be empty.");
	    } 
	    else if (!password.equals(confirmPassword)) {
		errorLabel.setText("Passwords do not match.");
	    } 
	    else {
		errorLabel.setText("");
		showTrainerPage(); // Proceed with updating logic here
	    }
	});

	Button logoutButton = new Button("Log Out");
	logoutButton.setOnAction(e -> showTrainerPage());

	VBox profileManagementLayout = new VBox(10, backButton, title, passwordLabel, passwordField, 
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
    
    // Helper method to create pages with a back button
    private VBox createPage(String pageTitle) {

	StackPane backButton = createBackButton();
	backButton.setOnMouseClicked(e -> showTrainerPage());

	// Page title
	Label pageLabel = new Label(pageTitle);
	pageLabel.setStyle("-fx-font-size: 20px;");

	// Layout for the page
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

	// Create a StackPane to hold the triangle and handle events
	StackPane backButton = new StackPane(triangle);
	backButton.setPadding(new Insets(10));
	backButton.setAlignment(Pos.TOP_LEFT); 

	return backButton;
    }

    public static void main(String[] args) {
	launch(args);
    }
}