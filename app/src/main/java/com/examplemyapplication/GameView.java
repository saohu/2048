package com.examplemyapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 007 on 2015/10/27.
 */
public class GameView extends GridLayout{
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }
    private void initGameView(){
        setColumnCount(4);
        setBackgroundColor(0xffbbad00);
        setOnTouchListener(new OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                swipeLeft();
                            } else if (offsetX > 5) {
                                swipRight();
                            }
                        } else {
                            if (offsetY < -5) {
                                swipeUp();
                            } else if (offsetY > 5) {
                                swipeDown();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }
    protected void onSizeChanged(int w,int h,int oldw,int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = (Math.min(w,h) - 10)/4;
        addCards(cardWidth, cardWidth);
        startGame();
    }
    private void addCards(int cardWidth,int cardHeight){
        Card c;
        for (int y = 0;y<4;y++){
            for (int x =0;x<4;x++){
                c = new Card(getContext());
               c.setNum(0);
                addView(c,cardWidth,cardHeight);
                cardsmap[x][y] = c;
            }
        }
    }
    private void startGame(){
        MainActivity.getMainActivity().clearScore();
        for (int y = 0;y<4;y++){
            for (int x =0;x<4;x++){
                cardsmap[x][y].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }
    private void addRandomNum(){
        emptyPoint.clear();
        for (int y = 0;y<4;y++){
            for (int x= 0;x<4;x++){
                if (cardsmap[x][y].getNum() <=0){
                    emptyPoint.add(new Point(x,y));
                }
            }
        }
        Point p = emptyPoint.remove((int)(Math.random()*emptyPoint.size()));
        cardsmap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
    }
    private void swipeLeft(){
        boolean merge = false;
        for (int y =0;y<4;y++){
            for (int x =0;x<4;x++){
                for (int x1 = x+1;x1<4;x1++){
                    if (cardsmap[x][y].getNum() > 0){
                        if (cardsmap[x][y].getNum() <=0){
                            cardsmap[x][y].setNum(cardsmap[x1][y].getNum());
                            cardsmap[x1][y].setNum(0);
                            x--;
                           merge =true;
                        }else if (cardsmap[x][y].equals(cardsmap[x1][y])){
                            cardsmap[x][y].setNum(cardsmap[x][y].getNum()*2);
                            cardsmap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsmap[x][y].getNum());
                         merge =true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            checkfinish();
        }

    }
    private void swipRight(){
        boolean merge = false;
        for (int y =0;y<4;y++){
            for (int x =3;x>=0;x--){
                for (int x1 = x-1;x1>=4;x1--){
                    if (cardsmap[x][y].getNum() > 0){
                        if (cardsmap[x][y].getNum() <=0){
                            cardsmap[x][y].setNum(cardsmap[x1][y].getNum());
                            cardsmap[x1][y].setNum(0);
                            x++;
                            merge =true;

                        }else if (cardsmap[x][y].equals(cardsmap[x1][y])){
                            cardsmap[x][y].setNum(cardsmap[x][y].getNum()*2);
                            cardsmap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsmap[x][y].getNum());
                           merge =true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum(); checkfinish();
        }


    }
    private void swipeUp(){
boolean merge = false;
        for (int x =0;x<4;x++){
            for (int y =0;y<4;y++){
                for (int y1 = y+1;y1<4;y1++){
                    if (cardsmap[x][y].getNum() > 0){
                        if (cardsmap[x][y].getNum() <=0){
                            cardsmap[x][y].setNum(cardsmap[x][y1].getNum());
                            cardsmap[x][y1].setNum(0);
                           y--;
               merge =true;
                        }else if (cardsmap[x][y].equals(cardsmap[x][y1])){
                            cardsmap[x][y].setNum(cardsmap[x][y].getNum()*2);
                            cardsmap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsmap[x][y].getNum());
                         merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum(); checkfinish();
        }

    }
    private void swipeDown(){
        boolean merge = false;
        for (int x =0;x<4;x++){
            for (int y =3;y>=0;y--){
                for (int y1 = y-1;y1>=0;y1--){
                    if (cardsmap[x][y].getNum() > 0){
                        if (cardsmap[x][y].getNum() <=0){
                            cardsmap[x][y].setNum(cardsmap[x][y1].getNum());
                            cardsmap[x][y1].setNum(0);
                            y++;
                            merge =true;

                        }else if (cardsmap[x][y].equals(cardsmap[x][y1])){
                            cardsmap[x][y].setNum(cardsmap[x][y].getNum()*2);
                            cardsmap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsmap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum(); checkfinish();
        }

    }
    private void checkfinish(){
        boolean finish = true;
        all:
        for (int y = 0;y<4;y++){
            for (int x=0 ;x<4;x++){
                if (cardsmap[x][y].getNum() == 0
                        ||(x>0&&cardsmap[x][y].equals(cardsmap[x-1][y]))
                        ||(x<0&&cardsmap[x][y].equals(cardsmap[x+1][y]))
                        ||(y>0&&cardsmap[x][y].equals(cardsmap[x][y-1]))
                        ||(y<3&&cardsmap[x][y].equals(cardsmap[x][y+1]))){
                   finish =false;
                    break all;
                }
            }
        }
        if (finish){
            new AlertDialog.Builder(getContext()).setTitle("您好").setMessage("游戏结束").setPositiveButton("重来", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }
    }
    private Card[][] cardsmap = new Card[4][4];
    private List<Point> emptyPoint = new ArrayList<>();
}
