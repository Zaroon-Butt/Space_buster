package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    public Thread Tobj;
    boolean playing;
    boolean Gameover = false;


    private BackGround background1, background2;
    int screenX;
    int screenY;
    int score = 0;
    public Paint Paint;
    List<Bullet> bullets;
    public static float screenRatioX;
    public static float ScreenRatioY;
    private Flight flight;
    private enemyship[] enemies;
    private Random random;


    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        ScreenRatioY = 1080f / screenY;

        background1 = new BackGround(screenX, screenY, getResources());
        background2 = new BackGround(screenX, screenY, getResources());

        flight = new Flight(this, screenY, getResources());

        bullets = new ArrayList<>();

        background2.x = screenX;

        Paint = new Paint();

        enemies = new enemyship[4];
        for (int i = 0; i < 4; i++) {
            enemyship enemy = new enemyship(getResources());
            enemies[i] = enemy;
        }

        random = new Random();
    }


    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            sleep();
        }
    }


    public void update() {
        background1.x -= 10 * screenRatioX;
        background2.x -= 10 * screenRatioX;

        if (background1.x + background1.Background.getWidth() < 0) {
            background1.x = screenX;
        }
        if (background2.x + background2.Background.getWidth() < 0) {
            background2.x = screenX;
        }
        if (flight.isGoingUp) {
            flight.y -= 30 * ScreenRatioY;
        } else {
            flight.y += 30 * ScreenRatioY;
        }
        if (flight.y < 0) {
            flight.y = 0;
        }
        if (flight.y >= screenY - flight.height) {
            flight.y = screenY - flight.height;
        }

        List<Bullet> trash = new ArrayList<>();
        for (Bullet bullet : bullets) {
            if (bullet.x > screenRatioX) {
                trash.add(bullet);
            } else {
                bullet.x += 50 * screenRatioX;
            }

            for (enemyship enemy : enemies) {
                if (Rect.intersects(enemy.collision(), bullet.collision())) {
                    enemy.x = -500;
                    bullet.x = screenX + 500;
                    enemy.wasShot = true;
                }
            }
        }


        for (Bullet bullet : trash) {
            bullets.remove(bullet);
        }
        for (enemyship enemy : enemies) {
            enemy.x -= enemy.speed;
//put int and check
            if ((enemy.x + enemy.width) < 0) {

                if (!enemy.wasShot) {
                    Gameover = true;
                }

                int bound = (int) (30 * screenRatioX);
                enemy.speed = random.nextInt(bound);


                if (enemy.speed < 10 * screenRatioX) {
                    enemy.speed = (int) (10 * screenRatioX);
                }

                enemy.x = screenX;
                enemy.y = random.nextInt(screenY - enemy.height);

                enemy.wasShot = false;


            }
            if (Rect.intersects(enemy.collision(), flight.collision())) {
                Gameover = true;
                return;
            }
        }


    }

    public void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.Background, background1.x, background1.y, Paint);
            canvas.drawBitmap(background2.Background, background2.x, background2.y, Paint);



            if (Gameover) {
                playing = false;
                canvas.drawBitmap(flight.Dead(), flight.x, flight.y, Paint);
                getHolder().unlockCanvasAndPost(canvas);
                return;
            }



            for (enemyship enemy : enemies) {
                canvas.drawBitmap(enemy.getenemy(), enemy.x, enemy.y, Paint);
            }

            canvas.drawBitmap(flight.getFlight(), flight.x, flight.y, Paint);


            for (Bullet bullet : bullets)
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, Paint);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }


    public void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void resume() {
        playing = true;
        Tobj = new Thread(this);
        Tobj.start();

    }

    public void pause() {
        try {
            playing = false;
            Tobj.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2) {
                    flight.isGoingUp = true;
                }
                break;
            //right side of the screen
            case MotionEvent.ACTION_UP:
                flight.isGoingUp = false;
                if (event.getX() > screenX / 2) {
                    flight.toShoot++;
                    break;
                }
        }
        return true;
    }

    public void newBullet() {
        Bullet bullet = new Bullet(getResources());
        bullet.x = flight.x;//+ flight.width
        bullet.y = flight.y;
        bullet.x += 50 * screenRatioX;//+ (flight.height / 2)
        bullets.add(bullet);
    }


}
