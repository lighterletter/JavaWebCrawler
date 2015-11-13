package c4q.lighterletter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by c4q-john on 11/12/15.
 */
public class Spider
{
    //Fields

    private static final int MAX_PAGES_TO_SEARCH = 10;

    private Set<String> pagesVisited = new HashSet<String>(); // a Set contains unique entries. No duplicates.

    private List<String> pagesToVisit = new LinkedList<String>(); // Using list makes it so that our crawler will visit sites in a breadth-first approach, not depth-first.

    private String nextUrl(){
        String nextUrl;

        do{
            nextUrl = this.pagesToVisit.remove(0);
        } while (this.pagesVisited.contains(nextUrl));
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }

    public void search(String url, String searchWord){

        while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH){

            String currentUrl;
            SpiderLeg leg = new SpiderLeg();

            if(this.pagesToVisit.isEmpty()){
                currentUrl = url;
                this.pagesVisited.add(url);
            }
            else{
                currentUrl = this.nextUrl();
            }
            leg.crawl(currentUrl); //magic

            boolean success = leg.searchForWord(searchWord);
            if(success){
                System.out.println(String.format("**Success** Word %s", searchWord, currentUrl));
                break;
            }
            this.pagesToVisit.addAll(leg.getLinks());
        }
        System.out.println(String.format("**Done** Visited %s web page(s)", this.pagesVisited.size()));
    }


}
