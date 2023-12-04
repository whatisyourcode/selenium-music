package music.musicselenium;

import music.musicselenium.service.crolling.SongData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class test {
    public static void main(String[] args){
        int page = 3;
        System.setProperty("webdriver.chrome.driver", "chromedriver"); // chromedriver 객체 선언 및 생성.
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless"); // headless 모드 활성화
        WebDriver driver = new ChromeDriver(options); // genie 사이트 url 설정
        String url = "https://www.genie.co.kr/detail/artistSong?xxnm=" + 67872918;
        driver.get(url);

        driver.navigate().refresh();


        String xpathExpression;

        if (page == 1) {
            // page가 1일 때 다른 onclick 속성을 갖는 요소를 클릭
            xpathExpression = "//div[@id='divListContainer']//div[@class='page-nav']//a[contains(@class, 'current')]";
        } else if (page == 3) {
            // page가 3일 때는 직접 XPath로 찾아 클릭
            WebElement button = driver.findElement(By.xpath("//*[@id='divListContainer']/div[2]/a[5]"));
            button.click();
            return; // 3일 때 바로 클릭 후 함수 종료
        } else {
            // 그 외의 경우에는 기존의 요소를 클릭
            xpathExpression = "//div[@id='divListContainer']//div[@class='page-nav']//a[contains(@onclick, \"" + page + "\")]";
        }



        System.out.println(xpathExpression);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 페이지 버튼이 클릭 가능할 때까지 대기
        WebElement pageButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));
        pageButton.click();


        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }


        List<WebElement> songs = driver.findElements(By.cssSelector("#songlist-box > div.music-list-wrap > table > tbody > tr"));
        List<String[]> artistSongData = new ArrayList<>();   // 노래 데이터를 저장할 리스트 생성.


        for(WebElement song : songs){
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
        String songNumber = driver.findElement(By.className("article")).getText().replaceAll("\\D", "");
        String currentPage = driver.getCurrentUrl().replaceAll("\\D", "");
        String lastDigit = currentPage.substring(currentPage.length() - 1);


        SongData songData = new SongData();
        songData.setSongList(artistSongData);
        songData.setTotalSong(Integer.parseInt(songNumber));
        songData.setArtistId(67872918);
        songData.setCurrentPage(Integer.parseInt(lastDigit));

        driver.quit();

    }
}
