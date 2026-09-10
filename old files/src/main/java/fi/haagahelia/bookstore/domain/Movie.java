// this is for practicing purpose at class to build bookstore and book

package fi.haagahelia.bookstore.model; // need to add this MODEL folder or package

import org.springframework.stereotype.Indexed;

import jakarta.persistence.Entity; //dependency
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Movie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Long id;
    private String movieName;

}
