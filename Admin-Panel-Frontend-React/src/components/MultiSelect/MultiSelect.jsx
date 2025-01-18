import React, { useState, useRef } from 'react';
import './MultiSelect.css';
import Button from '../Button/Button';


const options = [
  { value: "start", label: "start" },
  { value: "signature", label: "signature" },
  { value: "black", label: "black" },
  { value: "premium", label: "premium" },
  { value: "gold", label: "gold" },
];


const MultiSelect = ({ selectedOptions, setSelectedOptions }) => {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const dropdownRef = useRef(null);

  const onApply = (selectedOptions) => {
    console.log("Selected options:", selectedOptions);
  };
  const toggleDropdown = () => {
    document.body.style.overflow = "hidden";
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
    document.body.style.overflow = "unset";
    if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
      setIsDropdownOpen(false);
    }
  };

  React.useEffect(() => {
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  return (
    <>
      {isDropdownOpen && <div className="bg"></div>}
      <div className="multi-select-container" ref={dropdownRef}>
        <div className="multi-select" onClick={toggleDropdown}>
          {selectedOptions.length === 0
            ? "Уровень поддержки"
            : "Уровни:" + selectedOptions.join(", ")}
          <img
            className={isDropdownOpen ? "open" : ""}
            src="./arrow.svg"
            alt="arrow"
          />
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
