import { NavLink } from 'react-router-dom';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

export default function Header(props) {
    return (
        <Navbar collapseOnSelect expand="lg" bg="primary" variant="primary">
      <Container className='lg justify-content-center'>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="me-auto">
                {
                        props.links.map(route =>          
                            <NavLink key={route.path} className="nav-link" to={route.path}>
                                  <div>{route.label}</div>
                            </NavLink>                            
                        )
                }
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
    );
}