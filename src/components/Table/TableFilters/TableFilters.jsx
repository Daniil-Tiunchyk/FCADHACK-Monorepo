import React from 'react'
import CalendarDropdown from '../../CalendarDropdown/CalendarDropdown';
import Input from '../../Input/Input';
import MultiSelect from '../../MultiSelect/MultiSelect';
import Button from '../../Button/Button';

const TableFilters = () => {
  return (
    <div>
      <div className="filters_block">
        <div className="filter_block1">
          <div className="calendars_block">
            <h4 className="ExplainingText ">Выберите метку времени</h4>
            <div className="calendars">
              <CalendarDropdown title="Дата начала" />
              <CalendarDropdown title="Дата конца" />
            </div>
          </div>
          <Input placeholder={"Электронная почта"} type={"email"} />
          <Input placeholder={"Точка доступа"} type={"text"} />
          <MultiSelect />
        </div>
        <div className="filter_block2">
          <Input placeholder={"Логин"} type={"text"} />
          <Input placeholder={"Имя"} type={"text"} />
          <Input placeholder={"ID пользователя"} type={"text"} />
        </div>
      </div>
      <Button className="apply-button apply-button-filters">Применить</Button>
    </div>
  );
}

export default TableFilters