package fr.univlyon1.mif10.service;

import fr.univlyon1.mif10.classes.RoleEnum;
import fr.univlyon1.mif10.dto.UserDTO;
import fr.univlyon1.mif10.dto.UserRequestDto;
import fr.univlyon1.mif10.dto.UserResponseDto;
import fr.univlyon1.mif10.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserRequestDto userDTO){
        UserDTO user = new UserDTO(userDTO.getLogin(), userDTO.getPassword(), RoleEnum.USER);
        userRepository.save(user);
    }

    public List<UserResponseDto> listUser(){
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        for (UserDTO userDTO : userRepository.findAll()){
            userResponseDtoList.add(new UserResponseDto(userDTO));
        }
        return userResponseDtoList;
    }

    public Optional<UserDTO> oneUser(int id){
        return userRepository.findById((long) id);
    }

    public void updateUser(UserDTO userDTO, UserRequestDto userRequestDto){
        userDTO.setLogin(userRequestDto.getLogin());
        userDTO.setPassword(userRequestDto.getPassword());
        userRepository.save(userDTO);
    }

    public void updateConnected(UserDTO userDTO){
        userRepository.save(userDTO);
    }

    public UserDTO findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
