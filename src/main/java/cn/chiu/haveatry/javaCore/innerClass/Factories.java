package cn.chiu.haveatry.javaCore.innerClass;

/**
 * Created by yeachiu on 2019/6/28.
 *
 * 使用匿名内部类的工厂方法
 *
 */
/*
    银河系上有颗行星，叫做地球。地球上有个女儿奴总裁，称为大BOSS。
    大BOSS为了讨小公主开心，创建了梦工厂来专门负责生产小公主要玩的游戏玩具。BOSS甚至还自己设计了几款游戏，并把游戏工具的设计图交给了梦工厂来生产。
 */

interface Game {
    //玩游戏
    boolean move();
}
interface GameFactory {
    //梦工厂生产游戏工具
    Game getGame();
}

//BOSS设计出Checkers游戏，并把游戏工具设计图交给梦工厂生产
class Checkers implements Game {
    private Checkers() {}
    private int moves = 0;
    private static final int MOVES = 3;
    @Override
    public boolean move() {
        System.out.println("Checkers move" + moves);
        return ++moves != MOVES;
    }
    //只需要创建单一的工厂对象，使用static
    public static GameFactory factory = new GameFactory() {
        @Override
        public Game getGame() {
            return new Checkers();
        }
    };
}

//BOSS设计出Chess游戏，并把游戏工具设计图交给梦工厂生产
class Chess implements Game {
    private Chess() {}
    private  int moves = 0;
    private static final int MOVES = 7;
    @Override
    public boolean move() {
        System.out.println("Chess move" + moves);
        return ++moves != MOVES;
    }
    public static GameFactory factory = new GameFactory() {
        @Override
        public Game getGame() {
            return new Chess();
        }
    };
}
public class Factories {
    //好无聊啊，找个游戏来玩吧
    public static void playGame(GameFactory factory) {
        //决定玩哪个游戏
        Game s = factory.getGame();
        //开始玩游戏
        while (s.move())
            ;
    }

    public static void main(String[] args) {
        // 大BOSS：宝贝，Daddy让人做了这几种游戏(Checkers、Chess)，你看看玩哪个
        // 小公主：哇Daddy,全部都好好玩的样子，我都要玩
        // 大BOSS: 好好 我家宝贝高兴就行 我们一个一个玩
        playGame(Checkers.factory);
        playGame(Chess.factory);
    }

}

