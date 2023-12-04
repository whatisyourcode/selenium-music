package music.musicselenium.service.crolling;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenieSearch implements GenieSearchInterface {

    private WebDriver driverForArtist;

    // 제목 검색
    public SongData searchTitle(String titleName, int pageNumber) {

        System.setProperty("webdriver.chrome.driver", "chromedriver");  // chomedriver path setting
        ChromeOptions options = new ChromeOptions(); // ChromeOptions를 생성
        options.addArguments("--headless"); // headless 모드 활성화
        WebDriver driver = new ChromeDriver(options);  // chromedriver 객체 선언 및 생성.
        String url = "https://www.genie.co.kr";  // genie 사이트 url 설정
        driver.get(url);
        WebElement songSearch = driver.findElement(By.id("sc-fd"));  // 노래 검색창 id crawling
        songSearch.sendKeys(titleName); // 노래 검색 창에 sendKeys() ==이용해서 가수이름 입력
        songSearch.sendKeys(Keys.ENTER);
        // 곡 title 버튼 누르기
        WebElement songTitle = driver.findElement(By.xpath("//*[@id=\"body-content\"]/div[2]/ul/li[3]/a"));
        songTitle.click();

        // 현재 url 가져오기
        String currentUrl = driver.getCurrentUrl();
        currentUrl = currentUrl.replace("Coll=", "page=");
        // 현재 페이지 + 원하는 페이지 번호
        currentUrl = currentUrl + pageNumber;
        // 페이지 이동
        driver.get(currentUrl);

        // 테이블 내의 모든 행을 가져옴
        List<WebElement> songs = driver.findElements(By.cssSelector("#body-content > div.search_song > div.music-list-wrap > div.music-list-wrap > table > tbody > tr"));
        // 노래 데이터를 저장할 리스트 생성
        List<String[]> songsData = new ArrayList<>();

        // 노래 데이터 추출
        for (WebElement song : songs) {
            String title = song.findElement(By.cssSelector("a.title.ellipsis")).getText().trim();
            if (title.startsWith("TITLE")) {
                title = title.substring("TITLE".length()).trim();
            }
            String artist = song.findElement(By.cssSelector("a.artist.ellipsis")).getText();
            String album = song.findElement(By.cssSelector("a.albumtitle.ellipsis")).getText();
            String img = song.findElement(By.tagName("img")).getAttribute("src");
            songsData.add(new String[]{title, artist, album, img});
        }

        SongData songData = new SongData();
        songData.setSongList(songsData);

        driver.quit();
        return songData;
    } // 완성


    // 앨범 검색
    public SongData searchAlbum(String albumName, int pageNumber) {
        System.setProperty("webdriver.chrome.driver", "chromedriver"); // chromedriver 객체 선언 및 생성.
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // headless 모드 활성화
        WebDriver driver = new ChromeDriver(options); // genie 사이트 url 설정
        String url = "https://www.genie.co.kr";
        driver.get(url);

        WebElement songSearch = driver.findElement(By.id("sc-fd"));   // 노래 검색창 id crawling
        songSearch.sendKeys(albumName);          // 노래 검색 창에 sendKeys() 이용해서 앨범 이름 입력
        songSearch.sendKeys(Keys.ENTER);
        // album title 가져오기
        WebElement songTitle = driver.findElement(By.xpath("//*[@id=\"body-content\"]/div[2]/ul/li[4]/a"));
        songTitle.click();

        String currentUrl = driver.getCurrentUrl(); // 현재 url 가져오기
        currentUrl = currentUrl.replace("Coll=", "page=");
        currentUrl = currentUrl + pageNumber; // 현재 페이지 + 원하는 페이지 번호
        driver.get(currentUrl);     // 페이지 이동


        List<WebElement> albums = driver.findElements(By.cssSelector("#body-content > div.search_album > div.music-list-wrap.type-album > div > ul > li "));   // 테이블 내의 모든 행을 가져옴
        List<String[]> albumData = new ArrayList<>(); // 노래 데이터를 저장할 리스트 생성

        // 앨범 데이터 추출
        for (WebElement album : albums) {
            String albumTitle = album.findElement(By.cssSelector("dt.ellipsis")).getText().trim();
            String artist = album.findElement(By.className("album-artist")).getText();
            String since = album.findElement(By.cssSelector("dd.desc")).getText();
            String albumId = album.findElement(By.cssSelector("a")).getAttribute("onclick").replaceAll("\\D+", "");
            String img = album.findElement(By.tagName("img")).getAttribute("src");
            albumData.add(new String[]{albumTitle, artist, since, albumId, img});
        }

        SongData songData = new SongData();
        songData.setSongList(albumData);

        // chromedriver 종료();
        driver.quit();
        return songData;
    }   // 완성


    // 앨범 수록곡 검색
    public SongData searchDetailAlbum(int albumid) {
        System.setProperty("webdriver.chrome.driver", "chromedriver"); // chromedriver 객체 선언 및 생성.
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // headless 모드 활성화
        WebDriver driver = new ChromeDriver(options); // genie 사이트 url 설정
        String url = "https://www.genie.co.kr/detail/albumInfo?axnm=" + albumid;
        driver.get(url);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 테이블 내의 모든 행을 가져옴
        List<WebElement> songs = driver.findElements(By.cssSelector("#listContainer > div.songlist-box > div.music-list-wrap.none-album-list2 > table > tbody > tr"));
        // 노래 데이터를 저장할 리스트 생성
        List<String[]> songsData = new ArrayList<>();

        // 노래 데이터 추출
        for (WebElement song : songs) {
            String title = song.findElement(By.cssSelector("a.title.ellipsis")).getText().trim();
            String singer = song.findElement(By.cssSelector("a.artist.ellipsis")).getText();
            String dtoSinger = driver.findElement(By.className("value")).getText();
            String dtoSince = driver.findElement(By.cssSelector("#body-content > div.album-detail-infos > div.info-zone > ul > li:nth-child(5) > span.value")).getText();
            String img = driver.findElement(By.cssSelector("a.album3-thumb img")).getAttribute("src");
            songsData.add(new String[]{title, singer, dtoSinger, dtoSince, img});
        }
        SongData songData = new SongData();
        songData.setSongList(songsData);

        driver.quit();

        return songData;
    } // 완성

    // 가수 검색
    public SongData searchArtist(String artist) {
        // chomedriver path setting
        System.setProperty("webdriver.chrome.driver", "chromedriver"); // chromedriver 객체 선언 및 생성.
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // headless 모드 활성화
        WebDriver driver = new ChromeDriver(options); // chromedriver 객체 선언 및 생성.
        String url = "https://www.genie.co.kr"; // genie 사이트 url 설정
        driver.get(url);
        WebElement songSearch = driver.findElement(By.id("sc-fd")); // 노래 검색창 id crawling
        songSearch.sendKeys(artist);  // 노래 검색 창에 sendKeys() 이용해서 가수이름 입력
        songSearch.sendKeys(Keys.ENTER); // Enter

        WebElement songTitle = driver.findElement(By.xpath("//*[@id=\"body-content\"]/div[2]/ul/li[2]/a"));
        songTitle.click();

        // 테이블 내의 모든 행을 가져옴
        List<WebElement> artists = driver.findElements(By.cssSelector("#body-content > div.search_artist > div.artist-type-1 > div > div > p "));
        // 노래 데이터를 저장할 리스트 생성
        List<String[]> artistData = new ArrayList<>();

        // 가수 데이터 추출
        for (WebElement data : artists) {
            String artistId = data.findElement(By.cssSelector("a")).getAttribute("onclick").replaceAll("\\D+", "");
            String singer = data.findElement(By.className("artist")).getText().trim();
            String gender = data.findElement(By.className("type")).getText();
            String country = data.findElement(By.className("nationality")).getText();
            String img = driver.findElement(By.cssSelector("a.thumb img")).getAttribute("src");
            artistData.add(new String[]{artistId, singer, gender, country, img});
        }

        SongData songData = new SongData();
        songData.setSongList(artistData);
        // driver 종료
        driver.quit();

        return songData;
    } // 완성


    // 가수 발매곡 검색
    public SongData searchArtistSongs(int artistId) {
        System.setProperty("webdriver.chrome.driver", "chromedriver"); // chromedriver 객체 선언 및 생성.
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // headless 모드 활성화
        driverForArtist = new ChromeDriver(options); // genie 사이트 url 설정
        String url = "https://www.genie.co.kr/detail/artistSong?xxnm=" + artistId;
        driverForArtist.get(url);

        List<WebElement> songs = driverForArtist.findElements(By.cssSelector("#songlist-box > div.music-list-wrap > table > tbody > tr"));
        List<String[]> artistSongData = new ArrayList<>();   // 노래 데이터를 저장할 리스트 생성

        for (WebElement song : songs) {
            String title = song.findElement(By.cssSelector("a.title.ellipsis")).getText().trim();
            if (title.startsWith("TITLE")) {
                title = title.substring("TITLE".length()).trim();
            }
            String artist = song.findElement(By.cssSelector("a.artist.ellipsis")).getText().trim();
            String album = song.findElement(By.cssSelector("a.albumtitle.ellipsis")).getText();
            String img = song.findElement(By.tagName("img")).getAttribute("src");
            artistSongData.add(new String[]{title, artist, album, img});
        }

        // 총 음악 갯수 추출
        String songNumber = driverForArtist.findElement(By.className("article")).getText().replaceAll("\\D", "");


        SongData songData = new SongData();
        songData.setSongList(artistSongData);
        songData.setTotalSong(Integer.parseInt(songNumber));
        songData.setArtistId(artistId);

        return songData;
    } // 완성


    // 가수 발매곡 페이지 이동
    public SongData artistPageMove(@PathVariable("artistid") int artistId, @PathVariable("page") int page) {

        WebDriverWait wait = new WebDriverWait(driverForArtist, Duration.ofSeconds(25));


        if (page == 1) {
            // page가 1일 때 다른 onclick 속성을 갖는 요소를 클릭
            WebElement pageButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='divListContainer']//div[@class='page-nav']//a[contains(@class, 'current')]")));
            pageButton.click();
        } else if (page == 3) {
            // page가 3일 때는 직접 XPath로 찾아 클릭
            WebElement button = driverForArtist.findElement(By.xpath("//*[@id='divListContainer']/div[2]/a[5]"));
            button.click();
        } else {
            // 그 외의 경우에는 기존의 요소를 클릭
            WebElement pageButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='divListContainer']//div[@class='page-nav']//a[contains(@onclick, \"" + page + "\")]")));
            pageButton.click();
        }


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> songs = driverForArtist.findElements(By.cssSelector("#songlist-box > div.music-list-wrap > table > tbody > tr"));
        List<String[]> artistSongData = new ArrayList<>();   // 노래 데이터를 저장할 리스트 생성.


        for (WebElement song : songs) {
            String title = song.findElement(By.cssSelector("a.title.ellipsis")).getText().trim();
            if (title.startsWith("TITLE")) {
                title = title.substring("TITLE".length()).trim();
            }
            String artist = song.findElement(By.cssSelector("a.artist.ellipsis")).getText().trim();
            String album = song.findElement(By.cssSelector("a.albumtitle.ellipsis")).getText();
            String img = song.findElement(By.tagName("img")).getAttribute("src");
            artistSongData.add(new String[]{title, artist, album, img});
        }

        // 총 곡 갯수 추출
        String songNumber = driverForArtist.findElement(By.className("article")).getText().replaceAll("\\D", "");
        // 페이지 추출
        String currentPage = driverForArtist.getCurrentUrl().replaceAll("\\D", "");
        String lastDigit = currentPage.substring(currentPage.length() - 1);

        SongData songData = new SongData();
        songData.setSongList(artistSongData);
        songData.setTotalSong(Integer.parseInt(songNumber));
        songData.setArtistId(artistId);
        songData.setCurrentPage(Integer.parseInt(lastDigit));


        return songData;
    }  // 완성

    // 가수 발매곡 페이지 (<) 버튼

    public SongData artistPagePrev(@PathVariable("artistid") int artistId) {

        driverForArtist.findElement(By.xpath("//*[@id=\"divListContainer\"]/div[2]/a[2]")).click();


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        List<WebElement> songs = driverForArtist.findElements(By.cssSelector("#songlist-box > div.music-list-wrap > table > tbody > tr"));
        List<String[]> artistSongData = new ArrayList<>();   // 노래 데이터를 저장할 리스트 생성.


        for (WebElement song : songs) {
            String title = song.findElement(By.cssSelector("a.title.ellipsis")).getText().trim();
            if (title.startsWith("TITLE")) {
                title = title.substring("TITLE".length()).trim();
            }
            String artist = song.findElement(By.cssSelector("a.artist.ellipsis")).getText().trim();
            String album = song.findElement(By.cssSelector("a.albumtitle.ellipsis")).getText();
            String img = song.findElement(By.tagName("img")).getAttribute("src");
            artistSongData.add(new String[]{title, artist, album, img});
        }
        // 총 곡 갯수 추출
        String songNumber = driverForArtist.findElement(By.className("article")).getText().replaceAll("\\D", "");
        // 페이지 추출
        String currentPage = driverForArtist.getCurrentUrl().replaceAll("\\D", "");
        String lastDigit = currentPage.substring(currentPage.length() - 1);


        System.out.println(lastDigit);

        SongData songData = new SongData();
        songData.setSongList(artistSongData);
        songData.setTotalSong(Integer.parseInt(songNumber));
        songData.setArtistId(artistId);
        songData.setCurrentPage(Integer.parseInt(lastDigit));


        return songData;
    } // 진행중


    public SongData artistPageNext(@PathVariable("artistid") int artistId) {


        driverForArtist.findElement(By.xpath("//*[@id=\"divListContainer\"]/div[2]/a[9]")).click();


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        List<WebElement> songs = driverForArtist.findElements(By.cssSelector("#songlist-box > div.music-list-wrap > table > tbody > tr"));
        List<String[]> artistSongData = new ArrayList<>();   // 노래 데이터를 저장할 리스트 생성.


        for (WebElement song : songs) {
            String title = song.findElement(By.cssSelector("a.title.ellipsis")).getText().trim();
            if (title.startsWith("TITLE")) {
                title = title.substring("TITLE".length()).trim();
            }
            String artist = song.findElement(By.cssSelector("a.artist.ellipsis")).getText().trim();
            String album = song.findElement(By.cssSelector("a.albumtitle.ellipsis")).getText();
            String img = song.findElement(By.tagName("img")).getAttribute("src");
            artistSongData.add(new String[]{title, artist, album, img});
        }
        // 총 곡 갯수 추출
        String songNumber = driverForArtist.findElement(By.className("article")).getText().replaceAll("\\D", "");
        // 페이지 추출
        String currentPage = driverForArtist.getCurrentUrl().replaceAll("\\D", "");
        String lastDigit = currentPage.substring(currentPage.length() - 1);


        SongData songData = new SongData();
        songData.setSongList(artistSongData);
        songData.setTotalSong(Integer.parseInt(songNumber));
        songData.setArtistId(artistId);
        songData.setCurrentPage(Integer.parseInt(lastDigit));

        return songData;
    } // 완성

    public void artistPageClose() {
          driverForArtist.quit();
    }



}