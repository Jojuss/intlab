import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function Logout() {
    const navigate = useNavigate();

    useEffect(() => {
        // Удаление токена из localStorage или другого места
        localStorage.removeItem('token');
        localStorage.removeItem("user");
        localStorage.removeItem("role");
        window.dispatchEvent(new Event("storage"));
        // Перенаправление пользователя на страницу входа или другую страницу
        navigate('/login');
    }, [navigate]);

    return null;
}

export default Logout;