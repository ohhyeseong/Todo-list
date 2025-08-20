package com.example.todo.controller;

import com.example.todo.domain.Todo;
import com.example.todo.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController 선언: 해당 클래스가 REST API 요청을 처리하는 컨트롤러임을 선언 
@RestController
// 기본 URL 매핑: 모든 엔드포인트에서 "/todos"를 기본 경로로 사용
@RequestMapping("/todos")
public class TodoController {

    // TodoService를 final 필드로 선언하여 불변셩을 보장 (생성자 주입을 통해 초기화 됨)
    private final TodoService todoService;

    // 생성자 주입: TodoService 의존성을 생성자를 통해 주입받음
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // POST(생성) 요청 : 새로운 Todo 엔티티를 생성하는 엔드포인트
    @PostMapping
    // TodoService의 createTodo() 메서드를 호출하여 새로운 Todo를 생성하고 DB에 저장 후 반환
    public Todo createTodo(@RequestParam("title") String title) {
        return todoService.createTodo(title);
    }

    // PUT(수정) 요청 : 기존 Todo의 제목을 변경하는 엔드포인트
    @PutMapping("/{id}/title")
    public Todo updateTodoTitle(
            // URL 경로의 {id} 값을 파라미터로 받아옴
            @PathVariable("id") Long id,
            // 요청 파라미터로 전달된 newTitle을 받아옴
            @RequestParam("newTitle") String newTitle) {
        // TodoService의 updateTodoTitle() 메서드를 호출하여 제목 변경 후 업데이트된 Todo 반환
        return todoService.updateTodoTitle(id, newTitle);
    }

    // PUT(수정) 요청 : Todo의 완료 상태(done)를 토글(변경)하는 엔드포인트
    @PutMapping("/{id}/status")
    public Todo toggleTodoStatus(
            // URL 경로의 {id}값을 파라미터로 받아옴

            @PathVariable("id") Long id,
            // 요청 파라미터로 전달된 done 상태 값을 받아옴
            @RequestParam("status") boolean done) {
        // TodoService의 toggleTodoStatus() 메서드를 호출하여 완료 상태를 업데이트한 Todo 반환
        return todoService.toggleTodoStatus(id, done);
    }

    // GET(가져오기) 요청 : 특정 id의 Todo 엔티티를 조회하는 엔드포인트
    @GetMapping("/{id}")
    public Todo findTodoById(
            // URL 경로에서 {id} 값을 받아옴
            @PathVariable("id") Long id) {
        // todoService의 findTodoById() 메서드를 호출하여 해당 Todo를 조회 후 반환
        return todoService.findTodoById(id);
    }

    // GET 요청 : 모든 Todo 리스트를 조회하는 엔드포인트
    @GetMapping
    public List<Todo> findAllTodos() {
        // todoService의 findAllTodos() 메서드를 호출하여 전체 Todo 리스트 조회 후 반환
        return todoService.findAllTodos();
    }

    // DELETE(삭제) 요청: 특정 id의 Todo 엔티티를 삭제하는 엔드포인트
    @DeleteMapping("/{id}")
    public String deleteTodo(
            // URL 경로에서 삭제할 Todo의 id를 받아옴
            @PathVariable("id") Long id) {
        // TodoService의 deleteTode() 메서드를 호출하여 해당 Todo 삭제 처리
        todoService.deleteTodo(id);
        // 삭제 성공 후 메시지 문자열을 반환
        return "Todo 아이디:" + id + "가 성공적으로 삭제 되었습니다!";
    }
}
