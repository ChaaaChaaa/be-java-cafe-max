package kr.codesqaud.cafe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.codesqaud.cafe.dto.comment.CommentReadDto;
import kr.codesqaud.cafe.dto.comment.CommentUpdateDto;
import kr.codesqaud.cafe.dto.comment.CommentWriteDto;
import kr.codesqaud.cafe.service.CommentService;
import kr.codesqaud.cafe.session.LoginMemberSession;

@RequestMapping("/posts/{postId}")
@RestController
public class CommentController {
	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/comments")
	public CommentReadDto writeComment(@PathVariable Long postId, @RequestBody CommentWriteDto commentWriteDto,
		@SessionAttribute("loginMember") LoginMemberSession loginMemberSession) {
		commentWriteDto.initMemberInfo(loginMemberSession.getMemberId(), loginMemberSession.getMemberEmail(), postId);
		return commentService.save(commentWriteDto);
	}

	@PutMapping("/comments")
	public String update(@PathVariable Long postId, @RequestBody CommentUpdateDto commentUpdateDto) {
		commentService.update(commentUpdateDto);
		return "redirect:/posts/" + postId;
	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<Void> delete(@PathVariable Long postId, @PathVariable Long commentId,
		@SessionAttribute("loginMember") LoginMemberSession loginMemberSession) {
		String commentWriteMemberEmail = loginMemberSession.getMemberEmail();
		commentService.deleteId(commentWriteMemberEmail, loginMemberSession.getMemberEmail(), commentId);
		return ResponseEntity.noContent().build();
	}
}
