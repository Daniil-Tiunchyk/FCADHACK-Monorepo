import { useEffect, useState } from "react";
import "./App.css"
import Table from "./components/Table/index"
import data from "./elements.json"
import TableFilters from "./components/Table/TableFilters/TableFilters";
import Header from "./components/Header/Header";

const headerData = [
  'Электронная почта',
  'Имя',
  'Точка доступа',
  'Логин',
  'Уровень поддержки',
  'Метка времени',
  'Пол',
  "Возраст",
  "ID пользователя"
]

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
            <Header setOpenFilters={setOpenFilters} isOpenfilters={isOpenfilters} isOpenBurger={isOpenBurger} setIsOpenBurger={setIsOpenBurger} />
            <main>
              {isOpenfilters && (
                  <TableFilters />
              ) }
              {isLoading || <Table headerData={headerData} data={data}/>}
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
