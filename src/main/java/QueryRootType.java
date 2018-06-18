import com.coxautodev.graphql.tools.GraphQLRootResolver;

import java.util.List;

public class QueryRootType implements GraphQLRootResolver {

    private final LinkRepository linkRepository;

    public QueryRootType(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> allLinks() {
        return linkRepository.getAllLinks();
    }

    public Link getLink(String id) {
        return linkRepository.findById(id);
    }
}
