package music.musicselenium.service.crolling;

public interface GenieSearchInterface {

    public SongData searchTitle(String titleName, int pageNumber);

    public SongData searchAlbum(String albumName, int pageNumber);

    public SongData searchDetailAlbum(int albumid);

    public SongData searchArtist(String artist);

    public SongData searchArtistSongs(int artistId);

    public SongData artistPageMove(int artistId, int page);

    public SongData artistPagePrev(int artistId);

    public SongData artistPageNext(int artistId);

    public void artistPageClose();


}
