package pt.ua.s103823.ex3.model;


import javax.persistence.*;

@Entity
@Table(name = "quotes")
public class Quote {
    private String quote;
    private String author;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    public Quote(String quote, String author) {
        this.quote = quote;
        this.author = author;
    }

    public Quote() {
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }


    @Override
    public String toString() {
        return "Quote[" +
                "quote='" + quote +
                ", author='" + author +
                ", id=" + id +
                ']';
    }

}
