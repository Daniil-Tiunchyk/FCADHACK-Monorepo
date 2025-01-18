import React, { useState } from 'react'
import Button from '../../../../components/Button/Button';

import './ConfFilter.css';
import Select from '../Select/Select';
import axios from 'axios';



import { toast, ToastContainer } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";


const ConfFIlter = ({items, setResults, data, fetchURL, fetchData}) => {
  const [inputValue, setInputValue] = useState(""); // Храним выбранное значение

  const [selectedValueToAdd, setSelectedValueToAdd] = useState(""); // Храним выбранное значение
  const [selectedValue, setSelectedValue] = useState(""); // Храним выбранное значение



  const onClickApply = () => {
    if (selectedValue !== "Выбрать") {
      let filteredResults = data.filter(
        (item) =>  item?.field?.toLowerCase().indexOf(selectedValue?.toLowerCase()) >= 0
      );
      setResults(filteredResults);
    } else {
      setResults(data)
    }
  };

  const onClickAdd = () => {
     axios
       .post(fetchURL, {
         field: selectedValueToAdd,
         enabled: true,
         modes: [],
         pattern: inputValue,
       })
       .then((response) => {
         console.log(response);
         fetchData()
         toast.success("Добавлено!");
       })
       .catch((error) => {
         console.log(error);
       });
   
  }

  return (
    <div className="confFilter_block">
      <div className="confFilter_inner">
        <div className="confFilters">
          <Select
            value={selectedValueToAdd}
            setValue={setSelectedValueToAdd}
            options={items}
          />
          <div className="Input_block">
            <input
              value={inputValue}
              onChange={(e) => setInputValue(e.target.value)}
              type={"text"}
              placeholder="Введите значение"
            />
          </div>
          <Button onClick={onClickAdd}>Добавить</Button>
        </div>
        <div className="confFiltersArea">
          <h2>Фильтр по полю</h2>
          <Select
            setValue={setSelectedValue}
            value={selectedValue}
            options={items}
          />
          <Button onClick={onClickApply}>Применить</Button>
        </div>
      </div>
      <ToastContainer />
    </div>
  );
}

export default ConfFIlter