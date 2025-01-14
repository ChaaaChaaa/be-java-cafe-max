package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Comment {
	private final LocalDateTime createDate;
	private Long commentId;
	private final Long memberId;
	private final Long postId;
	private final String writer;
	private final String content;
	private final LocalDateTime updatedDate;

	public Comment(Long postId, Long memberId, String writer, String content) {
		this.postId = postId;
		this.memberId = memberId;
		this.writer = writer;
		this.content = content;
		this.createDate = LocalDateTime.now();
		this.updatedDate = LocalDateTime.now();
	}

	public Comment(Long commentId, Long memberId, Long postId, String writer, String content,
		LocalDateTime updatedDate) {
		this.commentId = commentId;
		this.memberId = memberId;
		this.postId = postId;
		this.writer = writer;
		this.content = content;
		this.createDate = LocalDateTime.now();
		this.updatedDate = updatedDate;
	}

	public static Comment updateOf(Long id, String content) {
		return new Comment(id, null, null, content, null, LocalDateTime.now());
	}

	public Long getCommentId() {
		return commentId;
	}

	public Long getPostId() {
		return postId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public String getWriter() {
		return writer;
	}

	public String getContent() {
		return content;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
}
