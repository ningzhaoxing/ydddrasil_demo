package cjml;

import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class MyFrame extends JFrame implements KeyListener, Runnable {
    //用于存储所有的背景
    private List<BackGround> allBg;
    //用于存储当前的背景
    private BackGround nowBg;
    //用于双缓存
    private Image offScreenImage = null;
    //马里奥对象
    private Mario mario = new Mario();
    //游戏暂停
    public static boolean isStart = false;
    //定义一个线程判断游戏进程
    private Thread thread = new Thread(this);

    private boolean backMusicFlag=false;

    public MyFrame() {
        //设置窗口的大小为800 * 600
        this.setSize(800, 600);
        //设置窗口居中显示
        this.setLocationRelativeTo(null);
        //设置窗口的可见性
        this.setVisible(true);
        //设置点击窗口上的关闭键,结束程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口大小不可变
        this.setResizable(false);
        //向窗口对象添加键盘监听器
        this.addKeyListener(this);
        //设置窗口名称
        this.setTitle("超级玛丽");
        init();
        //绘制图像
        repaint();
        thread.start();
//        new Thread("background") {
//            public void run() {
//                String filename = "src//Music//music.wav";
//                try {
//                    sleep(100);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                try {
//                    BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(filename));
//                    Player player = new Player(buffer);
//                    player.play();
//                } catch (Exception e) {
//                    System.out.println(e);
//                }
//            }
//        }.start();

    }


    public void init() {
        nowBg = new BackGround();
        allBg = new ArrayList<>();
        //初始化图片
        StaticValue.init();
        isStart = false;
        //初始化马里奥
        mario = new Mario(0, 355, this.getHeight(), this.getWidth());
        //创建全部的场景
        for (int i = 1; i <= 3; i++) {
            allBg.add(new BackGround(i, i == 3 ? true : false));
        }
        //将第一个场景设置为当前场景
        nowBg = allBg.get(0);
        mario.setBackGround(nowBg);
    }


    @Override
    public void paint(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = createImage(800, 600);
        }

        Graphics graphics = offScreenImage.getGraphics();
        if (!isStart) {
            graphics.drawImage(StaticValue.isStart, 0, 0, this);
        } else {
            graphics.fillRect(0, 0, 800, 600);

            //绘制背景
            graphics.drawImage(nowBg.getBgImage(), 0, 0, this);

            //绘制敌人
            for (Enemy e : nowBg.getEnemyList()) {
                graphics.drawImage(e.getShow(), e.getX(), e.getY(), this);
            }

            //绘制障碍物
            List<Obstacle> obstacleList = nowBg.getObstacleList();
            for (int i = 0; i < obstacleList.size(); i++) {
                Obstacle ob = obstacleList.get(i);
                graphics.drawImage(ob.getShow(), ob.getX(), ob.getY(), this);
            }

            //绘制城堡
            graphics.drawImage(nowBg.getTower(), 620, 270, this);

            //绘制旗杆
            graphics.drawImage(nowBg.getGan(), 500, 220, this);

            //绘制马里奥
            if (!mario.isBig()) {
                graphics.drawImage(mario.getShow(), mario.getX(), mario.getY(), 40, 25, this);
            } else {
                graphics.drawImage(mario.getShow(), mario.getX(), mario.getY() - 15, 50, 40, this);
            }

            //绘制子弹
            for (Bullet bullet : nowBg.getBulletList()) {
                graphics.drawImage(bullet.getShow(), bullet.getX() + 5, bullet.getY() + 7, this);
            }
            //绘制道具
            for (DaoJv daoJv : nowBg.getDaoJvList()) {
                graphics.drawImage(daoJv.getShow(), daoJv.getX(), daoJv.getY(), this);
            }

            //添加分数
            Color c = graphics.getColor();
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("黑体", Font.BOLD, 25));
            graphics.drawString("当前的分数为: " + mario.getScore(), 300, 100);
            graphics.setColor(c);
        }

        //将图像绘制到窗口中
        g.drawImage(offScreenImage, 0, 0, this);
    }

    public static void main(String[] args) {
       new MyFrame();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //当键盘按下按键时调用
    @Override
    public void keyPressed(KeyEvent e) {
        //向右移动
        if (e.getKeyCode() == 39) {
            mario.rightMove();
        }
        //向左移动
        if (e.getKeyCode() == 37) {
            mario.leftMove();
        }
        //跳跃
        if (e.getKeyCode() == 38) {
            mario.jump();
        }
        //发射子弹
        if (e.getKeyCode() == 32) {
            mario.fire();
        }
        //游戏暂停
        if (e.getKeyCode() == 113) {
            isStart = !isStart;
        }
    }

    //当键盘松开按键时调用
    @Override
    public void keyReleased(KeyEvent e) {
        //想左停止
        if (e.getKeyCode() == 37) {
            mario.leftStop();
        }
        //向右停止
        if (e.getKeyCode() == 39) {
            mario.rightStop();
        }
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isStart) {
                if (mario.getX() >= 775) {
                    nowBg = allBg.get(nowBg.getSort());
                    mario.setBackGround(nowBg);
                    mario.setX(10);
                    mario.setY(355);
                }

                //判断马里奥是否死亡
                if (mario.isDeath()) {
                    new Thread("die") {
                        public void run() {
                            String filename = "src//Music//die.mp3";
                            try {
                                sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(filename));
                                Player player = new Player(buffer);
                                player.play();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    }.start();
                    int option = JOptionPane.showConfirmDialog(this, "游戏失败，是否重新开始游戏？", "提示", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        init();
                    } else {
                        System.exit(0);
                    }
                }

                //判断游戏是否结束
                if (mario.isOK()) {
                    new Thread("ok") {
                        public void run() {
                            String filename = "src//Music//gameOver.mp3";
                            try {
                                sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(filename));
                                Player player = new Player(buffer);
                                player.play();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    }.start();
                    int option = JOptionPane.showConfirmDialog(this, "恭喜你，通关成功!!!是否重新开始游戏？", "提示", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        init();
                    } else {
                        System.exit(0);
                    }
                }else{
                    if(backMusicFlag){
                        continue;
                    }
                    new Thread("background") {
                        public void run() {
                            String filename = "src//Music//music.wav";
                            try {
                                sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(filename));
                                Player player = new Player(buffer);
                                player.play();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    }.start();
                    backMusicFlag=true;
                }
            }
        }
    }
}
