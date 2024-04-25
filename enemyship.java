package com.example.game;

import static com.example.game.GameView.ScreenRatioY;
import static com.example.game.GameView.screenRatioX;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.game.R;

public class enemyship {
    public int speed=10;
    public boolean wasShot = true;
    int x = 0, y;
    int width, height;
    Bitmap enemy1, enemy2, enemy3, enemy4;
    int enemyCounter = 1;

    enemyship(Resources resources) {

        enemy1 = BitmapFactory.decodeResource(resources, R.drawable.enemy);
        enemy2 = BitmapFactory.decodeResource(resources, R.drawable.enemy2);
        enemy3 = BitmapFactory.decodeResource(resources, R.drawable.enemy);
        enemy4 = BitmapFactory.decodeResource(resources, R.drawable.enemy2);

        width = enemy1.getWidth();
        height = enemy1.getHeight();

        width /= 6;
        height /= 6;

        width = (int) (width * screenRatioX);
        height = (int) (height * ScreenRatioY);

        enemy1 = Bitmap.createScaledBitmap(enemy1, width, height, false);
        enemy2 = Bitmap.createScaledBitmap(enemy2, width, height, false);
        enemy3 = Bitmap.createScaledBitmap(enemy3, width, height, false);
        enemy4 = Bitmap.createScaledBitmap(enemy4, width, height, false);

        y = -height;

    }

    Bitmap getenemy() {

        if (enemyCounter == 1) {
            enemyCounter++;
            return enemy1;
        }
        if (enemyCounter == 2) {
            enemyCounter++;
            return enemy2;
        }
        if (enemyCounter == 3) {
            enemyCounter++;
            return enemy1;
        }


        enemyCounter = 1;
        return enemy4;
    }

    Rect collision() {
        return new Rect(x, y, x + width, y + height);
    }
}
