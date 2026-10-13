package fi.haagahelia.bookstore;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class WebLayerTest {
    
    @Autowired
    private MockMvc mockMvc;

    // @Test
    // public void homePageShowsBookList() throws Exception {
    //     this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
    //         .andExpect(content().string(containsString("Books")));
    // }

    @Test
    void visitorCanSeeTheBookCatalogue() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("<h2>Books</h2>")))
                .andExpect(content().string(containsString("The Great Gatsby")));
    }

    @Test
    void booklistUrlAlsoShowsTheCatalogue() throws Exception {
        mockMvc.perform(get("/booklist"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h2>Books</h2>")));
    }

    @Test
    void visitorIsRedirectedWhenOpeningAdminOnlyAddBookPage() throws Exception {
        mockMvc.perform(get("/book/addbook"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}
