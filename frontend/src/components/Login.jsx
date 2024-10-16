import React, { useState } from 'react';
import './Login.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useNavigate} from "react-router-dom";

export default function Login(props) {
    const navigate = useNavigate();
    const [loginData, setLoginData] = useState({ login: '', password: '' });
    const hostURL = 'http://localhost:8080';
    const handleLoginSubmit = (e) => {
        e.preventDefault();

        login(loginData.login, loginData.password);
    };

    const login = async function (login, password) {
        const requestParams = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({login: login, password: password}),
        };
        const response = await fetch(hostURL + "/jwt/login", requestParams);
        const result = await response.text();
        if (response.status === 200) {
            localStorage.setItem("token", result);
            localStorage.setItem("user", login);
            let jwtData = result.split('.')[1]
            let decodedJwtJsonData = window.atob(jwtData);
            let decodedJwtData = JSON.parse(decodedJwtJsonData);

            let role = decodedJwtData.role;
            localStorage.setItem("role", role.toUpperCase());
            window.dispatchEvent(new Event("storage"));
            navigate("/");
        } else {
            localStorage.removeItem("token");
            localStorage.removeItem("user");
            localStorage.removeItem("role");
            alert(result);
        }
    }


    return (
        <div className="container-fluid">
            <div className="row justify-content-center align-items-center vh-100">
                <div className="col-sm-6 col-md-4">
                    <div className="card">
                        <div className="card-body">
                            <h5 className="card-title">Авторизация</h5>
                            <form onSubmit={handleLoginSubmit}>
                                <div className="form-group mb-3">
                                    <label htmlFor="login">Логин</label>
                                    <input type="text" className="form-control" id="login" value={loginData.login} onChange={(e) => setLoginData({ ...loginData, login: e.target.value })} />
                                </div>
                                <div className="form-group mb-3">
                                    <label htmlFor="loginPassword">Пароль</label>
                                    <input type="password" className="form-control" id="loginPassword" value={loginData.password} onChange={(e) => setLoginData({ ...loginData, password: e.target.value })} />
                                </div>
                                <button type="submit" className="btn btn-primary">Вход</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}