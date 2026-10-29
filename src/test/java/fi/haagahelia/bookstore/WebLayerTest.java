package fi.haagahelia.bookstore;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fi.haagahelia.bookstore.domain.AppUser;
import fi.haagahelia.bookstore.domain.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@AutoConfigureMockMvc
public class WebLayerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Test
    void visitorCanOpenSignupPage() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("name=\"email\"")));
    }

    @Test
    void visitorCanRegisterWithSignupForm() throws Exception {
        MvcResult signupPage = mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andReturn();
        Matcher csrfMatcher = Pattern.compile("name=\"_csrf\" value=\"([^\"]+)\"")
                .matcher(signupPage.getResponse().getContentAsString());
        assertTrue(csrfMatcher.find());

        mockMvc.perform(post("/signup")
                        .session((org.springframework.mock.web.MockHttpSession) signupPage.getRequest().getSession(false))
                        .param("_csrf", csrfMatcher.group(1))
                        .param("username", "new-reader")
                        .param("password", "reader-password")
                        .param("email", "reader@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?registered"));

        AppUser registeredUser = userRepository.findByUsername("new-reader").orElseThrow();
        assertThat(registeredUser.getRole()).isEqualTo("USER");
        assertThat(registeredUser.getEmail()).isEqualTo("reader@example.com");
        assertThat(passwordEncoder.matches("reader-password", registeredUser.getPasswordHash())).isTrue();
    }
}
