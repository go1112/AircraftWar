package edu.hitsz.music;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import edu.hitsz.music.MusicThread.PlayMode;

public class MusicManager {

    private static volatile MusicManager instance;

    private MusicManager() {
        initAudioPaths();
        preloadedAllSoundClips();
    }

    public static MusicManager getInstance() {
        if (instance == null) {
            synchronized (MusicManager.class) {
                if (instance == null) {
                    instance = new MusicManager();
                }
            }
        }
        return instance;
    }

    // 音乐名字 和 音频文件路径 的对应哈希表
    private final Map<String, String> audioPaths = new HashMap<>();
    // 音乐名字 和 音频线程 的对应哈希表 而且是正在播放的音乐
    private final Map<String, MusicThread> activeBgmThreads = new ConcurrentHashMap<>();
    // 音乐名字 和 音频播放器 的对应哈希表 而且是短音效
    private final Map<String, Clip> preloadedSoundClips = new ConcurrentHashMap<>();
    // 用于短音效的线程池 最多可以同时播放4种短音效
    private final ExecutorService soundEffectExecutor = Executors.newFixedThreadPool(4);

    private void initAudioPaths() {
        audioPaths.put("bgm_boss", "src/videos/bgm_boss.wav");
        audioPaths.put("bgm", "src/videos/bgm.wav");
        audioPaths.put("bomb_explosion", "src/videos/bomb_explosion.wav");
        audioPaths.put("bullet_hit", "src/videos/bullet_hit.wav");
        audioPaths.put("game_over", "src/videos/game_over.wav");
        audioPaths.put("get_supply", "src/videos/get_supply.wav");
    }

    private void preloadedAllSoundClips() {
        for (String key : audioPaths.keySet()) {
            if (key.equals("bgm") || key.equals("bgm_boss")) {
                continue;
            }
            preloadedSoundClip(key);
        }
    }

    private void preloadedSoundClip(String key) {
        soundEffectExecutor.submit(() -> {
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(new File(audioPaths.get(key)));
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                ais.close();
                // clip先不用start
                preloadedSoundClips.put(key, clip);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void playBgmMusic(String key, boolean loop) {
        stopBgmMusic(key); // 先停止之前的
        MusicThread thread = new MusicThread(audioPaths.get(key), MusicThread.PlayMode.STREAM, loop);
        thread.start();
        activeBgmThreads.put(key, thread);
    }

    public void stopBgmMusic(String key) {
        MusicThread bgm = activeBgmThreads.get(key);
        if (bgm != null) {
            bgm.stopPlayback();
            activeBgmThreads.remove(key);
        }
    }

    public void playEffectMusic(String key) {
        if (!preloadedSoundClips.containsKey(key)) {
            return;
        }

        soundEffectExecutor.submit(() -> {
            Clip clip = preloadedSoundClips.get(key);
            synchronized (clip) {
                clip.setFramePosition(0);
                clip.start();
            }
        });
    }

    // 停止所有音频
    public void stopAllMusic() {
        // 停止长音频线程
        for (MusicThread t : activeBgmThreads.values())
            t.stopPlayback();
        activeBgmThreads.clear();
        // 停止所有正在播放的音效Clip
        for (Clip c : preloadedSoundClips.values()) {
            c.stop();
            c.setFramePosition(0);
        }
        // 注意：不要关闭线程池，除非游戏结束
    }

    // 游戏完全结束时调用
    public void shutdown() {
        stopAllMusic();
        soundEffectExecutor.shutdownNow(); // 关闭线程池
        for (Clip c : preloadedSoundClips.values())
            c.close(); // 释放音频资源
    }
}
