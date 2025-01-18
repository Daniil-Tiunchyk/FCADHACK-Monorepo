import React from 'react'
import { Link, NavLink } from "react-router-dom";
import './Header.css'

const Header = ({
  isOpenBurger,
  setIsOpenBurger,
  setOpenFilters,
  isOpenfilters,
  isBlockedFilter=false,
}) => {
  return (
    <header className="header">
      <div className="header_wrap">
        <Link to="/">
          <h2 className="logo">FCADHACK</h2>
        </Link>
        <div className="burger">
          <img
            onClick={() => setIsOpenBurger(!isOpenBurger)}
            width={40}
            height={40}
            src="./burger.svg"
            alt="burger"
          />
          {isOpenBurger && (
            <ul className="burger_menu">
              <Link to="/auth">
                <li>Войти</li>
              </Link>
              <Link to="/">
                <li>Отображение логов</li>
              </Link>
              <Link to="/blockUrl">
                <li>Блокировка URL</li>
              </Link>
              <Link to="/conFilter">
              <li>Конфигурация фильтрации</li>
              </Link>
              <li
                className={
                  "header_filter-mobile " +
                  (isOpenfilters ? "active" : "") +
                  (isBlockedFilter ? "blockedFilter" : "")
                }
                onClick={() => {
                  setOpenFilters(!isOpenfilters);
                  setIsOpenBurger(!isOpenBurger);
                }}
              >
                Фильтр логов
              </li>
            </ul>
          )}
        </div>
        <div className="header_items-block">
          <Link to="/auth">
            <h2>Войти</h2>
          </Link>
          <NavLink activeclassname="activeLink" to="/">
            <h2>Отображение логов</h2>
          </NavLink>
          <NavLink activeclassname="activeLink" to="/blockUrl">
            <h2>Блокировка URL</h2>
          </NavLink>
          <NavLink activeclassname="activeLink" to="/conFilter">
            <h2>Конфигурация фильтрации</h2>
          </NavLink>
          {isBlockedFilter || (
            <div
              onClick={() => setOpenFilters(!isOpenfilters)}
              className={"header_filter" + (isOpenfilters ? " active" : "")}
            >
              <h2>Фильтр логов</h2>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                viewBox="0 0 24 24"
                fill="none"
              >
                <path
                  d="M22 3H2L10 12.46V19L14 21V12.46L22 3Z"
                  stroke="#1A1A1A"
                  strokeWidth="2"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                />
              </svg>
            </div>
          )}
        </div>
      </div>
    </header>
  );
};

export default Header