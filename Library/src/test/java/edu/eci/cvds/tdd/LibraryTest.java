package test.java.edu.eci.cvds.tdd;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import edu.eci.cvds.tdd.library.*;
import edu.eci.cvds.tdd.library.book.Book;
import edu.eci.cvds.tdd.library.loan.Loan;
import edu.eci.cvds.tdd.library.user.User;

class LibraryTest {

    @Test
    void testAddBookNewBook() {
        // Arrange
        Library library = new Library();
        Book book = new Book("Libro1", "Samuel", "ISBN12345");

        // Act
        boolean result = library.addBook(book);

        // Assert
        assertTrue(result);
        assertEquals(1, library.getBooks().get(book));
    }

    @Test
    void testAddBookExistingBook() {
        // Arrange
        Library library = new Library();
        Book book = new Book("Libro1", "Samuel", "ISBN12345");
        library.addBook(book); // Agregamos el libro

        // Act
        boolean result = library.addBook(book); // Volvemos a agregarlo y verificamos el resultado

        // Assert
        assertTrue(result);
        assertEquals(2, library.getBooks().get(book)); // Verifica la cantidad del libro existente
    }
}