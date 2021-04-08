package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
    static PlayerType currentTurn;
    static GameState gameStateManager;
    static Group textGroup;
    static String blackText;
    static String whiteText;
    static Text blackScore;
    static Text whiteScore;
    static Text currentPlayer;
    Stage gameStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Othello!");
        this.gameStage = primaryStage;
        startGame();
    }

    private void startGame() {
        currentTurn = PlayerType.Black;
        gameStateManager = new GameState();
        textGroup = new Group();
        blackText = "Black Score: 2";
        whiteText = "White Score: 2";
        blackScore = new Text(blackText);
        blackScore.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        blackScore.setX(825);
        blackScore.setY(200);
        whiteScore = new Text(whiteText);
        whiteScore.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        whiteScore.setX(825);
        whiteScore.setY(600);
        currentPlayer = new Text("Current Player:\nBlack");
        currentPlayer.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        currentPlayer.setX(820);
        currentPlayer.setY(325);
        whiteScore.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        whiteScore.setX(825);
        whiteScore.setY(500);
        textGroup.getChildren().addAll(blackScore,whiteScore,currentPlayer);
        renderGame();
    }

    private void renderGame() {
        Pane root = new Pane();
        Button restart = new Button("Restart Game!");
        restart.setOnAction(e -> {
            gameStage.close();
            startGame();
        });
        restart.setLayoutX(930);
        restart.setLayoutY(575);
        root.getChildren().add(restart);
        root.getChildren().add(textGroup);
        refreshScore();
        root.setPrefSize(1200,800);
        Tiles[][] board = gameStateManager.getGameBoard();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Tiles tile = board[x][y];
                tile.relocate(tile.xPos * 100, tile.yPos * 100);
                if (tile.isWhite()) {
                    tile.drawWhite();
                } else if (tile.isBlack()) {
                    tile.drawBlack();
                }
                root.getChildren().add(tile);
            }
        }
        gameStage.setScene(new Scene(root));
        gameStage.show();
    }
    protected static void refreshScore() {
        blackScore.setText(blackText);
        whiteScore.setText(whiteText);
    }
    protected static void displayGameResult() {
        if (gameStateManager.blackScore() > gameStateManager.whiteScore()) {
            blackScore.setText("Black Wins!");
            whiteScore.setText("White Loses!");
        } else if (gameStateManager.blackScore() < gameStateManager.whiteScore()){
            blackScore.setText("Black Loses!");
            whiteScore.setText("White Wins!");
        } else {
            blackScore.setText("Draw!");
            whiteScore.setText("Draw!");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
