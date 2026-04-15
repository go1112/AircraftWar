package edu.hitsz.rank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PlayRecordDaoImpl implements PlayRecordDao {
    // 位于内存中的游戏记录表
    private List<PlayRecord> records;

    

    public PlayRecordDaoImpl(List<PlayRecord> records) {
        this.records = new ArrayList<>();
    }

    @Override
    public List<PlayRecord> getAllScores() {
        return records;
    }

    @Override
    public void deleteAllRecords(String playerName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllRecords'");
    }

    @Override
    public List<PlayRecord> getAllPlayRecords(String playerName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllPlayRecords'");
    }

    @Override
    public List<PlayRecord> getAllPlayRecords(Difficulty difficulty) {
        List<PlayRecord> result = new ArrayList<>();
        for(int i = 0;i < records.size();i++){
            PlayRecord record = records.get(i);
            if (record.getDifficulty().equals(difficulty)) {
                result.add(record);
            }
        }
        Collections.sort(result);
        return null;
    }

    @Override
    public void readFromFile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readFromFile'");
    }

    @Override
    public void writeToFile(Difficulty difficulty) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeToFile'");
    }

    @Override
    public void addRecord(PlayRecord record) {
        records.add(record); // 在末尾进行添加
    }

}
