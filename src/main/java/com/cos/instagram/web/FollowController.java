package com.cos.instagram.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.instagram.config.auth.LoginUserAnnotation;
import com.cos.instagram.config.auth.dto.LoginUser;
import com.cos.instagram.service.FollowService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller

public class FollowController {

	private final FollowService followService;

	@GetMapping("/follow/followingList/{pageUserId}")
	public String followingList(@PathVariable int pageUserId, @LoginUserAnnotation LoginUser loginUser, Model model) {
		model.addAttribute("followingList", followService.팔로잉리스트(loginUser.getId(), pageUserId));
		return "follow/following-list";
	}

	@GetMapping("/follow/followerList/{pageUserId}")
	public String followerList(@PathVariable int pageUserId, @LoginUserAnnotation LoginUser loginUser, Model model) {
		model.addAttribute("followerList", followService.팔로워리스트(loginUser.getId(), pageUserId));
		return "follow/follower-list";
	}

	@PostMapping("/follow/{id}")
	public ResponseEntity<?> follow(@PathVariable int id, @LoginUserAnnotation LoginUser loginUser) {
		followService.팔로우(loginUser.getId(), id);
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

	@DeleteMapping("/follow/{id}")
	public ResponseEntity<?> unFollow(@PathVariable int id, @LoginUserAnnotation LoginUser loginUser) {
		followService.팔로우취소(loginUser.getId(), id);
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
}
