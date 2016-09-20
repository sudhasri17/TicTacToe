package com.example.sudhasri.tictactoe.data;

/**
 * Created by sudhasri on 26/8/16.
 */
public enum Sign
{
    EMPTY (" "),
    X ("X"),
    O ("O");

    private final String mValue;

    Sign(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }

    public Player getPlayer()
    {
        if (mValue.equals("X"))
            return Player.X_PLAYER;
        else if (mValue.equals("O"))
            return Player.O_PLAYER;
        return null;
    }
}
