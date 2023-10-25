package hh.sof03.bookstore;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import hh.sof03.bookstore.domain.Book;
import hh.sof03.bookstore.domain.BookRepository;
import hh.sof03.bookstore.domain.Category;
import hh.sof03.bookstore.domain.CategoryRepository;
import hh.sof03.bookstore.domain.User;
import hh.sof03.bookstore.domain.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookstoreRepositoryTest {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	// BookRepository tests:

	// Test for creating a new book and adding it to database
	@Test
	public void createNewBook() {
		Category category = new Category("Fantasy");
		Book book = new Book("Harry Potter and the Order of the Phoenix", "J.K. Rowling", 2003, "978-0439567626", 25.00,
				category);
		bookRepository.save(book);

		assertThat(book.getId()).isNotNull();
	}

	// Test for deleting a book from database
	@Test
	public void deleteBook() {
		Category category = new Category("TestCategory");
		Book book = new Book("Harry Potter's test book", "Just. Kidding. Rowling", 2050, "123-3456789", 2500, category);
		bookRepository.save(book);
		Long bookId = book.getId();
		bookRepository.deleteById(bookId);
		Book deletedBook = bookRepository.findById(bookId).orElse(null);

		assertNull(deletedBook);
	}

	// Test for finding a book by its title from database
	@Test
	public void findBookByTitle() {
		List<Book> books = bookRepository.findByTitle("Harry Potter and the Philosopher's Stone");

		assertThat(books).hasSize(1);
		assertThat(books.get(0).getIsbn()).isEqualTo("978-0747532743");
	}

	// Test for finding all books in database
	@Test
	public void findAllBooks() {
		Iterable<Book> books = bookRepository.findAll();

		assertThat(books).isNotNull();
		assertThat(books.iterator().hasNext());

	}

	// CategoryRepository tests:

	// Test for creating a new category into database
	@Test
	public void createNewCategory() {
		Category category = new Category("Scifi");
		categoryRepository.save(category);

		assertThat(category.getCategoryId()).isNotNull();
	}

	// Test for deleting a category from database
	@Test
	public void deleteCategory() {
		Category category = new Category("Classics");
		categoryRepository.save(category);
		Long categoryId = category.getCategoryId();
		categoryRepository.deleteById(categoryId);
		Category deletedCategory = categoryRepository.findById(categoryId).orElse(null);

		assertNull(deletedCategory);
	}

	// Test for finding a category by its name from database
	@Test
	public void findCategoryByName() {
		List<Category> categories = categoryRepository.findByName("Fantasy");

		assertThat(categories).hasSize(1);
		assertThat(categories.get(0).getCategoryId()).isEqualTo(1);

	}

	// Test for finding all categories from database
	@Test
	public void findAllCategories() {
		Iterable<Category> categories = categoryRepository.findAll();

		assertThat(categories).isNotNull();
		assertThat(categories.iterator().hasNext());

	}

	// UserRepository tests:

	// Test for creating a new user into database
	@Test
	public void createNewUser() {
		String pwd = "password";
		BCryptPasswordEncoder cryptEncoder = new BCryptPasswordEncoder();
		String hashPwd = cryptEncoder.encode(pwd);
		User user = new User("newUser", hashPwd, "newUser@gmail.com", "USER");
		userRepository.save(user);

		assertThat(user.getId()).isNotNull();
	}

	// Test for deleting a user from database
	@Test
	public void deleteUser() {
		String pwd = "veryhardtoguessthistestpassword";
		BCryptPasswordEncoder cryptEncoder = new BCryptPasswordEncoder();
		String hashPwd = cryptEncoder.encode(pwd);

		User user = new User("testUser", hashPwd, "testUser@testing.net", "USER");
		userRepository.save(user);
		Long userId = user.getId();
		userRepository.deleteById(userId);
		User deletedUser = userRepository.findById(userId).orElse(null);

		assertNull(deletedUser);
	}

	// Test for finding a user by username from database
	// and testing for non-existent cases
	@Test
	public void findUserByName() {
		User user = userRepository.findByUsername("user");
		User nonExistentUser = userRepository.findByUsername("nonExistentUser");

		assertThat(user).isNotNull();
		assertThat(user.getRole()).isEqualTo("USER");
		assertThat(nonExistentUser).isNull();
	}

	// Test for finding a user by email from database
	// and testing for non-existent cases
	@Test
	public void findUserByEmail() {
		User user = userRepository.findByEmail("user@bookstore.com");
		User nonExistentUser = userRepository.findByEmail("nonExistentUser@bookstore.com");
		User userWithNullEmail = userRepository.findByEmail(null);

		assertThat(user).isNotNull();
		assertThat(user.getEmail()).isEqualTo("user@bookstore.com");
		assertThat(user.getRole()).isEqualTo("USER");
		assertThat(nonExistentUser).isNull();
		assertThat(userWithNullEmail).isNull();

	}

	// Test to find all users from database
	@Test
	public void findAllUsers() {
		Iterable<User> users = userRepository.findAll();

		assertThat(users).isNotNull();
		assertThat(users.iterator().hasNext());
		assertThat(users).isNotEmpty();
	}

}
