import React from "react";
import { useRoutes, Outlet, BrowserRouter } from 'react-router-dom';
import Students from './components/Students.jsx';
import Header from "./components/commons/Header.jsx"
import 'bootstrap/dist/css/bootstrap.min.css';
import DrivingSchools from './components/DrivingSchools.jsx';
import Categories from './components/Categories.jsx';
import OneDrivingSchool from './components/OneDrivingSchool.jsx';
import CountStudInCategory from './components/CountStudInCategory.jsx';
import Login from './components/Login.jsx';
import Logout from './components/Logout.jsx';
import { useState, useEffect } from 'react';

function Router(props) {
    return useRoutes(props.rootRoute);
  }
  
  function App() {
    const routes = [
      { index: true, element: <DrivingSchools /> },
      { path: '/', element: <DrivingSchools />, label: 'Сеть элективов' },
      { path: '/schools', element: <Schools />, label: 'Школы' },
      { path: '/students', element: <Students />, label: 'Студенты' },
      { path: '/categories', element: <Categories />, label: 'Элективы' },
      { path: '/studcategory', element: <CountStudInCategory />, label: 'Количество студентов в элективе' },
      { path: '/school/:id', element: <OneSchool />},
      { path: '/login', element: <Login />},
      { path: '/logout', element: <Logout />},
    ];
    const links = routes.filter(route => route.hasOwnProperty('label'));
    const [token, setToken] = useState(localStorage.getItem('token'));
    useEffect(() => {

      function handleStorageChange() {
        setToken(localStorage.getItem('token'));
      }

      window.addEventListener('storage', handleStorageChange);

      return () => {
        window.removeEventListener('storage', handleStorageChange);
      };
    }, []);
    const rootRoute = [
      { path: '/', element: render(links), children: routes }
    ];
    function render(links) {
      return (
        <div className="App">
          <Header token={token} links={links} />
          <div className="w-100">
            <Outlet />
          </div>        
      </div>
    );
  }
  
    return (
      <BrowserRouter>
        <Router rootRoute={ rootRoute } />
      </BrowserRouter>
    );
  }
  
  export default App;