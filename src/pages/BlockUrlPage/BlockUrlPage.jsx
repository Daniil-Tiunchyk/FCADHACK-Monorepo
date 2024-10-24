import React, { useEffect, useState } from 'react'
import "./BlockUrlPage.css";
import Header from '../../components/Header/Header';
import Table from '../../components/Table';
import Button from "../../components/Button/Button";
import axios from 'axios';



import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";


const headerData = ["URL", "Фильтрация", ""];

const fetchURL = "http://193.22.147.81:8082/api/endpoints";

const BlockUrlPage = () => {
      const [isLoading, setIsLoading] = useState(true);
      const [isOpenfilters, setOpenFilters] = useState(false);
      const [isOpenBurger, setIsOpenBurger] = useState(false);

      const [newData, setNewData] = useState([])
      const [inputValue, setInputValue] = useState("");

      
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
            response.data.forEach((item,i) => {
              item.id = i
            })
             setNewData(response.data); // Устанавливаем полученные данные
             setIsLoading(false); // Отключаем индикатор загрузки
           })
           .catch((error) => {
             console.error(error);
             setIsLoading(false);
           });
      


        return () => setIsLoading(false);
      }, []);

      const onClickAdd = () => {
        if (inputValue !== "") {
          axios
            .post(fetchURL, {
              endpoint: inputValue,
              enabled: false,
            })
            .then((response) => {
              console.log(response);
              const newID = newData[newData.length - 1]?.id
                ? newData[newData.length - 1]?.id + 1
                : 1;
              setNewData((prev) => [
                ...prev,
                {
                  endpoint: inputValue,
                  enabled: false,
                  id: newID
                },
              ]);
              toast.success("Добавлено!")
            })
            .catch((error) => {
              console.log(error);
            });

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
            {isLoading || (
              <Table fetchURL={fetchURL} data={newData} headerData={headerData} />
            )}
          </main>
        </div>
      </div>
      <ToastContainer />
    </div>
  );
}

export default BlockUrlPage