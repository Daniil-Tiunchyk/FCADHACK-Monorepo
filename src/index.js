import React from 'react';
import ReactDOM from 'react-dom/client';
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";

import App from './App';
import AuthPage from './pages/AuthPage/AuthPage';

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
  },
  {
    path: '/auth',
    element: <AuthPage />,
  }
]);


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
