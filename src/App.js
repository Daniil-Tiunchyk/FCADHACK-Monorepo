import { useEffect, useState } from "react";
import "./App.css"
import Table from "./components/Table/index"
import TableFilters from "./components/Table/TableFilters/TableFilters";
import Header from "./components/Header/Header";


import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import axios from "axios";


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
const data = []
function App() {
  const fetchURL = "http://193.22.147.81:8081/api/support-messages";


  const [isLoading, setIsLoading] = useState(true)
  const [isOpenfilters, setOpenFilters] = useState(false)
  const [isOpenBurger, setIsOpenBurger] = useState(false);

  const [newData,setNewData] = useState([])

 

  useEffect(() => {
     axios
       .get(fetchURL, {
         headers: {
           "Content-Type": "application/json",
           "Access-Control-Allow-Credentials": "true",
           "Access-Control-Allow-Origin": "*",
           "Access-Control-Allow-Methods":
             "GET, POST, PATCH, DELETE, PUT, OPTIONS",
           "Access-Control-Allow-Headers":
             "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With",
         },
       })
       .then((response) => {
        data.push(...response.data);
        setNewData(response.data); // Устанавливаем полученные данные
         setIsLoading(false); // Отключаем индикатор загрузки
       })
       .catch((error) => {
         console.error(error);
         toast.error('Ошибка запроса!')
         setIsLoading(false);
       });
  }, [])


  return (
    <div className={"App"}>
      <div className="container">
        <div className="wrapper">
          <Header
            setOpenFilters={setOpenFilters}
            isOpenfilters={isOpenfilters}
            isOpenBurger={isOpenBurger}
            setIsOpenBurger={setIsOpenBurger}
          />
          <main>
            {isOpenfilters && <TableFilters data={data} setResults={setNewData} />}
            <Table headerData={headerData} data={newData} />
          </main>
            {isLoading && <h1 className="loading">Загрузка...</h1>}
          <footer>
            <p className="ExplainingText">
              Выберите нужный лог и просмотрите подробную информацию о нем
            </p>
          </footer>
        </div>
      <ToastContainer />
      </div>
    </div>
  );
}

export default App;
