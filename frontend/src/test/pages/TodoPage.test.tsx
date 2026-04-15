import { render, screen, fireEvent } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { Routes, Route } from 'react-router-dom';
import TodoPage from '../../pages/TodoPage';

describe('TodoPage', () => {

  // basic render test
  it('renders todo page', () => {
    render(
      <MemoryRouter>
        <TodoPage />
      </MemoryRouter>
    );

    expect(screen.getByText(/todo app/i)).toBeInTheDocument();
  });

  // log out test
  it('logs out user and clears token', () => {
    localStorage.setItem('token', 'test-token');

    render(
      <MemoryRouter>
        <TodoPage />
      </MemoryRouter>
    );

    const logoutBtn = screen.getByRole('button', { name: /logout/i });

    fireEvent.click(logoutBtn);
    expect(localStorage.getItem('token')).toBe(null);
  });

  it('redirects to login after logout', () => {
    localStorage.setItem('token', 'test-token');
  
    render(
      <MemoryRouter initialEntries={['/todos']}>
        <Routes>
          <Route path="/todos" element={<TodoPage />} />
          <Route path="/login" element={<div>Login Page</div>} />
        </Routes>
      </MemoryRouter>
    );
  
    fireEvent.click(screen.getByRole('button', { name: /logout/i }));
  
    expect(screen.getByText(/login page/i)).toBeInTheDocument();
  });

});
