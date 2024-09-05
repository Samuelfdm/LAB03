package edu.eci.cvds.tdd.library;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import edu.eci.cvds.tdd.library.*;
import edu.eci.cvds.tdd.library.book.Book;
import edu.eci.cvds.tdd.library.loan.Loan;
import edu.eci.cvds.tdd.library.loan.LoanStatus;
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

    @Test
    void testAddBookNullBook() {
        // Arrange
        Library library = new Library();

        // Act
        boolean result = library.addBook(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void testFindBookAvailable() {
        // Arrange
        Library library = new Library();
        Book book = new Book("LibroNuevo", "Santiago", "ISBN123");
        library.addBook(book);

        // Act
        Book foundBook = library.findBook("ISBN123");

        // Assert
        assertNotNull(foundBook);
    }

    @Test
    void testFindUserExists() {
        // Arrange
        Library library = new Library();
        User user = new User("Santiago", "123"); 
        library.addUser(user);

        // Act
        User foundUser = library.findUser("123");

        // Assert
        assertNotNull(foundUser);
    }

    @Test
    public void testLoanBookUserAvailableAndExisting() {
        // Arrange
        Library library = new Library();
        Book book = new Book("pepe", "Clean Code", "12345");
        library.addBook(book);
        User user = new User("Sam", "11111");
        library.addUser(user);

        // Act
        Loan loan = library.loanABook(user.getId(), book.getIsbn());

        // Assert
        assertNotNull(loan);
        assertEquals(book, loan.getBook());
        assertEquals(user, loan.getUser());
        assertEquals(LoanStatus.ACTIVE, loan.getStatus());
    }

    @Test
    public void testLoanBookInexistenteNull() {
        // Arrange
        Library library = new Library();
        User user = new User("Sam", "11111");
        library.addUser(user);

        // Act
        Loan loan = library.loanABook(user.getId(), null);

        // Assert
        assertNull(loan);
    }
}