package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.game.R;

public class BackGround {
     int x=0,y=0;
     Bitmap Background;

     BackGround(int screenX, int screenY, Resources res)
     {
         Background= BitmapFactory.decodeResource(res, R.drawable.background);
         Background=Bitmap.createScaledBitmap(Background,screenX,screenY,false);
     }
}
