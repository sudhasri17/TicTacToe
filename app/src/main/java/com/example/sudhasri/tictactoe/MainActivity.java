package com.example.sudhasri.tictactoe;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.sudhasri.tictactoe.data.DifficultyLevel;
import com.example.sudhasri.tictactoe.data.Player;

import java.util.ArrayList;


/**
 * Tic Tac Toe
 */
public class MainActivity extends AppCompatActivity implements ViewPresenterContract.View{

    private RecyclerView mRecyclerView;
    private GridAdapter mAdapter;
    private ViewPresenterContract.Presenter mPresenter;
    private AlertDialog mDialog;
    private TextView mCurrentPlayerText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.grid);
        mCurrentPlayerText = (TextView) findViewById(R.id.current_player);
        mPresenter = new LogicPresenter(this);

        mAdapter = new GridAdapter(MainActivity.this, mPresenter.getInitialGrid(), mPresenter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setFadingEdgeLength(0);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Utils.showPlayModeDialog(MainActivity.this, new Utils.DifficultySelectedListener() {
                            @Override
                            public void onDifficultySelected(DifficultyLevel difficultyLevel) {
                                mPresenter.setDifficultyLevel(difficultyLevel);

                                showChoosePlayerDialog();
                            }
                        });
                    }
                }, 300
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void showCurrentPlayer(Player player) {
        mCurrentPlayerText.setText(player.getPlayerSign().getValue());
    }

    @Override
    public void showCurrentMove(int position) {
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void showWin(boolean userWon, ArrayList<Integer> winningGrid) {
        final boolean _userWon = userWon;
        mAdapter.setWinningGrids(winningGrid);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                String title, message;

                if (_userWon)
                {
                    title = getString(R.string.you_won);
                    message = getString(R.string.won_message);
                }
                else
                {
                    title = getString(R.string.you_lost);
                    message = getString(R.string.lost_message);
                }

                mDialog = Utils.showWinDialog(MainActivity.this, title, message, new Utils.DialogClickInterface() {
                    @Override
                    public void onFirstButtonClick() {
                        mPresenter.startOver();
                    }
                });
            }
        }, 500);
    }

    private void showChoosePlayerDialog()
    {
        mDialog = Utils.showChoosePlayerDialog(MainActivity.this, new Utils.ChoiceClickListener() {
            @Override
            public void onSecondButtonClick() {
                mPresenter.setSystemPlayer(Player.X_PLAYER);
                mPresenter.startSystemMove();
            }

            @Override
            public void onCancelClick(DialogInterface dialog) {
                dialog.dismiss();
                finish();
            }

            @Override
            public void onFirstButtonClick() {
                mPresenter.setSystemPlayer(Player.O_PLAYER);
            }
        });
    }

    @Override
    public void initialise() {
        mAdapter.setData(mPresenter.getInitialGrid());
        mAdapter.setWinningGrids(new ArrayList<Integer>());
        mAdapter.notifyDataSetChanged();
        Utils.showPlayModeDialog(MainActivity.this, new Utils.DifficultySelectedListener() {
            @Override
            public void onDifficultySelected(DifficultyLevel difficultyLevel) {
                mPresenter.setDifficultyLevel(difficultyLevel);

                showChoosePlayerDialog();
            }
        });
    }

    @Override
    public void showDraw() {
        mDialog = Utils.showWinDialog(MainActivity.this, getString(R.string.match_draw), getString(R.string.draw_msg), new Utils.DialogClickInterface() {
            @Override
            public void onFirstButtonClick() {
                mPresenter.startOver();
            }
        });
    }
}