import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import TodoForm from '../components/TodoForm';
import TodoList from '../components/TodoList';
import type { Todo } from '../models/Todo';
import api from '../services/api';

function TodoPage() {
  const navigate = useNavigate();

  const [todos, setTodos] = useState<Todo[]>([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const fetchTodos = async () => {
    setLoading(true);
    setError('');

    try {
      const response = await api.get<Todo[]>('/todos');
      setTodos(response.data);
    } catch {
      setError('Failed to load todos');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTodos();
  }, []);

  const handleCreate = async (todoData: {
    title: string;
    status: string;
    priority: string;
    dueDate: string;
  }) => {
    setError('');

    try {
      await api.post('/todos', todoData);
      await fetchTodos();
    } catch {
      setError('Failed to create todo');
    }
  };

  const handleDelete = async (id: number) => {
    setError('');

    try {
      await api.delete(`/todos/${id}`);
      await fetchTodos();
    } catch {
      setError('Failed to delete todo');
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  return (
    <div style={{ padding: '2rem', maxWidth: '800px', margin: '0 auto' }}>
      <div
        style={{
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center',
        }}
      >
        <h1>Todo App</h1>
        <button onClick={handleLogout}>Logout</button>
      </div>

      <TodoForm onCreate={handleCreate} />

      {loading && <p>Loading...</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}

      <TodoList todos={todos} onDelete={handleDelete} />
    </div>
  );
}

export default TodoPage;
