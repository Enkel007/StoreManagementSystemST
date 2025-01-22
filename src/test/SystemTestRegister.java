package test;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Datasource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static org.testfx.assertions.api.Assertions.assertThat;

public class SystemTestRegister extends ApplicationTest {
    Button registerButton;

    @Override
    public void start(Stage stage) throws IOException {
        Parent sceneRoot = new FXMLLoader().load(getClass().getResource("/view/register.fxml"));
        Scene scene = new Scene(sceneRoot);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        registerButton = lookup(".button").queryAs(Button.class);
    }

    @Test
    public void should_contain_register_button() {
        assertThat(registerButton).hasText("Register");
    }

    @Test
    public void should_click_on_register_button() throws SQLException {
        // Initialize the Datasource
        Datasource datasource = Datasource.getInstance();
        datasource.open();

        // Simulate user input
        clickOn("#fullNameField").write("John Doe");
        clickOn("#usernameField").write("johndoe");
        clickOn("#emailField").write("johndoe@example.com");
        clickOn("#passwordField").write("password123");

        // Verify that the fields are not empty
        assertThat(lookup("#fullNameField").queryAs(TextField.class).getText()).isNotEmpty();
        assertThat(lookup("#usernameField").queryAs(TextField.class).getText()).isNotEmpty();
        assertThat(lookup("#emailField").queryAs(TextField.class).getText()).isNotEmpty();
        assertThat(lookup("#passwordField").queryAs(PasswordField.class).getText()).isNotEmpty();

        // Click the register button
        clickOn(registerButton);

        // Wait for the scene to update
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the scene has changed to the user main dashboard
        assertThat(Optional.ofNullable(lookup("#dashContent").query())).isNotNull();

        // Close the Datasource
        datasource.close();
    }
}
