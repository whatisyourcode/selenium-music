package music.musicselenium.model;

import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@ToString
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long song_id;
    private String title;
    private String artist;
    private String nickname;
    private LocalDateTime reg_date;

    private Long songNumber;

    public Long getSongNumber() {
        return songNumber;
    }

    public void setSongNumber(Long songNumber){
        this.songNumber = songNumber;
    }

    public void randomsetSongNumber() {
        // 랜덤한 6자리 숫자 생성
        Random random = new Random();
        this.songNumber = 100000L + random.nextInt(900000);
    }

    public Long getSong_id() {
        return song_id;
    }

    public void setSong_id(Long song_id) {
        this.song_id = song_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDateTime getReg_date() {
        return reg_date;
    }

    public void setReg_date(LocalDateTime reg_date) {
        this.reg_date = reg_date;
    }


}
