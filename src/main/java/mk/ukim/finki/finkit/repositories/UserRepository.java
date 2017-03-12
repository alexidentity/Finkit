package mk.ukim.finki.finkit.repositories;

import mk.ukim.finki.finkit.model.data.User;

public interface UserRepository extends BaseRepository<User> {
	
	User findUserByLogin(String login);
	
	User findUserById(Long id);
	
}
