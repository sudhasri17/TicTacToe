package com.example.sudhasri.tictactoe;

import com.example.sudhasri.tictactoe.data.DifficultyLevel;
import com.example.sudhasri.tictactoe.data.Player;
import com.example.sudhasri.tictactoe.data.Sign;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.calls;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by sudhasri on 19/9/16.
 */
public class LogicPresenterTest {

    private LogicPresenter mPresenter;
    @Mock
    private ViewPresenterContract.View mMockView;
    @Mock
    private ArrayList<Sign> mMockData;

    @org.junit.Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = LogicPresenter.getInstance(mMockView);
        ArrayList<Sign> mock = new ArrayList<Sign>();
        when(mMockData.get(0)).thenReturn(Sign.EMPTY);
        when(mMockData.get(1)).thenReturn(Sign.EMPTY);
        when(mMockData.get(2)).thenReturn(Sign.EMPTY);
        when(mMockData.get(3)).thenReturn(Sign.X);
        when(mMockData.get(4)).thenReturn(Sign.O);
        when(mMockData.get(5)).thenReturn(Sign.EMPTY);
        when(mMockData.get(6)).thenReturn(Sign.O);
        when(mMockData.get(7)).thenReturn(Sign.X);
        when(mMockData.get(8)).thenReturn(Sign.X);
        mPresenter.setMockData(mMockData);
    }

    @org.junit.Test
    public void testIsWin_true() throws Exception
    {
        when(mMockData.get(0)).thenReturn(Sign.O);
        when(mMockData.get(1)).thenReturn(Sign.O);
        when(mMockData.get(2)).thenReturn(Sign.O);
        mPresenter.setMockData(mMockData);
        assertTrue(mPresenter.isWin());
    }

    @org.junit.Test
    public void testIsWin_false() throws Exception
    {
        when(mMockData.get(0)).thenReturn(Sign.EMPTY);
        when(mMockData.get(1)).thenReturn(Sign.EMPTY);
        when(mMockData.get(2)).thenReturn(Sign.EMPTY);
        when(mMockData.get(3)).thenReturn(Sign.X);
        when(mMockData.get(4)).thenReturn(Sign.O);
        when(mMockData.get(5)).thenReturn(Sign.EMPTY);
        when(mMockData.get(6)).thenReturn(Sign.O);
        when(mMockData.get(7)).thenReturn(Sign.X);
        when(mMockData.get(8)).thenReturn(Sign.X);
        mPresenter.setMockData(mMockData);
        assertFalse(mPresenter.isWin());
    }

    @org.junit.Test
    public void testHorizontalMatch_true() throws Exception {
        when(mMockData.get(0)).thenReturn(Sign.X);
        when(mMockData.get(1)).thenReturn(Sign.X);
        when(mMockData.get(2)).thenReturn(Sign.X);
        mPresenter.setMockData(mMockData);
        assertTrue(mPresenter.isHorizontalMatch());
    }

    @org.junit.Test
    public void testHorizontalMatch_false() throws Exception {
        when(mMockData.get(0)).thenReturn(Sign.EMPTY);
        when(mMockData.get(1)).thenReturn(Sign.EMPTY);
        when(mMockData.get(2)).thenReturn(Sign.EMPTY);
        when(mMockData.get(3)).thenReturn(Sign.X);
        when(mMockData.get(4)).thenReturn(Sign.O);
        when(mMockData.get(5)).thenReturn(Sign.EMPTY);
        when(mMockData.get(6)).thenReturn(Sign.O);
        when(mMockData.get(7)).thenReturn(Sign.X);
        when(mMockData.get(8)).thenReturn(Sign.X);
        mPresenter.setMockData(mMockData);
        assertFalse(mPresenter.isHorizontalMatch());
    }

    @org.junit.Test
    public void testVerticalMatch_true() throws Exception {
        when(mMockData.get(0)).thenReturn(Sign.X);
        when(mMockData.get(3)).thenReturn(Sign.X);
        when(mMockData.get(6)).thenReturn(Sign.X);
        mPresenter.setMockData(mMockData);
        assertTrue(mPresenter.isVerticalMatch());
    }

    @org.junit.Test
    public void testVerticalMatch_false() throws Exception {
        when(mMockData.get(0)).thenReturn(Sign.EMPTY);
        when(mMockData.get(1)).thenReturn(Sign.EMPTY);
        when(mMockData.get(2)).thenReturn(Sign.EMPTY);
        when(mMockData.get(3)).thenReturn(Sign.X);
        when(mMockData.get(4)).thenReturn(Sign.O);
        when(mMockData.get(5)).thenReturn(Sign.EMPTY);
        when(mMockData.get(6)).thenReturn(Sign.O);
        when(mMockData.get(7)).thenReturn(Sign.X);
        when(mMockData.get(8)).thenReturn(Sign.X);
        mPresenter.setMockData(mMockData);
        assertFalse(mPresenter.isVerticalMatch());
    }

    @org.junit.Test
    public void testDiagonalMatch_true() throws Exception {
        when(mMockData.get(0)).thenReturn(Sign.X);
        when(mMockData.get(4)).thenReturn(Sign.X);
        when(mMockData.get(8)).thenReturn(Sign.X);
        mPresenter.setMockData(mMockData);
        assertTrue(mPresenter.isDiagonalMatch());
    }

    @org.junit.Test
    public void testDiagonalMatch_false() throws Exception {
        when(mMockData.get(2)).thenReturn(Sign.O);
        when(mMockData.get(4)).thenReturn(Sign.X);
        when(mMockData.get(6)).thenReturn(Sign.O);
        mPresenter.setMockData(mMockData);
        assertFalse(mPresenter.isDiagonalMatch());
    }

    @org.junit.Test
    public void testStartOver() throws Exception {
        mPresenter.startOver();
        assertEquals(0, mPresenter.mSystemMoveCount);
        assertEquals(Player.X_PLAYER, mPresenter.getCurrentPlayer());
    }

    @org.junit.Test
    public void testSwitchPlayer() throws Exception
    {
        mPresenter.setCurrentPlayer(Player.X_PLAYER);
        mPresenter.switchPlayer();
        assertEquals(Player.O_PLAYER, mPresenter.getCurrentPlayer());

        mPresenter.setCurrentPlayer(Player.O_PLAYER);
        mPresenter.switchPlayer();
        assertEquals(Player.X_PLAYER, mPresenter.getCurrentPlayer());
        verify(mMockView).showCurrentPlayer(mPresenter.getCurrentPlayer());
    }

    @org.junit.Test
    public void testGetNextMove() throws Exception
    {

    }

    @org.junit.Test
    public void testgetScore() throws Exception
    {

    }

    @org.junit.Test
    public void testUpdateData() throws Exception {

    }

    @org.junit.Test
    public void testMinMax() throws Exception {

    }

    @org.junit.Test
    public void testGetInitialGrid() throws Exception {
        mPresenter.getInitialGrid();
        assertEquals(Sign.EMPTY, mPresenter.getData().get(5));
    }

}