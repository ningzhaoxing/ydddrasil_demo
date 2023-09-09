package cjml;

import javazoom.jl.player.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class DaoJv implements Runnable{
    //定义道具坐标
    private int x,y;
    //定义道具类型
    private int type;
    //定义道具显示图片
    private BufferedImage show=null;
    //定义线程
    private Thread thread = new Thread(this);
    //定义一个背景类
    private BackGround bg;
    //显示图片的状态
    private int image_type=0;

    public DaoJv(){

    }

    public DaoJv(int x, int y, int type, BackGround bg){
        this.x=x;
        this.y=y;
        this.type=type;
        this.bg=bg;
        show=StaticValue.bigD;
        thread.start();
    }

    public void eat(){
        bg.getDaoJvList().remove(this);
        new Thread("eatGlory") {
            public void run() {
                String filename = "src//Music//glory.mp3";
                try {
                    BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(filename));
                    Player player = new Player(buffer);
                    player.play();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }.start();
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getImage_type() {
        return image_type;
    }

    public void setImage_type(int image_type) {
        this.image_type = image_type;
    }
    public Rectangle getRec(){
        return new Rectangle(this.x,this.y,35,35);
    }

    @Override
    public void run() {
        while(true){
            //是否是变大道具
            if(this.type==0){
                show=StaticValue.bigD;
            }
            //如果是跳跃提升道具
            if(this.type==1){
                show=StaticValue.jumpD;
            }
            //如果是金币，对金币进行动态化
            if(this.type==2){
                switch (image_type) {
                    case 0 -> image_type = 1;
                    case 1 -> image_type = 2;
                    case 2 -> image_type = 3;
                    case 3 -> image_type = 4;
                    case 4 -> image_type = 5;
                    case 5 -> image_type = 0;
                }
                show=StaticValue.coins.get(image_type);
            }

            try {
                thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
