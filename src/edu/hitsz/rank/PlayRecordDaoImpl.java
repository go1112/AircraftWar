package edu.hitsz.rank;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PlayRecordDaoImpl implements PlayRecordDao {
    // 位于内存中的游戏记录表
    private List<PlayRecord> records;

    public PlayRecordDaoImpl(List<PlayRecord> records) {
        this.records = records;
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
        List<PlayRecord> result = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            PlayRecord record = records.get(i);
            if (record.getName().equals(playerName)) {
                result.add(record);
            }
        }
        Collections.sort(result);
        return result;
    }

    @Override
    public List<PlayRecord> getAllPlayRecords(Difficulty difficulty) {
        List<PlayRecord> result = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            PlayRecord record = records.get(i);
            if (record.getDifficulty().equals(difficulty)) {
                result.add(record);
            }
        }
        Collections.sort(result);
        return result;
    }

    @Override
    @SuppressWarnings("all")
    // ! 必须保障对象是可序列化的 —— implements Serializable
    public void readFromFile(Difficulty difficulty) {
        // 生成目标文件名
        String fileName = getFileNameByDifficulty(difficulty);
        // 创建 File 对象，检查文件是否存在
        File file = new File(fileName);
        // 判断文件是否存在
        if (!file.exists()) {
            return;
        }
        // 文件存在则读取文件内容
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // 从文件读取序列化的 PlayRecord 列表
            // 注意：这里进行强制类型转换，因为我们知道写入的是 List<PlayRecord>
            List<PlayRecord> loadedRecords = (List<PlayRecord>) ois.readObject();
            // // 清除当前内存该难度存在的记录 防止重复
            // Iterator<PlayRecord> iterator = records.iterator();
            // while (iterator.hasNext()) {
            //     if (iterator.next().getDifficulty().equals(difficulty)) {
            //         iterator.remove();
            //     }
            // }
            // 将loadedRecords加入records
            for (PlayRecord record : loadedRecords) {
                records.add(record);
            }
            // 排序records
            Collections.sort(records);
            // 打印结果
            System.out.println("已从 " + fileName + " 加载 " + loadedRecords.size() + " 条记录");
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    // ! 必须保障对象是可序列化的 —— implements Serializable
    public void writeToFile(Difficulty difficulty) {
        // 生成目标文件名
        String fileName = getFileNameByDifficulty(difficulty);
        // 创建或者覆盖目标文件名对应的文件
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            // 在内存record中筛选符合对应难度的记录
            List<PlayRecord> difficultyRecords = getAllPlayRecords(difficulty);
            // 将记录写入文件中
            oos.writeObject(difficultyRecords);
            System.out.println("已成功保存" + difficultyRecords.size()
                    + "条" + difficulty + "难度记录到文件: " + fileName);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("写入文件 " + fileName + " 失败...");
        }
    }

    /**
     * 根据难度生成对应的文件名
     * 实现"每个游戏难度对应单独的存储文件"的要求
     */
    private String getFileNameByDifficulty(Difficulty difficulty) {
        // 将难度转换为小写作为文件名的一部分
        return "data" + File.separator + "rank" + File.separator + "rank_"
                + difficulty.name().toLowerCase() + ".dat";
    }

    @Override
    public void addRecord(PlayRecord record) {
        records.add(record); // 在末尾进行添加
    }

}
