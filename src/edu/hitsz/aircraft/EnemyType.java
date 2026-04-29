package edu.hitsz.aircraft;

public enum EnemyType {
    MOB(10), ELITE(20), VETERAN(30), ACE(50), BOSS(100);

    private int score;

    EnemyType(int score){
        this.score = score;
    }

    public int getScore(){
        return score;
    }
}