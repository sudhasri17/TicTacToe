package com.example.sudhasri.tictactoe;

import com.example.sudhasri.tictactoe.data.DifficultyLevel;
import com.example.sudhasri.tictactoe.data.Player;
import com.example.sudhasri.tictactoe.data.Sign;

import java.util.ArrayList;

/**
 * Logic presenter of the game
 */
public class LogicPresenter implements ViewPresenterContract.Presenter
{
    private static final int GRID_SIZE = 9;
    private Player mCurrentPlayer;
    DifficultyLevel mDifficultyLevel;
    ArrayList<Sign> mDataGrid;
    ArrayList<Integer> mWinningGrid = new ArrayList<>();
    private Player mSystemPlayer;
    int mSystemNextMove = -1;
    int mSystemMoveCount = 0;
    private ViewPresenterContract.View mActionView;

    ///
    // private constructor
    ///
    public LogicPresenter(ViewPresenterContract.View view)
    {
        mActionView = view;
        setCurrentPlayer(Player.X_PLAYER);
    }


    ///
    // check if board has reached a win
    ///
    @Override
    public boolean isWin() {
        return isHorizontalMatch() || isVerticalMatch() || isDiagonalMatch();
    }


    ///
    // does elements match diagonally
    ///
    boolean isDiagonalMatch() {
        if ((mDataGrid.get(2) == mDataGrid.get(4)) && (mDataGrid.get(4) == mDataGrid.get(6))
                && (mDataGrid.get(4) != Sign.EMPTY))
        {
            mWinningGrid.add(2);
            mWinningGrid.add(4);
            mWinningGrid.add(6);
            return true;
        }
        if ((mDataGrid.get(4) == mDataGrid.get(8)) && (mDataGrid.get(4) == mDataGrid.get(0))
                && (mDataGrid.get(4) != Sign.EMPTY))
        {
            mWinningGrid.add(0);
            mWinningGrid.add(4);
            mWinningGrid.add(8);
            return true;
        }
        return false;
    }


    ///
    // does elements match vertically
    ///
    boolean isVerticalMatch() {
        if ((mDataGrid.get(0) == mDataGrid.get(3)) && (mDataGrid.get(0) == mDataGrid.get(6))
                && (mDataGrid.get(0) != Sign.EMPTY))
        {
            mWinningGrid.add(0);
            mWinningGrid.add(3);
            mWinningGrid.add(6);
            return true;
        }
        if ((mDataGrid.get(1) == mDataGrid.get(4)) && (mDataGrid.get(4) == mDataGrid.get(7))
                && (mDataGrid.get(4) != Sign.EMPTY))
        {
            mWinningGrid.add(1);
            mWinningGrid.add(4);
            mWinningGrid.add(7);
            return true;
        }
        if ((mDataGrid.get(2) == mDataGrid.get(5)) && (mDataGrid.get(5) == mDataGrid.get(8))
                && (mDataGrid.get(5) != Sign.EMPTY))
        {
            mWinningGrid.add(2);
            mWinningGrid.add(5);
            mWinningGrid.add(8);
            return true;
        }
        return false;
    }


    ///
    // does elements match horizontally
    ///
    boolean isHorizontalMatch() {
        if ((mDataGrid.get(0) == mDataGrid.get(1)) && (mDataGrid.get(0) == mDataGrid.get(2))
                && (mDataGrid.get(0) != Sign.EMPTY))
        {
            mWinningGrid.add(0);
            mWinningGrid.add(1);
            mWinningGrid.add(2);
            return true;
        }
        if ((mDataGrid.get(3) == mDataGrid.get(4)) && (mDataGrid.get(4) == mDataGrid.get(5))
                && (mDataGrid.get(4) != Sign.EMPTY))
        {
            mWinningGrid.add(3);
            mWinningGrid.add(4);
            mWinningGrid.add(5);
            return true;
        }
        if ((mDataGrid.get(6) == mDataGrid.get(7)) && (mDataGrid.get(7) == mDataGrid.get(8))
                && (mDataGrid.get(7) != Sign.EMPTY))
        {
            mWinningGrid.add(6);
            mWinningGrid.add(7);
            mWinningGrid.add(8);
            return true;
        }
        return false;
    }

    ///
    // restarts the game
    ///
    @Override
    public void startOver() {
        mSystemMoveCount = 0;
        setCurrentPlayer(Player.X_PLAYER);
        mActionView.initialise();
        mWinningGrid.clear();
    }

    ///
    // switches turn for current player
    ///
    public void switchPlayer()
    {
        if (mCurrentPlayer == Player.X_PLAYER)
        {
            mCurrentPlayer = Player.O_PLAYER;
        }
        else if (mCurrentPlayer == Player.O_PLAYER)
        {
            mCurrentPlayer = Player.X_PLAYER;
        }

        if (mCurrentPlayer == mSystemPlayer)
        {
            getNextMove();
        }
        mActionView.showCurrentPlayer(mCurrentPlayer);
    }

    ///
    // generates next move for system
    ///
    void getNextMove() {

        mSystemMoveCount++;
        switch (mDifficultyLevel)
        {
            case EASY:
                getRandomMove();
                break;
            case MEDIUM:
                if (mSystemMoveCount == 3)
                    getRandomMove();
                else
                    minMax(2, getSystemPlayer());
                break;
            case HARD:
                minMax(2, getSystemPlayer());
                break;
        }
        updateData(mSystemNextMove, getSystemPlayer().getPlayerSign());
        mActionView.showCurrentMove(mSystemNextMove);
    }

    ///
    // generates random next move
    ///
    private void getRandomMove() {
        ArrayList<Integer> availableMoves = getAvailableMoves();
        int randomNumber = ((int)(Math.random()*10)) % (availableMoves.size());
        mSystemNextMove = availableMoves.get(randomNumber);
    }

