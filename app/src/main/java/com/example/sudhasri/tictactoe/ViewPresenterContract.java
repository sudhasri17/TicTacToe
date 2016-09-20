package com.example.sudhasri.tictactoe;

import com.example.sudhasri.tictactoe.data.DifficultyLevel;
import com.example.sudhasri.tictactoe.data.Player;
import com.example.sudhasri.tictactoe.data.Sign;

import java.util.ArrayList;

/**
 * class that gives contract between view an d presenter.
 */
public class ViewPresenterContract
{
    public interface View
    {
        void showCurrentPlayer(Player player);
        void showCurrentMove(int position);
        void showWin(boolean userWon, ArrayList<Integer> winningGrid);
        void initialise();
        void showDraw();
    }

    public interface Presenter
    {
        void updateData(int position, Sign sign);
        ArrayList<Sign> getInitialGrid();
        boolean isWin();
        void startOver();
        void startSystemMove();
        void setDifficultyLevel(DifficultyLevel difficultyLevel);
        void setSystemPlayer(Player player);
        Player getCurrentPlayer();
        Player getSystemPlayer();
    }
}
