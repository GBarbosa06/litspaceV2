package barbosa.guilherme.LitSpacev2.user.infrastructure.web;

import barbosa.guilherme.LitSpacev2.user.application.dto.in.UpdatePasswordInput;
import barbosa.guilherme.LitSpacev2.user.application.dto.out.UserOutput;
import barbosa.guilherme.LitSpacev2.user.application.usecase.*;
import barbosa.guilherme.LitSpacev2.user.infrastructure.web.dto.CreateUserRequest;
import barbosa.guilherme.LitSpacev2.user.infrastructure.web.dto.UpdatePasswordRequest;
import barbosa.guilherme.LitSpacev2.user.infrastructure.web.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final FindUserByUsernameUseCase findUserByUsernameUseCase;
    private final ListUsersUseCase listUsersUseCase;
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;

    public UserController(
            CreateUserUseCase createUserUseCase,
            DeleteUserUseCase deleteUserUseCase,
            FindUserByIdUseCase findUserByIdUseCase,
            FindUserByUsernameUseCase findUserByUsernameUseCase,
            ListUsersUseCase listUsersUseCase,
            UpdateUserPasswordUseCase updateUserPasswordUseCase
    ) {
        this.createUserUseCase = createUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.findUserByIdUseCase = findUserByIdUseCase;
        this.findUserByUsernameUseCase = findUserByUsernameUseCase;
        this.listUsersUseCase = listUsersUseCase;
        this.updateUserPasswordUseCase = updateUserPasswordUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid CreateUserRequest request) {
        UserOutput output = createUserUseCase.execute(request.toInput());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromOutput(output));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable long id) {
        UserResponse response = UserResponse.fromOutput(findUserByIdUseCase.execute(id));
        return ResponseEntity.ok(response);
    }

    // TODO: Fazer retornar uma lista de usernames compativeis
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> findByUsername(@PathVariable String username) {
        UserResponse response = UserResponse.fromOutput(findUserByUsernameUseCase.execute(username));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserResponse> list = listUsersUseCase.execute().stream()
                .map(UserResponse::fromOutput)
                .toList();
        return ResponseEntity.ok(list);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(
            @PathVariable long id,
            @RequestBody @Valid UpdatePasswordRequest request
    ) {
        updateUserPasswordUseCase.execute(request.toInput(id));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        deleteUserUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}