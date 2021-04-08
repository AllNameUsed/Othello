package sample;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private Tiles[][] gameBoard;

    public Tiles[][] getGameBoard() {
        return gameBoard;
    }

    public GameState() {
        this.gameBoard = new Tiles[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Tiles tile = new Tiles(i,j);
                if (i == 3 && j == 3) {
                    tile.setToWhite();
                } else if (i == 3 && j == 4) {
                    tile.setToBlack();
                } else if (i == 4 && j == 3) {
                    tile.setToBlack();
                } else if (i == 4 && j == 4) {
                    tile.setToWhite();
                }
                this.gameBoard[i][j] = tile;
            }
        }
    }
    public int blackScore() {
        int output = 0;
        for (int x = 0; x < 8; x ++) {
            for (int y = 0; y < 8;y ++) {
                if (this.gameBoard[x][y].isBlack()) {
                    output ++;
                }
            }
        }
        return output;
    }
    public int whiteScore() {
        int output = 0;
        for (int x = 0; x < 8; x ++) {
            for (int y = 0; y < 8;y ++) {
                if (this.gameBoard[x][y].isWhite()) {
                    output ++;
                }
            }
        }
        return output;
    }
    public List<Tiles> allLegalMoves() {
        List<Tiles> output = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y ++) {
                if (!this.gameBoard[x][y].isEmpty()) {
                    continue;
                }
                if (Main.currentTurn == PlayerType.Black) {
                    if (isMoveLegal(Tiles.TileState.Black, x, y)) {
                        output.add(this.gameBoard[x][y]);
                    } else {
                        this.gameBoard[x][y].drawEmpty();
                    }
                } else {
                    if (isMoveLegal(Tiles.TileState.White, x, y)) {
                        output.add(this.gameBoard[x][y]);
                    } else {
                        this.gameBoard[x][y].drawEmpty();
                    }
                }
            }
        }
        return output;
    }
    public boolean gameFinished() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8;y++) {
                if (this.gameBoard[x][y].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
    public void updateGameBoard(int x, int y) {
        Tiles justPlayedTile = this.gameBoard[x][y];
        if (isLegalInHorizontalLeft(justPlayedTile.state, x, y)) {
            updateHorizontalLeft(x,y);
        }
        if (isLegalInHorizontalRight(justPlayedTile.state, x, y)) {
            updateHorizontalRight(x,y);
        }
        if (isLegalInColDown(justPlayedTile.state, x, y)) {
            updateVerticalDown(x, y);
        }
        if (isLegalInColUp(justPlayedTile.state,x,y)) {
            updateVerticalUp(x, y);
        }
        if (isLegalInDiagonalBottomLeft(justPlayedTile.state, x, y)) {
            updateDiagonalBottomLeft(x, y);
        }
        if (isLegalInDiagonalBottomRight(justPlayedTile.state,x,y)) {
            updateDiagonalBottomRight(x, y);
        }
        if (isLegalInDiagonalTopLeft(justPlayedTile.state, x, y)) {
            updateDiagonalTopLeft(x, y);
        }
        if (isLegalInDiagonalTopRight(justPlayedTile.state,x,y)) {
            updateDiagonalTopRight(x,y);
        }
        Main.whiteText = "White Score: " +  this.whiteScore();
        Main.blackText = "Black Score: " +  this.blackScore();
    }
    private void updateDiagonalBottomRight(int x, int y) {
        Tiles justPlayedTile = this.gameBoard[x][y];
        int start_xpos = x + 1;
        int start_ypos = y + 1;
        // update down:
        for (int i = Math.max(start_xpos, start_ypos); i < 8; i ++) {
            if (start_xpos < 0 || start_xpos > 7 || start_ypos < 0 || start_ypos > 7) {
                break;
            }
            Tiles tile = this.gameBoard[start_xpos][start_ypos];
            if (tile.state == Tiles.TileState.Empty || tile.state == justPlayedTile.state) {
                break;
            } else {
                tile.setTo(justPlayedTile);
            }
            start_xpos ++;
            start_ypos ++;
        }
    }
    private void updateDiagonalTopLeft(int x, int y) {
        Tiles justPlayedTile = this.gameBoard[x][y];
        // update up;
        int start_xpos = x - 1;
        int start_ypos = y - 1;
        for (int i = Math.min(start_xpos, start_ypos); i >= 0; i --) {
            if (start_xpos < 0 || start_xpos > 7 || start_ypos < 0 || start_ypos > 7) {
                break;
            }
            Tiles tile = this.gameBoard[start_xpos][start_ypos];
            if (tile.state == Tiles.TileState.Empty || tile.state == justPlayedTile.state) {
                break;
            } else {
                tile.setTo(justPlayedTile);
            }
            start_xpos --;
            start_ypos --;
        }
    }
    private void updateDiagonalTopRight(int x, int y) {
        Tiles justPlayedTile = this.gameBoard[x][y];
        int start_xpos = x + 1;
        int start_ypos = y - 1;
        // update up:
        for (int i = Math.max(start_xpos, start_ypos); i < 8; i ++) {
            if (start_xpos < 0 || start_xpos > 7 || start_ypos < 0 || start_ypos > 7) {
                break;
            }
            Tiles tile = this.gameBoard[start_xpos][start_ypos];
            if (tile.state == Tiles.TileState.Empty || tile.state == justPlayedTile.state) {
                break;
            } else {
                tile.setTo(justPlayedTile);
            }
            start_xpos ++;
            start_ypos --;
        }
    }
    private void updateDiagonalBottomLeft(int x, int y) {
        Tiles justPlayedTile = this.gameBoard[x][y];
        int start_xpos = x - 1;
        int start_ypos = y + 1;
        // update down:
        for (int i = Math.min(start_xpos, start_ypos); i >= 0; i --) {
            if (start_xpos < 0 || start_xpos > 7 || start_ypos < 0 || start_ypos > 7) {
                break;
            }
            Tiles tile = this.gameBoard[start_xpos][start_ypos];
            if (tile.state == Tiles.TileState.Empty || tile.state == justPlayedTile.state) {
                break;
            } else {
                tile.setTo(justPlayedTile);
            }
            start_xpos --;
            start_ypos ++;
        }
    }
    private void updateVerticalDown(int x, int y) {
        Tiles justPlayedTile = this.gameBoard[x][y];
        // update up:
        for (int i = y + 1; i < 8; i++) {
            Tiles tile = this.gameBoard[x][i];
            if (tile.state == Tiles.TileState.Empty || tile.state == justPlayedTile.state) {
                break;
            } else {
                tile.setTo(justPlayedTile);
            }
        }
    }
    private void updateVerticalUp(int x, int y) {
        Tiles justPlayedTile = this.gameBoard[x][y];
        // update down:
        for (int i = y - 1; i >= 0; i --) {
            Tiles tile = this.gameBoard[x][i];
            if (tile.state == Tiles.TileState.Empty || tile.state == justPlayedTile.state) {
                break;
            } else {
                tile.setTo(justPlayedTile);
            }
        }
    }
    private void updateHorizontalRight(int x, int y) {
        Tiles justPlayedTile = this.gameBoard[x][y];
        // update right:
        for (int i = x + 1; i < 8; i++) {
            Tiles tile = this.gameBoard[i][y];
            if (tile.state == Tiles.TileState.Empty || tile.state == justPlayedTile.state) {
                break;
            } else {
                tile.setTo(justPlayedTile);
            }
        }
    }
    private void updateHorizontalLeft(int x, int y) {
        Tiles justPlayedTile = this.gameBoard[x][y];
        // update left:
        for (int i = x - 1; i >= 0; i--) {
            Tiles tile = this.gameBoard[i][y];
            if (tile.state == Tiles.TileState.Empty || tile.state == justPlayedTile.state) {
                break;
            } else {
                tile.setTo(justPlayedTile);
            }
        }
    }
    private boolean isLegalInDiagonalTopRight  (Tiles.TileState state, int x, int y) {
        if (state == Tiles.TileState.Black) {
            int start_xpos = x + 2;
            int start_ypos = y - 2;
            int loopCondition = Math.min((8 - start_xpos),(start_ypos + 1));
            // search up:
            for (int i = 0; i < loopCondition; i ++) {
                if (start_xpos < 0 || start_xpos > 7 || start_ypos < 0 || start_ypos > 7) {
                    break;
                } else if (x + 1 < 0 || x + 1 > 7 || y - 1 < 0 || y - 1 > 7 || !this.gameBoard[x + 1][y - 1].isWhite()) {
                    break;
                }
                Tiles tile = this.gameBoard[start_xpos][start_ypos];
                if (tile.isEmpty()) {
                    break;
                } else if (tile.isBlack()) {
                    return true;
                }
                start_xpos ++;
                start_ypos --;
            }
        } else {
            int start_xpos = x + 2;
            int start_ypos = y - 2;
            int loopCondition = Math.min((8 - start_xpos),(start_ypos + 1));
            // search up:
            for (int i = 0; i < loopCondition; i ++) {
                if (start_xpos < 0 || start_xpos > 7 || start_ypos < 0 || start_ypos > 7) {
                    break;
                } else if (x + 1 < 0 || x + 1 > 7 || y - 1 < 0 || y - 1 > 7 || !this.gameBoard[x + 1][y - 1].isBlack()) {
                    break;
                }
                Tiles tile = this.gameBoard[start_xpos][start_ypos];
                if (tile.isEmpty()) {
                    break;
                } else if (tile.isWhite()) {
                    return true;
                }
                start_xpos ++;
                start_ypos --;
            }
        }
        return false;
    }
    private boolean isLegalInDiagonalBottomLeft (Tiles.TileState state, int x, int y) {
        if (state == Tiles.TileState.Black) {
            // Player black:
            int start_xpos = x - 2;
            int start_ypos = y + 2;
            int loopCondition = Math.min((1 + start_xpos),(8 - start_ypos));
            // search down:
            for (int i = 0; i < loopCondition; i ++) {
                if (start_xpos < 0 || start_xpos > 7 || start_ypos < 0 || start_ypos > 7) {
                    break;
                } else if (x - 1 < 0 || x - 1 > 7 || y + 1 < 0 || y + 1 > 7 || !this.gameBoard[x - 1][y + 1].isWhite()) {
                    break;
                }
                Tiles tile = this.gameBoard[start_xpos][start_ypos];
                if (tile.isEmpty()) {
                    break;
                } else if (tile.isBlack()) {
                    return true;
                }
                start_xpos --;
                start_ypos ++;
            }
        } else {
            //Player white:
            int start_xpos = x - 2;
            int start_ypos = y + 2;
            int loopCondition = Math.min((1 + start_xpos),(8 - start_ypos));
            // search down:
            for (int i = 0; i < loopCondition; i ++) {
                if (start_xpos < 0 || start_xpos > 7 || start_ypos < 0 || start_ypos > 7) {
                    break;
                }  else if (x - 1 < 0 || x - 1 > 7 || y + 1 < 0 || y + 1 > 7 || !this.gameBoard[x - 1][y + 1].isBlack()) {
                    break;
                }
                Tiles tile = this.gameBoard[start_xpos][start_ypos];
                if (tile.isEmpty()) {
                    break;
                } else if (tile.isWhite()) {
                    return true;
                }
                start_xpos --;
                start_ypos ++;
            }

        }
        return false;
    }
    private boolean isLegalInDiagonalBottomRight(Tiles.TileState state, int x, int y) {
        if (state == Tiles.TileState.Black) {
            // search down:
            int start_xpos = x + 2;
            int start_ypos = y + 2;
            int loopCondition = Math.min((8 - start_xpos),(8 - start_ypos));
            for (int i = 0; i < loopCondition; i ++) {
                if (start_xpos < 0 || start_xpos > 7 || start_ypos < 0 || start_ypos > 7) {
                    break;
                } else if (x + 1 < 0 || x + 1 > 7 || y + 1 < 0 || y + 1 > 7 ||  !this.gameBoard[x + 1][y + 1].isWhite()) {
                    break;
                }
                Tiles tile = this.gameBoard[start_xpos][start_ypos];
                if (tile.isEmpty()) {
                    break;
                } else if (tile.isBlack()) {
                    return true;
                }
                start_xpos ++;
                start_ypos ++;
            }
        } else {
            // search down:
            int start_xpos = x + 2;
            int start_ypos = y + 2;
            int loopCondition = Math.min((8 - start_xpos),(8 - start_ypos));
            for (int i = 0; i < loopCondition; i++) {
                if (start_xpos < 0 || start_xpos > 7 || start_ypos < 0 || start_ypos > 7) {
                    break;
                } else if (x + 1 < 0 || x + 1 > 7 || y + 1 < 0 || y + 1 > 7 ||  !this.gameBoard[x + 1][y + 1].isBlack()) {
                    break;
                }
                Tiles tile = this.gameBoard[start_xpos][start_ypos];
                if (tile.isEmpty()) {
                    break;
                } else if (tile.isWhite()) {
                    return true;
                }
                start_xpos ++;
                start_ypos ++;
            }
        }
        return false;
    }
    private boolean isLegalInDiagonalTopLeft(Tiles.TileState state, int x, int y) {
        if (state == Tiles.TileState.Black) {
            // Player black:
            // search up:
            int start_xpos = x - 2;
            int start_ypos = y - 2;
            int loopCondition = Math.min((1 + start_xpos),(1 + start_ypos));
            for (int i = 0; i < loopCondition; i++) {
                if (start_xpos < 0 || start_xpos > 7 || start_ypos < 0 || start_ypos > 7) {
                    break;
                } else if (x - 1 < 0 || x - 1 > 7 || y - 1 < 0 || y - 1 > 7 ||  !this.gameBoard[x - 1][y - 1].isWhite()) {
                    break;
                }
                Tiles tile = this.gameBoard[start_xpos][start_ypos];
                if (tile.isEmpty()) {
                    break;
                } else if (tile.isBlack()) {
                    return true;
                }
                start_xpos --;
                start_ypos --;
            }
        } else {
            // Player white:
            int start_xpos = x - 2;
            int start_ypos = y - 2;
            int loopCondition = Math.min((1 + start_xpos),(1 + start_ypos));
            for (int i = 0; i < loopCondition; i++) {
                if (start_xpos < 0 || start_xpos > 7 || start_ypos < 0 || start_ypos > 7) {
                    break;
                } else if (x - 1 < 0 || x - 1 > 7 || y - 1 < 0 || y - 1 > 7 ||  !this.gameBoard[x - 1][y - 1].isBlack()) {
                    break;
                }
                Tiles tile = this.gameBoard[start_xpos][start_ypos];
                if (tile.isEmpty()) {
                    break;
                } else if (tile.isWhite()) {
                    return true;
                }
                start_xpos --;
                start_ypos --;
            }
        }
        return false;
    }
    private boolean isLegalInColUp(Tiles.TileState state, int x, int y) {
        if (state == Tiles.TileState.Black) {
            // search down:
            for (int i = y - 2; i >= 0; i--) {
                if (x < 0 || x > 7 || y - 1 < 0 || y - 1 > 7 || !this.gameBoard[x][y - 1].isWhite()) {
                    break;
                }
                Tiles tile = this.gameBoard[x][i];
                if (tile.isEmpty()) {
                    break;
                } else if (tile.isBlack()) {
                    return true;
                }
            }
        } else {
            // search down:
            for (int i = y - 2; i >= 0; i--) {
                if (x < 0 || x > 7 || y - 1 < 0 || y - 1 > 7 || !this.gameBoard[x][y - 1].isBlack()) {
                    break;
                }
                Tiles tile = this.gameBoard[x][i];
                if (tile.isEmpty()) {
                    break;
                } else if (tile.isWhite()) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean isLegalInColDown(Tiles.TileState state, int x, int y) {
        if (state == Tiles.TileState.Black) {
            // Player is black:
            // search up:
            for (int i = y + 2; i < 8; i++) {
                if (x < 0 || x > 7 || y + 1 < 0 || y + 1 > 7 || !this.gameBoard[x][y + 1].isWhite()) {
                    break;
                }
                Tiles tile = this.gameBoard[x][i];
                if (tile.isEmpty()) {
                    break;
                } else if (tile.isBlack()) {
                    return true;
                }
            }
        } else {
            // Player is white:
            // search up:
            for (int i = y + 2; i < 8; i++) {
                if (x < 0 || x > 7 || y + 1 < 0 || y + 1 > 7 || !this.gameBoard[x][y + 1].isBlack()) {
                    break;
                }
                Tiles tile = this.gameBoard[x][i];
                if (tile.isEmpty()) {
                    break;
                } else if (tile.isWhite()) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean isLegalInHorizontalLeft(Tiles.TileState state, int x, int y) {
        if (state == Tiles.TileState.Black) {
            // search left
            for (int i = x - 2; i >= 0; i--) {
                if (x - 1 < 0 || x - 1 > 7 || y < 0 || y > 7 || !this.gameBoard[x - 1][y].isWhite()) {
                    break;
                }
                Tiles tile = this.gameBoard[i][y];
                if (tile.isEmpty()) {
                    break;
                } else if (tile.isBlack()) {
                    return true;
                }
            }
        } else {
            // search left
            for (int i = x - 2; i >= 0; i--) {
                if (x - 1 < 0 || x - 1 > 7 || y < 0 || y > 7 || !this.gameBoard[x - 1][y].isBlack()) {
                    break;
                }
                Tiles tile = this.gameBoard[i][y];
                if (tile.isEmpty()) {
                    break;
                } else if (tile.isWhite()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isLegalInHorizontalRight(Tiles.TileState state, int x, int y) {
        if (state == Tiles.TileState.Black) {
            // if player is black:
            // search right:
            for (int i = x + 2; i < 8; i ++) {
                if (x + 1 < 0 || x + 1 > 7 || y < 0 || y > 7 || !this.gameBoard[x + 1][y].isWhite()) {
                    break;
                }
                Tiles tile = this.gameBoard[i][y];
                if (tile.isEmpty()) {
                    break;
                } else if (tile.isBlack()) {
                    return true;
                }
            }
        } else {
            // if player is white:
            // search right:
            for (int i = x + 2; i < 8; i ++) {
                if (x + 1 < 0 || x + 1> 7 || y < 0 || y > 7 || !this.gameBoard[x + 1][y].isBlack()) {
                    break;
                }
                Tiles tile = this.gameBoard[i][y];
                if (tile.isEmpty()) {
                    break;
                } else if (tile.isWhite()) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isMoveLegal(Tiles.TileState state, int x, int y) {
        boolean horizontal, vertical, diagonalDown, diagonalUp;
        horizontal = isLegalInHorizontalRight(state, x,y) || isLegalInHorizontalLeft(state, x , y);
        vertical = isLegalInColUp(state, x, y) || isLegalInColDown(state, x, y);
        diagonalDown = isLegalInDiagonalTopLeft(state, x, y) || isLegalInDiagonalBottomRight(state, x, y);
        diagonalUp = isLegalInDiagonalTopRight(state, x, y) || isLegalInDiagonalBottomLeft(state, x, y);
        return horizontal || vertical || diagonalDown || diagonalUp;
    }

    public void report() {
        System.out.println("==========================================================");
        System.out.println("                   CURRENT GAME STATE");
        System.out.println("==========================================================");
        for (int i = 0; i < 8; i ++) {
            for (int j = 0; j <8;j++) {
                Tiles t = this.gameBoard[i][j];
                if (t.isBlack()) {
                    System.out.println("Piece at ("+t.xPos + ","+t.yPos + ") is BLACK");
                } else if (t.isWhite()) {
                    System.out.println("Piece at ("+t.xPos + ","+t.yPos + ") is WHITE");
                }
            }
        }
        System.out.println("==========================================================");
    }
}
