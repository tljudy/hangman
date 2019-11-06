package hangman.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import hangmanjpa.entities.User;

public class InitialDBTest {

	// This class is only to test the initial JPA build and database connection
	//   -- This will be deleted as soon as we start coding the entities and JUnit tests
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("HangmanEntities");
		EntityManager em = emf.createEntityManager();
		
/* 
   This is basically what a JUnit test will be doing:
       1.  Grab a known database row
             - We will use User with ID of 1
             - The data should look like this (currently): 
				+----+----------+----------+-------------+---------------------+---------------------+
				| id | username | password | totalPoints | preferredModelColor | preferredDifficulty |
				+----+----------+----------+-------------+---------------------+---------------------+
				|  1 | paulo    | paulo    |          0  | NULL                | NULL                |
	  
	   2. Verify that the data pulled from the DB via Java entity matches
*/ 
		
		User user = em.find(User.class, 1);
		
		// We won't print these out in the JUnit.  This is just for a quick test
		System.out.println("ID: " + user.getId());
		System.out.println("Username: " + user.getUsername());
		System.out.println("Password: " + user.getPassword());
		System.out.println("Preferred color: " + user.getPreferredModelColor());
		System.out.println("Preferred difficulty: " + user.getPreferredDifficulty());
		
		
		// EMs are temporary and typically run briefly inside a method 
		// Always close EMs after the method that uses it is done
		em.close();
		
		// A single EM Factory creates all of the EMs, so this only gets closed upon the entire program completion
		emf.close();
	}
}
