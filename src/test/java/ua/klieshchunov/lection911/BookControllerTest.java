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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ua.klieshchunov.lection911.entity.Book;
import ua.klieshchunov.lection911.repository.BookRepository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Lection911Application.class)
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestUtils testUtils;

    @Test
    @Sql("/data.sql")
    public void testGetAllBooks() throws Exception {
        String pageSize = "1";
        String pageNum = "1";
        String genreId = "2";
        String author = "Erich Remark";

        Map<String, List<String>> params = new HashMap<>();
        params.put("pageSize", List.of(pageSize));
        params.put("pageNum", List.of(pageNum));
        params.put("genreId", List.of(genreId));
        params.put("author", List.of(author));

        MultiValueMap<String, String> paramsMultimap = new LinkedMultiValueMap<>(params);
        paramsMultimap.putAll(params);

        MvcResult mvcResult = mvc.perform(get("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .params(paramsMultimap))
                .andExpect(status().isOk())
                .andReturn();

        Book[] books = testUtils.parseResponse(mvcResult, Book[].class);

        for (Book book : books) {
            Optional<Book> bookDbOptional = bookRepository.findById(book.getId());
            Assertions.assertTrue(bookDbOptional.isPresent());

            Assertions.assertEquals(book, bookDbOptional.get());
        }

    }

    @Test
    @Sql("/data.sql")
    public void testGetCertainBook() throws Exception {
        String id = "1";

        MvcResult mvcResult = mvc.perform(get(String.format("/books/%s", id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Book book = testUtils.parseResponse(mvcResult, Book.class);
        Optional<Book> bookDbOptional = bookRepository.findById(book.getId());

        Assertions.assertTrue(bookDbOptional.isPresent());
        Assertions.assertEquals(book, bookDbOptional.get());
    }

    @Test
    @Sql("/data.sql")
    public void addNewBook() throws Exception {
        String bookId = "7";
        String title = "The Picture of Dorian Gray";
        String author = "Oscar Wilde";

        String genreId = "3";
        String genreName = "Fiction";
        String pagesNumber = "320";

        String body = """
                {
                    "title" : "%s",
                    "author" : "%s",
                    "genre" : {
                        "id" : "%s",
                        "name" : "%s"
                    },
                    "pagesNumber" : "%s"
                }
                """.formatted(title, author, genreId, genreName, pagesNumber);

        MvcResult mvcResult = mvc.perform(post("/books/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn();

        Book book = testUtils.parseResponse(mvcResult, Book.class);
        Optional<Book> bookDbOptional = bookRepository.findById(Integer.parseInt(bookId));

        Assertions.assertTrue(bookDbOptional.isPresent());
        Assertions.assertEquals(book, bookDbOptional.get());
    }

    @Test
    @Sql("/data.sql")
    public void updateBook() throws Exception {
        String bookId = "1";
        String title = "The Picture of Dorian Gray";
        String author = "Oscar Wilde";

        String genreId = "3";
        String genreName = "Fiction";
        String pagesNumber = "320";

        String body = """
                {
                    "id" : "%s",
                    "title" : "%s",
                    "author" : "%s",
                    "genre" : {
                        "id" : "%s",
                        "name" : "%s"
                    },
                    "pagesNumber" : "%s"
                }
                """.formatted(bookId, title, author, genreId, genreName, pagesNumber);

        MvcResult mvcResult = mvc.perform(patch("/books/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn();

        Book book = testUtils.parseResponse(mvcResult, Book.class);
        Optional<Book> bookDbOptional = bookRepository.findById(Integer.parseInt(bookId));

        Assertions.assertTrue(bookDbOptional.isPresent());
        Assertions.assertEquals(book, bookDbOptional.get());
    }

    @Test
    @Sql("/data.sql")
    public void testDeleteBook() throws Exception {
        String id = "1";

        mvc.perform(delete(String.format("/books/%s", id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Optional<Book> bookDbOptional = bookRepository.findById(Integer.parseInt(id));

        Assertions.assertTrue(bookDbOptional.isEmpty());
    }

}
