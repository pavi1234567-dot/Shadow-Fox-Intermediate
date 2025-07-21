import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    private TableView<Item> table;
    private TextField tfId, tfName, tfQuantity, tfPrice, tfSearch;
    private Label totalValueLabel;
    private ObservableList<Item> inventory;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        inventory = FXCollections.observableArrayList();
        FilteredList<Item> filteredList = new FilteredList<>(inventory, p -> true);
        table.setItems(filteredList);

        
        tfId = new TextField();
        tfName = new TextField();
        tfQuantity = new TextField();
        tfPrice = new TextField();
        tfSearch = new TextField();

        tfId.setPromptText("Product ID");
        tfName.setPromptText("Product Name");
        tfQuantity.setPromptText("Quantity");
        tfPrice.setPromptText("Price");
        tfSearch.setPromptText("Search by Product Name");

        Button addBtn = new Button("Add");
        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Delete");
        Button clearBtn = new Button("Clear");

        HBox inputBox = new HBox(10, tfId, tfName, tfQuantity, tfPrice);
        inputBox.setPadding(new Insets(10));

        HBox btnBox = new HBox(10, addBtn, updateBtn, deleteBtn, clearBtn);
        btnBox.setPadding(new Insets(10));

        table = new TableView<>();
        inventory = FXCollections.observableArrayList();
        table.setItems(inventory);

        TableColumn<Item, String> idCol = new TableColumn<>("Product ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Item, String> nameCol = new TableColumn<>("Product Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Item, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().addAll(idCol, nameCol, qtyCol, priceCol);

        totalValueLabel = new Label("Total Inventory Value: ₹0.0");

        VBox root = new VBox(10, tfSearch, inputBox, btnBox, table, totalValueLabel);
        root.setPadding(new Insets(10));

        // Event Handling
        addBtn.setOnAction(e -> addItem());
        updateBtn.setOnAction(e -> updateItem());
        deleteBtn.setOnAction(e -> deleteItem());
        clearBtn.setOnAction(e -> clearFields());
        tfSearch.textProperty().addListener((obs, oldVal, newVal) -> filterList(newVal));

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                tfId.setText(newSel.getId());
                tfName.setText(newSel.getName());
                tfQuantity.setText(String.valueOf(newSel.getQuantity()));
                tfPrice.setText(String.valueOf(newSel.getPrice()));
            }
        });

        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    private void addItem() {
        if (validateInput()) {
            Item item = new Item(tfId.getText(), tfName.getText(),
                    Integer.parseInt(tfQuantity.getText()), Double.parseDouble(tfPrice.getText()));
            inventory.add(item);
            updateTotalValue();
            clearFields();
            System.out.println("Add button clicked");
        }
    }

    private void updateItem() {
        Item selected = table.getSelectionModel().getSelectedItem();
        if (selected != null && validateInput()) {
            selected.setId(tfId.getText());
            selected.setName(tfName.getText());
            selected.setQuantity(Integer.parseInt(tfQuantity.getText()));
            selected.setPrice(Double.parseDouble(tfPrice.getText()));
            table.refresh();
            updateTotalValue();
            clearFields();
        }
    }

    private void deleteItem() {
        Item selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            inventory.remove(selected);
            updateTotalValue();
            clearFields();
        }
    }

    private void clearFields() {
        tfId.clear();
        tfName.clear();
        tfQuantity.clear();
        tfPrice.clear();
        table.getSelectionModel().clearSelection();
    }

    private void filterList(String keyword) 
    {
    FilteredList<Item> filteredList = (FilteredList<Item>) table.getItems();
    filteredList.setPredicate(item -> {
        if (keyword == null || keyword.isEmpty()) {
            return true;
        }
        return item.getName().toLowerCase().contains(keyword.toLowerCase());
    });
}
    private void updateTotalValue() {
        double total = 0.0;
        for (Item item : inventory) {
            total += item.getQuantity() * item.getPrice();
        }
        totalValueLabel.setText("Total Inventory Value: ₹" + total);
    }

    private boolean validateInput() {
    if (tfId.getText().isEmpty() || tfName.getText().isEmpty() ||
        tfQuantity.getText().isEmpty() || tfPrice.getText().isEmpty()) {
        System.out.println("Validation failed: Empty field");
        showAlert("Please fill all fields!");
        return false;
    }
    try {
        Integer.parseInt(tfQuantity.getText());
        Double.parseDouble(tfPrice.getText());
    } catch (NumberFormatException e) {
        System.out.println("Validation failed: Invalid number");
        showAlert("Quantity must be integer and Price must be numeric!");
        return false;
    }
    return true;
}
    private void showAlert(String msg) 
    {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Input Error");
    alert.setHeaderText("Invalid Input");
    alert.setContentText(msg);
    alert.showAndWait();  // Ensures alert stays until user clicks OK
}


    public static class Item {
        private final SimpleStringProperty id;
        private final SimpleStringProperty name;
        private final SimpleIntegerProperty quantity;
        private final SimpleDoubleProperty price;

        public Item(String id, String name, int quantity, double price) {
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.price = new SimpleDoubleProperty(price);
        }

        public String getId() { return id.get(); }
        public void setId(String id) { this.id.set(id); }

        public String getName() { return name.get(); }
        public void setName(String name) { this.name.set(name); }

        public int getQuantity() { return quantity.get(); }
        public void setQuantity(int quantity) { this.quantity.set(quantity); }

        public double getPrice() { return price.get(); }
        public void setPrice(double price) { this.price.set(price); }
    }
}
