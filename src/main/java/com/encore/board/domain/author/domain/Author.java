package com.encore.board.domain.author.domain;

import com.encore.board.domain.author.Role;
import com.encore.board.domain.post.domain.Post;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Setter
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(columnDefinition = "TINYINT", length=1)
    private Integer age;

    // author객체가 post객체를 필요로 할 때 선언
    // mappedBy를 연관관계의 주인이라 부르고, fk를 관리하는 변수명을 명시
    // 1:1관계일 경우 @OneToOne도 존재
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Setter // cascade.persist를 위한 테스트
    List<Post> posts;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;


    public void authorizeUser() {
        this.role = Role.USER;
    }

    public void updateAuthor(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
