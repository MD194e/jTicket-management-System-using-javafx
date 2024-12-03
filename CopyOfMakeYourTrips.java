import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class CopyOfMakeYourTrips extends Application {

    // List of available trains with name, departure time, price, and reserved seats
    private List<Train> availableTrains = List.of(
            new Train("Tejas Express", LocalTime.of(10, 0), 500),
            new Train(" Vande Bharat", LocalTime.of(12, 30), 700),
            new Train("Shatabdi Express", LocalTime.of(15, 45), 900),
            new Train("Rajdhani Express", LocalTime.of(17, 0), 1200),
            new Train("Duranto Express", LocalTime.of(20, 15), 400)
    );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create login screen
        createLoginScreen(primaryStage);
    }

    // Method to create the login screen
    private void createLoginScreen(Stage primaryStage) {
        Label headerLabel = new Label("MAKE YOUR TRIP LOGIN");
        headerLabel.setStyle("-fx-text-fill: red; -fx-font-size: 20px; -fx-font-weight: bold;");
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        Label loginMessage = new Label();
        usernameField.setPrefWidth(30);  // Set the preferred width for the username field
        passwordField.setPrefWidth(30);

        // Set login action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Simple hardcoded authentication
            if (username.equals("user") && password.equals("password")) {
                loginMessage.setText("Login successful!");
                loginMessage.setStyle("-fx-text-fill: green;");
                createReservationScreen(primaryStage);  // Proceed to reservation screen
            } else {
                loginMessage.setText("Invalid username or password.");
                loginMessage.setStyle("-fx-text-fill: red;");
            }
        });

        // Create VBox layout for login form
        VBox loginLayout = new VBox(10, headerLabel, usernameLabel, usernameField, passwordLabel, passwordField, loginButton, loginMessage);
        loginLayout.setAlignment(Pos.CENTER); // Centering the form vertically and horizontally
        loginLayout.setPadding(new Insets(20)); // Padding around the form
        loginLayout.setSpacing(15); // Spacing between the elements

        // Set background image for the login screen
        String imagePath = "C:/Users/manga/Downloads/innerbanner.jpg";  // Path to your image
        Image backgroundImage = new Image("file:" + imagePath);

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImageFullScreen = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize);

        loginLayout.setBackground(new Background(backgroundImageFullScreen));

        // Create Scene with the login layout and set it on the stage
        Scene loginScene = new Scene(loginLayout, 400, 250);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("User Login");
        primaryStage.show();
    }

    // Method to create the train reservation screen after successful login
    private void createReservationScreen(Stage primaryStage) {
        // Create labels and other UI components for the reservation screen
        Label reservationLabel = new Label("Train Ticket Reservation System");
        Label originLabel = new Label("Origin:");
        Label destinationLabel = new Label("Destination:");
        Label dateLabel = new Label("Journey Date:");
        Label passengersLabel = new Label("Number of Passengers:");
        Label trainLabel = new Label("Select Train:");

        // Train selection ComboBox and other form elements (for simplicity, you can customize this)
        ComboBox<String> originComboBox = new ComboBox<>();
        ComboBox<String> destinationComboBox = new ComboBox<>();
        originComboBox.getItems().addAll("MUMBAI", "SURAT", "PUNE", "AHEMDABAD", "DELHI");
        destinationComboBox.getItems().addAll("MUMBAI", "SURAT", "PUNE", "AHEMDABAD", "DELHI");

        // Add other controls like DatePicker, Spinner, etc.
        DatePicker datePicker = new DatePicker();
        Spinner<Integer> numPassengersSpinner = new Spinner<>(1, 10, 1);
        Button reserveButton = new Button("Reserve Ticket");
        Label resultLabel = new Label();

        // Set up date validation (ensure the selected date is not in the past)
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                // Disable dates in the past
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });

        // Create ComboBox for train selection
        ComboBox<Train> trainComboBox = new ComboBox<>();
        for (Train train : availableTrains) {
            trainComboBox.getItems().add(train);
        }
        trainComboBox.setCellFactory(lv -> new ListCell<Train>() {
            @Override
            protected void updateItem(Train train, boolean empty) {
                super.updateItem(train, empty);
                setText(empty ? null : train.getName());
            }
        });
        trainComboBox.setButtonCell(new ListCell<Train>() {
            @Override
            protected void updateItem(Train train, boolean empty) {
                super.updateItem(train, empty);
                setText(empty ? null : train.getName());
            }
        });

        // Display selected train's time, price, and available seats
        Label trainDetailsLabel = new Label();
        Label availableSeatsLabel = new Label();

        trainComboBox.setOnAction(e -> {
            Train selectedTrain = trainComboBox.getValue();
            if (selectedTrain != null) {
                trainDetailsLabel.setText("Train Time: " + selectedTrain.getDepartureTime() +
                        "\nPrice: ₹" + selectedTrain.getPrice());
                availableSeatsLabel.setText("Available Seats: " + selectedTrain.getAvailableSeats());
            }
        });

        // Reserve ticket action (simplified for this example)
        reserveButton.setOnAction(e -> {
            String origin = originComboBox.getValue();
            String destination = destinationComboBox.getValue();
            LocalDate selectedDate = datePicker.getValue();
            int numPassengers = numPassengersSpinner.getValue();
            Train selectedTrain = trainComboBox.getValue();

            if (origin == null || destination == null || selectedDate == null || origin.equals(destination) || selectedTrain == null) {
                resultLabel.setText("Please fill all fields correctly.");
                resultLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Validate the date (it should be in the future)
            if (selectedDate.isBefore(LocalDate.now())) {
                resultLabel.setText("Please select a valid future date.");
                resultLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Check if there are enough available seats
            if (numPassengers > selectedTrain.getAvailableSeats()) {
                resultLabel.setText("Not enough available seats.");
                resultLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Update the reserved seats
            selectedTrain.reserveSeats(numPassengers);

            // Simulating ticket reservation (for simplicity)
            resultLabel.setText("Reservation Successful!\nFrom: " + origin + "\nTo: " + destination +
                    "\nDate: " + selectedDate + "\nPassengers: " + numPassengers + "\nTrain: " +
                    selectedTrain.getName() + "\nPrice: ₹" + selectedTrain.getPrice()*numPassengersSpinner.getValue() +
                    "\nAvailable Seats: " + selectedTrain.getAvailableSeats());
            resultLabel.setStyle("-fx-text-fill: red;");
        });

        // Create GridPane layout for reservation screen
        GridPane reservationLayout = new GridPane();
        reservationLayout.setVgap(10);
        reservationLayout.setHgap(10);
        reservationLayout.setPadding(new Insets(20));

        // Adding labels and controls to the grid
        reservationLayout.add(reservationLabel, 0, 0, 2, 1); // Reservation label spans across 2 columns
        reservationLayout.add(originLabel, 0, 1);
        reservationLayout.add(originComboBox, 1, 1);
        reservationLayout.add(destinationLabel, 0, 2);
        reservationLayout.add(destinationComboBox, 1, 2);
        reservationLayout.add(dateLabel, 0, 3);
        reservationLayout.add(datePicker, 1, 3);
        reservationLayout.add(passengersLabel, 0, 4);
        reservationLayout.add(numPassengersSpinner, 1, 4);
        reservationLayout.add(trainLabel, 0, 5);
        reservationLayout.add(trainComboBox, 1, 5);
        reservationLayout.add(trainDetailsLabel, 1, 6);
        reservationLayout.add(availableSeatsLabel, 1, 7);
        reservationLayout.add(reserveButton, 1, 8);
        reservationLayout.add(resultLabel, 1, 9);

        // Set background image for reservation screen
        String reservationImagePath = "C:/Users/manga/Downloads/IMG_1944.jpg";  // Path to your image
        BackgroundImage reservationBackgroundImage = new BackgroundImage(
                new Image("file:" + reservationImagePath),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        reservationLayout.setBackground(new Background(reservationBackgroundImage));

        // Create Scene with the reservation layout and set it on the stage
        Scene reservationScene = new Scene(reservationLayout, 600, 400);
        primaryStage.setScene(reservationScene);
        primaryStage.setTitle("Train Ticket Reservation System");
    }

    // Train class to hold train details
    static class Train {
        private String name;
        private LocalTime departureTime;
        private int price;
        private int reservedSeats;
        private final int totalSeats = 30;

        public Train(String name, LocalTime departureTime, int price) {
            this.name = name;
            this.departureTime = departureTime;
            this.price = price;
            this.reservedSeats = 0;
        }

        public String getName() {
            return name;
        }

        public LocalTime getDepartureTime() {
            return departureTime;
        }

        public int getPrice() {
            return price;
        }

        public int getAvailableSeats() {
            return totalSeats - reservedSeats;
        }

        public void reserveSeats(int seats) {
            if (reservedSeats + seats <= totalSeats) {
                reservedSeats += seats;
            }
        }

        @Override
        public String toString() {
            return name; // This ensures that only the train name is displayed in the ComboBox
        }
//Mangalya H desai    }
}
