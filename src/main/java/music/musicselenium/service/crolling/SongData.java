package music.musicselenium.service.crolling;

import java.util.ArrayList;
import java.util.List;

public class SongData {
    private List<String[]> songList;
    private int totalSong;
    private int artistId;
    private int currentPage;

    public List<String[]> getSongList() {
        return songList;
    }

    public void setSongList(List<String[]> songList) {
        this.songList = songList;
    }

    public int getTotalSong() {
        return totalSong;
    }

    public void setTotalSong(int totalSong) {
        this.totalSong = totalSong;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }



}
