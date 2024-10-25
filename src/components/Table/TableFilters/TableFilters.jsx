import React, { useState } from 'react'
import CalendarDropdown from '../../CalendarDropdown/CalendarDropdown';
import Input from '../../Input/Input';
import MultiSelect from '../../MultiSelect/MultiSelect';
import Button from '../../Button/Button';

import {
  format,
} from "date-fns";


// Функция для преобразования строки даты в объект Date
const parseDate = (dateString) => {
  const [day, month, year] = dateString.split('.')
  return new Date(`${year}-${month}-${day}`); // Преобразуем в формат yyyy-mm-dd для конструктора Date
};

const TableFilters = ({ data, setResults }) => {
  const[emailInputValue, setEmailInputValue] = useState("")
  const[endPointInputValue, setEndPointInputValue] = useState("")
  const[loginInputValue, setLoginInputValue] = useState("")
  const[nameInputValue, setNameInputValue] = useState("")
  const[userIDInputValue, setUserIdInputValue] = useState("")

  /* calendar */
  const [selectedDateBegin, setSelectedDateBegin] = useState(null);
  const [selectedDateEnd, setSelectedDateEnd] = useState(null);

  /* select */
  const [selectedOptions, setSelectedOptions] = useState([]);
  /*  */

  const onClickApply = () => {
    console.log('data', data)
     let filteredResults = data.filter(
       (item) =>
       {
        const isHasFirstName =
          item?.firstName !== null
            ? item?.firstName
                ?.toString()
                ?.toLowerCase()
                ?.includes(nameInputValue?.toLowerCase())
            : true;
        return (
          item?.email
            ?.toString()
            ?.toLowerCase()
            .includes(emailInputValue?.toLowerCase()) &&
          isHasFirstName &&
          item?.login
            ?.toString()
            .toLowerCase()
            .includes(loginInputValue?.toLowerCase()) &&
          item?.userId?.toString().includes(userIDInputValue?.toString()) &&
          item?.endpoint
            ?.toString()
            ?.toLowerCase()
            .includes(endPointInputValue?.toLowerCase())
        );
       }
        
     );
    console.log(filteredResults);
     if (selectedDateBegin !== null && selectedDateEnd !== null) {
       const dateStart = format(selectedDateBegin, "dd/MM/yyyy");
       const dateEnd = format(selectedDateEnd, "dd/MM/yyyy");

       const start = new Date(dateStart.split("/").reverse().join("-"));
       const end = new Date(dateEnd.split("/").reverse().join("-"));

       filteredResults = data
         .filter((item) => {
           const itemDate = parseDate(format(item?.timestamp, "dd.MM.yyyy"));
           console.log("date",itemDate, itemDate >= start);
           return itemDate >= start && itemDate <= end;
         })
     } 
     
     if (filteredResults.length === 0) {
       setResults(data);
      } else {
        setResults(filteredResults);
      }
  }



  return (
    <div>
      <div className="filters_block">
        <div className="filter_block1">
          <div className="calendars_block">
            <h4 className="ExplainingText ">Выберите метку времени</h4>
            <div className="calendars">
              <CalendarDropdown
                setSelectedDate={setSelectedDateBegin}
                selectedDate={selectedDateBegin}
                title="Дата начала"
                />
              <CalendarDropdown
                setSelectedDate={setSelectedDateEnd}
                selectedDate={selectedDateEnd}
                title="Дата конца"
              />
            </div>
          </div>
          <Input
            onChange={(e) => setEmailInputValue(e.target.value)}
            placeholder={"Электронная почта"}
            type={"email"}
            value={emailInputValue}
          />
          <Input
            value={endPointInputValue}
            placeholder={"Точка доступа"}
            type={"text"}
            onChange={(e) => setEndPointInputValue(e.target.value)}
          />
          <MultiSelect
            selectedOptions={selectedOptions}
            setSelectedOptions={setSelectedOptions}
          />
        </div>
        <div className="filter_block2">
          <Input
            onChange={(e) => setLoginInputValue(e.target.value)}
            placeholder={"Логин"}
            type={"text"}
            value={loginInputValue}
          />
          <Input
            onChange={(e) => setNameInputValue(e.target.value)}
            placeholder={"Имя"}
            type={"text"}
            value={nameInputValue}
          />
          <Input
            onChange={(e) => setUserIdInputValue(e.target.value)}
            placeholder={"ID пользователя"}
            type={"text"}
            value={userIDInputValue}
          />
        </div>
      </div>
      <Button
        onClick={onClickApply}
        className="apply-button apply-button-filters"
      >
        Применить
      </Button>
    </div>
  );
};

export default TableFilters