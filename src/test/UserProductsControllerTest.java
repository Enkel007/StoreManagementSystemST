package test;

import app.utils.JavaFXInitializer;
import controller.user.pages.UserProductsController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import model.Datasource;
import model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//test for addActionButtonsToTable and listProducts methods
public class UserProductsControllerTest {

    private UserProductsController userProductsController;

    @BeforeAll
    public static void setUpAll() {
        new JavaFXInitializer().init();
    }

    @BeforeEach
    public void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            userProductsController = new UserProductsController();
            userProductsController.tableProductsPage = new TableView<>();
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void addActionButtonsToTable_test() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            userProductsController.addActionButtonsToTable();

            TableColumn<Product, Void> actionColumn = (TableColumn<Product, Void>) userProductsController.tableProductsPage.getColumns().get(0);
            assertEquals("Actions", actionColumn.getText());

            // Create a TableCell instance to test the cell factory
            TableCell<Product, Void> cell = actionColumn.getCellFactory().call(actionColumn);

            HBox buttonsPane = (HBox) cell.getGraphic();
            assertNotNull(buttonsPane);
            assertEquals(1, buttonsPane.getChildren().size());
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void listProducts_test() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            userProductsController.listProducts();

            ObservableList<Product> products = FXCollections.observableArrayList(Datasource.getInstance().getAllProducts(Datasource.ORDER_BY_NONE));
            userProductsController.tableProductsPage.setItems(products);

            assertNotNull(userProductsController.tableProductsPage.getItems());
            assertEquals(products.size(), userProductsController.tableProductsPage.getItems().size());
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }
}