package music.musicselenium.service.song;

import music.musicselenium.model.Song;
import music.musicselenium.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class SongService implements SongServiceInterface {
    @Autowired
    private SongRepository songRepository;

//    @Transactional
    public void requestOrUpdate(Song request){
        Optional<Song> optionalSong = songRepository.findBySongNumber(828326L);

        System.out.println("optionalSong.get() = " + optionalSong.get());
        
        if (optionalSong.isPresent()) {
            // 이미 존재하는 노래일 경우 업데이트
            Song existingSong = optionalSong.get();
            existingSong.setTitle(request.getTitle());
            existingSong.setArtist(request.getArtist());
            existingSong.setReg_date(LocalDateTime.now());
            songRepository.save(existingSong);
        } else{
            Song newSong = new Song();
            newSong.setTitle(request.getTitle());
            newSong.setArtist(request.getArtist());
            newSong.setReg_date(LocalDateTime.now());
            newSong.randomsetSongNumber();

            songRepository.save(newSong);

        }

    }


}
