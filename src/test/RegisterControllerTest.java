package test;

import app.utils.JavaFXInitializer;
import controller.RegisterController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Datasource;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

//test for handleRegisterButtonAction method
public class RegisterControllerTest {

    private TextField fullNameField;
    private TextField usernameField;
    private TextField emailField;
    private PasswordField passwordField;
    private ActionEvent actionEvent;

    private RegisterController registerController;

    @BeforeAll
    public static void setUpAll() {
        new JavaFXInitializer().init();
    }

    @BeforeEach
    public void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            registerController = new RegisterController();
            fullNameField = new TextField();
            usernameField = new TextField();
            emailField = new TextField();
            passwordField = new PasswordField();
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void handleRegisterButtonAction_test() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Set user input
            fullNameField.setText("John Doe");
            usernameField.setText("johndoe");
            emailField.setText("johndoe@example.com");
            passwordField.setText("password123");

            // Set fields in controller
            registerController.fullNameField = fullNameField;
            registerController.usernameField = usernameField;
            registerController.emailField = emailField;
            registerController.passwordField = passwordField;

            // Call the method to be tested
            try {
                registerController.handleRegisterButtonAction(actionEvent);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Verify the result
            Datasource datasource = Datasource.getInstance();
            try {
                User user = datasource.getUserByUsername("johndoe");
                assertNotNull(user);
                assertEquals("John Doe", user.getFullname());
                assertEquals("johndoe@example.com", user.getEmail());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void handleRegisterButtonAction_boundaryValues_test() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Set fields in controller
            registerController.fullNameField = fullNameField;
            registerController.usernameField = usernameField;
            registerController.emailField = emailField;
            registerController.passwordField = passwordField;

            // Empty Strings
            fullNameField.setText("");
            usernameField.setText("");
            emailField.setText("");
            passwordField.setText("");
            try {
                registerController.handleRegisterButtonAction(actionEvent);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Minimum length strings
            fullNameField.setText("a");
            usernameField.setText("a");
            emailField.setText("a@b.com");
            passwordField.setText("a");
            try {
                registerController.handleRegisterButtonAction(actionEvent);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Maximum length strings
            String maxLengthString = "a".repeat(255);
            fullNameField.setText(maxLengthString);
            usernameField.setText(maxLengthString);
            emailField.setText(maxLengthString + "@example.com");
            try {
                registerController.handleRegisterButtonAction(actionEvent);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void handleRegisterButtonAction_coverage_test() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Set fields in controller
            registerController.fullNameField = fullNameField;
            registerController.usernameField = usernameField;
            registerController.emailField = emailField;
            registerController.passwordField = passwordField;

            // Valid input
            fullNameField.setText("John Doe");
            usernameField.setText("johndoe");
            emailField.setText("johndoe@example.com");
            passwordField.setText("password123");
            try {
                registerController.handleRegisterButtonAction(actionEvent);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Test with existing username
            fullNameField.setText("John Doe");
            usernameField.setText("johndoe");
            emailField.setText("johndoe@example.com");
            passwordField.setText("password123");
            try {
                registerController.handleRegisterButtonAction(actionEvent);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Test with existing email
            fullNameField.setText("John Doe");
            usernameField.setText("johndoe");
            emailField.setText("johndoe@example.com");
            passwordField.setText("password123");
            try {
                registerController.handleRegisterButtonAction(actionEvent);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Test with empty fields
            fullNameField.setText("");
            usernameField.setText("");
            emailField.setText("");
            passwordField.setText("");
            try {
                registerController.handleRegisterButtonAction(actionEvent);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }
}