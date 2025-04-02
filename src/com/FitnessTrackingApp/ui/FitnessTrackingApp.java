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
	loginEnthusiastBtn.setOnAction(e -> showEnthusiastPage());

	Button loginTrainerBtn = new Button("Login as Trainer");
	loginTrainerBtn.setOnAction(e -> showTrainerPage());

	Button createAccountBtn = new Button("Create Account");
	createAccountBtn.setOnAction(e -> showCreateAccountPage());

	Hyperlink forgotPasswordLink = new Hyperlink("Forgot Password?");
	forgotPasswordLink.setOnAction(e -> showForgotPasswordPage());

	Button adminLoginBtn = new Button("Admin Login");
	adminLoginBtn.setVisible(false);
	adminLoginBtn.setOnAction(e -> showAdminPage());

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
	VBox createAccountLayout = createPage("Create Account Page");
	Scene createAccountScene = new Scene(createAccountLayout, 600, 800);
	primaryStage.setScene(createAccountScene);
    }

    private void showForgotPasswordPage() {
	VBox forgotPasswordLayout = createPage("Forgot Password Page");
	Scene forgotPasswordScene = new Scene(forgotPasswordLayout, 600, 800);
	primaryStage.setScene(forgotPasswordScene);
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