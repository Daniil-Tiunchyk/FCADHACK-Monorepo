import React, { useEffect, useState } from 'react'
import "./BlockUrlPage.css";
import Header from '../../components/Header/Header';
import Table from '../../components/Table';
import Button from "../../components/Button/Button";

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

      const [newData, setNewData] = useState(data)
      const [inputValue, setInputValue] = useState("");
      
      useEffect(() => {
        return () => setIsLoading(false);
      }, []);

      const onClickAdd = () => {
        if (inputValue !== "") {
          const newID = newData[newData.length - 1].id + 1;
          setNewData((prev) => [
            ...prev,
            {
              url: inputValue,
              filter: false,
              id: newID,
            },
          ]);
        }
        setInputValue("")
      }

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
            <div className="urlAdd_block">
              <div className="Input_block">
                <input
                  value={inputValue}
                  onChange={(e) => setInputValue(e.target.value)}
                  type={"text"}
                  placeholder="URL"
                />
              </div>
              <Button onClick={onClickAdd}>Добавить</Button>
            </div>
            {isLoading || <Table data={newData} headerData={headerData} />}
          </main>
        </div>
      </div>
    </div>
  );
}

export default BlockUrlPage