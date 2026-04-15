import type { Todo } from "../models/Todo";

type TodoItemProps = {
  todo: Todo;
  onDelete: (id: number) => Promise<void>;
};

function TodoItem({ todo, onDelete }: TodoItemProps) {
  return (
    <div
      style={{
        border: "1px solid #ccc",
        borderRadius: "8px",
        padding: "1rem",
      }}
    >
      <h3>{todo.title}</h3>
      <p>Status: {todo.status}</p>
      <p>Priority: {todo.priority}</p>
      <p>Due Date: {todo.dueDate}</p>
      <button onClick={() => onDelete(todo.id)}>Delete</button>
    </div>
  );
}

export default TodoItem;
