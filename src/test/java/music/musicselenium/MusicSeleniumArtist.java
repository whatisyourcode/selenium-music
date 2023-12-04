package music.musicselenium;

import music.musicselenium.MusicSeleniumApplication;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class MusicSeleniumArtist {
    public static void main(String[] args) {
        SpringApplication.run(MusicSeleniumApplication.class, args);
        {
            String singerName = "아이유";  // 검색어 예시
            int pageNumber = 2;     // url 페이지 number


            // chomedriver path setting
            System.setProperty("webdriver.chrome.driver", "chromedriver");

            // chromedriver 객체 선언 및 생성.
            WebDriver driver = new ChromeDriver();

            // genie 사이트 url 설정
            String url = "https://www.genie.co.kr";
            driver.get(url);

            // 노래 검색창 id crawling
            WebElement songSearch = driver.findElement(By.id("sc-fd"));

            // 노래 검색 창에 sendKeys() 이용해서 가수이름 입력
            songSearch.sendKeys(singerName);

            // Enter
            songSearch.sendKeys(Keys.ENTER);


            WebElement songTitle = driver.findElement(By.xpath("//*[@id=\"body-content\"]/div[2]/ul/li[2]/a"));
            songTitle.click();


            // 테이블 내의 모든 행을 가져옴
            List<WebElement> artists = driver.findElements(By.cssSelector("#body-content > div.search_artist > div.artist-type-1 > div > div > p"));
            // 노래 데이터를 저장할 리스트 생성
            List<String[]> artistData = new ArrayList<>();

            // 노래 데이터 추출
            for (WebElement artist : artists) {
                String singer = artist.findElement(By.cssSelector("a")).getText().trim();
                String nationality = artist.findElement(By.className("nationality")).getText();
                String type = artist.findElement(By.className("type")).getText();
                artistData.add(new String[]{"Genie", singer, nationality, type});
            }

            System.out.println(artistData);


            // 아티스트 클릭하는 로직

            List<WebElement> artists2 = driver.findElements(By.cssSelector("#body-content > div.search_artist > div.artist-type-1 > div > div "));


            Random random = new Random();
            int randomIndex = random.nextInt(artists2.size());
            WebElement randomAlbumElement = artists2.get(randomIndex);
            WebElement randomAlbumTitle = randomAlbumElement.findElement(By.cssSelector("a.thumb"));
            randomAlbumTitle.click();



            /*
            // 더 보기 버튼 누르기 로직 -> url page number가 존재하지 않는 부분이라 보류.
            WebElement songSearch2 = driver.findElement(By.cssSelector("#body-content > div.songlist-box > div.tit-box > div.more-link"));
            songSearch2.click();

            try {
                Thread.sleep(5000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

             */

            // 아티스트로 찾은 노래리스트 출력.
            List<WebElement> artists3 = driver.findElements(By.cssSelector("#body-content > div.songlist-box > div.music-list-wrap > table > tbody > tr > td.info"));

            List<String[]> artistData3 = new ArrayList<>();

            // 노래 데이터 추출
            for (WebElement artist : artists3) {
                String title = artist.findElement(By.cssSelector("a.title.ellipsis")).getText().trim();
                String singer = artist.findElement(By.cssSelector("a.artist.ellipsis")).getText().trim();
                String album = artist.findElement(By.cssSelector("a.albumtitle.ellipsis")).getText();
                artistData3.add(new String[]{"Genie", title, singer, album});
            }

            for (String[] data : artistData3) {
                System.out.println(Arrays.toString(data));
            }




            try{
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            // driver 종료

        }
    }
}

