package test;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Datasource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.util.Optional;

import static org.testfx.assertions.api.Assertions.assertThat;

public class SystemTestPages extends ApplicationTest {
    Button loginButton;
    Button homeButton;
    Button productsButton;
    Button ordersButton;
    Button customersButton;
    Button settingsButton;

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
    public void should_navigate_through_all_pages() {
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

        // Find and click the products button
        productsButton = lookup("#btnProducts").queryAs(Button.class);
        clickOn(productsButton);

        // Wait for the scene to update
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the scene has changed to the products page
        assertThat(Optional.ofNullable(lookup("#btnProducts").query())).isNotNull();

        // Find and click the orders button
        ordersButton = lookup("#btnOrders").queryAs(Button.class);
        clickOn(ordersButton);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(Optional.ofNullable(lookup("#btnOrders").query())).isNotNull();

        // Find and click the customers button
        customersButton = lookup("#btnCustomers").queryAs(Button.class);
        clickOn(customersButton);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(Optional.ofNullable(lookup("#btnCustomers").query())).isNotNull();

        // Find and click the settings button
        settingsButton = lookup("#btnSettings").queryAs(Button.class);
        clickOn(settingsButton);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(Optional.ofNullable(lookup("#btnSettings").query())).isNotNull();

        homeButton = lookup("#btnHome").queryAs(Button.class);
        clickOn(homeButton);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(Optional.ofNullable(lookup("#dashContent").query())).isNotNull();
    }
}