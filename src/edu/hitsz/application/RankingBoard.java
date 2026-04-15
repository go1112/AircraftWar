package edu.hitsz.application;

import java.time.LocalDateTime;
import java.util.List;

import edu.hitsz.rank.Difficulty;
import edu.hitsz.rank.PlayRecord;
import edu.hitsz.rank.PlayRecordDao;

public class RankingBoard {
    private final PlayRecordDao recordDao;

    public RankingBoard(PlayRecordDao recordDao) {
        this.recordDao = recordDao;
    }

    /**
     * 在控制台展示指定难度的排行榜
     * 
     * @param difficulty 游戏难度
     */
    public void showRanking(Difficulty difficulty) {
        recordDao.readFromFile(difficulty);
        List<PlayRecord> records = recordDao.getAllPlayRecords(difficulty);

        System.out.println("-----------------------------------------");
        System.out.println("        排行榜（" + difficulty + "）        ");
        System.out.println("-----------------------------------------");
        System.out.println("玩家名         分数              游戏时间");

        for (int i = 0; i < records.size(); i++) {
            System.out.println(records.get(i));
        }

        if (records.isEmpty()) {
            System.out.println("暂无记录");
        }
        System.out.println("-----------------------------------------");
    }

    /**
     * 添加当前玩家游戏记录
     */
    public void addCurRecord(String playerName, int score, Difficulty difficulty) {
        // 创建-》加入内存中的列表
        PlayRecord newPlayRecord = new PlayRecord(score, playerName, LocalDateTime.now(), difficulty);

        recordDao.addRecord(newPlayRecord);
    }

    /**
     * 将各个难度的游戏记录同步到文件中
     */
    public void writeRecordToFile(){
        recordDao.writeToFile(Difficulty.BEGINNER);
        recordDao.writeToFile(Difficulty.BASIC);
        recordDao.writeToFile(Difficulty.INTERMEDIATE);
        recordDao.writeToFile(Difficulty.ADVANCED);
        recordDao.writeToFile(Difficulty.EXPERT);
    }
}
