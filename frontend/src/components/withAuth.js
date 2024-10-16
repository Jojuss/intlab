import React from 'react'
import { useNavigate} from "react-router-dom";
import { useEffect } from 'react';

const withAuth = (Component) => {
    const AuthenticatedComponent = (props) => {
        const navigate = useNavigate();
        const token = localStorage.getItem('token');

        useEffect(() => {
            if (!token || token === 'undefined') {
                navigate('/login');
            }
        }, [navigate, token]);

        if (token && token !== 'undefined') {
            return <Component {...props} />
        }
        return null;
    }
    return AuthenticatedComponent;
}

export default withAuth;