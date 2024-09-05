package edu.eci.cvds.tdd.library;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.eci.cvds.tdd.library.*;
import edu.eci.cvds.tdd.library.book.Book;
import edu.eci.cvds.tdd.library.loan.Loan;
import edu.eci.cvds.tdd.library.loan.LoanStatus;
import edu.eci.cvds.tdd.library.user.User;

class LibraryTest {

    private Library library;
    private Book book1;
    private User user1;
    private Book book2;
    private User user2;

    @BeforeEach
    public void setUp() {
        library = new Library();
        book1 = new Book("Libro1", "Samuel", "ISBN12345");
        book2 = new Book("Libro2", "Santiago", "ISBN67890");
        user1 = new User("Samuel Felipe", "12345");
        user2 = new User("Santiago Silva", "67890");
    }

    @Test
    void testAddBookNewBook() {
        // Act
        boolean result = library.addBook(book1);

        // Assert
        assertTrue(result);
        assertEquals(1, library.getBooks().get(book1));
    }

    @Test
    void testAddBookExistingBook() {
        // Arrange
        library.addBook(book1); // Agregamos el libro

        // Act
        boolean result = library.addBook(book1); // Volvemos a agregarlo y verificamos el resultado

        // Assert
        assertTrue(result);
        assertEquals(2, library.getBooks().get(book1)); // Verifica la cantidad del libro existente
    }

    @Test
    void testAddBookNullBook() {
        // Act
        boolean result = library.addBook(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void testFindBookAvailable() {
        // Arrange
        library.addBook(book2);

        // Act
        Book foundBook = library.findBook(book2.getIsbn());

        // Assert
        assertNotNull(foundBook);
    }

    @Test
    void testFindUserExists() {
        // Arrange
        library.addUser(user2);

        // Act
        User foundUser = library.findUser(user2.getId());

        // Assert
        assertNotNull(foundUser);
    }

    @Test
    public void testLoanBookUserAvailableAndExisting() {
        // Arrange
        library.addBook(book1);
        library.addUser(user1);

        // Act
        Loan loan = library.loanABook(user1.getId(), book1.getIsbn());

        // Assert
        assertNotNull(loan);
        assertEquals(book1, loan.getBook());
        assertEquals(user1, loan.getUser());
        assertEquals(LoanStatus.ACTIVE, loan.getStatus());
    }

    @Test
    public void testLoanBookNonexistentBookNull() {
        // Arrange
        library.addUser(user1);

        // Act
        Loan loan = library.loanABook(user1.getId(), null);

        // Assert
        assertNull(loan);
    }

    @Test
    public void testLoanBookNonexistentISBN() {
        // Arrange
        library.addUser(user2);
        library.addBook(book2);

        // Act
        Loan loan = library.loanABook(user2.getId(), "8888");

        // Assert
        assertNull(loan);
    }

    @Test
    public void testLoanBookNonexistentUserNull() {
        // Arrange
        library.addBook(book1);
        
        // Act
        Loan loan = library.loanABook(null, book1.getIsbn());

        // Assert
        assertNull(loan);
    }

    @Test
    public void testLoanBookNonexistentUserId() {
        // Arrange
        library.addUser(user1);
        library.addBook(book1);
        
        // Act
        Loan loan = library.loanABook("2222", book1.getIsbn());

        // Assert
        assertNull(loan);
    }

    @Test
    public void testLoanBookExistingLoan() {
        // Arrange
        library.addBook(book1);
        library.addUser(user1);
        library.loanABook(user1.getId(), book1.getIsbn()); // Préstamo inicial

        // Act
        Loan loan = library.loanABook(user1.getId(), book1.getIsbn()); //Ya fue prestado al mismo

        // Assert
        assertNull(loan);
    }

    @Test
    public void testLoanBookNotAvailable() {
        // Arrange
        library.addBook(book2);
        library.addUser(user1);
        library.addUser(user2);
        library.loanABook(user1.getId(), book2.getIsbn()); // Préstamo del libro

        // Act
        Loan loan = library.loanABook(user2.getId(), book2.getIsbn()); //Se acabaron

        // Assert
        assertEquals(0, library.getBooks().get(book2)); //No hay disponibles
        assertNull(loan);
    }

    @Test
    public void testReturnLoanValidLoan() {
        // Arrange
        library.addBook(book1);
        library.addUser(user1);
        Loan loan = library.loanABook(user1.getId(), book1.getIsbn());

        // Act
        Loan returnedLoan = library.returnLoan(loan);

        // Assert
        assertNotNull(returnedLoan);
        assertEquals(LoanStatus.RETURNED, returnedLoan.getStatus());
        assertEquals(1, library.getBooks().get(book1));
    }

    public void testReturnLoanNullLoan() {
        //Act
        Loan returnedLoan = library.returnLoan(null);
        // Assert
        assertNull(returnedLoan);
      }
}