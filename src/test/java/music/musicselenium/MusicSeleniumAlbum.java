package music.musicselenium;

import music.musicselenium.MusicSeleniumApplication;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


@SpringBootApplication
public class MusicSeleniumAlbum {
    public static void main(String[] args) {
        SpringApplication.run(MusicSeleniumApplication.class, args); {
            String singerName = "아이유";  // 검색어 예시
            int pageNumber = 2;     // url 페이지 number
            // chomedriver path setting
            System.setProperty("webdriver.chrome.driver", "chromedriver"); // chromedriver 객체 선언 및 생성.
            WebDriver driver = new ChromeDriver(); // genie 사이트 url 설정
            String url = "https://www.genie.co.kr";
            driver.get(url);

            WebElement songSearch = driver.findElement(By.id("sc-fd"));   // 노래 검색창 id crawling

            songSearch.sendKeys(singerName);          // 노래 검색 창에 sendKeys() 이용해서 앨범 이름 입력
            // Enter
            songSearch.sendKeys(Keys.ENTER);


            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            WebElement songTitle = driver.findElement(By.xpath("//*[@id=\"body-content\"]/div[2]/ul/li[4]/a"));
            songTitle.click();


            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            // 현재 url 가져오기
            String currentUrl = driver.getCurrentUrl();
            currentUrl = currentUrl.replace("Coll=", "page=");

            // 테이블 내의 모든 행을 가져옴
            List<WebElement> albums = driver.findElements(By.cssSelector("#body-content > div.search_album > div.music-list-wrap.type-album > div > ul > li > dl "));
            // 노래 데이터를 저장할 리스트 생성
            List<String[]> albumData = new ArrayList<>();

            // 노래 데이터 추출
            for (WebElement album : albums) {
                String title = album.findElement(By.cssSelector("dt.ellipsis")).getText().trim();
                String representSong = album.findElement(By.cssSelector("dd.album-title.ellipsis")).getText();
                String singer = album.findElement(By.cssSelector("dd.ellipsis")).getText().trim();
                String info = album.findElement(By.cssSelector("dd.desc")).getText();
                albumData.add(new String[]{"Genie", title, representSong, singer, info});
            }

            // 노래 데이터 출력
            for (String[] data : albumData) {
                System.out.println(Arrays.toString(data));
            }

            // 현재 페이지 + 원하는 페이지 번호
            currentUrl = currentUrl + pageNumber;

            // 페이지 이동
            driver.get(currentUrl);


            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 최대 10초까지 기다림


            // 테이블 내의 모든 행을 가져옴
            List<WebElement> albums2 = driver.findElements(By.cssSelector("#body-content > div.search_album > div.music-list-wrap.type-album > div > ul > li"));



            // 노래 데이터를 저장할 리스트 생성
            List<String[]> albumData2 = new ArrayList<>();

            // 노래 데이터 추출
            for (WebElement album : albums2) {
                String title = album.findElement(By.cssSelector("dt.ellipsis")).getText().trim();
                String representSong = album.findElement(By.cssSelector("dd.album-title.ellipsis")).getText();
                String singer = album.findElement(By.cssSelector("dd.ellipsis")).getText().trim();
                String info = album.findElement(By.cssSelector("dd.desc")).getText();
                String onclick = album.findElement(By.cssSelector("a")).getAttribute("onclick");
                String albumId = onclick.replaceAll("\\D+", "");
                albumData2.add(new String[]{"Genie", title, representSong, singer, info, albumId});
            }




            for (String[] data : albumData2) {
                System.out.println(Arrays.toString(data));
            }


            List<WebElement> albums3 = driver.findElements(By.cssSelector("#body-content > div.search_album > div.music-list-wrap.type-album > div > ul > li  "));


            //       System.out.println(albums2);
            Random random = new Random();
            int randomIndex = random.nextInt(albums3.size());
            WebElement randomAlbumElement = albums3.get(randomIndex);
            WebElement randomAlbumTitle = randomAlbumElement.findElement(By.cssSelector("a.album-cover"));
            randomAlbumTitle.click();


            List<WebElement> songs = driver.findElements(By.cssSelector("#listContainer > div.songlist-box > div.music-list-wrap.none-album-list2 > table > tbody > tr"));
            // 노래 데이터를 저장할 리스트 생성
            List<String[]> songData = new ArrayList<>();

            // 노래 데이터 추출
            for (WebElement song : songs) {
                String title = song.findElement(By.cssSelector("a.title.ellipsis")).getText().trim();
                String singer = song.findElement(By.cssSelector("a.artist.ellipsis")).getText();
                String number = song.findElement(By.className("number")).getText();
                songData.add(new String[]{"Genie", number, title, singer});
            }


            for(String[] data : songData ){
                System.out.println(Arrays.toString(data));
            }

              driver.quit();



            }
        }
    }

