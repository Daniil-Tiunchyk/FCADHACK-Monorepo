import React, { useState } from 'react'
import Button from '../../../../components/Button/Button';

import './ConfFilter.css';
import Select from '../Select/Select';


const ConfFIlter = ({items, setResults, data}) => {
  const [inputValue, setInputValue] = useState(""); // Храним выбранное значение

  const [selectedValueToAdd, setSelectedValueToAdd] = useState(""); // Храним выбранное значение
  const [selectedValue, setSelectedValue] = useState(""); // Храним выбранное значение



  const onClickApply = () => {
    console.log(selectedValue);
    if (selectedValue !== "Выбрать") {
      let filteredResults = data.filter(
        (item) =>  item?.name?.toLowerCase().indexOf(selectedValue?.toLowerCase()) >= 0
      );
      setResults(filteredResults);
    }
  };

  const onClickAdd = () => {
    setResults((prev) => [
      ...prev,
      {
        name: selectedValueToAdd,
        value: inputValue,
        masking: false,
        filter: false,
        removingCP: false,
        id: prev[prev.length - 1].id + 1,
      }
    ]);
  }

  return (
    <div className="confFilter_block">
      <div className="confFilter_inner">
        <h3>
          Х инстансов, Q инстансов Z,{" "}
          <span className="conFilter_url">+ тут должна быть ссылка</span>
        </h3>
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
    </div>
  );
}

export default ConfFIlter