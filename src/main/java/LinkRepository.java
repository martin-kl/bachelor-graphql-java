import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class LinkRepository {

    private final List<Link> links;
    private int newId;

    public LinkRepository() {
        links = new ArrayList<>();
        //add some links to start off with
        links.add(new Link("1", "http://howtographql.com", "Favorite GraphQL page", "1000"));
        links.add(new Link("2", "http://graphql.org/learn/", "The official docs", "1001"));
        newId = 2;
    }

    public Link findById(String id) {
        for(Link link : links){
            if(link.getId().equals(id)) {
                return link;
            }
        }
        //TODO do not return null^^
        return null;
    }

    public List<Link> getAllLinks() {
        return links;
    }

    public void saveLink(Link link) {
        links.add(link);
    }

    public String getId() {
        newId++;
        return "" + newId;
    }
}
