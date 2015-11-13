package c4q.lighterletter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by c4q-john on 11/12/15.
 */
public class SpiderLeg
{

    private static final String USER_AGENT =
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<String> links = new LinkedList<String>(); // url list
    private Document htmlDocument; // this is our webpage

    //give url and make http request for a web page
    public void crawl(String url)
    {
        try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;

            System.out.println("Received web page at " + url);

            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");

            for(Element link : linksOnPage ){
                this.links.add(link.absUrl("href"));
            }
        }
        catch(IOException e)
        {
            System.out.println("Error in out HTTP request " + e);
        }

    }
    //attempts to find word on the page
    public boolean searchForWord(String searchWord){

        if(this.htmlDocument == null)
        {
            System.out.println("ERROR! Call crawl() before performing analysis on the document");
            return false;
        }

        System.out.println("Searching for the word " + searchWord + ". . .");

        String bodyText = this.htmlDocument.body().text();

        return
                bodyText.toLowerCase().contains(searchWord.toLowerCase());

    }
    //returns a list of all the urls on the page
    public List<String> getLinks(){
        return this.links;
    }
}
