import React, { useEffect, useState } from "react";

import styles from "./Table.module.css";

import TableFooter from "./TableFooter";
import TableItemModal from "./TableItemModal/TableItemModal";


const Table = ({ data, headerData }) => {
  const [isOpenModal, setIsOpenModal] = useState(false);
  const [selectedItem, setSelectedItem] = useState({
    email: "",
    endpoint: "",
    login: "",
    name: "",
    supportLevel: "",
    timestamp: null,
    userID: null,
    gender: "",
    age: null,
  });

  const [currentItems, setCurrentItems] = useState([]);
  const [pageCount, setPageCount] = useState(0);
  const [itemOffset, setItemOffset] = useState(0);

  const itemsPerPage = 5; // Количество элементов на страницу

  useEffect(() => {
    // Вычисляем конец и начало выборки данных для текущей страницы
    const endOffset = itemOffset + itemsPerPage;
    data[0]?.userID
      ? setCurrentItems(data.slice(itemOffset, endOffset))
      : setCurrentItems(data)
    
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
  }


  
  return (
    <>
      <div className="table_block">
        <table className={styles.table}>
          <thead
            className={`${styles.tableRowHeader} ${
              data[0]?.userID ? "" : styles.blockUrlTable
            }`}
          >
            <tr>
              {headerData.map((item) => (
                <th className={styles.tableHeader}>{item}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {data[0]?.masking === undefined
              ? data[0]?.userID
                ? currentItems.map((el, i) => (
                    <tr
                      onClick={() => onClickTableElem(el)}
                      className={styles.tableRowItems}
                      key={el.i}
                    >
                      {/* ПОМЕНЯТЬ НА userID */}
                      <td className={styles.tableCell}>{el?.email || "-"}</td>
                      <td className={styles.tableCell}>{el?.name || "-"}</td>
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
                      <td className={styles.tableCell}>{el?.userID || "-"}</td>
                    </tr>
                  ))
                : currentItems.map((el, i) => (
                    <tr
                      className={`${styles.tableRowItems} ${styles.blockUrlRowItems}`}
                      key={el.id}
                    >
                      {/* ПОМЕНЯТЬ НА userID */}
                      <td className={styles.tableCell}>{el?.url}</td>
                      <td className={styles.tableCell}>
                        {el?.name}
                        <input
                          type="checkbox"
                          className={`checkbox ${styles.checkboxUrlTable}`}
                          id={`checkbox${i}`}
                          defaultChecked={el.filter ? true : ""}
                        />
                        <label htmlFor={`checkbox${i}`}></label>
                      </td>
                      <td className={`${styles.tableCell} ${styles.urlDelete}`}>
                        <img onClick={() => onClickDelete(el.id)} src="./closeBtn.svg" alt="close" />
                      </td>
                    </tr>
                  ))
              : currentItems.map((el, i) => (
                  <tr className={`${styles.tableRowItems}`} key={el.id}>
                    {/* ПОМЕНЯТЬ НА userID */}
                    <td className={styles.tableCell}>{el?.name}</td>
                    <td className={styles.tableCell}>{el?.value}</td>
                    <td className={styles.tableCell}>
                      <input
                        type="checkbox"
                        className={`checkbox ${styles.checkboxUrlTable}`}
                        id={`checkboxmask${el.id}`}
                        defaultChecked={el.masking ? "true" : ""}
                      />
                      <label htmlFor={`checkboxmask${el.id}`}></label>
                    </td>
                    <td className={styles.tableCell}>
                      <input
                        type="checkbox"
                        className={`checkbox ${styles.checkboxUrlTable}`}
                        id={`checkbox${el.id}`}
                        defaultChecked={el.filter ? "true" : ""}
                      />
                      <label htmlFor={`checkbox${el.id}`}></label>
                    </td>
                    <td className={styles.tableCell}>
                      <input
                        type="checkbox"
                        className={`checkbox ${styles.checkboxUrlTable}`}
                        id={`checkboxCP${el.id}`}
                        defaultChecked={el.removingCP ? "true" : ""}
                      />
                      <label htmlFor={`checkboxCP${el.id}`}></label>
                    </td>
                    <td className={`${styles.tableCell} ${styles.urlDelete}`}>
                      <img
                        onClick={() => onClickDelete(el.id)}
                        src="./closeBtn.svg"
                        alt="close"
                      />
                    </td>
                  </tr>
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
      {data[0]?.userID !== undefined && (
        <TableFooter pageCount={pageCount} handlePageClick={handlePageClick} />
      )}
    </>
  );
};

export default Table;
