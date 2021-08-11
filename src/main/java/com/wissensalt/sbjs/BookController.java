package com.wissensalt.sbjs;

import com.wissensalt.sbjs.model.Author;
import com.wissensalt.sbjs.model.Book;
import com.wissensalt.sbjs.model.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public Set<Book> findAllBooks() {

        return bookService.findAllBooks();
    }

    @GetMapping("/book/{id}")
    public Book findBookById(@PathVariable("id") Long id) throws Exception {

        return bookService.findBookById(id);
    }

    @GetMapping("/book/range/{min}/{max}")
    public Set<Book> findBookByRangePrice(@PathVariable("min") String min, @PathVariable("max") String max){

        return bookService.findBookByRangePrice(
                BigDecimal.valueOf(Long.parseLong(min)), BigDecimal.valueOf(Long.parseLong(max))
        );
    }


    @GetMapping("/book/cheapest")
    public Book findCheapestBook() throws Exception {

        return bookService.findCheapestBook();
    }

    @GetMapping("/book/{title}/{minPrice}")
    public Set<Book> findByTitleAndMinimumPrice(
            @PathVariable("title") String title,
            @PathVariable("minPrice") String minPrice
    ) {

        return bookService.findByTitleAndMinimumPrice(title, BigDecimal.valueOf(Long.parseLong(minPrice)));
    }

    @GetMapping("/books/paginate")
    public List<Book> paginate(
            @RequestParam("page") Integer page,
            @RequestParam("limit") Integer limit,
            @RequestParam("sort") String sort,
            @RequestParam("direction") String direction){

        return bookService.paginate(page, limit, sort, direction);
    }

    @GetMapping("/books/group/author")
    public Map<Author, List<Book>> groupByAuthor(){

        return bookService.groupByAuthor();
    }

    @GetMapping("/books/group/publisher")
    public Map<Publisher, List<Book>> groupByPublisher(){

        return bookService.groupByPublisher();
    }
}
