import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import TodoItem from '../../components/TodoItem';

describe('TodoItem', () => {
  it('renders todo details', () => {
    render(
      <TodoItem
        todo={{
          id: 1,
          title: 'Finish capstone',
          status: 'OPEN',
          priority: 'HIGH',
          dueDate: '2026-04-20',
        }}
        onDelete={async () => {}}
      />,
    );

    expect(screen.getByText('Finish capstone')).toBeInTheDocument();
    expect(screen.getByText('Status: OPEN')).toBeInTheDocument();
    expect(screen.getByText('Priority: HIGH')).toBeInTheDocument();
    expect(screen.getByText('Due Date: 2026-04-20')).toBeInTheDocument();
  });

  it('calls onDelete when delete button is clicked', async () => {
    const user = userEvent.setup();
    const onDelete = vi.fn().mockResolvedValue(undefined);

    render(
      <TodoItem
        todo={{
          id: 5,
          title: 'Delete me',
          status: 'DONE',
          priority: 'LOW',
          dueDate: '2026-04-21',
        }}
        onDelete={onDelete}
      />,
    );

    await user.click(screen.getByRole('button', { name: /delete/i }));

    expect(onDelete).toHaveBeenCalledWith(5);
  });
});
