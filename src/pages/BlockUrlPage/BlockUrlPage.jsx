import React, { useEffect, useState } from 'react'
import "./BlockUrlPage.css";
import Header from '../../components/Header/Header';
import Table from '../../components/Table';

const data = [
  {
    url: "http://localhost:3000",
    filter: false,
    id: 1,
  },
  {
    url: "http://localhost:3000",
    filter: true,
    id: 2,
  },
  {
    url: "http://localhost:3000",
    filter: false,
    id: 3,
  },
  {
    url: "http://localhost:3000",
    filter: true,
    id: 4,
  },
  {
    url: "http://localhost:3000",
    filter: true,
    id: 5,
  },
  {
    url: "http://localhost:3000",
    filter: false,
    id: 6,
  },
  {
    url: "http://localhost:3000",
    filter: false,
    id: 7,
  },
];

const headerData = ["URL", "Фильтрация", ""];

const BlockUrlPage = () => {
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
            {isLoading || <Table data={data} headerData={headerData}/>}
          </main>
         
        </div>
      </div>
    </div>
  );
}

export default BlockUrlPage