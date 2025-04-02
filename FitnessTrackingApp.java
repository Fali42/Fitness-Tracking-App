package FitnessTrackingApp;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
        VBox trainerLayout = createPage("Welcome, Trainer!");
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
        VBox CreateWorkoutPlanLayout = createPage("Create Workout Plan");
        Scene CreateWorkoutPlanScene = new Scene(CreateWorkoutPlanLayout, 600, 800);
        primaryStage.setScene(CreateWorkoutPlanScene);
    }
    
    private void showUserProgressPage() {
        VBox UserProgressLayout = createPage("Create Workout Plan");
        Scene UserProgressScene = new Scene(UserProgressLayout, 600, 800);
        primaryStage.setScene(UserProgressScene);
    }
    
    private void showUpdateWorkoutPlanPage() {
        VBox updateWorkoutPlanLayout = createPage("Create Workout Plan");
        Scene UserProgressScene = new Scene(updateWorkoutPlanLayout, 600, 800);
        primaryStage.setScene(UserProgressScene);
    }
    
    private void showProfileManagementTrainerPage() {
        VBox ProfileManagementTrainerLayout = createPage("Profile Management");
        Scene ProfileManagementTrainerScene = new Scene(ProfileManagementTrainerLayout, 600, 800);
        primaryStage.setScene(ProfileManagementTrainerScene);
    }
    
    // Helper method to create pages with a back button
    private VBox createPage(String pageTitle) {
        
        StackPane backButton = createBackButton();
        backButton.setOnMouseClicked(e -> showHomePage());

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
