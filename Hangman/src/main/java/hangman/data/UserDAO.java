package hangman.data;

import java.util.List;

import hangmanjpa.entities.User;

public interface UserDAO {
	User getUserById(int id);
	User findUserByUsername(String username);
	List<User> getAllUsers();
	User addUser(UserDTO userDTO);
	User update(User user);
	List<User> getLeadersByPoints();
}
