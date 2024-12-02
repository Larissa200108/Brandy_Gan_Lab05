package com.example.mponaganbrandylarissa_comp228sec_lab5;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class GamePlayer extends Application {
    // Database connection details
    private static final String DB_URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
    private static final String USER = "COMP228_F24_soh_19";
    private static final String PASSWORD = "software";

    // Declare text fields for player and game input
    private TextField txtPlayerId, txtFirstName, txtLastName, txtAddress, txtPostalCode, txtProvince, txtPhoneNumber;
    private TextField txtGameId, txtGameTitle, txtScore;

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Player and Game Manager");

        // Create a grid layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        // Initialize player's input fields
        txtPlayerId = new TextField();
        txtFirstName = new TextField();
        txtLastName = new TextField();
        txtAddress = new TextField();
        txtPostalCode = new TextField();
        txtProvince = new TextField();
        txtPhoneNumber = new TextField();

        // Add labels and input fields for player details in the grid
        grid.add(new Label("Player ID:"), 0, 0);
        grid.add(txtPlayerId, 1, 0);
        grid.add(new Label("First Name:"), 0, 1);
        grid.add(txtFirstName, 1, 1);
        grid.add(new Label("Last Name:"), 0, 2);
        grid.add(txtLastName, 1, 2);
        grid.add(new Label("Address:"), 0, 3);
        grid.add(txtAddress, 1, 3);
        grid.add(new Label("Postal Code:"), 0, 4);
        grid.add(txtPostalCode, 1, 4);
        grid.add(new Label("Province:"), 0, 5);
        grid.add(txtProvince, 1, 5);
        grid.add(new Label("Phone Number:"), 0, 6);
        grid.add(txtPhoneNumber, 1, 6);

        // Initialize game's input fields
        txtGameId = new TextField();
        txtGameTitle = new TextField();
        txtScore = new TextField();

        // Add labels and input fields for game details in the grid
        grid.add(new Label("Game ID:"), 0, 7);
        grid.add(txtGameId, 1, 7);
        grid.add(new Label("Game Title:"), 0, 8);
        grid.add(txtGameTitle, 1, 8);
        grid.add(new Label("Score:"), 0, 9);
        grid.add(txtScore, 1, 9);

        // Buttons to insert, update, and display player and game details
        Button btnInsert = new Button("Insert Player and Game");
        Button btnUpdate = new Button("Update Player and Game");
        Button btnDisplay = new Button("Display Players and Games");

        // Add buttons to the grid layout
        grid.add(btnInsert, 0, 10);
        grid.add(btnUpdate, 1, 10);
        grid.add(btnDisplay, 0, 11, 2, 1);

        // Set button actions for handling player and game data
        btnInsert.setOnAction(_ -> insertPlayerAndGame());
        btnUpdate.setOnAction(_ -> updatePlayerAndGame());
        btnDisplay.setOnAction(_ -> displayPlayersAndGames());

        // Set up the scene and display the primary stage
        Scene scene = new Scene(grid, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to insert player and game data into the database
    private void insertPlayerAndGame() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            // Insert player details into BrandyGan_player table
            PreparedStatement playerStmt = conn.prepareStatement(
                    "INSERT INTO BrandyGan_player (player_id, first_name, last_name, address, postal_code, province, phone_number) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)");
            playerStmt.setInt(1, Integer.parseInt(txtPlayerId.getText()));
            playerStmt.setString(2, txtFirstName.getText());
            playerStmt.setString(3, txtLastName.getText());
            playerStmt.setString(4, txtAddress.getText());
            playerStmt.setString(5, txtPostalCode.getText());
            playerStmt.setString(6, txtProvince.getText());
            playerStmt.setString(7, txtPhoneNumber.getText());
            playerStmt.executeUpdate();

            // Insert game details into BrandyGan_game table
            PreparedStatement gameStmt = conn.prepareStatement(
                    "INSERT INTO BrandyGan_game (game_id, game_title) VALUES (?, ?)");
            gameStmt.setInt(1, Integer.parseInt(txtGameId.getText()));
            gameStmt.setString(2, txtGameTitle.getText());
            gameStmt.executeUpdate();

            // Insert player,game relationship into BrandyGan_player_and_game table
            PreparedStatement playerGameStmt = conn.prepareStatement(
                    "INSERT INTO BrandyGan_player_and_game (player_game_id, player_id, game_id, playing_date, score) " +
                            "VALUES (?, ?, ?, SYSDATE, ?)");
            playerGameStmt.setInt(1, Integer.parseInt(txtPlayerId.getText()));
            playerGameStmt.setInt(2, Integer.parseInt(txtPlayerId.getText()));
            playerGameStmt.setInt(3, Integer.parseInt(txtGameId.getText()));
            playerGameStmt.setInt(4, Integer.parseInt(txtScore.getText()));
            playerGameStmt.executeUpdate();

            // Display success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player and Game inserted successfully!");
            alert.show();

        } catch (SQLException e) {
            e.printStackTrace();
            // Display error message in case of an exception
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error inserting Player and Game: " + e.getMessage());
            alert.show();
        }
    }

    // Method to update player and game data in the database
    private void updatePlayerAndGame() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            // Update player details in BrandyGan_player table
            PreparedStatement updatePlayerStmt = conn.prepareStatement(
                    "UPDATE BrandyGan_player SET first_name = ?, last_name = ?, address = ?, postal_code = ?, province = ?, phone_number = ? " +
                            "WHERE player_id = ?");
            updatePlayerStmt.setString(1, txtFirstName.getText());
            updatePlayerStmt.setString(2, txtLastName.getText());
            updatePlayerStmt.setString(3, txtAddress.getText());
            updatePlayerStmt.setString(4, txtPostalCode.getText());
            updatePlayerStmt.setString(5, txtProvince.getText());
            updatePlayerStmt.setString(6, txtPhoneNumber.getText());
            updatePlayerStmt.setInt(7, Integer.parseInt(txtPlayerId.getText()));
            updatePlayerStmt.executeUpdate();

            // Update game details in BrandyGan_game table
            PreparedStatement updateGameStmt = conn.prepareStatement(
                    "UPDATE BrandyGan_game SET game_title = ? WHERE game_id = ?");
            updateGameStmt.setString(1, txtGameTitle.getText());
            updateGameStmt.setInt(2, Integer.parseInt(txtGameId.getText()));
            updateGameStmt.executeUpdate();

            // Display success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player and Game updated successfully!");
            alert.show();

        } catch (SQLException e) {
            e.printStackTrace();
            // Display error message in case of an exception
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error updating Player and Game: " + e.getMessage());
            alert.show();
        }
    }

    // Method to display players and games in a new window
    private void displayPlayersAndGames() {
        Stage displayStage = new Stage();
        displayStage.setTitle("Players and Games");

        TableView<ObservableList<String>> tableView = new TableView<>();
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT p.player_id, p.first_name, p.last_name, p.province, p.phone_number, g.game_id, g.game_title, pag.player_game_id, pag.score, pag.playing_date " +
                             "FROM BrandyGan_player p " +
                             "LEFT JOIN BrandyGan_player_and_game pag ON p.player_id = pag.player_id " +
                             "LEFT JOIN BrandyGan_game g ON pag.game_id = g.game_id " +
                             "ORDER BY p.player_id")) {

            // Create columns for the table based on the ResultSet metadata
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                final int index = i - 1;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(rs.getMetaData().getColumnName(i));
                column.setCellValueFactory(dataFeatures -> new javafx.beans.property.SimpleStringProperty(
                        dataFeatures.getValue().get(index)));
                tableView.getColumns().add(column);
            }

            // Populate the table data with the result set
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set the items in the TableView
        tableView.setItems(data);

        // Display the TableView in a VBox
        VBox vbox = new VBox(tableView);
        // Set the scene for the display window
        Scene scene = new Scene(vbox);
        displayStage.setScene(scene);
        displayStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
