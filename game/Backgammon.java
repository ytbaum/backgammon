package game;

import java.util.Random;
import board.*;

public class Backgammon
{

    private int[] dice = {0, 0};

    public static void main(String[] args)
    {
        Backgammon bg = new Backgammon();
        bg.test();
    }

    public void test()
    {
        this.rollDice();
        System.out.println(this.getDiceString());

        return;
    }

    private void rollDice()
    {
        Random rand = new Random();
        for (int i = 0; i < this.dice.length; i++) {
            this.dice[i] = rand.nextInt(6) + 1;
        }

        return;
    }

    private String getDiceString()
    {
        String output = Integer.toString(this.dice[0]) + " " + Integer.toString(this.dice[1]);

        return output;
    }
}



