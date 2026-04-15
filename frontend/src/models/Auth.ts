export type LoginRequest = {
    email: string;
    password: string;
  };
  
  export type RegisterRequest = {
    name: string;
    email: string;
    password: string;
  };
  
  export type AuthResponse = {
    token: string;
    message: string;
  };
