import { render, screen } from '@testing-library/react';
import TodoList from '../../components/TodoList';

describe('TodoList', () => {
  it('renders all todos', () => {
    render(
      <TodoList
        todos={[
          {
            id: 1,
            title: 'Todo one',
            status: 'OPEN',
            priority: 'HIGH',
            dueDate: '2026-04-20',
          },
          {
            id: 2,
            title: 'Todo two',
            status: 'DONE',
            priority: 'LOW',
            dueDate: '2026-04-21',
          },
        ]}
        onDelete={async () => {}}
      />,
    );

    expect(screen.getByText('Todo one')).toBeInTheDocument();
    expect(screen.getByText('Todo two')).toBeInTheDocument();
  });
});
