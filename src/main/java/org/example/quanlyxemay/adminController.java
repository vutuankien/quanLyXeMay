package org.example.quanlyxemay;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class adminController implements Initializable {
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    private Stage stage;
    @FXML
    private Button btnProfile;
    @FXML
    private Button btnBill;
    @FXML
    private AnchorPane profilePane;
    @FXML
    private AnchorPane billPane;
    @FXML
    private ChoiceBox<String> bike_select;
    @FXML
    private TextField customer_text_field;
    @FXML
    private TextField cccd_text_field;
    @FXML
    private TextField search_text_field;
    @FXML
    private TextField baoHanh_text_field;
    @FXML private TextField motor_id_text_field;
    @FXML private TextField motor_name_text_field;
    @FXML private TextField motor_type_text_field;
    @FXML private TextField motor_des_text_field;
    @FXML private TextField motor_status_text_field ;
    @FXML private TextField motor_year_text_field;
    @FXML private TextField motor_price_text_field;
    @FXML
    private DatePicker date_picker;
    private Connection connect;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    @FXML
    private TableView<Motor> motorTableView;
    @FXML
    private TableColumn<Motor, String> id_column;
    @FXML
    private TableColumn<Motor, String> name_column;
    @FXML
    private TableColumn<Motor, String> type_column;
    @FXML
    private TableColumn<Motor, String> status_column;
    @FXML
    private TableColumn<Motor, String> year_column;
    @FXML
    private TableColumn<Motor, String> des_column;
    @FXML
    private TableColumn<Motor, Double> price_column;

    @FXML
    private TableView<Customer> customer_table_view;
    @FXML
    private TableColumn<Customer, String> customer_column;
    @FXML
    private TableColumn<Customer, String> bikeName_column;
    @FXML
    private TableColumn<Customer, String> biketype_column;
    @FXML
    private TableColumn<Customer, String> cccd_column;
    @FXML
    private TableColumn<Customer, String> baoHanh_column;
    @FXML
    private TableColumn<Customer, String> date_column;
    @FXML
    private TableColumn<Customer, Double> bikePrice_column;

    public void showReady() throws SQLException {
        ArrayList<Motor> displayList = new ArrayList<>();
        String query = "Select * from motor where status = 'ready'";
        preparedStatement = connect.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Motor motor = new Motor(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("type"),
                    resultSet.getString("status"),
                    resultSet.getString("year"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price")
            );
            displayList.add(motor);
        }
        motorTableView.setItems(FXCollections.observableArrayList(displayList));
    }

    public void showHired() throws SQLException {
        ArrayList<Motor> displayList = new ArrayList<>();
        String query = "Select * from motor where status = 'hired'";
        preparedStatement = connect.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Motor motor = new Motor(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("type"),
                    resultSet.getString("status"),
                    resultSet.getString("year"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price")
            );
            displayList.add(motor);
        }
        motorTableView.setItems(FXCollections.observableArrayList(displayList));
    }

    public void switchPane() {
        btnProfile.setOnMouseClicked(mouseEvent -> {
            profilePane.setVisible(true);
            billPane.setVisible(false);
//            buttonClick = true;
            btnProfile.setStyle("-fx-background-color: #0398fc");
//            btnAssign.setStyle("-fx-text-fill: #fff");
            btnBill.setStyle("-fx-background-color: #ccc");
        });
        btnBill.setOnMouseClicked(mouseEvent -> {
            profilePane.setVisible(false);
            billPane.setVisible(true);
//            btnStudent.setStyle("-fx-text-fill: #fff");
            btnProfile.setStyle("-fx-background-color: #ccc");
            btnBill.setStyle("-fx-background-color: #0398fc");
        });


    }


    public void addMotor(){
        String query  = "insert into motor values(?,?,?,?,?,?,?)";
        String motor_id = motor_id_text_field.getText();
        String motor_name = motor_name_text_field.getText();
        String motor_type = motor_type_text_field.getText();
        String motor_status = motor_status_text_field.getText();
        String motor_year = motor_year_text_field.getText();
        String motor_des = motor_des_text_field.getText();
        double motor_price = Double.parseDouble(motor_price_text_field.getText());
        try {
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1,motor_id);
            preparedStatement.setString(2,motor_name);
            preparedStatement.setString(3,motor_type);
            preparedStatement.setString(4,motor_status);
            preparedStatement.setString(5,motor_year);
            preparedStatement.setString(6,motor_des);
            preparedStatement.setDouble(7,motor_price);
            int rowAffected = preparedStatement.executeUpdate();
            if(rowAffected > 0){
                Motor motor = new Motor(motor_id,motor_name,motor_type,motor_status,motor_year,motor_des,motor_price);
                listData.add(motor);
                motorTableView.setItems(listData);
                motorTableView.refresh();
                showAlert(null,"add successfully","add motor", Alert.AlertType.INFORMATION);
            }else{
                showAlert(null,"add fail","add motor", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void deleteMotor() {
        String query = "DELETE FROM motor WHERE name = ?";
        ArrayList<String> listName = new ArrayList<>();
        listData.forEach(e -> listName.add(e.getName()));

        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(listName.get(0), listName);
        choiceDialog.setContentText("Name: ");
        choiceDialog.setTitle("Delete motor");
        choiceDialog.setHeaderText("Choose a name to delete");

        Optional<String> result = choiceDialog.showAndWait();
        if (result.isPresent()) {
            String selectedName = result.get();
            int selected = -1;

            for (int i = 0; i < listData.size(); i++) {
                if (listData.get(i).getName().equals(selectedName)) {
                    selected = i;
                    break;
                }
            }

            if (selected != -1) {
                try {
                    preparedStatement = connect.prepareStatement(query);
                    preparedStatement.setString(1, selectedName);

                    int rowAffected = preparedStatement.executeUpdate();
                    if (rowAffected > 0) {
                        listData.remove(selected);
                        motorTableView.setItems(listData);
                        motorTableView.refresh();
                        showAlert(null, "Delete successful", "Delete Motor", Alert.AlertType.INFORMATION);
                    } else {
                        showAlert(null, "Delete failed", "Delete Motor", Alert.AlertType.ERROR);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert(null, "Database error", "Delete Motor", Alert.AlertType.ERROR);
                }
            }
        }
    }
    public void showMotor() {
        String sql = "select * from motor";
        try {
            preparedStatement = connect.prepareStatement(sql);
            ResultSet resultSet1 = preparedStatement.executeQuery();
            motorTableView.setItems(listData);
            motorTableView.refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void searchMotor() {
        ArrayList<Motor> motorArrayList = new ArrayList<>(listData);
        ArrayList<Motor> displayMotor = new ArrayList<>();
//        String sql = "SELECT * FROM assignment WHERE " + "? = ?";


        try {
            ArrayList<String> items = new ArrayList<>();
            items.add("id");
            items.add("name");
            items.add("type");
            items.add("status");
            items.add("year");
            items.add("description");
            items.add("price");

            ChoiceDialog<String> listChoice = new ChoiceDialog<>(items.get(0), items);
            listChoice.setTitle("search motor");
            listChoice.setHeaderText("Choose field to search motor:");
            listChoice.setContentText("field:");
            Optional<String> result = listChoice.showAndWait();

            Dialog<String> search = createInputDialog("Nhập thông tin tìm kiếm:");
            Optional<String> searchResult = search.showAndWait();

            searchResult.ifPresent(userEnteredValue -> {
                result.ifPresent(selectedField -> {
                    int selected = -1;
                    for (int i = 0; i < motorArrayList.size(); i++) {
                        Motor motor = motorArrayList.get(i);
                        switch (selectedField) {
                            case "id":
                                if (motor.getId().equals(userEnteredValue)) {
                                    selected = i;
                                }
                                break;
                            case "name":
                                if (motor.getName().equals(userEnteredValue)) {
                                    selected = i;
                                }
                                break;
                            case "type":
                                if (motor.getType().equals(userEnteredValue)) {
                                    selected = i;
                                }
                                break;
                            case "status":
                                if (motor.getStatus().equals(userEnteredValue)) {
                                    selected = i;
                                }
                                break;
                            case "year":
                                if (motor.getYear().equals(userEnteredValue)) {
                                    selected = i;
                                }
                                break;
                            case "description":
                                if (motor.getDescription().equals(userEnteredValue)) {
                                    selected = i;
                                }
                                break;
                            case "price":
                                if (motor.getPrice() == Double.parseDouble(userEnteredValue)) {
                                    selected = i;
                                }
                                break;
                            default:
                                System.out.println("Trường không hợp lệ được chọn");
                                break;
                        }
                    }

                    if (selected != -1) {
                        try {
                            String columnName = selectedField;
                            String selectQuery = "SELECT * FROM motor WHERE " + columnName + " = ?";
                            preparedStatement = connect.prepareStatement(selectQuery);
                            preparedStatement.setString(1, userEnteredValue);

                            ResultSet resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                Motor motor = new Motor(
                                        resultSet.getString("id"),
                                        resultSet.getString("name"),
                                        resultSet.getString("type"),
                                        resultSet.getString("status"),
                                        resultSet.getString("year"),
                                        resultSet.getString("description"),
                                        resultSet.getDouble("price")
                                );
                                displayMotor.add(motor);
                            }
                            motorTableView.setItems(FXCollections.observableArrayList(displayMotor));
                            motorTableView.refresh();
                        } catch (Exception e) {

                            System.out.println(e);
                        }
                    } else {
                        showAlert(null, "can not find motor", "search motor", Alert.AlertType.ERROR);
                    }
                });
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addBike() {
        ArrayList<String> bikes = new ArrayList<>();
        listData.forEach(e -> bikes.add(e.getName()));
        bike_select.getItems().addAll(bikes);
        bike_select.setOnAction(this::getBike);
    }

    public void getBike(ActionEvent e) {
        String bike = bike_select.getValue();

    }
    public void addCustomer() throws SQLException {
        String query = "INSERT INTO customer (name, bikeName, type,cccd,baoHanh,ngayThue,price)\n" +
                "SELECT ?, m.name, m.type, ?,?, ?, m.price\n" +
                "FROM motor m\n" +
                "WHERE m.name = ?;";
        String customerName = customer_text_field.getText();
        String cccd = cccd_text_field.getText();
        LocalDate myDate = date_picker.getValue();
        String baoHanh = baoHanh_text_field.getText();
        System.out.println(myDate.toString());
        String myFormattedDate = myDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String choice = bike_select.getValue();
        String type = null;
        double price = 0.0;
        for (int i = 0; i < listData.size(); i++) {
            if (listData.get(i).getName().equals(choice)) {
                if (listData.get(i).getStatus().equals("hired")) {

                    break;
                } else {
                    type = listData.get(i).getType();
                    price = listData.get(i).getPrice();
                    break;
                }

            }
        }
        preparedStatement = connect.prepareStatement(query);
        preparedStatement.setString(1, customerName);
        preparedStatement.setString(2, cccd);
        preparedStatement.setString(3, baoHanh);
        preparedStatement.setString(4, myFormattedDate);
        preparedStatement.setString(5, choice);
        if (type == null && price == 0.0) {
            showAlert(null, "xe đang trong tình trạng không thể thuê", "add fail", Alert.AlertType.ERROR);
        } else {
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                Customer customer = new Customer(customerName, choice, type, cccd, baoHanh, myFormattedDate, price);
                listCustomer.add(customer);
                customer_table_view.setItems(listCustomer);
                showAlert(null, "add successfully", "add customer", Alert.AlertType.INFORMATION);
            } else {
                showAlert(null, "add fail", "add customer", Alert.AlertType.ERROR);
            }
        }
    }

    public void deleteCustomer() {
        String query = "DELETE FROM customer WHERE name = ?";
        ArrayList<String> listName = new ArrayList<>();
        listCustomer.forEach(e -> listName.add(e.getName()));

        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(listName.get(0), listName);
        choiceDialog.setContentText("Name: ");
        choiceDialog.setTitle("Delete Customer");
        choiceDialog.setHeaderText("Choose a name to delete");

        Optional<String> result = choiceDialog.showAndWait();
        if (result.isPresent()) {
            String selectedName = result.get();
            int selected = -1;

            for (int i = 0; i < listCustomer.size(); i++) {
                if (listCustomer.get(i).getName().equals(selectedName)) {
                    selected = i;
                    break;
                }
            }

            if (selected != -1) {
                try {
                    preparedStatement = connect.prepareStatement(query);
                    preparedStatement.setString(1, selectedName);

                    int rowAffected = preparedStatement.executeUpdate();
                    if (rowAffected > 0) {
                        listCustomer.remove(selected);
                        customer_table_view.setItems(listCustomer);
                        showAlert(null, "Delete successful", "Delete Customer", Alert.AlertType.INFORMATION);
                    } else {
                        showAlert(null, "Delete failed", "Delete Customer", Alert.AlertType.ERROR);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert(null, "Database error", "Delete Customer", Alert.AlertType.ERROR);
                }
            }
        }
    }

    public void updateData() {
        ArrayList<Customer> customerArrayList = new ArrayList<>(listCustomer);

        if (customerArrayList.isEmpty()) {
            showAlert(null,"No assignments available","update customer", Alert.AlertType.INFORMATION);
            return;
        }

        ArrayList<String> listName = new ArrayList<>();
        customerArrayList.forEach(e -> listName.add(e.getName()));

        String sql = "UPDATE customer SET name = ?, cccd = ?, baoHanh = ?, ngayThue = ? WHERE name = ?";

        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(listName.get(0), listName);
        choiceDialog.setHeaderText("Choose customer");
        choiceDialog.setTitle("Choose name to update");
        choiceDialog.setContentText("name:");
        Optional<String> result = choiceDialog.showAndWait();

        result.ifPresent(selectedCustomer -> {
            int selectedID = -1;

            for (int i = 0; i < customerArrayList.size(); i++) {
                if (customerArrayList.get(i).getName().equals(selectedCustomer)) {
                    selectedID = i;
                    break;
                }
            }

            if (selectedID != -1) {
                try {
                    Dialog<String> dialogName = createInputDialog("name: ");
                    Dialog<String> dialogCccd = createInputDialog("cccd ");
                    Dialog<String> dialogBaoHanh = createInputDialog("baoHanh: ");
                    Dialog<String> dialogTHue = createInputDialog("ngayThue: ");

                    Optional<String> name = dialogName.showAndWait();
                    Optional<String> cccd = dialogCccd.showAndWait();
                    Optional<String> baoHanh = dialogBaoHanh.showAndWait();
                    Optional<String> thue = dialogTHue.showAndWait();
                    if (name.isPresent() && cccd.isPresent() && baoHanh.isPresent() && thue.isPresent()) {
                        preparedStatement = connect.prepareStatement(sql);
                        preparedStatement.setString(1, name.get());
                        preparedStatement.setString(2, cccd.get());
                        preparedStatement.setString(3, baoHanh.get());
                        preparedStatement.setString(4, thue.get());
                        preparedStatement.setString(5, selectedCustomer);


                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Update successfully");
//                            Customer cs = new Customer(id.get(), name.get(), link.get(), date.get(), student.get(), instructor.get(), formatGrade);
                            Customer cs = customerArrayList.get(selectedID);
                            cs.setName(name.get());
                            cs.setCccd(cccd.get());
                            cs.setBaoHanh(baoHanh.get());
                            cs.setThue(thue.get());
                            listCustomer.remove(selectedID);
                            listCustomer.add(selectedID,cs);
                            customer_table_view.setItems(listCustomer);
                            customer_table_view.refresh();
                            showAlert(null,"Update successfully","update customer", Alert.AlertType.INFORMATION);
                        } else {
                            System.out.println("Update unsuccessfully");
                            showAlert(null,"Update unsuccessfully","update customer", Alert.AlertType.ERROR);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
    }

    public void searchCustomer() throws SQLException {
        FilteredList<Customer> filteredList = new FilteredList<>(listCustomer, b -> true);

        search_text_field.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(customer -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String keyWord = newValue.toLowerCase();
                return customer.getName().toLowerCase().contains(keyWord) ||
                        customer.getBaoHanh().toLowerCase().contains(keyWord) ||
                        customer.getBikeName().toLowerCase().contains(keyWord) ||
                        customer.getThue().toLowerCase().contains(keyWord) ||
                        customer.getType().toLowerCase().contains(keyWord) ||
                        String.valueOf(customer.getPrice()).toLowerCase().contains(keyWord);
            });
        });

        SortedList<Customer> customerSortedList = new SortedList<>(filteredList);
        customerSortedList.comparatorProperty().bind(customer_table_view.comparatorProperty());

        customer_table_view.setItems(customerSortedList);
    }

    public void showListdata() {
        String sql = "select * from customer";
        try {
            preparedStatement = connect.prepareStatement(sql);
            ResultSet resultSet1 = preparedStatement.executeQuery();
            customer_table_view.setItems(listCustomer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnSearchCustomer() {
        ArrayList<Customer> customerArrayList = new ArrayList<>(listCustomer);
        ArrayList<Customer> displayedCustomer = new ArrayList<>();
//        String sql = "SELECT * FROM assignment WHERE " + "? = ?";


        try {
            ArrayList<String> items = new ArrayList<>();
            items.add("name");
            items.add("bikeName");
            items.add("type");
            items.add("cccd");
            items.add("baoHanh");
            items.add("ngayThue");
            items.add("price");

            ChoiceDialog<String> listChoice = new ChoiceDialog<>(items.get(0), items);
            listChoice.setTitle("search customer");
            listChoice.setHeaderText("Choose field to search customer:");
            listChoice.setContentText("field:");
            Optional<String> result = listChoice.showAndWait();

            Dialog<String> search = createInputDialog("Nhập thông tin tìm kiếm:");
            Optional<String> searchResult = search.showAndWait();

            searchResult.ifPresent(userEnteredValue -> {
                result.ifPresent(selectedField -> {
                    int selected = -1;
                    for (int i = 0; i < customerArrayList.size(); i++) {
                        Customer customer = customerArrayList.get(i);
                        switch (selectedField) {
                            case "name":
                                if (customer.getName().equals(userEnteredValue)) {
                                    selected = i;
                                }
                                break;
                            case "bikeName":
                                if (customer.getBikeName().equals(userEnteredValue)) {
                                    selected = i;
                                }
                                break;
                            case "type":
                                if (customer.getType().equals(userEnteredValue)) {
                                    selected = i;
                                }
                                break;
                            case "cccd":
                                if (customer.getCccd().equals(userEnteredValue)) {
                                    selected = i;
                                }
                                break;
                            case "baoHanh":
                                if (customer.getBaoHanh().equals(userEnteredValue)) {
                                    selected = i;
                                }
                                break;
                            case "ngayThue":
                                if (customer.getThue().equals(userEnteredValue)) {
                                    selected = i;
                                }
                                break;
                            case "price":
                                if (customer.getPrice() == Double.parseDouble(userEnteredValue)) {
                                    selected = i;
                                }
                                break;
                            default:
                                System.out.println("Trường không hợp lệ được chọn");
                                break;
                        }
                    }

                    if (selected != -1) {
                        try {
                            String columnName = selectedField;
                            String selectQuery = "SELECT * FROM customer WHERE " + columnName + " = ?";
                            preparedStatement = connect.prepareStatement(selectQuery);
                            preparedStatement.setString(1, userEnteredValue);

                            ResultSet resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                Customer customer = new Customer(
                                        resultSet.getString("name"),
                                        resultSet.getString("bikeName"),
                                        resultSet.getString("type"),
                                        resultSet.getString("cccd"),
                                        resultSet.getString("baoHanh"),
                                        resultSet.getString("ngayThue"),
                                        resultSet.getDouble("price")
                                );
                                displayedCustomer.add(customer);
                            }
                            customer_table_view.setItems(FXCollections.observableArrayList(displayedCustomer));
                        } catch (Exception e) {

                            System.out.println(e);
                        }
                    } else {
                        showAlert(null, "can not find customer", "search customer", Alert.AlertType.ERROR);
                    }
                });
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void logout(ActionEvent actionEvent){
        try {
            root  = FXMLLoader.load(getClass().getResource("Login.fxml"));
            stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            showAlert(null,"log out successfully","log out", Alert.AlertType.INFORMATION);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Dialog<String> createInputDialog(String contentText) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText(contentText);
        dialog.setTitle("Update Information");
        dialog.setHeaderText(null);
        return dialog;
    }

    ObservableList<Motor> listData = FXCollections.observableArrayList();
    ObservableList<Customer> listCustomer = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String query = "Select * FROM motor";
        connect = connectDB.connectionDB();
        System.out.println("connect to database");
        try {
            preparedStatement = connect.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Motor motor = new Motor(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("type"),
                        resultSet.getString("status"),
                        resultSet.getString("year"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price")
                );
                listData.add(motor);
            }


        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        id_column.setCellValueFactory(new PropertyValueFactory<Motor, String>("id"));
        name_column.setCellValueFactory(new PropertyValueFactory<Motor, String>("name"));
        type_column.setCellValueFactory(new PropertyValueFactory<Motor, String>("type"));
        status_column.setCellValueFactory(new PropertyValueFactory<Motor, String>("status"));
        year_column.setCellValueFactory(new PropertyValueFactory<Motor, String>("year"));
        des_column.setCellValueFactory(new PropertyValueFactory<Motor, String>("description"));
        price_column.setCellValueFactory(new PropertyValueFactory<Motor, Double>("price"));

        motorTableView.setItems(listData);


        String sql = "Select * FROM customer";
        connect = connectDB.connectionDB();
        System.out.println("connect to database");
        try {
            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getString("name"),
                        resultSet.getString("bikeName"),
                        resultSet.getString("type"),
                        resultSet.getString("cccd"),
                        resultSet.getString("baoHanh"),
                        resultSet.getString("ngayThue"),
                        resultSet.getDouble("price")
                );
                listCustomer.add(customer);
            }


        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }

        customer_column.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        bikeName_column.setCellValueFactory(new PropertyValueFactory<Customer, String>("bikeName"));
        biketype_column.setCellValueFactory(new PropertyValueFactory<Customer, String>("type"));
        cccd_column.setCellValueFactory(new PropertyValueFactory<Customer, String>("cccd"));
        baoHanh_column.setCellValueFactory(new PropertyValueFactory<Customer, String>("baoHanh"));
        date_column.setCellValueFactory(new PropertyValueFactory<Customer, String>("thue"));
        bikePrice_column.setCellValueFactory(new PropertyValueFactory<Customer, Double>("price"));

        customer_table_view.setItems(listCustomer);
        addBike();
    }

    public void showAlert(String header, String content, String title, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(content);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
