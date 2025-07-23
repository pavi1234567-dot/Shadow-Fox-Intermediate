# Shadow-Fox-Intermediate

# Inventory Management System (JavaFX)

This is a simple **Inventory Management System** built using **JavaFX**. It provides a graphical user interface (GUI) to perform basic CRUD operations — **Add**, **Update**, **Delete**, and **Search** — on inventory items. It also calculates the **Total Inventory Value**.

# Features

- Add new inventory items (ID, Name, Quantity, Price)
- Update existing item details
- Delete selected items
- Real-time search by product name
- Total inventory value calculation
- Input validation with error alerts
- Clean and responsive JavaFX UI

# Technologies Used

- Java
- JavaFX
- IntelliJ IDEA / VS Code (any Java-supported IDE)

# 📂 Project Structure

Inventory-Management-System/
│
├── Main.java # Main application class
├── README.md # Project documentation
├── javafx-sdk/ # JavaFX SDK (ensure it's linked properly in your IDE)


# ✅ How to Run

1. **Set up JavaFX SDK**:
   - Download JavaFX SDK from: https://gluonhq.com/products/javafx/
   - Extract and place it in your project directory or anywhere on your system.
   - Configure it in your IDE's JavaFX settings.

2. **Configure VM Options**:
   Add the following to your run configuration VM options:

# Example:

--module-path "C:\javafx-sdk-21.0.1\lib" --add-modules javafx.controls,javafx.fxml


3. **Compile and Run `Main.java`**:
- Make sure your IDE recognizes `Main.java` as the entry point.
- Click Run ▶️.

# 📝 Example Inputs

- Product ID: `P101`
- Product Name: `Keyboard`
- Quantity: `10`
- Price: `500`


# 📌 Notes

- Ensure that all text fields are filled correctly before performing Add or Update.
- The table updates dynamically based on the search input.
- If you delete an item, the total value is recalculated automatically.

# 👩‍💻 Author

**Pavitra Khanagaonkar**





