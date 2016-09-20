package com.example.sudhasri.tictactoe.data;

/**
 * Created by sudhasri on 27/8/16.
 */
public enum Player
{
    X_PLAYER,
    O_PLAYER;

    public Sign getPlayerSign()
    {
        if (this == X_PLAYER)
            return Sign.X;
        return Sign.O;
    }
}
