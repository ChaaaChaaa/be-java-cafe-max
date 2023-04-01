package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Member {
    private final String id;
    private String email;
    private String password;
    private String nickName;
    private final LocalDateTime createTime;

    public Member(String id, String email, String password, String nickName, LocalDateTime createTime) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean equalsEmail(String email){
        return this.email.equals(email);
    }
}
