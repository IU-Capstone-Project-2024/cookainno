package org.innopolis.cookainno.service;

import lombok.RequiredArgsConstructor;
import org.innopolis.cookainno.dto.GetUserInfoResponse;
import org.innopolis.cookainno.dto.SaveUserInfoRequest;
import org.innopolis.cookainno.dto.SaveUserInfoResponse;
import org.innopolis.cookainno.entity.Role;
import org.innopolis.cookainno.entity.User;
import org.innopolis.cookainno.exception.UserAlreadyExistsException;
import org.innopolis.cookainno.exception.UserNotFoundException;
import org.innopolis.cookainno.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return repository.save(user);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {

        if (user.isEnabled() && repository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует");
        }

        if (user.isEnabled() && repository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Пользователь с таким email уже существует");
        }

        if (repository.existsByUsername(user.getUsername())) {
            User existingUserByUsername = repository.findByUsername(user.getUsername()).get();
            return update(existingUserByUsername, user);
        }
        if (repository.existsByEmail(user.getEmail())) {
            User existingUserByUsername = repository.findByEmail(user.getEmail()).get();
            return update(existingUserByUsername, user);
        }

        return save(user);
    }

    private User update(User existingUser, User newUser) {
        existingUser.setUsername(newUser.getUsername());
        existingUser.setEmail(newUser.getEmail());
        existingUser.setConfirmationCode(newUser.getConfirmationCode());

        return repository.save(existingUser);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким username не найден"));

    }

    /**
     * Получение пользователя по почте
     *
     * @return пользователь
     */
    public User getByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с такиим email не найден"));
    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */
    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }

    /**
     * User info update
     *
     * @param request new user data
     * @return updated user data
     */
    @Transactional
    public SaveUserInfoResponse updateUserInfo(SaveUserInfoRequest request) {
        User user = repository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setHeight(request.getHeight());
        user.setWeight(request.getWeight());
        user.setDateOfBirth(request.getDateOfBirth());

        repository.save(user);

        return new SaveUserInfoResponse(
                user.getId(),
                user.getHeight(),
                user.getWeight(),
                user.getDateOfBirth()
        );
    }

    /**
     * Получение информации о пользователе по ID
     *
     * @return информация о пользователе
     */
    public GetUserInfoResponse getUserInfoById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

//        int age = Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();

        return new GetUserInfoResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getHeight(),
                user.getWeight(),
                user.getDateOfBirth()
        );
    }

    /**
     * Удаление аккаунта пользователя
     *
     * @param id идентификатор пользователя
     */
    public void deleteUserById(Long id) {
        if (!repository.existsById(id)) {
            throw new UserNotFoundException("User not found");
        }
        repository.deleteById(id);
    }
}