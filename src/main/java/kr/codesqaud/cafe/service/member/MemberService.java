package kr.codesqaud.cafe.service.member;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.member.MemberResponseDto;
import kr.codesqaud.cafe.dto.member.ProfileEditRequestDto;
import kr.codesqaud.cafe.dto.member.SignUpRequestDto;
import kr.codesqaud.cafe.repository.member.MemberRepository;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String signUp(SignUpRequestDto signUpRequestDto) {
        if (signUpRequestDto == null) {
            throw new IllegalArgumentException("signUpRequestDto가 null입니다.");
        }

        Member member = signUpRequestDto.toEntity();
        if (member == null) {
            throw new IllegalArgumentException("Member 객체를 생성할 수 없습니다.");
        }

        return memberRepository.save(member);
    }

    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponseDto::of)
                .collect(Collectors.toList());
    }

    public MemberResponseDto findById(String id) {
        return MemberResponseDto.of(memberRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 id를 가진 멤버를 찾을 수 없습니다.")));
    }


    public void update(ProfileEditRequestDto profileEditRequestDto) {
        Member findMember = memberRepository.findById(profileEditRequestDto.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 id를 가진 멤버를 찾을 수 없습니다."));
        findMember.setNickName(profileEditRequestDto.getNickName());
        findMember.setEmail(profileEditRequestDto.getEmail());
        memberRepository.update(findMember);
    }
}