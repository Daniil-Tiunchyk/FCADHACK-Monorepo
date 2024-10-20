import { useEffect, useState } from "react";
import "./App.css"
import Button from "./components/Button/Button";
import CalendarDropdown from "./components/CalendarDropdown/CalendarDropdown";
import Input from "./components/Input/Input";
import MultiSelect from "./components/MultiSelect/MultiSelect";
import Table from "./components/Table/index"
import data from "./elements.json"


function App() {
  const [isLoading, setIsLoading] = useState(true)
  const [isOpenfilters, setOpenFilters] = useState(false)
  const [isOpenBurger, setIsOpenBurger] = useState(false);

  useEffect(() => {
    return () => setIsLoading(false)
  }, [])

  return (
    <div className={"App"}>
      <div className="container">
        <div className="wrapper">
          <header className="header">
            <div className="header_wrap">
              <h2 className="logo">FCADHACK</h2>
              <div className="burger">
                  <img onClick={() => setIsOpenBurger(!isOpenBurger)} width={30} height={30} src="./burger.svg" alt="burger" />
                  {isOpenBurger && (
                     <ul className="burger_menu">
                    <li>Отображение логов</li>
                    <li className={"header_filter-mobile " + (isOpenfilters ? "active" : "")} onClick={() =>{ setOpenFilters(!isOpenfilters); setIsOpenBurger(!isOpenBurger)}}>Фильтр логов</li>
                  </ul>
                  )}
                 
              </div>
            <div className="header_items-block">
              <h2>Отображение логов</h2>
              <div onClick={() => setOpenFilters(!isOpenfilters)} className={"header_filter " + (isOpenfilters ? "active" : "")}>
                <h2>Фильтр логов</h2>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
                  <path d="M22 3H2L10 12.46V19L14 21V12.46L22 3Z" stroke="#1A1A1A" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                </svg>
              </div>
            </div>
            </div>
          </header>
            <main>
              {isOpenfilters && (
                <div>
              <div className="filters_block">
                <div className="filter_block1">
                  <div className="calendars_block">
                  <h4 className="ExplainingText ">Выберите метку времени</h4>
                      <div className="calendars">
                        <CalendarDropdown title="Дата начала" />
                        <CalendarDropdown title="Дата конца" />
                      </div>
                </div>
                  <Input placeholder={"Электронная почта"} type={"email"}/>
                 <Input placeholder={"Точка доступа"} type={"text"}/>
                 <MultiSelect/>
                </div>
                <div className="filter_block2">
                    <Input placeholder={"Логин"} type={"text"}/>
                  <Input placeholder={"Содержимое сообщения"} type={"text"}/>
                  <Input placeholder={"ID пользователя"} type={"text"}/>
                </div>
              </div>
              <Button className="apply-button apply-button-filters">Применить</Button>
              </div>
              ) }
              {isLoading || <Table data={data}/>}
            </main>
          <footer>
            <p className="ExplainingText">Выберите нужный лог и просмотрите подробную информацию о нем</p>
          </footer>
        </div>
      </div>
    </div>
  );
}

export default App;
