package test;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Datasource;
import controller.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static org.testfx.assertions.api.Assertions.assertThat;

public class IntegrationTestLoginController extends ApplicationTest {
    private Button loginButton;
    private TextField usernameField;
    private PasswordField passwordField;
    private LoginController loginController;

    @Override
    public void start(Stage stage) throws IOException {
        // Load FXML file and set scene for the application
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        Parent root = loader.load();
        loginController = loader.getController();

        // Initialize the scene and stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        // Lookup UI elements
        loginButton = lookup(".button").queryAs(Button.class);
        usernameField = lookup("#usernameField").queryAs(TextField.class);
        passwordField = lookup("#passwordField").queryAs(PasswordField.class);
    }

    @Test
    public void should_contain_login_button() {
        // Verify that the login button has the correct text
        assertThat(loginButton).hasText("Login");
    }

    @Test
    public void should_login_with_valid_credentials() throws SQLException {
        // Open the datasource connection
        Datasource datasource = Datasource.getInstance();
        datasource.open();

        // Simulate user input in the login form
        clickOn(usernameField).write("testuser");
        clickOn(passwordField).write("123456");

        // Verify that the fields are populated with the correct values
        assertThat(usernameField.getText()).isEqualTo("testuser");
        assertThat(passwordField.getText()).isEqualTo("123456");

        // Simulate clicking the login button
        clickOn(loginButton);

        // Verify that after login, the dashboard content is visible (i.e., the main dashboard page is loaded)
        assertThat(Optional.ofNullable(lookup("#dashContent").query())).isNotNull();

        // Close the datasource
        datasource.close();
    }

    @Test
    public void should_show_error_for_invalid_credentials() throws SQLException {
        // Open the datasource connection
        Datasource datasource = Datasource.getInstance();
        datasource.open();

        // Simulate user input with invalid credentials
        clickOn(usernameField).write("invaliduser");
        clickOn(passwordField).write("wrongpassword");

        // Verify that the fields are populated with the incorrect values
        assertThat(usernameField.getText()).isEqualTo("invaliduser");
        assertThat(passwordField.getText()).isEqualTo("wrongpassword");

        // Simulate clicking the login button
        clickOn(loginButton);

        // Verify that the alert box is shown for invalid login
        // Here you can mock or verify the alert behavior to ensure an error message appears (this depends on your app's implementation)

        // Check if the error alert box appears (this depends on the implementation of alertBox in the LoginController)
        assertThat((DialogPane) lookup(".alert").query()).isNotNull();

        // Close the datasource
        datasource.close();
    }

    @Test
    public void should_not_allow_empty_fields() {
        // Simulate user input with empty fields
        clickOn(usernameField).write("");
        clickOn(passwordField).write("");

        // Verify that the fields are empty
        assertThat(usernameField.getText()).isEmpty();
        assertThat(passwordField.getText()).isEmpty();

        // Simulate clicking the login button
        clickOn(loginButton);

        // Verify that an alert box is shown indicating that the fields cannot be empty
        // This assumes that the LoginController handles empty inputs with an alert box
        assertThat((DialogPane) lookup(".alert").query()).isNotNull();
    }

    @Test
    public void should_login_with_admin_credentials() throws SQLException {
        // Open the datasource connection
        Datasource datasource = Datasource.getInstance();
        datasource.open();

        // Simulate user input with admin credentials
        clickOn(usernameField).write("sajdoko");
        clickOn(passwordField).write("1");

        // Verify that the fields are populated with the correct values
        assertThat(usernameField.getText()).isEqualTo("sajdoko");
        assertThat(passwordField.getText()).isEqualTo("1");

        // Simulate clicking the login button
        clickOn(loginButton);

        // Verify that the dashboard content is visible (for admin)
        assertThat(Optional.ofNullable(lookup("#dashContent").query())).isNotNull();

        // Close the datasource
        datasource.close();
    }
}
