/*
Весникот FINKI Onion се одлучил да развие мобилна апликација за своите вести. Сите вести NewsItem се состојат од наслов, датум на објавување (објект од класата java.util.Date) и категорија. Категоријата на вести е објект од класата Category во која се чува само името на категоријата. Две категории се еднакви, ако се еднакви нивните имиња.

Во весникот засега постојат два видови вести кои се објавуваат и тоа:

Текстуални вести (TextNewsItem) за кој се чува дополнително текстот на веста,
Мултимедијални вести (MediaNewsItem) за кој се чува url кон локацијата на мултимедијалната содржина (String) и бројот на погледи.
Сите вести се додаваат во класа FrontPage во која се чува листа од вести и поле од сите категории на вести кои постојат. За оваа класа треба да се имплементираат следните методи:

конструктор: FrontPage(Category[] categories);
void addNewsItem(NewsItem newsItem) - додава нова вест во листата со вести,
List<NewsItem> listByCategory(Category category) - прима еден аргумент рефернца кон објект од Category и враќа листа со сите вести од таа категорија.
List<NewsItem> listByCategoryName(String category) - прима еден аргумент String името на категоријата и враќа листа со сите вести од категоријата со тоа име. Ако не постои категорија со вакво име во полето со категории, да се фрли исклучок од тип CategoryNotFoundException во кој се пренесува името на категоријата која не е најдена.
препокривање на методот toString() кој враќа String составен од сите кратки содржини на вестите (повик на методот getTeaser()).
Во класите за вести треба да се имплементира методот за враќање на кратка содржина getTeaser() на следниот начин:

TextNewsItem:getTeaser() - враќа String составен од насловот на веста, пред колку минути е објавена веста (цел број минути) и максимум 80 знаци од содржината на веста, сите одделени со нов ред.
MediaNewsItem:getTeaser() - враќа String составен од насловот на веста, пред колку минути е објавена веста (цел број минути), url-то на веста и бројот на погледи, сите одделени со нов ред.
* */

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;

public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for(Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch(CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}


// Vasiot kod ovde
//Technology Science Sport
//3
//Booksellers Resisting Amazon’s Disruption
//15
//There is the slow-motion crackup of electronics showroom Best Buy. There is Amazon’s rumored entry into the wine business, which is already agitating competitors. And there is the merger of Random House and Penguin, an effort to create a mega-publisher sufficiently hefty to negotiate with the retailer on equal terms.
//0
//Chinese Messaging App Gains Ground Elsewhere
//28
//Chinese Internet companies have long struggled to establish their products beyond the country’s borders. In 2007 China’s dominant search engine, Baidu, announced an ambitious plan to break into the Japanese search engine market; as of last year, the company said it had lost more than $108 million trying.
//1
//Shadow Marathon Quenches a Desire to Run
//12
//Some called it the shadow race. Others the underground marathon.
//0
//2
//Protecting the City, Before Next Time
//72
//http://www.nytimes.com/2012/11/04/nyregion/protecting-new-york-city-before-next-time.html?ref=science
//1432
//0
//After Getting Back to Normal, Big Job Is Facing New Reality
//16
//http://www.nytimes.com/2012/11/04/nyregion/after-getting-back-to-normal-the-big-job-is-to-face-a-new-reality.html?ref=science
//2783
//1
//Science




//Booksellers Resisting Amazon’s Disruption
//15
//There is the slow-motion crackup of electronics showroom Best Buy. There is Amaz
//Chinese Messaging App Gains Ground Elsewhere
//28
//Chinese Internet companies have long struggled to establish their products beyon
//Shadow Marathon Quenches a Desire to Run
//12
//Some called it the shadow race. Others the underground marathon.
//Protecting the City, Before Next Time
//72
//http://www.nytimes.com/2012/11/04/nyregion/protecting-new-york-city-before-next-time.html?ref=science
//1432
//After Getting Back to Normal, Big Job Is Facing New Reality
//16
//http://www.nytimes.com/2012/11/04/nyregion/after-getting-back-to-normal-the-big-job-is-to-face-a-new-reality.html?ref=science
//2783
//
//3
//2
//0
//2