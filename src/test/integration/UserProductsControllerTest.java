//package test.integration;
//
//import controller.user.pages.UserProductsController;
//
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.stage.Stage;
//import model.Datasource;
//import model.Product;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.testfx.framework.junit5.ApplicationTest;
//
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class UserProductsControllerTest extends ApplicationTest {
//
//    private UserProductsController controller;
//    private TableView<Product> tableProductsPage;
//    private TextField fieldProductsSearch;
//    private Datasource mockDatasource;
//
//    /**
//     * Initializes the JavaFX application and controller components.
//     * This method is called by TestFX before each test.
//     */
//    @Override
//    public void start(Stage stage) {
//        // Initialize JavaFX components
//        tableProductsPage = new TableView<>();
//        fieldProductsSearch = new TextField();
//
//        // Add columns to the TableView
//        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//
//        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
//        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//
//        tableProductsPage.getColumns().addAll(nameColumn, quantityColumn);
//
//        // Initialize the controller
//        controller = new UserProductsController();
//        controller.tableProductsPage = tableProductsPage;
//        controller.fieldProductsSearch = fieldProductsSearch;
//
//        // Replace Datasource with a mock instance
//        mockDatasource = Mockito.mock(Datasource.class);
//        Datasource.setInstance(mockDatasource);
//    }
//
//    /**
//     * Resets mock behavior and configurations before each test.
//     */
//    @BeforeEach
//    void setUp() {
//        reset(mockDatasource); // Reset mock to ensure clean test conditions
//    }
//
//    /**
//     * Tests the `listProducts` method by mocking the Datasource and verifying that
//     * products are correctly added to the TableView.
//     */
//    @Test
//    void testListProducts() {
//        // Arrange
//        when(mockDatasource.getAllProducts(Datasource.ORDER_BY_NONE))
//                .thenReturn(Arrays.asList(
//                        new Product(1, "Product A", "Description A", 10.0, 5, 1, "Category 1", 10),
//                        new Product(2, "Product B", "Description B", 20.0, 3, 2, "Category 2", 20)
//                ));
//
//        // Act
//        controller.listProducts();
//        sleep(2000); // Allow background Task to complete
//
//        // Assert
//        assertEquals(2, tableProductsPage.getItems().size());
//        assertEquals("Product A", tableProductsPage.getItems().get(0).getName());
//        assertEquals("Product B", tableProductsPage.getItems().get(1).getName());
//        verify(mockDatasource, times(1)).getAllProducts(Datasource.ORDER_BY_NONE);
//    }
//
//    /**
//     * Tests the `btnProductsSearchOnAction` method by simulating a search query
//     * and verifying that the correct results are displayed in the TableView.
//     */
//    @Test
//    void testBtnProductsSearchOnAction() {
//        // Arrange
//        when(mockDatasource.searchProducts("product a", Datasource.ORDER_BY_NONE))
//                .thenReturn(Arrays.asList(
//                        new Product(1, "Product A", "Description A", 10.0, 5, 1, "Category 1", 10)
//                ));
//
//        fieldProductsSearch.setText("Product A");
//
//        // Act
//        controller.btnProductsSearchOnAction();
//        sleep(2000); // Allow background Task to complete
//
//        // Assert
//        assertEquals(1, tableProductsPage.getItems().size());
//        assertEquals("Product A", tableProductsPage.getItems().get(0).getName());
//        verify(mockDatasource, times(1))
//                .searchProducts("product a", Datasource.ORDER_BY_NONE);
//    }
//
//
//
//
//    /**
//     * Utility method to allow time for JavaFX tasks to complete.
//     *
//     * @param millis The duration to wait in milliseconds.
//     */
//    private void sleep(int millis) {
//        try {
//            Thread.sleep(millis);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//}
