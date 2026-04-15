import { MemoryRouter } from 'react-router-dom';
import { render, screen } from '@testing-library/react';
import RegisterPage from '../../pages/RegisterPage';

describe('RegisterPage', () => {
  it('renders register form', () => {
    render(
      <MemoryRouter>
        <RegisterPage />
      </MemoryRouter>
    );

    expect(screen.getByRole('heading', { name: /register/i })).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/email/i)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/password/i)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /register/i })).toBeInTheDocument();
  });
});
