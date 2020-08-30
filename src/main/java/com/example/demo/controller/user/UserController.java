package com.example.demo.controller.user;

import com.example.demo.dto.user.UserDto;
import com.example.demo.model.user.User;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class UserController {
    @Autowired
    public UserService userService;

    //API trả về List User.
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> listAllUsers() {
        List<User> accounts = userService.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : accounts) {
            UserDto userDto = new UserDto(user);
            userDtos.add(userDto);
        }
        if (userDtos.isEmpty()) {
            return new ResponseEntity<List<UserDto>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);
    }

    //API trả về User có ID trên url.
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        System.out.println("Fetching User with id " + id);
        User account = userService.findById(id);
        if (account == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        account.setFriends(null);
        account.setFamilyMembers(null);
        account.setColleagues(null);
        return new ResponseEntity<User>(account, HttpStatus.OK);
    }

    //API trả về User có Token trên url.
    @RequestMapping(value = "/user/byToken", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByIdByToken(@RequestBody String token) {
        System.out.println("Fetching User with token " + token);
        User account = userService.findByToken(token);
        if (account == null) {
            System.out.println("User with token " + token + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        account.setFriends(null);
        account.setFamilyMembers(null);
        account.setColleagues(null);
        return new ResponseEntity<User>(account, HttpStatus.OK);
    }

    //API tạo một Admin mới.
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + user.getName());
        userService.updateUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một Admin với ID trên url.
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<User> updateAdmin(@PathVariable("id") Long id, @RequestBody User user) {
        System.out.println("Updating User " + id);

        User current = userService.findById(id);

        if (current == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        user.setFriends(current.getFriends());
        current = user;

        userService.updateUser(current);
        return new ResponseEntity<User>(current, HttpStatus.OK);
    }

    //API xóa một Admin với ID trên url.
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        System.out.println("Fetching & Deleting User with id " + id);

        User user = userService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        userService.deleteUser(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/addFriend/{id}/{friendID}", method = RequestMethod.PATCH)
    public ResponseEntity<User> addFriend(@PathVariable("id") Long id, @PathVariable("friendID") Long friendID) {
        System.out.println("Updating User " + id);
        // ------------------------------------------------------------
        User current = userService.findById(id);
        User friend = userService.findById(friendID);
        // ------------------------------------------------------------
        if (current == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        if (friend == null) {
            System.out.println("Friend with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        // ------------------------------------------------------------
        if (current.getFriends() == null || current.getFriends().size() == 0) {
            current.setFriends(new ArrayList<>());
        }
        if (friend.getFriends() == null || friend.getFriends().size() == 0) {
            friend.setFriends(new ArrayList<>());
        }
        // ------------------------------------------------------------
        List<User> friendList = current.getFriends();
        if (!friendList.contains(friend)) {
            friendList.add(friend);
        }
        current.setFriends(friendList);
        List<User> friendListFriend = friend.getFriends();
        if (!friendListFriend.contains(current)) {
            friendListFriend.add(current);
        }
        friend.setFriends(friendListFriend);
        // ------------------------------------------------------------
        userService.updateUser(current);
        userService.updateUser(friend);
        // ------------------------------------------------------------
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    @RequestMapping(value = "/addFamilyMember/{id}/{familyId}", method = RequestMethod.PATCH)
    public ResponseEntity<User> addFamilyMenbers(@PathVariable("id") Long id, @PathVariable("familyId") Long familyId) {
        System.out.println("Updating User " + id);
        // ------------------------------------------------------------
        User current = userService.findById(id);
        User familyMember = userService.findById(familyId);
        // ------------------------------------------------------------
        if (current == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        if (familyMember == null) {
            System.out.println("Family Member with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        // ------------------------------------------------------------
        if (current.getFamilyMembers() == null || current.getFamilyMembers().size() == 0) {
            current.setFamilyMembers(new ArrayList<>());
        }
        if (familyMember.getFamilyMembers() == null || familyMember.getFamilyMembers().size() == 0) {
            familyMember.setFamilyMembers(new ArrayList<>());
        }
        // ------------------------------------------------------------
        List<User> currentFamilyMembers = current.getFamilyMembers();
        if (!currentFamilyMembers.contains(familyMember)) {
            currentFamilyMembers.add(familyMember);
        }
        current.setFamilyMembers(currentFamilyMembers);
        List<User> familyMemberFamilyMembers = familyMember.getFamilyMembers();
        if (!familyMemberFamilyMembers.contains(current)) {
            familyMemberFamilyMembers.add(current);
        }
        familyMember.setFamilyMembers(familyMemberFamilyMembers);
        // ------------------------------------------------------------
        userService.updateUser(current);
        userService.updateUser(familyMember);
        // ------------------------------------------------------------
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    @RequestMapping(value = "/addColleague/{id}/{colleagueId}", method = RequestMethod.PATCH)
    public ResponseEntity<User> addColleague(@PathVariable("id") Long id, @PathVariable("colleagueId") Long colleagueId) {
        System.out.println("Updating User " + id);
        // ------------------------------------------------------------
        User current = userService.findById(id);
        User colleague = userService.findById(colleagueId);
        // ------------------------------------------------------------
        if (current == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        if (colleague == null) {
            System.out.println("Colleague with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        // ------------------------------------------------------------
        if (current.getColleagues() == null || current.getColleagues().size() == 0) {
            current.setColleagues(new ArrayList<>());
        }
        if (colleague.getColleagues() == null || colleague.getColleagues().size() == 0) {
            colleague.setColleagues(new ArrayList<>());
        }
        // ------------------------------------------------------------
        List<User> colleagues = current.getColleagues();
        if (!colleagues.contains(colleague)) {
            colleagues.add(colleague);
        }
        current.setColleagues(colleagues);
        List<User> colleagueColleagues = colleague.getColleagues();
        if (!colleagueColleagues.contains(current)) {
            colleagueColleagues.add(current);
        }
        colleague.setColleagues(colleagueColleagues);
        // ------------------------------------------------------------
        userService.updateUser(current);
        userService.updateUser(colleague);
        // ------------------------------------------------------------
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    @RequestMapping(value = "/unFriend/{id}/{friendID}", method = RequestMethod.PATCH)
    public ResponseEntity<User> unFriend(@PathVariable("id") Long id, @PathVariable("friendID") Long friendID) {
        System.out.println("Updating User " + id);
        // ------------------------------------------------------------
        User current = userService.findById(id);
        User friend = userService.findById(friendID);
        // ------------------------------------------------------------
        if (current == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        if (friend == null) {
            System.out.println("Friend with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        // ------------------------------------------------------------
        if (current.getFriends() == null || current.getFriends().size() == 0) {
            current.setFriends(new ArrayList<>());
        }
        if (friend.getFriends() == null || friend.getFriends().size() == 0) {
            friend.setFriends(new ArrayList<>());
        }
        // ------------------------------------------------------------
        List<User> friendList = current.getFriends();
        friendList.remove(friend);
        current.setFriends(friendList);
        List<User> friendListFriend = friend.getFriends();
        friendListFriend.remove(current);
        friend.setFriends(friendListFriend);
        // ------------------------------------------------------------
        userService.updateUser(current);
        userService.updateUser(friend);
        // ------------------------------------------------------------
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    @RequestMapping(value = "/unFamilyMenber/{id}/{familyId}", method = RequestMethod.PATCH)
    public ResponseEntity<User> unFamilyMenber(@PathVariable("id") Long id, @PathVariable("familyId") Long familyId) {
        System.out.println("Updating User " + id);
        // ------------------------------------------------------------
        User current = userService.findById(id);
        User familyMember = userService.findById(familyId);
        // ------------------------------------------------------------
        if (current == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        if (familyMember == null) {
            System.out.println("Family Member with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        // ------------------------------------------------------------
        if (current.getFamilyMembers() == null || current.getFamilyMembers().size() == 0) {
            current.setFamilyMembers(new ArrayList<>());
        }
        if (familyMember.getFamilyMembers() == null || familyMember.getFamilyMembers().size() == 0) {
            familyMember.setFamilyMembers(new ArrayList<>());
        }
        // ------------------------------------------------------------
        List<User> currentFamilyMembers = current.getFamilyMembers();
        currentFamilyMembers.remove(familyMember);
        current.setFamilyMembers(currentFamilyMembers);
        List<User> familyMemberFamilyMembers = familyMember.getFamilyMembers();
        familyMemberFamilyMembers.remove(current);
        familyMember.setFamilyMembers(familyMemberFamilyMembers);
        // ------------------------------------------------------------
        userService.updateUser(current);
        userService.updateUser(familyMember);
        // ------------------------------------------------------------
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    @RequestMapping(value = "/unColleague/{id}/{colleagueId}", method = RequestMethod.PATCH)
    public ResponseEntity<User> unColleague(@PathVariable("id") Long id, @PathVariable("colleagueId") Long colleagueId) {
        System.out.println("Updating User " + id);
        // ------------------------------------------------------------
        User current = userService.findById(id);
        User colleague = userService.findById(colleagueId);
        // ------------------------------------------------------------
        if (current == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        if (colleague == null) {
            System.out.println("Colleague with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        // ------------------------------------------------------------
        if (current.getColleagues() == null || current.getColleagues().size() == 0) {
            current.setColleagues(new ArrayList<>());
        }
        if (colleague.getColleagues() == null || colleague.getColleagues().size() == 0) {
            colleague.setColleagues(new ArrayList<>());
        }
        // ------------------------------------------------------------
        List<User> colleagues = current.getColleagues();
        colleagues.remove(colleague);
        current.setColleagues(colleagues);
        List<User> colleagueColleagues = colleague.getColleagues();
        colleagueColleagues.remove(current);
        colleague.setColleagues(colleagueColleagues);
        // ------------------------------------------------------------
        userService.updateUser(current);
        userService.updateUser(colleague);
        // ------------------------------------------------------------
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getFriend/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> listFriendById(@PathVariable("id") Long id) {
        User current = userService.findById(id);
        List<User> friends = current.getFriends();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : friends) {
            UserDto userDto = new UserDto(user);
            userDtos.add(userDto);
        }
        if (userDtos.isEmpty()) {
            return new ResponseEntity<List<UserDto>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/getFamilyMember/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getFamilyMember(@PathVariable("id") Long id) {
        User current = userService.findById(id);
        List<User> familyMembers = current.getFamilyMembers();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : familyMembers) {
            UserDto userDto = new UserDto(user);
            userDtos.add(userDto);
        }
        if (userDtos.isEmpty()) {
            return new ResponseEntity<List<UserDto>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/getColleague/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getColleague(@PathVariable("id") Long id) {
        User current = userService.findById(id);
        List<User> colleagues = current.getColleagues();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : colleagues) {
            UserDto userDto = new UserDto(user);
            userDtos.add(userDto);
        }
        if (userDtos.isEmpty()) {
            return new ResponseEntity<List<UserDto>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/getNotFriend/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> listNotFriendById(@PathVariable("id") Long id) {
        User current = userService.findById(id);
        List<User> allUsers = userService.findAll();
        List<User> friends = current.getFriends();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : allUsers) {
            if (!friends.contains(user)) {
                UserDto userDto = new UserDto(user);
                userDtos.add(userDto);
            }
        }
        if (userDtos.isEmpty()) {
            return new ResponseEntity<List<UserDto>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/getNotFamilyMember/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> listNotFamilyMemberById(@PathVariable("id") Long id) {
        User current = userService.findById(id);
        List<User> allUsers = userService.findAll();
        List<User> familyMembers = current.getFamilyMembers();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : allUsers) {
            if (!familyMembers.contains(user)) {
                UserDto userDto = new UserDto(user);
                userDtos.add(userDto);
            }
        }
        if (userDtos.isEmpty()) {
            return new ResponseEntity<List<UserDto>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/getNotColleague/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> listNotColleagueById(@PathVariable("id") Long id) {
        User current = userService.findById(id);
        List<User> allUsers = userService.findAll();
        List<User> colleagues = current.getColleagues();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : allUsers) {
            if (!colleagues.contains(user)) {
                UserDto userDto = new UserDto(user);
                userDtos.add(userDto);
            }
        }
        if (userDtos.isEmpty()) {
            return new ResponseEntity<List<UserDto>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/isFriend/{idOne}/{idTwo}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> isFriend(@PathVariable("idOne") Long id1, @PathVariable("idTwo") Long id2) {
        User friend1 = userService.findById(id1);
        User friend2 = userService.findById(id2);
        List<User> friends = friend1.getFriends();
        if (!friends.contains(friend2)) {
            return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<User>>(HttpStatus.OK);
    }

    @RequestMapping(value = "/isFamilyMember/{idOne}/{idTwo}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> isFamilyMembers(@PathVariable("idOne") Long id1, @PathVariable("idTwo") Long id2) {
        User familyMember1 = userService.findById(id1);
        User familyMember2 = userService.findById(id2);
        List<User> familyMembers = familyMember1.getFamilyMembers();
        if (!familyMembers.contains(familyMember2)) {
            return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<User>>(HttpStatus.OK);
    }

    @RequestMapping(value = "/isColleague/{idOne}/{idTwo}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> isColleague(@PathVariable("idOne") Long id1, @PathVariable("idTwo") Long id2) {
        User colleague1 = userService.findById(id1);
        User colleague2 = userService.findById(id2);
        List<User> colleagues = colleague1.getColleagues();
        if (!colleagues.contains(colleague2)) {
            return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<User>>(HttpStatus.OK);
    }
}
