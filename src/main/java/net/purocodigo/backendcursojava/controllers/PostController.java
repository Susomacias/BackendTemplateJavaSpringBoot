package net.purocodigo.backendcursojava.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.purocodigo.backendcursojava.models.requests.PostCreateRequestModel;
import net.purocodigo.backendcursojava.models.responses.OperationStatusModel;
import net.purocodigo.backendcursojava.models.responses.PostRest;
import net.purocodigo.backendcursojava.services.PostServiceInterface;
import net.purocodigo.backendcursojava.services.UserServiceInterface;
import net.purocodigo.backendcursojava.shared.dto.PostCreationDto;
import net.purocodigo.backendcursojava.shared.dto.PostDto;
import net.purocodigo.backendcursojava.shared.dto.UserDto;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostServiceInterface postService;

    @Autowired
    UserServiceInterface userService;

    @Autowired
    ModelMapper mapper;

    @PostMapping
    public PostRest createPost(@RequestBody PostCreateRequestModel createRequestModel) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getPrincipal().toString();

        PostCreationDto postCreationDto = mapper.map(createRequestModel, PostCreationDto.class);

        postCreationDto.setUserEmail(email);

        PostDto postDto = postService.createPost(postCreationDto);

        PostRest postToReturn = mapper.map(postDto, PostRest.class);

        if (postToReturn.getExpiresAt().compareTo(new Date(System.currentTimeMillis())) > 0) {
            postToReturn.setExpired(true);
        }

        return postToReturn;
    }

    @GetMapping(path = "/last")
    public List<PostRest> lastPosts(){
        List<PostDto> posts = postService.getLastPosts();

        List<PostRest> postRests = new ArrayList<>();

        for (PostDto post : posts){
            PostRest postRest = mapper.map(post, PostRest.class);
            postRests.add(postRest);
        }

        return postRests;
    }

    @GetMapping(path = "/{id}")
	public PostRest getPost(@PathVariable String id){

		PostDto postDto = postService.getPost(id);
		
        PostRest postRest = mapper.map(postDto, PostRest.class);

        if (postRest.getExpiresAt().compareTo(new Date(System.currentTimeMillis())) > 0) {
            postRest.setExpired(true);
        }

        if (postRest.getExposure().getId()==1 || postRest.getExpired()){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(authentication.getPrincipal().toString());

            if(user.getId() != postDto.getUser().getId()){
                throw new RuntimeException("No tienes permisos para realizar esta acción");
            }
        }

		return postRest;
	}

    @DeleteMapping(path="/{id}")
    public OperationStatusModel deletePost(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(authentication.getPrincipal().toString()); 

        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setName("DELETE");

        postService.deletePost(id, user.getId());
        operationStatusModel.setName("SUCCESS");

        return operationStatusModel;
    }

    @PutMapping(path="/{id}")
    public PostRest updatePost(
    @RequestBody PostCreateRequestModel postCreateRequestModel, 
    @PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(authentication.getPrincipal().toString()); 

        PostCreationDto postUpdateDto =  mapper.map(postCreateRequestModel, PostCreationDto.class);
        PostDto postDto = postService.updatePost(id, user.getId(), postUpdateDto);

        PostRest updatedPost =  mapper.map(postDto, PostRest.class);

        return updatedPost;
    }
}
