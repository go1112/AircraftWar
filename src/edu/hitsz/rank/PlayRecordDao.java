package edu.hitsz.rank;

import java.util.List;

public interface PlayRecordDao {
    /**
     * 查找所有玩家的得分记录
     */
    List<PlayRecord> getAllScores();

    /**
     * 根据玩家名删除对应的所有玩家记录
     * 
     * @return 返回true表示删除成功 false表示删除失败
     */
    boolean deleteAllRecords(String playerName);

    /**
     * 根据玩家名得到该玩家所有的玩家记录
     */
    List<PlayRecord> getAllPlayRecords(String playerName);

    /**
     * 根据难度类型得到对应的所有玩家记录
     */
    List<PlayRecord> getAllPlayRecords(Difficulty difficulty);

    /**
     * 从文件中读取数据
     */
    void readFromFile();

    /**
     * 将数据写入文件中
     */
    void writeToFile();
}
