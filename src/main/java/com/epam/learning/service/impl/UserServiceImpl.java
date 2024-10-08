package com.epam.learning.service.impl;

import com.epam.learning.dto.UserReqDto;
import com.epam.learning.dto.UserResDto;
import com.epam.learning.entity.TaskEntity;
import com.epam.learning.entity.UserEntity;
import com.epam.learning.exeption.AlreadyExistException;
import com.epam.learning.exeption.NotFoundException;
import com.epam.learning.mapper.UserMapper;
import com.epam.learning.repository.UserRepository;
import com.epam.learning.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public UserResDto createUser(UserReqDto user) {
        Optional<UserEntity> byPhoneNumber = repository.findByPhoneNumber(user.getPhoneNumber());
        if (byPhoneNumber.isPresent()) {
            throw new AlreadyExistException("user already exist with this number : " + user.getPhoneNumber());
        }
        UserEntity userEntity = mapper.dtoToEntity(user);
        UserEntity save = repository.save(userEntity);
        return mapper.entityToDto(save);
    }

    @Override
    public UserResDto updateUser(Integer id, UserReqDto user) {
        UserEntity taskEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with this id does not exist : " + id));
        if (user.getFullName() != null) {
            taskEntity.setFullName(user.getFullName());
        }
        if (user.getPhoneNumber() != null) {
            taskEntity.setPhoneNumber(user.getPhoneNumber());
        }
        if (user.getPassword() != null) {
            taskEntity.setPassword(user.getPassword());
        }
        if (user.getPassword() != null) {
            taskEntity.setPassword(user.getPassword());
        }
        if (user.getRole() != null) {
            taskEntity.setRole(user.getRole());
        }
        return mapper.entityToDto(repository.save(taskEntity));
    }

    @Override
    public String deleteUser(Integer id) {
        repository.findById(id).orElseThrow(() -> new NotFoundException("User with this id does not exist :" + id));
        repository.deleteById(id);
        return String.valueOf(id);
    }

    @Override
    public UserResDto getUser(Integer id) {
        UserEntity userEntity = repository.findById(id).orElseThrow(() -> new NotFoundException("User with this id does not exist"));
        return mapper.entityToDto(userEntity);
    }

    @Override
    public List<UserResDto> getAllUsers() {
        List<UserResDto> list = new ArrayList<>();
        repository.findAll().forEach(item -> list.add(mapper.entityToDto(item)));
        return list;
    }
}
