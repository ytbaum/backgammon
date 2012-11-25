package game;

import java.util.Random;
import java.util.Arrays;
import board.*;

public class Backgammon
{

    private int[] dice = {0, 0};

    public static final int DIE_NUM_SIDES = 6;

    public static void main(String[] args)
    {
        Backgammon bg = new Backgammon();
        bg.test();
    }

    public void test()
    {
        this.rollDice();
        System.out.println(this.getDiceString());

        int[] availableMoves = this.getAvailableMoves();
        System.out.println(Arrays.toString(availableMoves));

        return;
    }

    private void rollDice()
    {
        Random rand = new Random();
        for (int i = 0; i < this.dice.length; i++) {
            this.dice[i] = rand.nextInt(DIE_NUM_SIDES) + 1;
        }

        return;
    }

    private String getDiceString()
    {
        String output = Integer.toString(this.dice[0]) + " " + Integer.toString(this.dice[1]);

        return output;
    }

    private int[] getAvailableMoves()
    {
        int[] availableMoves = new int[DIE_NUM_SIDES];

        // dice are 1-indexed; convert them to 0-indexed
        int[] zDice = new int[2];
        zDice[0] = this.dice[0] - 1;
        zDice[1] = this.dice[1] - 1;

        for (int i = 0; i < DIE_NUM_SIDES; i++) {
            if (i == zDice[0] && i == zDice[1]) {
                availableMoves[i] = 4;
            } else if (i == zDice[0] || i == zDice[1]) {
                availableMoves[i] = 1;
            } else {
                availableMoves[i] = 0;
            }
        }

        return availableMoves;
    }
}



