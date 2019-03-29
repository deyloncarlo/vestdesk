package br.com.vestdesk.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.config.Constants;
import br.com.vestdesk.domain.Authority;
import br.com.vestdesk.domain.User;
import br.com.vestdesk.repository.AuthorityRepository;
import br.com.vestdesk.repository.PersistentTokenRepository;
import br.com.vestdesk.repository.UserRepository;
import br.com.vestdesk.security.AuthoritiesConstants;
import br.com.vestdesk.security.SecurityUtils;
import br.com.vestdesk.service.dto.UserDTO;
import br.com.vestdesk.service.util.RandomUtil;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService
{

	private final Logger log = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final PersistentTokenRepository persistentTokenRepository;

	private final AuthorityRepository authorityRepository;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			PersistentTokenRepository persistentTokenRepository, AuthorityRepository authorityRepository)
	{
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.persistentTokenRepository = persistentTokenRepository;
		this.authorityRepository = authorityRepository;
	}

	public Optional<User> activateRegistration(String key)
	{
		this.log.debug("Activating user for activation key {}", key);
		return this.userRepository.findOneByActivationKey(key).map(user ->
		{
			// activate given user for the registration key.
			user.setActivated(true);
			user.setActivationKey(null);
			this.log.debug("Activated user: {}", user);
			return user;
		});
	}

	public Optional<User> completePasswordReset(String newPassword, String key)
	{
		this.log.debug("Reset user password for reset key {}", key);

		return this.userRepository.findOneByResetKey(key)
				.filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400))).map(user ->
				{
					user.setPassword(this.passwordEncoder.encode(newPassword));
					user.setResetKey(null);
					user.setResetDate(null);
					return user;
				});
	}

	public Optional<User> requestPasswordReset(String mail)
	{
		return this.userRepository.findOneByEmailIgnoreCase(mail).filter(User::getActivated).map(user ->
		{
			user.setResetKey(RandomUtil.generateResetKey());
			user.setResetDate(Instant.now());
			return user;
		});
	}

	public User registerUser(UserDTO userDTO, String password)
	{

		User newUser = new User();
		Authority authorityUser = this.authorityRepository.findOne(AuthoritiesConstants.USER);
		Authority authorityAdmin = this.authorityRepository.findOne(AuthoritiesConstants.ADMIN);
		Set<Authority> authorities = new HashSet<>();
		String encryptedPassword = this.passwordEncoder.encode(password);
		newUser.setLogin(userDTO.getLogin());
		// new user gets initially a generated password
		newUser.setPassword(encryptedPassword);
		newUser.setFirstName(userDTO.getFirstName());
		newUser.setLastName(userDTO.getLastName());
		newUser.setEmail(userDTO.getEmail());
		newUser.setImageUrl(userDTO.getImageUrl());
		newUser.setLangKey(userDTO.getLangKey());
		// new user is not active
		newUser.setActivated(true);
		// new user gets registration key
		newUser.setActivationKey(RandomUtil.generateActivationKey());
		authorities.add(authorityUser);
		authorities.add(authorityAdmin);
		newUser.setAuthorities(authorities);
		this.userRepository.save(newUser);
		this.log.debug("Created Information for User: {}", newUser);
		return newUser;
	}

	public User createUser(UserDTO userDTO)
	{
		User user = new User();
		user.setLogin(userDTO.getLogin());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setImageUrl(userDTO.getImageUrl());
		if (userDTO.getLangKey() == null)
		{
			user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
		}
		else
		{
			user.setLangKey(userDTO.getLangKey());
		}
		if (userDTO.getAuthorities() != null)
		{
			Set<Authority> authorities = userDTO.getAuthorities().stream().map(this.authorityRepository::findOne)
					.collect(Collectors.toSet());
			user.setAuthorities(authorities);
		}
		String encryptedPassword = this.passwordEncoder.encode(RandomUtil.generatePassword());
		user.setPassword(encryptedPassword);
		user.setResetKey(RandomUtil.generateResetKey());
		user.setResetDate(Instant.now());
		user.setActivated(true);
		this.userRepository.save(user);
		this.log.debug("Created Information for User: {}", user);
		return user;
	}

	/**
	 * Update basic information (first name, last name, email, language) for the
	 * current user.
	 *
	 * @param firstName first name of user
	 * @param lastName last name of user
	 * @param email email id of user
	 * @param langKey language key
	 * @param imageUrl image URL of user
	 */
	public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl)
	{
		SecurityUtils.getCurrentUserLogin().flatMap(this.userRepository::findOneByLogin).ifPresent(user ->
		{
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setLangKey(langKey);
			user.setImageUrl(imageUrl);
			this.log.debug("Changed Information for User: {}", user);
		});
	}

	/**
	 * Update all information for a specific user, and return the modified user.
	 *
	 * @param userDTO user to update
	 * @return updated user
	 */
	public Optional<UserDTO> updateUser(UserDTO userDTO)
	{
		return Optional.of(this.userRepository.findOne(userDTO.getId())).map(user ->
		{
			user.setLogin(userDTO.getLogin());
			user.setFirstName(userDTO.getFirstName());
			user.setLastName(userDTO.getLastName());
			user.setEmail(userDTO.getEmail());
			user.setImageUrl(userDTO.getImageUrl());
			user.setActivated(userDTO.isActivated());
			user.setLangKey(userDTO.getLangKey());
			Set<Authority> managedAuthorities = user.getAuthorities();
			managedAuthorities.clear();
			userDTO.getAuthorities().stream().map(this.authorityRepository::findOne).forEach(managedAuthorities::add);
			this.log.debug("Changed Information for User: {}", user);
			return user;
		}).map(UserDTO::new);
	}

	public void deleteUser(String login)
	{
		this.userRepository.findOneByLogin(login).ifPresent(user ->
		{
			this.userRepository.delete(user);
			this.log.debug("Deleted User: {}", user);
		});
	}

	public void changePassword(String password)
	{
		SecurityUtils.getCurrentUserLogin().flatMap(this.userRepository::findOneByLogin).ifPresent(user ->
		{
			String encryptedPassword = this.passwordEncoder.encode(password);
			user.setPassword(encryptedPassword);
			this.log.debug("Changed password for User: {}", user);
		});
	}

	@Transactional(readOnly = true)
	public Page<UserDTO> getAllManagedUsers(Pageable pageable)
	{
		return this.userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
	}

	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthoritiesByLogin(String login)
	{
		return this.userRepository.findOneWithAuthoritiesByLogin(login);
	}

	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthorities(Long id)
	{
		return this.userRepository.findOneWithAuthoritiesById(id);
	}

	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthorities()
	{
		return SecurityUtils.getCurrentUserLogin().flatMap(this.userRepository::findOneWithAuthoritiesByLogin);
	}

	@Transactional(readOnly = true)
	public User getCurrentUser()
	{
		return SecurityUtils.getCurrentUserLogin().flatMap(this.userRepository::findOneByLogin).get();
	}

	/**
	 * Persistent Token are used for providing automatic authentication, they
	 * should be automatically deleted after 30 days.
	 * <p>
	 * This is scheduled to get fired everyday, at midnight.
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void removeOldPersistentTokens()
	{
		LocalDate now = LocalDate.now();
		this.persistentTokenRepository.findByTokenDateBefore(now.minusMonths(1)).forEach(token ->
		{
			this.log.debug("Deleting token {}", token.getSeries());
			User user = token.getUser();
			user.getPersistentTokens().remove(token);
			this.persistentTokenRepository.delete(token);
		});
	}

	/**
	 * Not activated users should be automatically deleted after 3 days.
	 * <p>
	 * This is scheduled to get fired everyday, at 01:00 (am).
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void removeNotActivatedUsers()
	{
		List<User> users = this.userRepository
				.findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS));
		for (User user : users)
		{
			this.log.debug("Deleting not activated user {}", user.getLogin());
			this.userRepository.delete(user);
		}
	}

	/**
	 * @return a list of all the authorities
	 */
	public List<String> getAuthorities()
	{
		return this.authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
	}

	public List<User> getAllActivesUser()
	{
		return this.userRepository.findAll();
	}

}
