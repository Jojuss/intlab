import React from "react";
import { useRoutes, Outlet, BrowserRouter } from 'react-router-dom';
import Students from './components/Students.jsx';
import Header from "./components/commons/Header.jsx"
import 'bootstrap/dist/css/bootstrap.min.css';
import DrivingSchools from './components/DrivingSchools.jsx';
import Categories from './components/Categories.jsx';
import OneDrivingSchool from './components/OneDrivingSchool.jsx';
import CountStudInCategory from './components/CountStudInCategory.jsx';

function Router(props) {
    return useRoutes(props.rootRoute);
  }
  
  function App() {
    const routes = [
      { index: true, element: <DrivingSchools /> },
      { path: '/', element: <DrivingSchools />, label: 'Сеть Автошкол' },
      { path: '/drivingSchools', element: <DrivingSchools />, label: 'Автошколы' },
      { path: '/students', element: <Students />, label: 'Студенты' },
      { path: '/categories', element: <Categories />, label: 'Категории' },
      { path: '/studcategory', element: <CountStudInCategory />, label: 'Количество студентов в категории' },
      { path: '/drivingSchool/:id', element: <OneDrivingSchool />},
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