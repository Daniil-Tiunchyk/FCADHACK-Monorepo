import React, { useEffect, useState } from 'react'
import "./ConfigureFilterPage.css";
import Table from '../../components/Table';
import Header from '../../components/Header/Header';
import ConfFIlter from './components/ConfFilter/ConfFIlter';
import axios from 'axios';


const headerData = [
  "Имя поля",
  "Значение поля",
  "Маскирование",
  "Фильтрация",
  "Удаление ЧП",
  "",
];


const data = []
const fetchURL = "http://193.22.147.81:8082/api/regex-configs";

const ConfigureFilterPage = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [isOpenfilters, setOpenFilters] = useState(false);
  const [isOpenBurger, setIsOpenBurger] = useState(false);
  
  const filterPerName = [
    { name: "Выбрать", id: 0 },
    { name: "Имя", id: 1 },
    { name: "Почта", id: 2 },
    { name: "Номер телефона", id: 3 },
  ];
     const [results, setResults] = useState([])

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
           response.data.forEach((item, i) => {
             item.id = i;
           });
           console.log('1',response.data);
           data.push(...response.data)
           setResults(response.data); // Устанавливаем полученные данные
           console.log(results)
           setIsLoading(false); // Отключаем индикатор загрузки
         })
         .catch((error) => {
          console.error(error)
           setIsLoading(false);
         });
        setIsLoading(false);
     }, []);

  return (
    <div className={"App"}>
      <div className="container">
        <div className="wrapper">
          <Header
            setOpenFilters={setOpenFilters}
            isOpenfilters={isOpenfilters}
            isOpenBurger={isOpenBurger}
            setIsOpenBurger={setIsOpenBurger}
            isBlockedFilter={true}
          />
          <main>
            <ConfFIlter data={data} setResults={setResults} items={filterPerName} />
            {isLoading || <Table fetchURL={fetchURL} headerData={headerData} data={results} />}
          </main>
        </div>
      </div>
    </div>
  );
}

export default ConfigureFilterPage