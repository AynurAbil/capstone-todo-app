import { render, screen, fireEvent } from '@testing-library/react';
import TodoForm from '../../components/TodoForm';

describe('TodoForm', () => {
  it('submits entered todo data', () => {
    const mockOnCreate = vi.fn();

    render(<TodoForm onCreate={mockOnCreate} />);

    fireEvent.change(screen.getByPlaceholderText(/todo title/i), {
      target: { value: 'Write tests' },
    });

    fireEvent.change(screen.getByDisplayValue(/select status/i), {
      target: { value: 'OPEN' },
    });

    fireEvent.change(screen.getByDisplayValue(/select priority/i), {
      target: { value: 'HIGH' },
    });

    fireEvent.change(screen.getByLabelText(/due date/i), {
      target: { value: '2026-04-20' },
    });

    fireEvent.click(screen.getByRole('button', { name: /add todo/i }));

    expect(mockOnCreate).toHaveBeenCalled();
  });
});
