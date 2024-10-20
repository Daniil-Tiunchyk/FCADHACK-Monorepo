import React from 'react'
import './AuthPage.css'
import AuthInput from './components/AuthInput/AuthInput'
import Button from '../../components/Button/Button.jsx'

const AuthPage = () => {
  return (
    <div className='auth_container'>
        <div className='auth_wrapper'>
          <div className='sideBar_block'>
            <div className='sideBar_inner'>
              <h1 className='auth_logo'>FCADHACK</h1>
              <div className='authform_block'>
                <h1>Добро пожаловать обратно! Пожалуйста, войдите в<br/> свой аккаунт.</h1>
                <form className='auth_form' action="">
                  <AuthInput type="text" placeholder={'Логин'} />
                  <AuthInput type="password" placeholder={'Пароль'} />
                  <div className='form_warn-block'>
                    <div className='form_save-block'>
                      <input type="checkbox" id="authcheckbox"/>
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
          <div className='authImage_block'>
            <img src="./men.svg" alt="menimage" />
          </div>
        </div>
    </div>
  )
}

export default AuthPage