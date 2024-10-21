import { useEffect, useState } from "react";
import "./App.css"
import Table from "./components/Table/index"
import data from "./elements.json"
import TableFilters from "./components/Table/TableFilters/TableFilters";


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
                  <TableFilters />
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
