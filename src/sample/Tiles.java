package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;


public class Tiles extends StackPane {
    private final Color officialColor = Color.rgb(202,167,132);
    // private final Color GREY = Color.rgb(186,177,150);
    private final double officialStrokeWidth = 2.3;

    public enum TileState {
        Empty, White, Black
    }
    TileState state;
    int xPos;
    int yPos;
    public void setToBlack() {
        this.state = TileState.Black;
    }
    public void setToWhite() {
        this.state = TileState.White;
    }
    public boolean isWhite() {
        return this.state == TileState.White;
    }
    public boolean isBlack() {
        return this.state == TileState.Black;
    }

    public boolean isEmpty() {
        return  this.state == TileState.Empty;
    }
    public Tiles(int x, int y) {
        this.xPos = x;
        this.yPos = y;
        this.state = TileState.Empty;

        Rectangle img = new Rectangle(100, 100);
        img.setStrokeWidth(officialStrokeWidth);
        img.setFill(officialColor);
        img.setStroke(Color.BLACK);
        this.getChildren().add(img);
        this.setAlignment(Pos.CENTER);



        setOnMouseClicked(mouseEvent -> {
            if (!this.isEmpty()) {
                this.drawRed();
            } else if (Main.currentTurn == PlayerType.Black) {// Currently Black player playing
                if (Main.gameStateManager.isMoveLegal(TileState.Black, this.xPos, this.yPos)) {
                    blackPlay();
                } else {
                    this.drawRed();
                }
                // For debug: Main.gameStateManager.report();
            } else {// Currently white player playing
                if (Main.gameStateManager.isMoveLegal(TileState.White, this.xPos, this.yPos)) {
                    this.whitePlay();
                } else {
                    this.drawRed();
                }
                // For debug: Main.gameStateManager.report();
            }
        });
    }
    private void blackPlay() {
        this.drawBlack();
        Main.gameStateManager.updateGameBoard(this.xPos, this.yPos);
        if (Main.gameStateManager.gameFinished()) {
            // if game has finished:
            Main.displayGameResult();
        } else {
            // if game has not finished:
            Main.refreshScore();
            Main.currentTurn = PlayerType.White;
            List<Tiles> availableMoves = Main.gameStateManager.allLegalMoves();
            if (availableMoves.size() == 0) {
                // if no moves to play, black keeps playing:
                Main.currentPlayer.setText("Current Player:\nBlack");
                Main.currentTurn = PlayerType.Black;
                availableMoves = Main.gameStateManager.allLegalMoves();
                for (Tiles t : availableMoves) {
                    t.outline();
                }
            } else {
                // outline white moves
                for (Tiles t : availableMoves) {
                    t.outline();
                }
                Main.currentPlayer.setText("Current Player:\nWhite");
            }
        }

    }
    public void setTo(Tiles tile) {
        this.state = tile.state;
        if (this.state == TileState.Black) {
            this.drawBlack();
            Main.gameStateManager.getGameBoard()[this.xPos][this.yPos].setToBlack();
        } else {
            this.drawWhite();
            Main.gameStateManager.getGameBoard()[this.xPos][this.yPos].setToWhite();
        }
    }
    private void whitePlay() {
        this.drawWhite();
        Main.gameStateManager.updateGameBoard(this.xPos, this.yPos);
        if (Main.gameStateManager.gameFinished()) {
            // game finished:
            Main.displayGameResult();
        } else {
            // game still going:
            Main.refreshScore();
            Main.currentTurn = PlayerType.Black;
            List<Tiles> availableMoves = Main.gameStateManager.allLegalMoves();
            if (availableMoves.size() == 0) {
                // if no moves to play, white keeps playing:
                Main.currentPlayer.setText("Current Player:\nWhite");
                Main.currentTurn = PlayerType.White;
                availableMoves = Main.gameStateManager.allLegalMoves();
                for (Tiles t : availableMoves) {
                    t.outline();
                }
            } else {
                // outline black moves:
                for (Tiles t : availableMoves) {
                    t.outline();
                }
            }
            Main.currentPlayer.setText("Current Player:\nBlack");
        }

    }
    public void drawWhite() {
        this.setToWhite();
        this.setAlignment(Pos.CENTER);
        this.getChildren().removeAll();
        Rectangle img = new Rectangle(100, 100);
        img.setStrokeWidth(officialStrokeWidth);
        img.setFill(officialColor);
        img.setStroke(Color.BLACK);
        this.getChildren().add(img);

        Circle circle = new Circle(47);
        circle.setFill(Color.WHITE);
        this.getChildren().add(circle);

        Main.gameStateManager.getGameBoard()[this.xPos][this.yPos].setToWhite();
    }
    public void drawBlack() {
        this.setToBlack();
        this.setAlignment(Pos.CENTER);
        this.getChildren().removeAll();
        Rectangle img = new Rectangle(100, 100);
        img.setStrokeWidth(officialStrokeWidth);
        img.setFill(officialColor);
        img.setStroke(Color.BLACK);
        this.getChildren().add(img);

        Circle circle = new Circle(47);
        circle.setFill(Color.BLACK);
        this.getChildren().add(circle);
        Main.gameStateManager.getGameBoard()[this.xPos][this.yPos].setToBlack();
    }
    public void drawEmpty() {
        this.state = TileState.Empty;
        this.getChildren().removeAll();
        this.setAlignment(Pos.CENTER);
        Rectangle img = new Rectangle(100, 100);
        img.setStrokeWidth(officialStrokeWidth);
        img.setFill(officialColor);
        img.setStroke(Color.BLACK);
        this.getChildren().add(img);
    }

    public void outline() {
        this.state = TileState.Empty;
        this.setAlignment(Pos.CENTER);
        this.getChildren().removeAll();
        Rectangle img = new Rectangle(100, 100);
        img.setStrokeWidth(officialStrokeWidth);
        img.setFill(officialColor);
        img.setStroke(Color.BLACK);
        this.getChildren().add(img);

        Circle circle = new Circle(47);
        circle.setFill(null);
        if (Main.currentTurn == PlayerType.Black) {
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(5);
        } else {
            circle.setStroke(Color.WHITE);
            circle.setStrokeWidth(5);
        }
        this.getChildren().add(circle);
    }

    public void drawRed() {
        this.setAlignment(Pos.CENTER);
        this.getChildren().removeAll();
        Rectangle img = new Rectangle(100, 100);
        img.setStrokeWidth(officialStrokeWidth);
        img.setFill(officialColor);
        img.setStroke(Color.BLACK);
        this.getChildren().add(img);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.25), e -> {
                    // code to execute here...
                    if (this.isBlack()) {
                        this.drawBlack();
                    } else if (this.isWhite()) {
                        this.drawWhite();
                    } else {
                        this.drawEmpty();
                    }
                })
        );

        Circle circle = new Circle(47);
        circle.setFill(Color.RED);
        this.getChildren().add(circle);
        timeline.play();
    }
}
