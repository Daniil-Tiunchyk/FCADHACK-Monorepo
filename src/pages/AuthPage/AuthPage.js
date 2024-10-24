import React, { useEffect, useState } from 'react'
import './AuthPage.css'
import AuthInput from './components/AuthInput/AuthInput'
import Button from '../../components/Button/Button.jsx'
import axios from 'axios'
import { useNavigate } from "react-router-dom";

const AuthPage = () => {
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();


const fetchURLReg = "http://193.22.147.81:8765/api/identity/registration";
const fetchURLAuth = "http://193.22.147.81:8765/api/identity/auth";


const onAuth = () => {

}

  return (
    <div className="auth_container">
      <div className="auth_wrapper">
        <div className="sideBar_block">
          <div className="sideBar_inner">
            <h1 className="auth_logo">FCADHACK</h1>
            <div className="authform_block">
              <h1>
                Добро пожаловать обратно! Пожалуйста, войдите в<br /> свой
                аккаунт.
              </h1>
              <form className="auth_form" action="">
                <AuthInput
                  onChange={(e) => setLogin(e.target.value)}
                  required
                  type="text"
                  placeholder={"Логин"}
                />
                <AuthInput onChange={(e) => setPassword(e.target.value)} required type="password" placeholder={"Пароль"} />
                <div className="form_warn-block">
                  <div className="form_save-block">
                    <input
                      className="checkbox"
                      type="checkbox"
                      id="authcheckbox"
                    />
                    <label htmlFor="authcheckbox">
                      <span>Запомнить меня</span>
                    </label>
                  </div>
                  <span>Забыли пароль?</span>
                </div>
                <Button>Войти</Button>
              </form>
            </div>
          </div>
        </div>
        <div className="authImage_block">
          <img src="./men.svg" alt="menimage" />
        </div>
      </div>
    </div>
  );
}

export default AuthPage