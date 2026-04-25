package edu.hitsz.music;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class MusicThread extends Thread {
    private String filePath;
    private boolean loop;
    private AudioPlayer audioPlayer;
    private volatile boolean isRunning;

    public enum PlayMode {
        STREAM,
        CLIP
    }

    private interface AudioPlayer {
        void play() throws Exception;

        void stop();

        boolean isPlaying();
    }

    /**
     * 适用于：bgm.wav, bgm_boss.wav
     */
    private class StreamPlayer implements AudioPlayer {
        private AudioInputStream stream;
        private SourceDataLine line;

        // 内部清理（不改标志）
        private void closeResources() {
            if (line != null) {
                line.drain();
                line.stop();
                line.close();
                // line = null;
            }
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stream = null;
            }
        }

        // 外部停止：设置标志 + 关闭资源
        @Override
        public void stop() {
            isRunning = false;
            closeResources();
        }

        // 播放逻辑
        @Override
        public void play() throws Exception {
            while (loop && isRunning) {
                // 确保上一轮资源已清理（安全）
                closeResources();

                // 完整的一次播放流程
                stream = AudioSystem.getAudioInputStream(new File(filePath));
                AudioFormat format = stream.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                line = (SourceDataLine) AudioSystem.getLine(info);
                line.open(format);
                line.start();

                byte[] buffer = new byte[4096];
                int bytesRead;
                do {
                    // 每次读取前可额外检查 isRunning，加快响应
                    if (!isRunning)
                        break;
                    bytesRead = stream.read(buffer);
                    if (bytesRead != -1) {
                        line.write(buffer, 0, bytesRead);
                    }
                } while (bytesRead != -1 && isRunning);

                // 播放完一遍，清理资源（不改 isRunning，以便下一轮重新打开）
                closeResources();
            }
        }

        @Override
        public boolean isPlaying() {
            return line != null && line.isActive();
        }

    }

    /**
     * 适用于：bomb_explosion.wav, bullet_hit.wav, game_over.wav, get_supply.wav
     */
    private class ClipPlayer implements AudioPlayer {
        private AudioInputStream stream;
        private Clip clip;

        @Override
        public void play() throws Exception {
            stream = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            clip.open(stream);
            stream.close(); 

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        }

        @Override
        public void stop() {
            if (clip != null) {
                clip.stop();
                clip.close();
                clip = null;
            }
        }

        @Override
        public boolean isPlaying() {
            return clip != null && clip.isActive();
        }

    }

    public MusicThread(String filePath, PlayMode playMode, boolean loop) {
        this.filePath = filePath;
        this.loop = loop;
        switch (playMode) {
            case STREAM:
                this.audioPlayer = new StreamPlayer();
                break;
            case CLIP:
                this.audioPlayer = new ClipPlayer();
                break;
            default:
                throw new IllegalArgumentException("不支持的播放模式...");
        }
    }

    @Override
    public void run() {
        isRunning = true;
        try {
            // audioPlayer.setVolume(volume);
            audioPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isRunning = false;
        }
    }

    /**
     * 停止播放
     */
    public void stopPlayback() {
        isRunning = false;
        audioPlayer.stop();
    }

    // /**
    //  * 设置音量
    //  */
    // public void setVolume(float volume) {
    //     this.volume = volume;
    //     audioPlayer.setVolume(volume);
    // }

    /**
     * 是否正在播放
     */
    public boolean isPlaying() {
        return isRunning && audioPlayer.isPlaying();
    }

}
