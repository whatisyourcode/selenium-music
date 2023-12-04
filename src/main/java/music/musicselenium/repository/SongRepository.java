package music.musicselenium.repository;

import music.musicselenium.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song,Long> {

    Optional<Song> findBySongNumber(Long number);

}
