package com.Hindol.User_Service.Service;

import com.Hindol.User_Service.DTO.CreateUserRequestDTO;
import com.Hindol.User_Service.Entity.User;
import com.Hindol.User_Service.Event.UserCreatedEvent;
import com.Hindol.User_Service.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    @Value("${kafka.topic.user-created-topic}")
    private String KAFKA_USER_CREATED_TOPIC;

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final KafkaTemplate<Long, UserCreatedEvent> kafkaTemplate;

    public void createUser(CreateUserRequestDTO createUserRequestDTO) {
        User user = modelMapper.map(createUserRequestDTO, User.class);
        User savedUser = userRepository.save(user);
        UserCreatedEvent userCreatedEvent = modelMapper.map(savedUser, UserCreatedEvent.class);
        kafkaTemplate.send(KAFKA_USER_CREATED_TOPIC, userCreatedEvent.getId(), userCreatedEvent);
    }

}
