import React, { useEffect, useState } from 'react'
import "./ConfigureFilterPage.css";
import Table from '../../components/Table';
import Header from '../../components/Header/Header';


const data = [
  {
    name: "Имя",
    value: "OLGA",
    masking: false,
    filter: false,
    removingCP: false,
    id: 1,
  },
  {
    name: "Имя2",
    value: "OLGA",
    masking: false,
    filter: true,
    removingCP: false,
    id: 2,
  },
  {
    name: "Имя3",
    value: "OLGA",
    masking: false,
    filter: true,
    removingCP: true,
    id: 3,
  },
  {
    name: "Имя4",
    value: "OLGA",
    masking: true,
    filter: false,
    removingCP: false,
    id: 4,
  },
  {
    name: "Имя5",
    value: "OLGA",
    masking: false,
    filter: true,
    removingCP: false,
    id: 5,
  },
  {
    name: "Имя5",
    value: "OLGA",
    masking: false,
    filter: true,
    removingCP: false,
    id: 9,
  },
  {
    name: "Имя5",
    value: "OLGA",
    masking: false,
    filter: true,
    removingCP: false,
    id: 6,
  },
  {
    name: "Имя5",
    value: "OLGA",
    masking: false,
    filter: true,
    removingCP: false,
    id: 7,
  },
  {
    name: "Имя5",
    value: "OLGA",
    masking: false,
    filter: true,
    removingCP: false,
    id: 8,
  },
];
const headerData = [
  "Имя поля",
  "Значение поля",
  "Маскирование",
  "Фильтрация",
  "Удаление ЧП",
  "",
];

const ConfigureFilterPage = () => {
     const [isLoading, setIsLoading] = useState(true);
     const [isOpenfilters, setOpenFilters] = useState(false);
     const [isOpenBurger, setIsOpenBurger] = useState(false);

     useEffect(() => {
       return () => setIsLoading(false);
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
            {isLoading || <Table headerData={headerData} data={data} />}
          </main>
        </div>
      </div>
    </div>
  );
}

export default ConfigureFilterPage