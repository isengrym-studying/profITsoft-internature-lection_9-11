package ua.klieshchunov.lection911;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ua.klieshchunov.lection911.entity.Genre;
import ua.klieshchunov.lection911.repository.GenreRepository;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Lection911Application.class)
@AutoConfigureMockMvc
public class GenreControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestUtils testUtils;

    @Test
    @Sql("/data.sql")
    public void testGetAllGenres() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/genres")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Genre[] genres = testUtils.parseResponse(mvcResult, Genre[].class);

        for (Genre genre : genres) {
            Optional<Genre> genreDbOptional = genreRepository.findById(genre.getId());
            Assertions.assertTrue(genreDbOptional.isPresent());

            Assertions.assertEquals(genre, genreDbOptional.get());
        }
    }
}
