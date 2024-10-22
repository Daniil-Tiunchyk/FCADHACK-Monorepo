import React from 'react';
import ReactDOM from 'react-dom/client';
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";

import App from './App';
import AuthPage from './pages/AuthPage/AuthPage';
import BlockUrlPage from './pages/BlockUrlPage/BlockUrlPage';
import ConfigureFilterPage from './pages/ConfigureFIlter/ConfigureFilterPage';

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
  },
  {
    path: '/auth',
    element: <AuthPage />,
  },
  {
    path: '/blockUrl',
    element: <BlockUrlPage />,
  },
  {
    path: '/conFilter',
    element: <ConfigureFilterPage />,
  },
]);


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
