package com.example.todo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity  // 이 클래스가 jpa 엔티티임을 선언하여 데이터베이스 테이블과 매핑됨을 의미한다.
@Table(name = "todos") // 엔티티가 매핑될 테이블의 이름을 "todos"로 지정한다.
public class Todo {
    @Id // 기본 키 (primary Key) 필드를 지정한다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 값을 데이터 베이스에 위임하여 자동 생성되도록 설정하는것.
    private Long id;

    @NotBlank(message = "제목은 비어 있을 수 없습니다.") // 제목에 null이나 공백이 아니어야 하는걸 검증한다.
    @Size(max = 200, message = "제목은 200자 이하로 입력해 주세요.") // 제목의 길이가 200자로 제한한다.
    @Column(nullable = false, length = 200) // 해당 컬럼이 null값을 허용하지 않으며 길이가 200자로 제한된다.
    private  String title;

    @Column(nullable = false) // 이것도 이 필드에 null 값을 허용하지 않는 db 컬럼에 매핑된다.
    private boolean done = false; //Todo 완료 상태 (기본값은 false)

    @Column(nullable = false, updatable = false) // 생성후 수정 할 수 없음을 의미한다.
    private LocalDateTime createdAt; // 생성날짜(만든 현재를 기준으로)

    @Column(nullable = false) // 생성후 수정 할 수 없음을 의미한다.
    private LocalDateTime updatedAt; // 업데이트날짜(업데이트한 현재를 기준으로)

    protected Todo() { // jpq에서는 기본 생성자가 반드기 필요하고, 접근 제어자는 protected 이상만 허용된다.
        // JPA 기본 생성자 (protected 이상)
    }

    private Todo(String title, boolean done) { // 내부에서만 사용되는 생성자 - title과 done 값을 받음
        this.title = title;
        this.done = done;
    }

    public static Todo of(String title) { // title을 받아 새로운 Todo 객체를 생성 (기본 done은 false)
        return new Todo(title, false);
    }

    public static Todo of(String title, boolean done) { // title과 done을 받아 새로훈 Todo 객체를 생성
        return new Todo(title, done);
    }

    /**
     * 엔티티가 데이터베이스에 삽입되기 전 호출되는 메서드.
     * 생성 시간과 수정 시간을 현재 시각으로 설정.
     */
    @PrePersist // 엔티티가 저장되기 전에 호출되어, createdAt과 updatedAt 값을 초기화 한다.
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    /**
     * 엔티티가 업데이트 되기 전 호출되는 메서드.
     * 수정 시간을 현재 시각으로 갱신.
     */
    @PreUpdate // 엔티티가 갱신되기 전에 호출되어, updatedAt 값을 현재 시각으로 변경한다.
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 각 필드에 대한 Getter 메서드 (엔티티는 가급적 Setter 최소화)
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return done;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // 의도된 변경만 메서드로 노출
    /**
     * Todo의 제목을 변경하는 메서드.
     *
     * @param title 새로운 제목
     */
    public void changeTitle(String title) {
        this.title = title;
    }

    /**
     * Todo의 완료 상태를 변경하는 메서드.
     *
     * @param done 새로운 완료 상태
     */
    public void toggleDone(boolean done) {
        this.done = done;
    }
}
