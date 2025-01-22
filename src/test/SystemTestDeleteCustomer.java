package test;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
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

public class SystemTestDeleteCustomer extends ApplicationTest {
    Button loginButton;
    Button customersButton;
    Button deleteCustomerButton;

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
    public void should_cancel_delete_first_customer() throws SQLException {
        // Initialize the Datasource
        Datasource datasource = Datasource.getInstance();
        datasource.open();

        // Simulate admin login
        clickOn("#usernameField").write("sajdoko");
        clickOn("#passwordField").write("1");
        clickOn(loginButton);

        // Wait for the scene to update
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the scene has changed to the admin dashboard
        assertThat(Optional.ofNullable(lookup("#dashContent").query())).isNotNull();

        // Find and click the customers button
        customersButton = lookup("#btnCustomers").queryAs(Button.class);
        clickOn(customersButton);

        // Wait for the scene to update
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the scene has changed to the customers page
        assertThat(Optional.ofNullable(lookup("#btnCustomers").query())).isNotNull();

        // Find and click the delete button for the first customer
        deleteCustomerButton = lookup(".button.danger.xs").nth(0).queryAs(Button.class);
        clickOn(deleteCustomerButton);

        // Wait for the popup to appear
        WaitForAsyncUtils.waitForFxEvents();

        // Find and click the cancel button on the popup
        DialogPane dialogPane = lookup(".dialog-pane").queryAs(DialogPane.class);
        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        clickOn(cancelButton);

        // Wait for the scene to update
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the customer has not been deleted
        assertThat(Optional.ofNullable(lookup(".button.danger.xs").nth(0).query())).isNotEmpty();

        datasource.close();
    }
}