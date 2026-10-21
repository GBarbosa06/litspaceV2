package barbosa.guilherme.LitSpacev2.user.infrastructure.web;

import barbosa.guilherme.LitSpacev2.user.application.dto.out.UserOutput;
import barbosa.guilherme.LitSpacev2.user.application.usecase.CreateUserUseCase;
import barbosa.guilherme.LitSpacev2.user.infrastructure.web.dto.CreateUserRequest;
import barbosa.guilherme.LitSpacev2.user.infrastructure.web.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid CreateUserRequest request){
        UserOutput output = (createUserUseCase.execute(request.toInput()));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromOutput(output));
    }
}
