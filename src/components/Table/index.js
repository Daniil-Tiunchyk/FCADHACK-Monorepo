import React, { useEffect, useState } from "react";

import styles from "./Table.module.css";

import TableFooter from "./TableFooter";
import TableItemModal from "./TableItemModal/TableItemModal";
import Modal from "../Modal/Modal";
import axios from "axios";


const Table = ({ data, headerData, fetchURL = "" }) => {
  const [isOpenModal, setIsOpenModal] = useState(false);
  const [selectedItem, setSelectedItem] = useState({
    email: "",
    endpoint: "",
    login: "",
    name: "",
    supportLevel: "",
    timestamp: null,
    userId: null,
    gender: "",
    age: null,
  });

  const [currentItems, setCurrentItems] = useState([]);
  const [pageCount, setPageCount] = useState(0);
  const [itemOffset, setItemOffset] = useState(0);

  const [isOpenModalUrl, setIsOpenModalUrl] = useState(-1);

  /*  */

  const itemsPerPage = 5; // Количество элементов на страницу

  useEffect(() => {
    // Вычисляем конец и начало выборки данных для текущей страницы
    const endOffset = itemOffset + itemsPerPage;
    data[0]?.userId
      ? setCurrentItems(data.slice(itemOffset, endOffset))
      : setCurrentItems(data);
    setPageCount(Math.ceil(data.length / itemsPerPage));
  }, [itemOffset, itemsPerPage, data]);

  // Обработчик изменения страницы
  const handlePageClick = (event) => {
    const newOffset = (event.selected * itemsPerPage) % data.length;
    setItemOffset(newOffset);
  };

  const formatDate = (timestamp) => {
    return new Intl.DateTimeFormat("ru", {
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
      hour: "2-digit",
      minute: "2-digit",
      second: "2-digit",
    }).format(timestamp);
  };

  const onClickTableElem = (el) => {
    document.body.style.overflow = "hidden";
    setSelectedItem(el);
    setIsOpenModal(true);
  };

  const onClickDelete = (id) => {
    const arr = currentItems.filter((item, index) => item.id !== id);
    setCurrentItems(arr);
  };


  const onChangeUrlCheckbox = (e, el) => {
    const newItem = { endpoint: el.endpoint, enabled: e.target.checked };
    axios
      .put(fetchURL + "?endpointName=" + el.endpoint, newItem)
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  const onChangeFieldCheckbox = (e,el, mode) => {
    const newModes = el.modes;
    const indexOfMode = newModes.findIndex((element) => element.includes(mode));
    indexOfMode >= 0 ? newModes.splice(indexOfMode, 1) : newModes.push(mode);
     const newItem = {
       enabled: el.enabled,
       field: el.field,
       modes: newModes,
       pattern: el.pattern,
     };
     const updatedPattern = encodeURIComponent(el.pattern)
     const newFetchUrl = `${fetchURL}?field=${el.field}&pattern=${updatedPattern}`;
     axios
       .put(newFetchUrl, newItem)
       .then((response) => {
         console.log(response);
       })
       .catch((error) => {
         console.log(error);
       });
  }
  return (
    <>
      <div className="table_block">
        <table className={styles.table}>
          <thead
            className={`${styles.tableRowHeader} ${
              data[0]?.url ? styles.blockUrlTable : ""
            }`}
          >
            <tr>
              {headerData.map((item, i) => (
                <th key={i} className={styles.tableHeader}>
                  {item}
                </th>
              ))}
            </tr>
          </thead>
          <tbody>
            {data[0]?.field === undefined
              ? data[0]?.userId
                ? currentItems.map((el, i) => (
                    <tr
                      onClick={() => onClickTableElem(el)}
                      className={styles.tableRowItems}
                      key={i}
                    >
                      {/* ПОМЕНЯТЬ НА userId */}
                      <td className={styles.tableCell}>{el?.email || "-"}</td>
                      <td className={styles.tableCell}>
                        {el?.firstName || "-"}
                      </td>
                      <td className={styles.tableCell}>
                        {el?.endpoint || "-"}
                      </td>
                      <td className={styles.tableCell}>{el?.login || "-"}</td>
                      <td className={styles.tableCell}>
                        {el?.supportLevel || "-"}
                      </td>
                      <td className={styles.tableCell}>
                        {(el?.timestamp && formatDate(el?.timestamp)) || "-"}
                      </td>
                      <td className={styles.tableCell}>{el?.gender || "-"}</td>
                      <td className={styles.tableCell}>{el?.age || "-"}</td>
                      <td className={styles.tableCell}>{el?.userId || "-"}</td>
                    </tr>
                  ))
                : currentItems.map((el, i) => (
                    <>
                      {isOpenModalUrl === el.id && (
                        <Modal
                          fetchURL={fetchURL}
                          item={el}
                          onDelete={onClickDelete}
                          openModal={setIsOpenModalUrl}
                          title={"УДАЛЕНИЕ URL"}
                          message={
                            "Вы уверены что хотите удалить выбранный URL?"
                          }
                          name={el.endpoint}
                        />
                      )}

                      <tr
                        className={`${styles.tableRowItems} ${styles.blockUrlRowItems}`}
                        key={el.id}
                      >
                        {/* ПОМЕНЯТЬ НА userId */}
                        <td className={styles.tableCell}>{el?.endpoint}</td>
                        <td className={styles.tableCell}>
                          <input
                            type="checkbox"
                            className={`checkbox ${styles.checkboxUrlTable}`}
                            id={`checkbox${el.id}`}
                            defaultChecked={el.enabled ? true : ""}
                            onChange={(e) => onChangeUrlCheckbox(e, el)}
                          />
                          <label htmlFor={`checkbox${el.id}`}></label>
                        </td>
                        <td
                          className={`${styles.tableCell} ${styles.urlDelete}`}
                        >
                          <img
                            onClick={() => setIsOpenModalUrl(el.id)}
                            src="./closeBtn.svg"
                            alt="close"
                          />
                        </td>
                      </tr>
                    </>
                  ))
              : currentItems.map((el, i) => (
                  <>
                    {isOpenModalUrl === el.id && (
                      <Modal
                        fetchURL={fetchURL}
                        item={el}
                        onDelete={onClickDelete}
                        openModal={setIsOpenModalUrl}
                        title={"УДАЛЕНИЕ ПОЛЯ"}
                        message={
                          "Вы уверены что хотите удалить выбранное поле?"
                        }
                        name={el.field}
                      />
                    )}
                    <tr
                      className={`${styles.tableRowItems} ${styles.confTable}`}
                      key={el.id}
                    >
                      {/* ПОМЕНЯТЬ НА userId */}
                      <td className={styles.tableCell}>{el?.field}</td>
                      <td className={styles.tableCell}>Регулярное выражение: {el.pattern}</td>
                      <td className={styles.tableCell}>
                        <div>
                          <input
                            type="checkbox"
                            className={`checkbox ${styles.checkboxUrlTable}`}
                            id={`checkboxMasking${el.id}`}
                            defaultChecked={
                              el.modes.findIndex((element) =>
                                element.includes("HIDE_DATA")
                              ) >= 0 && true
                            }
                            onChange={(e) => onChangeFieldCheckbox(e, el, "HIDE_DATA")}
                          />
                          <label htmlFor={`checkboxMasking${el.id}`}></label>
                        </div>
                      </td>
                      <td className={styles.tableCell}>
                        <div>
                          <input
                            type="checkbox"
                            className={`checkbox ${styles.checkboxUrlTable}`}
                            id={`checkboxFilter${el.id}`}
                            defaultChecked={
                              el.modes.findIndex((element) =>
                                element.includes("REMOVE_FIELD")
                              ) >= 0 && true
                            }
                            onChange={(e) => onChangeFieldCheckbox(e, el, "REMOVE_FIELD")}
                          />
                          <label htmlFor={`checkboxFilter${el.id}`}></label>
                        </div>
                      </td>
                      <td className={styles.tableCell}>
                        <div>
                          <input
                            type="checkbox"
                            className={`checkbox ${styles.checkboxUrlTable}`}
                            id={`checkboxCP${el.id}`}
                            defaultChecked={
                              el.modes.findIndex((element) =>
                                element.includes("REMOVE_OBJECT")
                              ) >= 0 && true
                            }
                            onChange={(e) => onChangeFieldCheckbox(e, el, "REMOVE_OBJECT")}
                          />
                          <label htmlFor={`checkboxCP${el.id}`}></label>
                        </div>
                      </td>
                      <td className={`${styles.tableCell} ${styles.urlDelete}`}>
                        <img
                          onClick={() => setIsOpenModalUrl(el.id)}
                          src="./closeBtn.svg"
                          alt="close"
                        />
                      </td>
                    </tr>
                  </>
                ))}
          </tbody>
        </table>
      </div>
      {isOpenModal && (
        <TableItemModal
          formatDate={formatDate}
          closeModal={setIsOpenModal}
          selectedItem={selectedItem}
        />
      )}
      {data[0]?.userId !== undefined && (
        <TableFooter pageCount={pageCount} handlePageClick={handlePageClick} />
      )}
    </>
  );
};

export default Table;
