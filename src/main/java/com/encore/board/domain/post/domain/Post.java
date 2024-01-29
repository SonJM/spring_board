package com.encore.board.domain.post.domain;

import com.encore.board.domain.author.domain.Author;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 50)
    private String title;
    @Setter
    @Column(length = 3000)
    private String contents;

    private String appointment;
    private LocalDateTime appointmentTime;

    // author_id는 DB의 컬럼명, 별다른 옵션 없을 시 author의 pk에 fk가 설정
    // post 객체 입장에서는 한 사람이 여러개 글을 쓸 수 있으므로 N:1
    // @JoinColumn(nullable=false, name="author-email", referenceColumnName="email")
    @JoinColumn(name = "author_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;
    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;

    public Post(String title, String contents, Author author){
        this.title = title;
        this.contents = contents;
        this.author = author;
//        author 객체의 posts를 초기화 한 후
//        this.author.getPosts().add(this);
    }

    public void addPost(){
        this.author.getPosts().add(this);
    }
    public void UpdateAppointment(String appointment){
        this.appointment = appointment;
    }
}

