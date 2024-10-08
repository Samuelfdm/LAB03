package edu.eci.cvds.tdd.library;

import edu.eci.cvds.tdd.library.book.Book;
import edu.eci.cvds.tdd.library.loan.Loan;
import edu.eci.cvds.tdd.library.loan.LoanStatus;
import edu.eci.cvds.tdd.library.user.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

/**
 * Library responsible for manage the loans and the users.
 */
public class Library {

    private final List<User> users;
    private final Map<Book, Integer> books;
    private final List<Loan> loans;

    public Library() {
        users = new ArrayList<>();
        books = new HashMap<>();
        loans = new ArrayList<>();
    }

    /**
     * Adds a new {@link edu.eci.cvds.tdd.library.book.Book} into the system, the book is store in a Map that contains
     * the {@link edu.eci.cvds.tdd.library.book.Book} and the amount of books available, if the book already exist the
     * amount should increase by 1 and if the book is new the amount should be 1, this method returns true if the
     * operation is successful false otherwise.
     *
     * @param book The book to store in the map.
     *
     * @return true if the book was stored false otherwise.
     */
    public boolean addBook(Book book) {
        boolean state = true;
        if (book == null) {
            state = false;
        } else if (books.containsKey(book)){
            books.put(book, books.get(book) + 1);
        } else {
            books.put(book, 1);
        }
        return state;
    }

    /**
     * This method creates a new loan with for the User identify by the userId and the book identify by the isbn,
     * the loan should be store in the list of loans, to successfully create a loan is required to validate that the
     * book is available, that the user exist and the same user could not have a loan for the same book
     * {@link edu.eci.cvds.tdd.library.loan.LoanStatus#ACTIVE}, once these requirements are meet the amount of books is
     * decreased and the loan should be created with {@link edu.eci.cvds.tdd.library.loan.LoanStatus#ACTIVE} status and
     * the loan date should be the current date.
     *
     * @param userId id of the user.
     * @param isbn book identification.
     *
     * @return The new created loan.
     */
    public Loan loanABook(String userId, String isbn) {
        Loan answer = null;
        User user = findUser(userId);
        Book book = findBook(isbn);
        if (user != null && book != null) {
            // Check for existing active loan for the same book
            boolean existingLoan = false;
            for (Loan loan : loans) {
                if (loan.getUser().equals(user) && loan.getBook().equals(book) && loan.getStatus() == LoanStatus.ACTIVE) {
                    existingLoan = true;
                }
            }

            if (!existingLoan) {
                //Check books amount
                int amount = books.get(book);
                if (amount >= 1) {
                    answer = new Loan(user, book, LocalDateTime.now(), LoanStatus.ACTIVE);
                    loans.add(answer);
                    books.put(book, amount - 1);
                }
            }
        }       
        return answer;
    }

    /**
     * This method return a loan, meaning that the amount of books should be increased by 1, the status of the Loan
     * in the loan list should be {@link edu.eci.cvds.tdd.library.loan.LoanStatus#RETURNED} and the loan return
     * date should be the current date, validate that the loan exist.
     *
     * @param loan loan to return.
     *
     * @return the loan with the RETURNED status.
     */
    public Loan returnLoan(Loan loan) {
        if (loan == null) {
            return null;
        }
        loan.setStatus(LoanStatus.RETURNED);
        loan.setReturnDate(LocalDateTime.now());
        loans.add(loan);
        // Update book availability
        int count = books.get(loan.getBook());
        books.put(loan.getBook(), count + 1);
    
        return loan;
    }

    public boolean addUser(User user) {
        return users.add(user);
    }

    public Book findBook(String isbn) {
        for (Book book : books.keySet()) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }
    
    public User findUser(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Return a copy of the map or a read-only view
     * @return the copy of the map books
     */
    public Map<Book, Integer> getBooks() {
        return Collections.unmodifiableMap(new HashMap<>(books));
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public List<User> getUsers() {
        return users;
    }
}