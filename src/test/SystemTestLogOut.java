package test;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
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

public class SystemTestLogOut extends ApplicationTest {
    Button loginButton;
    Button logoutButton;

    @Override
    public void start(Stage stage) throws IOException {
        Parent sceneRoot = new FXMLLoader().load(getClass().getResource("/view/login.fxml"));
        Scene scene = new Scene(sceneRoot);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        loginButton = lookup(".button").queryAs(Button.class);
    }

    @Test
    public void should_contain_button() {
        assertThat(loginButton).hasText("Login");
    }

    @Test
    public void should_click_on_button_user_and_logout() throws SQLException {
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
        clickOn(loginButton);

        // Wait for the scene to update
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the scene has changed to the user main dashboard
        assertThat(Optional.ofNullable(lookup("#dashContent").query())).isNotNull();

        // Find and click the logout button
        logoutButton = lookup("#lblLogOut").queryAs(Button.class);
        clickOn(logoutButton);

        // Wait for the popup to appear
        WaitForAsyncUtils.waitForFxEvents();

        // Find and click the OK button on the popup
        DialogPane dialogPane = lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        clickOn(okButton);

        // Wait for the scene to update
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the scene has changed to the login page
        assertThat(Optional.ofNullable(lookup("#loginContent").query())).isNotNull();

        // Close the Datasource
        datasource.close();
    }

    @Test
    public void should_click_on_button_admin_and_logout() throws SQLException {
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
        clickOn(loginButton);

        // Wait for the scene to update
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the scene has changed to the admin dashboard
        assertThat(Optional.ofNullable(lookup("#dashContent").query())).isNotNull();

        // Find and click the logout button
        logoutButton = lookup("#lblLogOut").queryAs(Button.class);
        clickOn(logoutButton);

        // Wait for the popup to appear
        WaitForAsyncUtils.waitForFxEvents();

        // Find and click the OK button on the popup
        DialogPane dialogPane = lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        clickOn(okButton);

        // Wait for the scene to update
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the scene has changed to the login page
        assertThat(Optional.ofNullable(lookup("#loginContent").query())).isNotNull();

        // Close the Datasource
        datasource.close();
    }
}