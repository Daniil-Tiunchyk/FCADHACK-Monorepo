import React, { useState, useRef } from 'react';
import './MultiSelect.css';
import Button from '../Button/Button';


const options = [
    { value: 'Уровень 1', label: 'Уровень 1' },
    { value: 'Уровень 2', label: 'Уровень 2' },
    { value: 'Уровень 3', label: 'Уровень 3' },
    { value: 'Уровень 4', label: 'Уровень 4' },
    { value: 'Уровень 5', label: 'Уровень 5' },
    { value: 'Уровень 6', label: 'Уровень 6' },
    { value: 'Уровень 7', label: 'Уровень 7' },
    { value: 'Уровень 8', label: 'Уровень 8' },
    { value: 'Уровень 9', label: 'Уровень 9' },
    { value: 'Уровень 10', label: 'Уровень 10' },
  ];


const MultiSelect = () => {
  const [selectedOptions, setSelectedOptions] = useState([]);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const dropdownRef = useRef(null);



  const onApply = (selectedOptions) => {
    console.log('Selected options:', selectedOptions);
  };
  const toggleDropdown = () => {
    document.body.style.overflow = 'hidden';
    setIsDropdownOpen((prev) => !prev);
  };

  const handleOptionChange = (option) => {
    setSelectedOptions((prev) =>
      prev.includes(option)
        ? prev.filter((o) => o !== option)
        : [...prev, option]
    );
  };

  const handleApply = () => {
    onApply(selectedOptions);
    setIsDropdownOpen(false);
  };

  const handleClickOutside = (event) => {
    document.body.style.overflow = 'unset';
    if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
      setIsDropdownOpen(false);
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
    {isDropdownOpen && <div className='bg'></div>}
    <div className="multi-select-container" ref={dropdownRef}>
      
      <div className="multi-select" onClick={toggleDropdown}>
        {selectedOptions.length === 0
          ? 'Уровень поддержки'
          : selectedOptions.join(', ')}
          <img className={isDropdownOpen ? "open" : ""} src="./arrow.svg" alt="arrow" />
      </div>
      {isDropdownOpen && (
        <div className="options-dropdown">
          {options.map((option) => (
            <div key={option.value} className="option">
              <label className="custom-checkbox">
                <input
                  type="checkbox"
                  checked={selectedOptions.includes(option.value)}
                  onChange={() => handleOptionChange(option.value)}
                />
                <span className="checkmark"></span>
                {option.label}
              </label>
            </div>
          ))}

          <Button onClick={handleApply} className="apply-button">
            Применить
          </Button>
        </div>
      )}
    </div>
    </>
  );
};

export default MultiSelect;
