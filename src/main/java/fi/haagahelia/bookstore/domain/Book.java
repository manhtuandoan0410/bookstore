package fi.haagahelia.bookstore.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType; //dependency
import jakarta.persistence.Id;

public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title, author;
    private Integer publicationYear, isbn;
    private Double price;

    public Book() {
        super();
    }

    public Book(Long id, String title, String author) {
        super();
        this.id = id;
        this.title = title;
        this.author = author;
    
    }

    public Book(Integer publicationYear, Integer isbn, Double price) {
        super();
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.price = price;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }
    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Integer getIsbn() {
        return isbn;
    }
    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    // Tostring in this case written here to understand
    // can we get informationfrom the database to hte controller

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author="  + author + ", publicationYear=" + publicationYear + ", isbn=" + isbn + ", price=" + price + "]";
    }

}
