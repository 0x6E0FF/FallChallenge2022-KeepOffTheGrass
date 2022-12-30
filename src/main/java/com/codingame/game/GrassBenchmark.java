package com.codingame.game;
import java.io.*;

import com.codingame.gameengine.runner.MultiplayerGameRunner;
import com.codingame.gameengine.runner.simulate.GameResult;
import com.google.common.io.Files;

public class GrassBenchmark {

    static final long[] seeds = {
        1222614572458913500l,
    };
    public static void main(String[] args){

        File dir = new File("../benchmark");
        int totalNbGames = 0;
        int totalWin = 0;
        for (long seed : seeds){
            System.out.println("\u001B[0mseed " + seed + ":");
            for (String f : dir.list()) {
                if (f.contains(".cpp")) continue;
                MultiplayerGameRunner gameRunner = new MultiplayerGameRunner();
                gameRunner.setSeed(seed);
                gameRunner.addAgent("../bots/current", "current");
                gameRunner.addAgent("../benchmark/" + f, "x");
                gameRunner.setLeagueLevel(2);
                GameResult res = gameRunner.simulate();
                int myScore = res.scores.get(0);
                int oppScore = res.scores.get(1);

                String title = "current/" + f;
                String summary = String.format("  %1$-40s : %2$3d / %3$3d", title, myScore, oppScore);

                totalNbGames++;
                if (myScore >= oppScore){
                    System.out.println("\u001B[32m" + summary);
                    totalWin++;
                }
                else {
                    System.out.println("\u001B[31m" + summary);
                }

                
                gameRunner = new MultiplayerGameRunner();
                gameRunner.setSeed(seed);
                gameRunner.addAgent("../benchmark/" + f, "x");
                gameRunner.addAgent("../bots/current", "current");
                gameRunner.setLeagueLevel(2);
                res = gameRunner.simulate();
                myScore = res.scores.get(0);
                oppScore = res.scores.get(1);

                title = f + "/current";
                summary = String.format("  %1$-40s : %2$3d / %3$3d", title, myScore, oppScore);

                totalNbGames++;
                if (myScore >= oppScore){
                    System.out.println("\u001B[32m" + summary);
                    totalWin++;
                }
                else {
                    System.out.println("\u001B[31m" + summary);
                }
            }
        }
        float winrate = (float)totalWin * 100 / (float)totalNbGames;
        System.out.println("\u001B[0mWin rate = " + winrate + "%");
    }
}
