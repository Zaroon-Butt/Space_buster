package com.example.game;

import static com.example.game.GameView.ScreenRatioY;
import static com.example.game.GameView.screenRatioX;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Bullet {
    int x,y,Width,Height;
    Bitmap bullet;
    Bullet(Resources resources){
        bullet= BitmapFactory.decodeResource(resources,R.drawable.bullet);

        Width= bullet.getWidth();
        Height= bullet.getHeight();

        Width/=4;
        Height/=4;

        Width = (int) (Width * screenRatioX);
        Height = (int) (Height * ScreenRatioY);

        bullet=Bitmap.createScaledBitmap(bullet, Width,Height,false);


    }
    Rect collision()
    {
        return  new Rect(x,y,x+Width,y+Height);

    }


}
