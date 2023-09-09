package cjml;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet implements Runnable {
    //子弹坐标
    private int x;
    private int y;
    //子弹的朝向
    private boolean face_to = true;
    //子弹的图像
    private BufferedImage show = null;
    //创建一个背景类
    private BackGround bg;
    //子弹图像的状态
    private int image_type = 0;
    //子弹移动的线程
    private Thread thread = new Thread(this);


    public Bullet(int x, int y, boolean face_to, BackGround bg) {
        this.x = x;
        this.y = y;
        this.face_to = face_to;
        this.bg = bg;
        show = StaticValue.bullet_R;
        thread.start();
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isFace_to() {
        return face_to;
    }

    public void setFace_to(boolean face_to) {
        this.face_to = face_to;
    }

    public BufferedImage getShow() {
        return show;
    }

    public void setShow(BufferedImage show) {
        this.show = show;
    }

    public BackGround getBg() {
        return bg;
    }

    public void setBg(BackGround bg) {
        this.bg = bg;
    }

    public Rectangle getRec() {
        return new Rectangle(this.x, this.y, 15, 15);
    }

    @Override
    public void run() {
        while (true) {
            if (face_to) {
                this.x -= 10;
                show = StaticValue.bullet_L;
            } else {
                this.x += 10;
                show = StaticValue.bullet_R;
            }
            //遍历所有障碍物
            for (int i = 0; i < bg.getObstacleList().size(); i++) {
                Obstacle ob = bg.getObstacleList().get(i);
                if (this.getRec().intersects(ob.getRec())) {
                    this.y=900;
                    this.bg.getBulletList().remove(this);
                }
            }
            //遍历所有敌人
            for (int i = 0; i < bg.getEnemyList().size(); i++) {
                Enemy e = bg.getEnemyList().get(i);
                if (this.getRec().intersects(e.getRec())) {
                    this.y = 900;
                    this.bg.getBulletList().remove(this);
                    e.death();
                }
            }


            try {
                thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
