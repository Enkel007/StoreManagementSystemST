package test;

import model.Datasource;
import model.Product;
import model.Customer;
import model.Order;
import model.User;
import model.Categories;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatasourceTestEnkel {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private Datasource datasource;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        datasource = Datasource.getInstance();
        datasource.conn = mockConnection;
    }

    @Test
    void open_test() throws SQLException {
        when(mockConnection.isValid(1)).thenReturn(true);
        assertTrue(datasource.open());
    }

    @Test
    void close_test() throws SQLException {
        datasource.conn = mockConnection;
        datasource.close();
        verify(mockConnection, times(1)).close();
    }

    @Test
    void getOneProduct_test() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt(1)).thenReturn(1);
        when(mockResultSet.getString(2)).thenReturn("Product1");

        List<Product> products = datasource.getOneProduct(1);
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Product1", products.get(0).getName());
    }

    @Test
    void searchProducts_test() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt(1)).thenReturn(1);
        when(mockResultSet.getString(2)).thenReturn("Product1");

        List<Product> products = datasource.searchProducts("Product", Datasource.ORDER_BY_NONE);
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Product1", products.get(0).getName());
    }

    @Test
    void deleteSingleProduct_test() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        assertTrue(datasource.deleteSingleProduct(1));
    }

    @Test
    void insertNewProduct_test() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        assertTrue(datasource.insertNewProduct("Product1", "Description1", 10.0, 100, 1));
    }

    @Test
    void updateOneProduct_test() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        assertTrue(datasource.updateOneProduct(1, "Product1", "Description1", 10.0, 100, 1));
    }

    @Test
    void decreaseStock_test() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        datasource.decreaseStock(1);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void getOneCustomer_test() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt(1)).thenReturn(1);
        when(mockResultSet.getString(2)).thenReturn("Customer1");

        List<Customer> customers = datasource.getOneCustomer(1);
        assertNotNull(customers);
        assertEquals(1, customers.size());
        assertEquals("Customer1", customers.get(0).getFullname());
    }

    @Test
    void searchCustomers_test() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt(1)).thenReturn(1);
        when(mockResultSet.getString(2)).thenReturn("Customer1");

        List<Customer> customers = datasource.searchCustomers("Customer", Datasource.ORDER_BY_NONE);
        assertNotNull(customers);
        assertEquals(1, customers.size());
        assertEquals("Customer1", customers.get(0).getFullname());
    }

    @Test
    void deleteSingleCustomer_test() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        assertTrue(datasource.deleteSingleCustomer(1));
    }

    @Test
    void getUserByEmail_test() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("fullname")).thenReturn("User1");

        User user = datasource.getUserByEmail("user@example.com");
        assertNotNull(user);
        assertEquals("User1", user.getFullname());
    }

    @Test
    void getUserByUsername_test() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("fullname")).thenReturn("User1");

        User user = datasource.getUserByUsername("username");
        assertNotNull(user);
        assertEquals("User1", user.getFullname());
    }

    @Test
    void insertNewUser_test() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        assertTrue(datasource.insertNewUser("User1", "username", "user@example.com", "password", "salt"));
    }

    @Test
    void insertNewOrder_test() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        assertTrue(datasource.insertNewOrder(1, 1, "2023-01-01", "Pending"));
    }

    @Test
    void countUserOrders_test() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(10);

        assertEquals(10, datasource.countUserOrders(1));
    }
}