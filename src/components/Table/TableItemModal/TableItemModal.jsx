import React, { useEffect, useRef } from 'react'
import './TableItemModal.css'

const TableItemModal = ({ selectedItem, closeModal, formatDate }) => {
  const modalRef = useRef(null);

  const handleClickOutside = (event) => {
    document.body.style.overflow = "unset";
    if (modalRef.current && !modalRef.current.contains(event.target)) {
      closeModal(false);
    }
  };

  useEffect(() => {
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  });

  return (
    <>
      <div className="bg"></div>
      <div className="table_Modal" ref={modalRef}>
        <img
          className="closeModal_btn"
          onClick={() => closeModal(false)}
          src="./closeBtn.svg"
          alt="close"
        />
        <div className="Tablemodal_inner">
          <h1>Подробная информация </h1>
          <ul className="modal_list">
            <li>
              <h3>Электронная почта</h3>
              <p>{selectedItem?.email || "-"}</p>
            </li>
            <li>
              <h3>Имя</h3>
              <p>{selectedItem?.name || "-"}</p>
            </li>
            <li>
              <h3>Точка доступа</h3>
              <p>{selectedItem?.endpoint || "-"}</p>
            </li>
            <li>
              <h3>Логин</h3>
              <p>{selectedItem?.login || "-"}</p>
            </li>
            <li>
              <h3>Уровень поддержки</h3>
              <p>{selectedItem?.supportLevel || "-"}</p>
            </li>
            <li>
              <h3>Метка времени</h3>
              <p>
                {(selectedItem?.timestamp &&
                  formatDate(selectedItem?.timestamp)) ||
                  "-"}
              </p>
            </li>
            <li>
              <h3>Пол</h3>
              <p>{selectedItem?.gender || "-"}</p>
            </li>
            <li>
              <h3>Возраст</h3>
              <p>{selectedItem?.age || "-"}</p>
            </li>
            <li>
              <h3>ID пользователя</h3>
              <p>{selectedItem?.userID || "-"}</p>
            </li>
          </ul>
        </div>
      </div>
    </>
  );
};

export default TableItemModal