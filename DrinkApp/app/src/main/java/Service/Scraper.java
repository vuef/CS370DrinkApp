/**
 * Created by Vue on 10/19/2015.
 * This Scraper class will scrape a website of its html contents and then push them to
 * some type of model to be sent to the interface. This class does not send any information
 * to the interface yet. It nearly is hardcoded to find one drink -- 'The Kicker'
 */

package Service;

// These imports are for the jaunt libraries which are used to scrape a website.
import com.jaunt.*;
import com.jaunt.Document;
import com.jaunt.Element;
import com.jaunt.component.Form;

public class Scraper
{
    public static void main(String[] args) throws JauntException
    {
        UserAgent userAgent = new UserAgent();          // Create an instance of UserAgent
        userAgent.settings.autoSaveAsHTML = true;       // Auto save the HTML.
        userAgent.visit("http://www.drinksmixer.com/"); // Visit the website.

        Form form = userAgent.doc.getForm(0);           // Find the first form (text box).

        // Need to insert %20 for every space in the search.
        // This only applies to this site.
        // Save "The Kicker" append all spaces then search.

        form.setTextField("SearchTerms", "The%20Kicker");   // Search for 'The Kicker'

        ///DEBUG: System.out.println(userAgent.doc.getForms());

        Document doc = form.submit("Search");               // Press the search button

        ///DEBUG: System.out.println(doc.getUrl());

        // Find the first drink
        Element div = userAgent.doc.findFirst("<div class=\"l1a\">");

        // Visit the new webpage with the searched drink. This is probably gonna change
        // since we will want to have multiple drinks appear instead of just one drink.
        userAgent.visit(div.innerHTML().substring(10, div.innerHTML().length() - 20));

        // Set the title
        Element title = userAgent.doc.findFirst("<h1 class = \"fn recipe_title\">");

        /// DEBUG:
        System.out.println(title.innerHTML());
    }
}