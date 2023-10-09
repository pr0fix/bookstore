package hh.sof03.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.sof03.bookstore.domain.Book;
import hh.sof03.bookstore.domain.BookRepository;
import hh.sof03.bookstore.domain.Category;
import hh.sof03.bookstore.domain.CategoryRepository;
import hh.sof03.bookstore.domain.User;
import hh.sof03.bookstore.domain.UserRepository;

@SpringBootApplication
public class BookstoreApplication {
	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner BookRunner(BookRepository bookRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
		return (args) -> {
			log.info("save a couple of categories");
			Category category1 = new Category("Fantasy");
			categoryRepository.save(category1);
			Category category2 = new Category("Mystery");
			categoryRepository.save(category2);
			Category category3 = new Category("Fiction");
			categoryRepository.save(category3);

			log.info("save a couple of books");
			bookRepository.save(
					new Book("Harry Potter and the Chamber of Secrets", "J.K. Rowling", 2002, "978-8831000154", 59.95,
							category3));
			bookRepository.save(new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", 1997,
					"978-0747532743", 16.39, category1));

			log.info("create a couple of users");
			User user1 = new User("user", "$2a$10$NJv3yK1GaqZjGeiJXKVE6ewclpHLw1q/PU0tkbA6ASZSRGSo0USHC", "user@bookstore.com", "USER");
			User user2 = new User("admin", "$2a$10$H5jQf38AHhWNBBQRUu.W4.KM0z8ejD.02wCqmA7f3DRGYmPjps.TC", "admin@bookstore.com", "ADMIN");

			
			log.info("save users");
			userRepository.save(user1);
			userRepository.save(user2);

			log.info("fetch all categories");
			for (Category category : categoryRepository.findAll()) {
				log.info(category.toString());
			}
			log.info("fetch all books");
			for (Book book : bookRepository.findAll()) {
				log.info(book.toString());
			}

		};

	};
}
