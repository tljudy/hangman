package hangman.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hangmanjpa.entities.User;

class UserTest {
	static EntityManagerFactory emf;
	EntityManager em;
	User user;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("HangmanEntities");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		emf.close();
	}
	
	@BeforeEach
	void setUp() throws Exception {
		em = emf.createEntityManager();
		user = em.find(User.class, 1);
	}
	
	@AfterEach
	void tearDown() throws Exception {
		em.close();
		user = null;
	}

//	SELECT * FROM user WHERE id = 1;
//	+----+----------+----------+-------------+---------------------+---------------------+
//	| id | username | password | totalPoints | preferredModelColor | preferredDifficulty |
//	+----+----------+----------+-------------+---------------------+---------------------+
//	|  1 | paulo    | paulo    |           0 | NULL                | NULL                |
//	+----+----------+----------+-------------+---------------------+---------------------+
	@Test
	void test_get_user_from_db() {
		assertEquals("paulo", user.getUsername());
		assertEquals("paulo", user.getPassword());
		assertEquals(0, user.getTotalPoints());
		assertNull(user.getPreferredDifficulty());
		assertNull(user.getPreferredDifficulty());
	}
	
	// test that the user has secret questions
	@Test
	void test_user_secret_questions() {
		// replace this
		System.out.println(user.getUserSecretQuestions());
	}
	
	// test that the user has games...

}
