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

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static org.testfx.assertions.api.Assertions.assertThat;

public class SystemTestLogin extends ApplicationTest {
    Button button;
    @Override
    public void start(Stage stage) throws IOException {
        Parent sceneRoot = new FXMLLoader().load(getClass().getResource("/view/login.fxml"));
        Scene scene = new Scene(sceneRoot);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        button = lookup(".button").queryAs(Button.class);
    }

    @Test
    public void should_contain_button() {
        assertThat(button).hasText("Login");
    }

    @Test
    public void should_click_on_button_user() throws SQLException {
        // Initialize the Datasource
        Datasource datasource = Datasource.getInstance();
        datasource.open();

        // Simulate user input
        clickOn("#usernameField").write("testuser");
        clickOn("#passwordField").write("123456");

        // Verify that the fields are not empty
        assertThat(lookup("#usernameField").queryAs(TextField.class).getText()).isNotEmpty();
        assertThat(lookup("#passwordField").queryAs(PasswordField.class).getText()).isNotEmpty();

        // Click the login button
        clickOn(button);

        // Verify the scene has changed to the user main dashboard
        assertThat(Optional.ofNullable(lookup("#dashContent").query())).isNotNull();

        // Close the Datasource
        datasource.close();
    }

    @Test
    public void should_click_on_button_admin() throws SQLException {
        // Initialize the Datasource
        Datasource datasource = Datasource.getInstance();
        datasource.open();

        // Simulate user input
        clickOn("#usernameField").write("sajdoko");
        clickOn("#passwordField").write("1");

        // Verify that the fields are not empty
        assertThat(lookup("#usernameField").queryAs(TextField.class).getText()).isNotEmpty();
        assertThat(lookup("#passwordField").queryAs(PasswordField.class).getText()).isNotEmpty();

        // Click the login button
        clickOn(button);

        // Verify the scene has changed to the user main dashboard
        assertThat(Optional.ofNullable(lookup("#dashContent").query())).isNotNull();

        // Close the Datasource
        datasource.close();
    }
}