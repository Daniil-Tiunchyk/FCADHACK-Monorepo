import React, { useRef, useState } from "react";
import { format, getDaysInMonth, isToday, isWeekend, addMonths } from "date-fns";
import { ru } from "date-fns/locale";
import "./CalendarDropdown.css";

const CalendarDropdown = ({ title = "Выберите дату" }) => {
  const [isOpen, setIsOpen] = useState(false);
  const [selectedDate, setSelectedDate] = useState(null);
  const [currentMonth, setCurrentMonth] = useState(new Date().getMonth());
  const [currentYear, setCurrentYear] = useState(new Date().getFullYear());

  const dropdownRef = useRef(null);

  // Получение дней в месяце
  const getDaysArray = () => {
    const daysInMonth = getDaysInMonth(new Date(currentYear, currentMonth));
    const days = [];
    for (let day = 1; day <= daysInMonth; day++) {
      const date = new Date(currentYear, currentMonth, day);
      days.push(date);
    }
    return days;
  };

  const daysArray = getDaysArray();

  // Переключение на предыдущий или следующий месяц
  const prevMonth = () => {
    const newDate = addMonths(new Date(currentYear, currentMonth), -1);
    setCurrentMonth(newDate.getMonth());
    setCurrentYear(newDate.getFullYear());
  };

  const nextMonth = () => {
    const newDate = addMonths(new Date(currentYear, currentMonth), 1);
    setCurrentMonth(newDate.getMonth());
    setCurrentYear(newDate.getFullYear());
  };

  // Выбор даты
  const selectDate = (date) => {
    setSelectedDate(date);
    setIsOpen(false);
  };

  const toggleDropdown = () => {
    document.body.style.overflow = 'hidden';
    setIsOpen(!isOpen); 
  };

   const handleClickOutside = (event) => {
    document.body.style.overflow = 'unset';
    if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
      setIsOpen(false);
    }
  };

  React.useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  return (
    <>
    {isOpen && <div className="bg"></div>}
   
    <div className="calendar-dropdown" ref={dropdownRef}>
      {/* Кнопка для открытия/закрытия календаря */}
      <button onClick={toggleDropdown} className="calendar-dropdown-toggle">
        {selectedDate ? format(selectedDate, "dd/MM/yyyy") : title}
        <img className={isOpen ? "open" : ""} src="./arrow.svg" alt="arrow" />
      </button>

      {/* Выпадающий календарь */}
      {isOpen && (
        <div className="dropdown-calendar">
          {/* Панель с переключением месяцев */}
          <div className="calendar-header">
            <span>
              {format(new Date(currentYear, currentMonth), "LLLL yyyy", { locale: ru })}
            </span>
            <div>
              <button onClick={prevMonth}>
                <img src="./chevronLeft.svg" alt="arrow" />
              </button>
              <button onClick={nextMonth}>
                <img src="./chevronRight.svg" alt="arrow" />
              </button>
            </div>
          </div>
          <div className="week_block"><p>Пн</p><p>Вт</p><p>Ср</p><p>Чт</p><p>Пт</p><p>Сб</p><p>Вс</p></div>
          {/* Сетка дней */}
          <div className="calendar-days">
            {daysArray.map((day, index) => (
              <div
                key={index}
                className={`calendar-day 
                  ${isToday(day) ? "today" : ""} 
                  ${isWeekend(day) ? "weekend" : ""} 
                  ${selectedDate && format(selectedDate, "dd/MM/yyyy") === format(day, "dd/MM/yyyy") ? "selected" : ""}`}
                onClick={() => selectDate(day)}
              >
                {format(day, "d")}
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
    </>
  );
};

export default CalendarDropdown;
