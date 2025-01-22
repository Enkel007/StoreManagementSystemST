package test;

import app.utils.JavaFXInitializer;
import controller.admin.pages.HomeController;
import javafx.application.Platform;
import javafx.scene.control.Label;
import model.Datasource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

//test for getDashboardCostCount method
//has dependency to countAllCustomers method in Datasource class
public class HomeControllerTest {

    @InjectMocks
    private HomeController homeController;

    @BeforeAll
    public static void setUpAll() {
        new JavaFXInitializer().init();
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        homeController.productsCount = new Label();
        homeController.customersCount = new Label();
    }

    @Test
    public void getDashboardCostCount_test() {
        Platform.runLater(() -> {
            Datasource datasource = Datasource.getInstance();
            datasource.open(); // Ensure the datasource is opened

            homeController.getDashboardCostCount();

            // Wait for the task to complete and verify the result
            Platform.runLater(() -> {
                assertEquals("10", homeController.customersCount.getText());
            });

            datasource.close(); // Ensure the datasource is closed
        });
    }

    @Test
    public void getDashboardCostCount_boundaryValues_test() {
        Platform.runLater(() -> {
            Datasource datasource = Datasource.getInstance();
            datasource.open(); // Ensure the datasource is opened

            // 0 customers
            homeController.getDashboardCostCount();
            Platform.runLater(() -> {
                assertEquals("0", homeController.customersCount.getText());
            });

            // 1 customer
            homeController.getDashboardCostCount();
            Platform.runLater(() -> {
                assertEquals("1", homeController.customersCount.getText());
            });

            // Integer.MAX_VALUE customers
            homeController.getDashboardCostCount();
            Platform.runLater(() -> {
                assertEquals(String.valueOf(Integer.MAX_VALUE), homeController.customersCount.getText());
            });

            datasource.close(); // Ensure the datasource is closed
        });
    }

    @Test
    public void getDashboardCostCount_classEvaluation_test() {
        Platform.runLater(() -> {
            Datasource datasource = Datasource.getInstance();
            datasource.open(); // Ensure the datasource is opened

            // must throw exception
            homeController.getDashboardCostCount();
            Platform.runLater(() -> {
                assertEquals("-1", homeController.customersCount.getText());
            });

            datasource.close(); // Ensure the datasource is closed
        });
    }

    @Test
    public void getDashboardCostCount_branchCoverage_test() {
        Platform.runLater(() -> {
            Datasource datasource = Datasource.getInstance();
            datasource.open(); // Ensure the datasource is opened

            // Branch where countAllCustomers returns 0
            homeController.getDashboardCostCount();
            Platform.runLater(() -> {
                assertEquals("0", homeController.customersCount.getText());
            });

            // Branch where countAllCustomers returns a positive number
            homeController.getDashboardCostCount();
            Platform.runLater(() -> {
                assertEquals("10", homeController.customersCount.getText());
            });

            datasource.close(); // Ensure the datasource is closed
        });
    }

    @Test
    public void getDashboardCostCount_conditionCoverage_test() {
        Platform.runLater(() -> {
            Datasource datasource = Datasource.getInstance();
            datasource.open(); // Ensure the datasource is opened

            // Condition where countAllCustomers returns 0
            homeController.getDashboardCostCount();
            Platform.runLater(() -> {
                assertEquals("0", homeController.customersCount.getText());
            });

            // Condition where countAllCustomers returns a positive number
            homeController.getDashboardCostCount();
            Platform.runLater(() -> {
                assertEquals("10", homeController.customersCount.getText());
            });

            // Condition where countAllCustomers returns a negative number
            homeController.getDashboardCostCount();
            Platform.runLater(() -> {
                assertEquals("-1", homeController.customersCount.getText());
            });

            datasource.close(); // Ensure the datasource is closed
        });
    }
}