package com.example.game;

import static com.example.game.GameView.ScreenRatioY;
import static com.example.game.GameView.screenRatioX;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Flight {
    public boolean isGoingUp = false;
    public static GameView gameView;

    int toShoot = 0;
    int x, y, width, height;
    int BoosterCounter = 0;
    int shootCounter = 1;
    Bitmap flight1, flight2, bullet, bullet1, bullet3,bullet2, destroyed;


    Flight(GameView gameView, int ScreenY, Resources resources) {

        this.gameView =gameView;
        //spaceship images adding
        flight1 = BitmapFactory.decodeResource(resources, R.drawable.spaceship2);
        flight2 = BitmapFactory.decodeResource(resources, R.drawable.spaceship2rgb);


        width = flight1.getWidth();
        height = flight1.getHeight();

        width /= 4;
        height /= 4;


        width = (int) (width * screenRatioX);
        height = (int) (height * ScreenRatioY);

        flight1 = Bitmap.createScaledBitmap(flight1, width, height, false);
        flight2 = Bitmap.createScaledBitmap(flight2, width, height, false);



        //shooting images adding
        bullet = BitmapFactory.decodeResource(resources, R.drawable.bullet);
        bullet1 = BitmapFactory.decodeResource(resources, R.drawable.bullet1);
        bullet2 = BitmapFactory.decodeResource(resources, R.drawable.bullet2);
        bullet3 = BitmapFactory.decodeResource(resources, R.drawable.bullet3);

        bullet = Bitmap.createScaledBitmap(bullet, width, height, false);
        bullet1 = Bitmap.createScaledBitmap(bullet1, width, height, false);
        bullet2 = Bitmap.createScaledBitmap(bullet2, width, height, false);
        bullet3 = Bitmap.createScaledBitmap(bullet3, width, height, false);

        destroyed = BitmapFactory.decodeResource(resources, R.drawable.destroyed);
        destroyed = Bitmap.createScaledBitmap(destroyed, width, height, false);

        y = ScreenY / 2;
        x = (int) (64*screenRatioX);

    }

    Bitmap getFlight() {

        if (toShoot != 0) {
            if (shootCounter == 1) {
                shootCounter++;
                return flight1;
            }
            if (shootCounter == 2) {
                shootCounter++;
                return flight2;
            }
            if (shootCounter == 3) {
                shootCounter++;
                return flight1;
            }
            shootCounter = 1;
            toShoot--;

            gameView.newBullet();

            return flight2;
        }

        if (BoosterCounter == 0) {
            BoosterCounter++;
            return flight1;
        } else {
            BoosterCounter--;
            return flight2;
        }
    }
    Rect collision()
    {
        return  new Rect(x,y,x+width,y+height);
    }

    Bitmap Dead(){
        return  destroyed;
    }

}
