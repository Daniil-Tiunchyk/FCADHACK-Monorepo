import React from "react";

import Button from "../Button/Button";
import "./Modal.css";

const Modal = ({ title, item, message, name, openModal, onDelete }) => {
  const onDeleteElem = (id) => {
    onDelete(id);
    openModal(null);
  };

  return (
    <>
      <div className="bg"></div>
      <div className="modal">
        <div className="modal_inner">
          <img
            onClick={() => openModal(null)}
            width={40}
            src="./closeBtn.svg"
            alt="close"
          />
          <h1>{title}</h1>
          <h4>{message}</h4>
          <div className="ModalurlName_block">{name}</div>
          <Button onClick={() => onDeleteElem(item.id)}>Хочу</Button>
        </div>
      </div>
    </>
  );
};

export default Modal;
