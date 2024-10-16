import React from "react";
import { useRoutes, Outlet, BrowserRouter } from 'react-router-dom';
import Students from './components/Students.jsx';
import Header from "./components/commons/Header.jsx"
import 'bootstrap/dist/css/bootstrap.min.css';
import Schools from './components/Schools.jsx';
import Categories from './components/Categories.jsx';
import OneSchool from './components/OneSchool.jsx';
import CountStudInCategory from './components/CountStudInCategory.jsx';

function Router(props) {
    return useRoutes(props.rootRoute);
  }
  
  function App() {
    const routes = [
      { index: true, element: <Schools /> },
      { path: '/', element: <Schools />, label: 'Сеть Автошкол' },
      { path: '/Schools', element: <Schools />, label: 'Автошколы' },
      { path: '/students', element: <Students />, label: 'Студенты' },
      { path: '/categories', element: <Categories />, label: 'Категории' },
      { path: '/studcategory', element: <CountStudInCategory />, label: 'Количество студентов в категории' },
      { path: '/School/:id', element: <OneSchool />},
    ];
    const links = routes.filter(route => route.hasOwnProperty('label'));
    const rootRoute = [
      { path: '/', element: render(links), children: routes }
    ];
    function render(links) {
      return (
        <div className="App">
          <Header links={links} />         
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