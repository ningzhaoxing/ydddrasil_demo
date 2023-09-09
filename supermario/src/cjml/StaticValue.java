package cjml;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaticValue {
    //背景
    public static BufferedImage bg = null;
    public static BufferedImage bg2 = null;
    //马里奥向左跳跃
    public static BufferedImage jump_L = null;
    //马里奥向右跳跃
    public static BufferedImage jump_R = null;
    //马里奥向左站立
    public static BufferedImage stand_L = null;
    //马里奥向右站立
    public static BufferedImage stand_R = null;
    //城堡
    public static BufferedImage tower = null;
    //BufferedImage=null
    //旗杆
    public static BufferedImage gan = null;
    //障碍物
    public static List<BufferedImage> obstacle = new ArrayList<>();
    //金币
    public static List<BufferedImage> coins = new ArrayList<>();
    //变大道具
    public static BufferedImage bigD = null;
    //跳跃提升道具
    public static BufferedImage jumpD = null;

    //马里奥向左跑
    public static List<BufferedImage> run_L = new ArrayList<>();
    //马里奥向右跑
    public static List<BufferedImage> run_R = new ArrayList<>();
    //蘑菇敌人
    public static List<BufferedImage> mogu = new ArrayList<>();
    //食人花敌人
    public static List<BufferedImage> flower = new ArrayList<>();
    //子弹
    public static BufferedImage bullet_R = null;
    public static BufferedImage bullet_L = null;
    //游戏暂停界面
    public static BufferedImage isStart=null;

    //路径的前缀,方便后续调用
    public static String path ="src//images//";

    //初始化方法
    public static void init() {
        try {
            //加载背景图片
            bg = ImageIO.read(new File(path + "bg.png"));
            bg2 = ImageIO.read(new File(path + "bg2.png"));
            //加载马里奥向左站立
            stand_L = ImageIO.read(new File(path + "s_mario_stand_L.png"));
            //加载马里奥向右站立
            stand_R = ImageIO.read(new File(path + "s_mario_stand_R.png"));
            //加载城堡
            tower = ImageIO.read(new File(path + "tower.png"));
            //加载旗杆
            gan = ImageIO.read(new File(path + "gan.png"));
            //加载马里奥向左跳跃
            jump_L = ImageIO.read(new File(path + "s_mario_jump1_L.png"));
            //加载马里奥向右跳跃
            jump_R = ImageIO.read(new File(path + "s_mario_jump1_R.png"));
            //加载子弹
            bullet_L = ImageIO.read(new File(path + "bullet1_left.png"));
            bullet_R = ImageIO.read(new File(path + "bullet1_right.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        //加载马里奥向左跑
        for (int i = 1; i <= 2; i++) {
            try {
                run_L.add(ImageIO.read(new File(path + "s_mario_run" + i + "_L.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //加载马里奥向右跑
        for (int i = 1; i <= 2; i++) {
            try {
                run_R.add(ImageIO.read(new File(path + "s_mario_run" + i + "_R.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            //加载障碍物
            obstacle.add(ImageIO.read(new File(path + "brick.png")));
            obstacle.add(ImageIO.read(new File(path + "soil_up.png")));
            obstacle.add(ImageIO.read(new File(path + "soil_base.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //加载水管
        for (int i = 1; i <= 4; i++) {
            try {
                obstacle.add(ImageIO.read(new File(path + "pipe" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //加载不可破坏的砖块和旗子
        try {
            obstacle.add(ImageIO.read(new File(path + "brick2.png")));
            obstacle.add(ImageIO.read(new File(path + "flag.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //加载特殊物品

        try {
            obstacle.add(ImageIO.read(new File(path + "danjia.png")));
            obstacle.add(ImageIO.read(new File(path + "wenhaofangkuai.jpg")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //加载蘑菇敌人
        for (int i = 1; i <= 3; i++) {
            try {
                mogu.add(ImageIO.read(new File(path + "fungus" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //加载食人花敌人
        for (int i = 1; i <= 2; i++) {
            try {
                flower.add(ImageIO.read(new File(path + "flower1." + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //加载变大道具
        try {
            bigD = ImageIO.read(new File(path + "big.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //加载跳跃提升道具
        try {
            jumpD = ImageIO.read(new File(path + "jump.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //记载金币道具
        for (int i = 1; i <= 6; i++) {
            try {
                coins.add(ImageIO.read(new File(path + "coin" + i + ".png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //加载界面
        try {
            isStart=ImageIO.read(new File(path+"start.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
