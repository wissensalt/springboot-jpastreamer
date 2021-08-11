package com.wissensalt.sbjs;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.wissensalt.sbjs.model.Author;
import com.wissensalt.sbjs.model.Book;
import com.wissensalt.sbjs.model.Book$;
import com.wissensalt.sbjs.model.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class BookService {

    private final JPAStreamer jpaStreamer;

    Set<Book> findAllBooks(){
        return jpaStreamer.stream(Book.class).collect(Collectors.toSet());
    }

    Book findBookById(Long id) throws Exception {
        return jpaStreamer.stream(Book.class)
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Book with id : " + id + " is not found"));
    }

    Set<Book> findBookByRangePrice(BigDecimal min, BigDecimal max) {
        return jpaStreamer.stream(Book.class)
                .filter(Book$.price.between(min, max))
                .collect(Collectors.toSet());
    }

    Book findCheapestBook() throws Exception {
        return jpaStreamer.stream(Book.class)
                .min(Comparator.comparing(Book::getPrice))
                .orElseThrow(() -> new Exception("Cheapest Book not Found!"));
    }

    List<Book> paginate(Integer page, Integer limit, String sort, String direction) {
        return jpaStreamer.stream(Book.class)
                .skip(page)
                .limit(limit)
                .sorted(sortComparator(sort, direction))
                .collect(Collectors.toList());
    }

    private Comparator<Book> sortComparator(String sort, String direction) {
        if (sort.equalsIgnoreCase("title")) {
            if (direction.equalsIgnoreCase("desc")) {
                return Comparator.comparing(Book::getTitle).reversed();
            }

            return Comparator.comparing(Book::getTitle);
        }

        if (sort.equalsIgnoreCase("price")) {
            if (direction.equalsIgnoreCase("desc")) {
                return Comparator.comparing(Book::getPrice).reversed();
            }

            return Comparator.comparing(Book::getPrice);
        }

        if (sort.equalsIgnoreCase("numberOfPages")) {
            if (direction.equalsIgnoreCase("desc")) {
                return Comparator.comparing(Book::getNumberOfPages).reversed();
            }

            return Comparator.comparing(Book::getNumberOfPages);
        }

        if (direction.equalsIgnoreCase("desc")) {
            return Comparator.comparing(Book::getId).reversed();
        }

        return Comparator.comparing(Book::getId);

    }

    Set<Book> findByTitleAndMinimumPrice(String title, BigDecimal minPrice) {
        final Predicate<Book> isNameEqual = Book$.title.containsIgnoreCase(title);
        final Predicate<Book> isPriceGreaterThan = Book$.price.greaterThan(minPrice);

        return jpaStreamer.stream(Book.class)
                .filter(isNameEqual.and(isPriceGreaterThan))
                .collect(Collectors.toSet());
    }

    Map<Author, List<Book>> groupByAuthor() {
        return jpaStreamer.stream(Book.class)
                .collect(Collectors.groupingBy(Book::getAuthor));
    }

    Map<Publisher, List<Book>> groupByPublisher() {
        return jpaStreamer.stream(Book.class)
                .collect(Collectors.groupingBy(Book::getPublisher));
    }
}
