package music.musicselenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class MusicSeleniumTitle {

    public static void main(String[] args) {
        SpringApplication.run(MusicSeleniumApplication.class, args);
        {

            String singerName = "아이처럼";  // 검색어 예시
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

            // 노래 검색 창에 sendKeys() ==이용해서 가수이름 입력
            songSearch.sendKeys(singerName);

            // Enter
            songSearch.sendKeys(Keys.ENTER);


        /*
            WebElement searchClick = driver.findElement(By.className("btn-submit"));
            searchClick.click();
        */

            // 곡 title 버튼 누르기
            WebElement songTitle = driver.findElement(By.xpath("//*[@id=\"body-content\"]/div[2]/ul/li[3]/a"));
            songTitle.click();

            // 현재 url 가져오기
            String currentUrl = driver.getCurrentUrl();
            currentUrl = currentUrl.replace("Coll=", "page=");
            //           System.out.println("currentUrl " + currentUrl);


            // 테이블 내의 모든 행을 가져옴
            List<WebElement> songs = driver.findElements(By.cssSelector("#body-content > div.search_song > div.music-list-wrap > div.music-list-wrap > table > tbody > tr"));
            // 노래 데이터를 저장할 리스트 생성
            List<String[]> songData = new ArrayList<>();

            // 노래 데이터 추출
            for (WebElement song : songs) {
                String title = song.findElement(By.cssSelector("a.title.ellipsis")).getText().trim();
                String singer = song.findElement(By.cssSelector("a.artist.ellipsis")).getText();
                String number = song.findElement(By.className("number")).getText();
                songData.add(new String[]{"Genie", number, title, singer});

            }



            // 노래 데이터 출력
            for (String[] data : songData) {
                System.out.println(Arrays.toString(data));
            }

            // 현재 페이지 + 원하는 페이지 번호
            currentUrl = currentUrl + pageNumber;

            // 페이지 이동
            driver.get(currentUrl);


            // 테이블 내의 모든 행을 가져옴
            List<WebElement> songs2 = driver.findElements(By.cssSelector("#body-content > div.search_song > div.music-list-wrap > div.music-list-wrap > table > tbody > tr"));

            // 노래 데이터를 저장할 리스트 생성
            List<String[]> songData2 = new ArrayList<>();

            for (WebElement song : songs2) {
                String title = song.findElement(By.cssSelector("a.title.ellipsis")).getText().trim();
                String singer = song.findElement(By.cssSelector("a.artist.ellipsis")).getText().trim();
                String number = song.findElement(By.className("number")).getText();
                songData2.add(new String[]{"Genie", number , title, singer});

            }

            for (String[] data : songData2) {
                System.out.println(Arrays.toString(data));
            }

            // 곡 갯수 -> int형으로 변환
            String totalPageString = driver.findElement(By.cssSelector("#ALL > a > strong > strong")).getText();
            int totalPage = Integer.parseInt(totalPageString);

            System.out.println(totalPage);



            // driver 종료
            driver.quit();
        }
    }
}
