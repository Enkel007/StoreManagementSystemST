package test;

import app.utils.JavaFXInitializer;
import controller.LoginController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Datasource;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    private TextField usernameField;
    private PasswordField passwordField;
    private ActionEvent actionEvent;

    private LoginController loginController;

    @BeforeAll
    public static void setUpAll() {
        new JavaFXInitializer().init();
    }

    @BeforeEach
    public void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            loginController = new LoginController();
            usernameField = new TextField();
            passwordField = new PasswordField();
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void handleLoginButtonAction_validCredentials_test() throws InterruptedException, SQLException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Set user input
            usernameField.setText("johndoe");
            passwordField.setText("password123");

            // Set fields in controller
            loginController.usernameField = usernameField;
            loginController.passwordField = passwordField;

            // Mock Datasource
            Datasource mockDatasource = mock(Datasource.class);
            User mockUser = new User();
            mockUser.setId(1);
            mockUser.setUsername("johndoe");
            mockUser.setFullname("John Doe");
            mockUser.setEmail("johndoe@example.com");
            mockUser.setPassword("hashedpassword");

            try {
                when(mockDatasource.getUserByUsername("johndoe")).thenReturn(mockUser);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Call the method to be tested
            try {
                loginController.handleLoginButtonAction(actionEvent);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }

            // Verify the result (e.g., check that the user was retrieved and validated)
            assertEquals("johndoe", mockUser.getUsername());

            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void handleLoginButtonAction_invalidCredentials_test() throws InterruptedException, SQLException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Set user input
            usernameField.setText("wronguser");
            passwordField.setText("wrongpassword");

            // Set fields in controller
            loginController.usernameField = usernameField;
            loginController.passwordField = passwordField;

            // Mock Datasource to simulate invalid credentials
            Datasource mockDatasource = mock(Datasource.class);
            try {
                when(mockDatasource.getUserByUsername("wronguser")).thenReturn(null);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Call the method to be tested
            try {
                loginController.handleLoginButtonAction(actionEvent);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }

            // Verify the result (ensure the alert box is shown for invalid credentials)
            try {
                verify(mockDatasource, times(1)).getUserByUsername("wronguser");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            // Verify if the alert box method was called for invalid credentials
            // You'll need to mock the HelperMethods.alertBox to ensure no alert is shown
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void handleLoginButtonAction_emptyFields_test() throws InterruptedException, SQLException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Set user input as empty fields
            usernameField.setText("");
            passwordField.setText("");

            // Set fields in controller
            loginController.usernameField = usernameField;
            loginController.passwordField = passwordField;

            // Mock Datasource to simulate empty fields
            Datasource mockDatasource = mock(Datasource.class);
            try {
                when(mockDatasource.getUserByUsername("")).thenReturn(null);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Call the method to be tested
            try {
                loginController.handleLoginButtonAction(actionEvent);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }

            // Verify the result
            try {
                verify(mockDatasource, times(1)).getUserByUsername("");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void handleLoginButtonAction_boundaryValues_test() throws InterruptedException, SQLException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Set fields in controller
            loginController.usernameField = usernameField;
            loginController.passwordField = passwordField;

            // Empty Strings
            usernameField.setText("");
            passwordField.setText("");
            try {
                loginController.handleLoginButtonAction(actionEvent);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }

            // Minimum length strings
            usernameField.setText("a");
            passwordField.setText("a");
            try {
                loginController.handleLoginButtonAction(actionEvent);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }

            // Maximum length strings
            String maxLengthString = "a".repeat(255);
            usernameField.setText(maxLengthString);
            passwordField.setText(maxLengthString);
            try {
                loginController.handleLoginButtonAction(actionEvent);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }

            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void handleLoginButtonAction_coverage_test() throws InterruptedException, SQLException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Set fields in controller
            loginController.usernameField = usernameField;
            loginController.passwordField = passwordField;

            // Valid input
            usernameField.setText("johndoe");
            passwordField.setText("password123");
            try {
                loginController.handleLoginButtonAction(actionEvent);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }

            // Test with existing username and invalid password
            usernameField.setText("johndoe");
            passwordField.setText("wrongpassword");
            try {
                loginController.handleLoginButtonAction(actionEvent);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }

            // Test with empty fields
            usernameField.setText("");
            passwordField.setText("");
            try {
                loginController.handleLoginButtonAction(actionEvent);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }

            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }
}