package music.musicselenium.controller;


import music.musicselenium.service.crolling.GenieSearchInterface;
import music.musicselenium.service.crolling.SongData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class GenieCrawlController {

    @Autowired
    private GenieSearchInterface genieSearchInterface;

    @GetMapping("/")
    public String showIndexPage(){
        return "index";
    }

    @GetMapping("/api/song/title/{title}/{page}")
    public String searchByTitle(@PathVariable("title") String title, @PathVariable("page") int page, Model model){
        SongData songData = genieSearchInterface.searchTitle(title,page);
        model.addAttribute("title",title); // 제목
        model.addAttribute("page",page); // 페이지
        model.addAttribute("songData", songData); // 음악 곡들

        return "songResult";
    }


    @GetMapping("api/song/album/{album}/{page}")
    public String searchByAlbum(@PathVariable("album") String album, @PathVariable("page") int page, Model model){
        SongData albumData = genieSearchInterface.searchAlbum(album,page);
        model.addAttribute("title",album);
        model.addAttribute("page",page);
        model.addAttribute("albumData", albumData);
        return "albumResult";
    }



    @GetMapping("api/song/detail-album/{albumid}")
    public String searchByDetailAlbum(@PathVariable("albumid") int albumid, Model model){
        SongData albumDetailData = genieSearchInterface.searchDetailAlbum(albumid);
        model.addAttribute("albumDetailData", albumDetailData);
        return "detailAlbumResult";
    }


    @GetMapping("api/song/artist/{artist}")
    public String searchByArtist(@PathVariable("artist") String artist,Model model){
        SongData artistData = genieSearchInterface.searchArtist(artist);
        model.addAttribute("artistData", artistData);
        return "artistResult";
    }

    @GetMapping("api/song/artist-song/{artistid}")
    public String searchByArtistSong(@PathVariable("artistid")int artistId, Model model){
        SongData artistSong = genieSearchInterface.searchArtistSongs(artistId);
        model.addAttribute("artistSong", artistSong);
        return "artistSongResult";
    }

    @GetMapping("api/song/artist-song/{artistid}/{page}")
    public String artistPageMove(@PathVariable("artistid") int artistid, @PathVariable("page") int page, Model model){
        SongData artistSong = genieSearchInterface.artistPageMove(artistid, page);
        model.addAttribute("artistSong" , artistSong);
        return "artistPageMoveResult";
    }

    @GetMapping("api/song/artist-song/{artistid}/prev")
    public String artistPagePrev(@PathVariable("artistid") int artistid, Model model){
        SongData pagePrev = genieSearchInterface.artistPagePrev(artistid);
        model.addAttribute("pagePrev", pagePrev );
        return "artistPagePrevResult";
    }

    @GetMapping("api/song/artist-song/{artistid}/next")
    public String artistPageNext(@PathVariable("artistid") int artistid, Model model){
        SongData pageNext = genieSearchInterface.artistPageNext(artistid);
        model.addAttribute("pageNext", pageNext);
        return "artistPageNextResult";
    }

    @GetMapping("api/song/artist-song/close")
    public void artistPageClose(){
        genieSearchInterface.artistPageClose();
    }


}



