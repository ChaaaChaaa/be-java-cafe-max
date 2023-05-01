package kr.codesqaud.cafe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Comment;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.comment.CommentWriteDto;
import kr.codesqaud.cafe.dto.member.MemberJoinRequestDto;
import kr.codesqaud.cafe.repository.comment.CommentRepository;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.repository.post.PostRepository;
import kr.codesqaud.cafe.service.CommentService;
import kr.codesqaud.cafe.service.MemberService;
import kr.codesqaud.cafe.service.PostService;
import kr.codesqaud.cafe.session.LoginMemberSession;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql(scripts = "classpath:schema.sql")
class CommentControllerTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CommentService commentService;

    private MockMvc mockMvc;

    @BeforeEach
    void clean() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MemberController(memberService)).build();
        memberRepository.deleteAll();
    }

    @Test
    void writeComment() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MemberJoinRequestDto requestDtoMember = basicMemberData();
        Long memberId = memberService.join(requestDtoMember);
        Member member = memberRepository.findById(memberId).orElseThrow();

        Post post = basicPostData(member);
        Long savedPostId = postRepository.save(post, member);
        Optional<Post> savedPost = postRepository.findById(savedPostId);

        Comment comment = basicComment(member, post,savedPostId);
        Comment savedComment =commentRepository.save(comment);

        CommentWriteDto commentWriteDto =basicCommentWriteDto(member,savedComment,savedPostId);

                //when
        LoginMemberSession loginMemberSession = new LoginMemberSession(member.getEmail(), memberId);
        String json = objectMapper.writeValueAsString(commentWriteDto);
        System.out.println(json);
        //when,then
        mockMvc.perform(post("/posts/{postId}/comments",savedPostId)
                        .sessionAttr("loginMember", loginMemberSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value(1L))
                .andExpect(jsonPath("$.memberEmail").value("test@gmail.com"))
                .andExpect(jsonPath("$.commentId").value(1L))
                .andExpect(jsonPath("$.postId").value(1L))
                .andExpect(jsonPath("$.content").value("정답 아이즈원!"))
                .andExpect(jsonPath("$.createDate").exists());
    }

    @Test
    void update() {

    }

    @Test
    void delete() {
    }

    private MemberJoinRequestDto basicMemberData() {
        String email = "test@gmail.com";
        String password = "testtest";
        String nickname = "차차";
        return new MemberJoinRequestDto(email, password, nickname);
    }

    private Post basicPostData(Member member) {
        String title = "피에스타";
        String content = "내맘에 태양을 꼭 삼킨채 영원토록 뜨겁게 지지 않을게";
        LocalDateTime writeTime = LocalDateTime.now();
        Long views = 0L;
        return new Post(title, content, member.getEmail(), writeTime, views);
    }

    private Comment basicComment(Member member, Post post,Long savedPostId) {
        Long postId = savedPostId;
        Long memberId = member.getMemberId();
        String writer = member.getEmail();
        String content = post.getContent();
        return new Comment(postId, memberId, writer, content);
    }

    private CommentWriteDto basicCommentWriteDto(Member member, Comment basicComment,Long savedPostId) {
        Long memberId = member.getMemberId();
        String memberEmail = member.getEmail();
        Long commentId = basicComment.getCommentId();
        Long postId = savedPostId;
        String content = "정답 아이즈원!";
        return new CommentWriteDto(memberId, memberEmail, commentId, postId, content);
    }
}
