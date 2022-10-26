package pt.ua.s103823.ex3.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shows")
public class Show  {

    @Column(name = "source")
    private String source;
    @Column(name = "type")
    private String type;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "show")
    private List<Quote> quotes;

    public Show(String source, String type) {
        this.source = source;
        this.type = type;
        this.quotes= new ArrayList<>();
    }

    public Show() {
        this.quotes= new ArrayList<>();
    }

    public void addQuote(Quote quote) {
        this.quotes.add(quote);
    }

    public List<Quote> getQuotes() {
        return null;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Show[" +
                "source='" + source +
                ", type='" + type +
                ", id=" + id +
                ']';
    }
}
