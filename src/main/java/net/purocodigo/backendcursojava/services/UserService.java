package net.purocodigo.backendcursojava.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.purocodigo.backendcursojava.entities.PostEntity;
import net.purocodigo.backendcursojava.entities.UserEntity;
import net.purocodigo.backendcursojava.exceptions.EmailExistException;
import net.purocodigo.backendcursojava.repositories.PostRepository;
import net.purocodigo.backendcursojava.repositories.UserRepository;
import net.purocodigo.backendcursojava.shared.dto.PostDto;
import net.purocodigo.backendcursojava.shared.dto.UserDto;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository userRespository;

    @Autowired
    PostRepository postRespository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto user) {

        if (userRespository.findByEmail(user.getemail()) != null) 
        throw new EmailExistException("El correo electronico ya exite");

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        userEntity.setencryptedPassword(
            bCryptPasswordEncoder.encode(user.getpassword())
        );

        UUID userId = UUID.randomUUID();
        userEntity.setuserId(userId.toString());

        UserEntity storedUserDetails = userRespository.save(userEntity);
        
        UserDto userToReturn = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, userToReturn);

        return userToReturn;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRespository.findByEmail(email);

        if (userEntity == null){
            throw new UsernameNotFoundException(email);
        }

        return new User(userEntity.getemail(), userEntity.getencryptedPassword(),new ArrayList<>());
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRespository.findByEmail(email);

        if (userEntity == null){
            throw new UsernameNotFoundException(email);
        }

        UserDto userToReturn = new UserDto();

        BeanUtils.copyProperties(userEntity, userToReturn);

        return userToReturn;
    }

    @Override
    public List<PostDto> getUserPosts(String email) {
        UserEntity userEntity = userRespository.findByEmail(email);
        List<PostEntity> posts = postRespository.getByUserIdOrderByCreatedAtDesc(userEntity.getId());
        List<PostDto> postDtos = new ArrayList<>();

        for (PostEntity post : posts){
            PostDto postDto = mapper.map(post, PostDto.class);
            postDtos.add(postDto);
        }

        return postDtos;
    }


    
    
}
