package fr.univlyon1.mif10.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.univlyon1.mif10.dto.UserDTO;
import fr.univlyon1.mif10.dto.UserRequestDto;
import fr.univlyon1.mif10.dto.UserResponseDto;
import fr.univlyon1.mif10.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class UserRessourcesController implements ControllerBase {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserRessourcesController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(path = "/users", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createUser(@RequestBody UserRequestDto userDTO){
        UserDTO findUser = userService.findByLogin(userDTO.getLogin());
        if (findUser != null) {
            ObjectNode userNode = objectMapper.createObjectNode();
            userNode.put("status", "The user already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userNode.toPrettyString());
        }

        userService.createUser(userDTO);
        ObjectNode userNode = objectMapper.createObjectNode();
        userNode.put("status", userDTO.getLogin());
        return ResponseEntity.ok(userNode.toPrettyString());
    }

    @GetMapping(path = "/users", produces = "application/json")
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<UserResponseDto> users = userService.listUser();
        return ResponseEntity.ok(users);
    }

    @GetMapping(path = "/users/{id}", produces = "application/json")
    public ResponseEntity<String> getUser(@PathVariable int id){
        UserDTO userDTO = userService.oneUser(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "UserDTO not found"));
        ObjectNode userNode = objectMapper.createObjectNode();
        userNode.put("login", userDTO.getLogin());
        userNode.put("roleEnum", userDTO.getRole().toString());
        return ResponseEntity.ok(userNode.toPrettyString());
    }

    @PutMapping(path = "/users/{id}")
    public ResponseEntity<String> modifyUser(
            @RequestBody UserRequestDto userRequestDto,
            @PathVariable("id") int id
    ){
        Optional<UserDTO> userOptional = userService.oneUser(id);
        if (userOptional.isPresent()) {
            UserDTO userDTO = userOptional.get(); // Access the UserDTO object safely
            userService.updateUser(userDTO, userRequestDto);
            return new ResponseEntity<>("UserDTO is updated successfully", HttpStatus.ACCEPTED);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserDTO not found");
        }
    }
}
