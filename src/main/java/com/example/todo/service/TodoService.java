package com.example.todo.service;

import com.example.todo.domain.Todo;
import com.example.todo.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    // TodoRepository를 생성자 주입으로 받아서 데이터 베이스와 상호작용을 처리한다.(생성시 한번 주입 되고 이후에는 변경되지 않는다.)
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) { // 생성자 주입을 통해 TodoRepository를 의존성 주입한다.
        this.todoRepository = todoRepository;
    }
    /*
      새 Todo를 생성하는 메서드
    * @Transactional 어노테이션은 메서드 내 모든 데이터베이스 작업을 하나의 트랜잭션으로 처리한다.
    * 작업 성공 시 커밋되고, 예외 발생 시 모든 변경 사항을 롤백한다.
    * */
    @Transactional
    public Todo createTodo(String title) {
        // Todo.of() 팩토리 메서드를 사용해 새로운 Todo 인스턴스를 생성한다.
        Todo newTodo = Todo.of(title);
        // 생성된 Todo 객체를 데이터베이스에 저장하고 반환한다.
        return todoRepository.save(newTodo);
    }

    /*
    * 기존 Todo의 제목을 변경하는 메서드
    * 트랜잭션 내에서 조회 후 변경 작업을 수행하여 자동으로 커밋된다.
    * */
    @Transactional
    public Todo updateTodoTitle(Long id, String newTitle) {
        // id로 기존 Todo를 조회하며, 없으면 예외 발생
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("없는 아이디입니다." + id));
        // 변경 메서드 호출: changeTitle()는 Todo의 제목을 업데이트한다.
        todo.changeTitle(newTitle);
        // 변경된 엔티티를 반환 (트랜잭션 종료 시 자동 커밋)
        return todo;
    }
    /*
     * Todo의 완료 상태를 토글(변경)하는 메서드.
     * 트랜잭션 내에서 상태 변경을 수행하여 모든 작업이 원자적으로 처리된다.
     */
    @Transactional
    public Todo toggleTodoStatus(Long id, boolean done) {
        // Todo 엔티티를 id로 조회, 없으면 예외 발생
        Todo todo = todoRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("없는 리스트입니다." + id));
        // toggleDone() 메서드를 호출하여 완료 상태를 업데이트 한다.
        todo.toggleDone(done);
        // 변경된 엔티티를 반환 (트랜잭션 종료 시 자동 커밋)
        return todo;
    }
    /*
     * 특정 Todo 엔티티를 id로 조회하는 메서드.
     * @Transactional(readOnly=true) 어노테이션을 이용하여 읽기 전용 트랜잭션으로 처리한다.
     */
    @Transactional(readOnly = true)
    public Todo findTodoById(Long id) {
        // 데이터베이스에서 해당 id의 Todo를 조회하고 반환한다.
        return todoRepository.findById(id).orElseThrow(()->new EntityNotFoundException("없는 아이디 입니다."+id));
    }

    /*
     * 모든 Todo 리스트를 조회하는 메서드.
     * 읽기 전용 트랜잭션을 사용하여 성능을 최적화한다.
     */
    @Transactional(readOnly = true)
    public List<Todo> findAllTodos() {
        return todoRepository.findAll();
    }

    /*
     * 특정 Todo 엔티티를 삭제하는 메서드.
     * 트랜잭션 어노테이션을 사용하여 삭제 작업이 원자적으로 처리되도록 한다.
     */
    @Transactional
    public void deleteTodo(Long id) {
        // 삭제 전, 해당 Todo가 존재하는지 확인한다.
        Todo todo = todoRepository.findById(id).orElseThrow(()->new EntityNotFoundException("없는 아이디 입니다."+id));
        // 존재하는 Todo 엔티티를 데이터베이스에서 삭제한다.
        todoRepository.delete(todo);
    }
}
