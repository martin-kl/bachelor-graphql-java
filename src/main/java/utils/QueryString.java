package utils;

public class QueryString {
    String query;
    String verification;

    public QueryString(String query, String verification) {
        this.query = query;
        this.verification = verification;
    }

    public String getQuery() {
        return query;
    }

    public String getVerification() {
        return verification;
    }
}