    ///
    // updates data and calls switchPlayer
    ///
    @Override
    public void updateData(int position, Sign sign) {
        mDataGrid.set(position, sign);
        boolean userWon;
        if (isWin())
        {
            userWon = sign.getPlayer() != getSystemPlayer();
            mActionView.showWin(userWon, mWinningGrid);
        }
        else if (isBoardFilled())
        {
            mActionView.showDraw();
        }
        else {
            switchPlayer();
        }
    }

    ///
    // generates next move based on minMax algo
    ///
    public int minMax(int depth, Player player )
    {
        int bestScore = Integer.MAX_VALUE;
        int bestIndex = -1;
        if (player == getSystemPlayer())
        {
            bestScore = Integer.MIN_VALUE;
        }

        ArrayList<Integer> possibleMoves = getAvailableMoves();
        if (possibleMoves.isEmpty() || isWin() || depth == 0)
        {
            bestScore = getScore();
        }
        else
        {
            for (int i = 0; i < possibleMoves.size(); ++i)
            {
                int currentIndex = possibleMoves.get(i);
                mDataGrid.set(currentIndex, player.getPlayerSign());

                if (player == getSystemPlayer())
                {
                    int currentScore = minMax(depth - 1, getOpponentPlayer());
                    if (currentScore > bestScore)
                    {
                        bestScore = currentScore;
                        bestIndex = currentIndex;
                    }
                }
                else
                {
                    int currentScore = minMax(depth - 1, getSystemPlayer());
                    if (currentScore < bestScore)
                    {
                        bestScore = currentScore;
                        bestIndex = currentIndex;
                    }

                }
                mDataGrid.set(currentIndex, Sign.EMPTY); //Reset this point
            }
        }
        mSystemNextMove = bestIndex;
        if (depth == 0)
        System.out.println("current best score of grid: " + bestScore);
        return bestScore;
    }

    ///
    // calculates score of current board
    ///
    private int getScore()
    {
        int currentScore = 0;
        // horizontal rows
        currentScore += getLineScore(0, 1, 2);
        currentScore += getLineScore(3, 4, 5);
        currentScore += getLineScore(6, 7, 8);

        //vertical columns
        currentScore += getLineScore(0, 3, 6);
        currentScore += getLineScore(1, 4, 7);
        currentScore += getLineScore(2, 5, 8);

        //diagonal
        currentScore += getLineScore(0, 4, 8);
        currentScore += getLineScore(2, 4, 6);

        System.out.println("current score of grid: " + currentScore);
        return currentScore;
    }

    ///
    // mocking for testing
    ///
    public void setMockData(ArrayList<Sign> mockData)
    {
        mDataGrid = mockData;
    }

    ///
    // calculates score of a single line
    ///
    private int getLineScore(int cube1, int cube2, int cube3)
    {
        int score = 0;
        if (mDataGrid.get(cube1) == getSystemPlayer().getPlayerSign())
        {
            score = 1;
        }
        else if (mDataGrid.get(cube1) != Sign.EMPTY)
        {
            score = -1;
        }

        if (mDataGrid.get(cube2) == getSystemPlayer().getPlayerSign())
        {
            if (score == 1)
            {
                score = 10;
            }
            else if (score == -1)
            {
                score = 0;
            }
            else
                score = 1;

        }
        else if (mDataGrid.get(cube2) != Sign.EMPTY)
        {
            if (score == -1)
            {
                score = -10;
            }
            else if (score == 1)
            {
                score = 0;
            }
            else
                score = -1;
        }

        if (mDataGrid.get(cube3) == getSystemPlayer().getPlayerSign())
        {
            if (score > 0)
            {
                score *= 10;
            }
            else if (score < 0)
            {
                score =  0;
            }
            else
                score =  1;

        }
        else if (mDataGrid.get(cube3) != Sign.EMPTY)
        {
            if (score < 0)
            {
                score *= 10;
            }
            else if (score > 0)
            {
                score = 0;
            }
            else
                score = 1;
        }


        return score;
    }

    ///
    // gives all empty grids
    ///
    ArrayList<Integer> getAvailableMoves()
    {
        ArrayList<Integer> availableMoves = new ArrayList<>();
        int i = 0;
        for (Sign move :
                mDataGrid) {
            if(move == Sign.EMPTY)
            {
                availableMoves.add(i);
            }
            i++;
        }
        return availableMoves;
    }

    ///
    // checks if all grids are filled
    ///
    private boolean isBoardFilled() {
        for (Sign s:mDataGrid)
        {
            if (s == Sign.EMPTY)
                return false;
        }
            return true;
    }

    ///
    // makes all grid empty
    ///
    public ArrayList<Sign> getInitialGrid()
    {
        mDataGrid = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++)
        {
            mDataGrid.add(Sign.EMPTY);
        }
        return mDataGrid;
    }


    ///
    // starts system move
    ///
    public void startSystemMove() {
        getNextMove();
    }

    /// getters and setters
    public DifficultyLevel getDifficultyLevel() {
        return mDifficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel mDifficultyLevel) {
        this.mDifficultyLevel = mDifficultyLevel;
    }

    public Player getSystemPlayer() {
        return mSystemPlayer;
    }

    public Player getOpponentPlayer()
    {
        if (mSystemPlayer == Player.X_PLAYER)
            return Player.O_PLAYER;
        else
            return Player.X_PLAYER;
    }

    public void setSystemPlayer(Player mSystemPlayer) {
        this.mSystemPlayer = mSystemPlayer;
    }

    @Override
    public Player getCurrentPlayer() {
        return mCurrentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.mCurrentPlayer = currentPlayer;
    }

    public ArrayList<Sign> getData() {
        return mDataGrid;
    }
}
