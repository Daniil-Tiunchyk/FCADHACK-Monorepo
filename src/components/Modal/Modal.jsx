import React from "react";

import Button from "../Button/Button";
import "./Modal.css";
import axios from "axios";




import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const Modal = ({ title, item, message, name, openModal, onDelete, fetchURL }) => {
  const onDeleteElem = (id) => {
    axios.delete(fetchURL + "?endpointName=" + item.endpoint);
    onDelete(id);
    openModal(null);
    toast.error('Удалено!')
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
      <ToastContainer />
    </>
  );
};

export default Modal;
