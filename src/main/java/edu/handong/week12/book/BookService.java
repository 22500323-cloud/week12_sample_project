package edu.handong.week12.book;

import org.springframework.stereotype.Service;
import edu.handong.week12.book.dto.BookRequest;
import edu.handong.week12.book.dto.BookResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookService {
  private final BookRepository repository;

  public BookService(BookRepository repository) {
    this.repository = repository;
  }

  public List<BookResponse> findAll() {
    return repository.findAll().stream()
            .map(this::toResponse)
            .toList();
  }

  public BookResponse findById(Long id) {
    Book book = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Book not found"
            ));

    return toResponse(book);
  }

  public BookResponse create(BookRequest request) {
    Book book = new Book(
            request.title(),
            request.author(),
            request.price()
    );

    return toResponse(repository.save(book));
  }

  public BookResponse update(Long id, BookRequest input) {
    Book book = findBook(id);

    book.update(input.title(), input.author(), input.price());

    return toResponse(repository.save(book));
  }

  public void delete(Long id) {
    Book book = findBook(id);
    repository.delete(book);
  }

  private Book findBook(Long id) {
    return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Book not found"
            ));
  }

  private BookResponse toResponse(Book book) {
    return new BookResponse(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getPrice()
    );
  }
}
