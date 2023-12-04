package music.musicselenium.controller;

import music.musicselenium.model.Song;
import music.musicselenium.repository.SongRepository;
import music.musicselenium.service.song.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SongController {

    @Autowired
    private SongService songService;


    @PostMapping("/api/song/request")
    @ResponseBody
    public void requestSong(@RequestBody Song request){
        songService.requestOrUpdate(request);
    }

}
