package kr.codesqaud.cafe.repository.post;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.page.StandardPage;

public interface PostRepository {
	Long save(Post post, Member member);

	Optional<Post> findById(Long id);

	List<Post> findPostByWriterEmail(String writerEmail);

	List<Post> findPagingPosts(StandardPage standardPage);

	List<Post> findAll();

	void update(Post post);

	void increaseViews(Long id);

	void deleteAll();

	void deleteId(Long id);
}
